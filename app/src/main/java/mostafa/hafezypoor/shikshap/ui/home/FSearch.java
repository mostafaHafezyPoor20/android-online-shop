package mostafa.hafezypoor.shikshap.ui.home;

import static android.view.View.VISIBLE;

import android.animation.Animator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;
import mostafa.hafezypoor.shikshap.ui.main.MainFragment;

public class FSearch extends Fragment {
    private TextInputEditText edtSearch;
    private String searchText=" ";
    private HomeViewModel homeViewModel;
    private RecyclerView list;
   private ActivityResultLauncher<Intent> speechLauncher;
    public FSearch(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speechLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode()== Activity.RESULT_OK&&o.getData()!=null){
                    ArrayList<String> matches=o.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
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
        return LayoutInflater.from(getContext()).inflate(R.layout.fsearch,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel=new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        edtSearch=view.findViewById(R.id.edtSearch);
        list=view.findViewById(R.id.list);
        if (searchText.equals(" ")){
            edtSearch.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(edtSearch,InputMethodManager.SHOW_IMPLICIT);
        }
        if (!searchText.trim().isEmpty()){
            edtSearch.setText(searchText);
            setEdtSearch(searchText);
        }
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              setEdtSearch(charSequence.toString().toString().trim());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
        ((ImageView)view.findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new MainFragment(new FHome()),"floading").commit();

            }
        });
        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new MainFragment(new FHome()),"floading").commit();
            }
        });
        ((ImageView)view.findViewById(R.id.imgMic)).setOnClickListener(new View.OnClickListener() {
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
    private void setEdtSearch(String search){
        homeViewModel.search(search).observe(getActivity(), new Observer<List<ModelDetailProduct>>() {
            @Override
            public void onChanged(List<ModelDetailProduct> modelDetailProducts) {
                if (modelDetailProducts.isEmpty()){
                    list.setLayoutManager(new LinearLayoutManager(getContext()));
                    String title=search+" رو در شیک شاپ پیدا نکردم";
                    list.setAdapter(new AdapterFSearchNotFound(getContext(),title));
                }else{
                    list.setLayoutManager(new LinearLayoutManager(getContext()));
                    list.setAdapter(new AdapterFSearch(getActivity(),modelDetailProducts));
                }
            }
        });
    }
}
