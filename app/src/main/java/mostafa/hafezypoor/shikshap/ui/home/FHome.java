package mostafa.hafezypoor.shikshap.ui.home;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.FHomeModelGroup;
import mostafa.hafezypoor.shikshap.data.model.FHomeModelProduct;

public class FHome extends Fragment {
    private HomeViewModel viewModel;
    private RecyclerView list;
    private CardView cardViewSearch,imgMic;
    private  AdapterFHomeGroups adapterFHomeGroups;
    ActivityResultLauncher<Intent>speechLauncher;
    private Activity activity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=getActivity();
        speechLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode()== Activity.RESULT_OK&&o.getData()!=null){
                    ArrayList<String>matches=o.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (matches!=null&&!matches.isEmpty()){
                        String text=matches.get(0);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FSearch(text),"floading").commit();
                    }
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fhome,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=view.findViewById(R.id.list);
        imgMic=view.findViewById(R.id.imgMic);
        cardViewSearch=view.findViewById(R.id.cardViewSearch);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new AdapterLoadingList(getContext()));
        viewModel=new ViewModelProvider((ViewModelStoreOwner) activity).get(HomeViewModel.class);
        viewModel.getTopShow().observe(getActivity(), new Observer<List<FHomeModelProduct>>() {
            @Override
            public void onChanged(List<FHomeModelProduct> fHomeModelProducts) {
                viewModel.getGroups().observe((LifecycleOwner) activity, new Observer<List<FHomeModelGroup>>() {
                    @Override
                    public void onChanged(List<FHomeModelGroup> fHomeModelGroups) {
                        adapterFHomeGroups =new AdapterFHomeGroups(getActivity(), fHomeModelGroups,fHomeModelProducts, new AdapterFHomeGroups.IEvent() {
                            @Override
                            public void showAllProductGroup(String group_id,String group_name) {
                                Intent intent=new Intent(getActivity(),ShowAllProductInGroup.class);
                                intent.putExtra("group_id",group_id);
                                intent.putExtra("group_name",group_name);
                                startActivity(intent);
                            }

                            @Override
                            public void showProducts(String group_id, RecyclerView listProduct) {
                                viewModel.getProducts(group_id).observe(getActivity(), new Observer<List<FHomeModelProduct>>() {
                                    @Override
                                    public void onChanged(List<FHomeModelProduct> fHomeModelProducts) {
                                        listProduct.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                                        listProduct.setAdapter(new AdapterShowProductInGroup(getActivity(),fHomeModelProducts));
                                    }
                                });
                            }

                        });
                        list.setLayoutManager(new LinearLayoutManager(getContext()));
                        list.setAdapter(adapterFHomeGroups);
                        imgMic.setVisibility(VISIBLE);
                        cardViewSearch.setVisibility(VISIBLE);

                    }
                });
            }
        });

        cardViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FSearch(" "),"floading").commit();
            }
        });
        imgMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceInputSearch();
            }
        });
    }
    private void startVoiceInputSearch(){

        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"fa-IR");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"نام محصول رو بگو");
        try {
            speechLauncher.launch(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getActivity(), "دستگاه شما از دستیار صوتی پشتیبانی نمی کند", Toast.LENGTH_SHORT).show();
        }
    }
}
