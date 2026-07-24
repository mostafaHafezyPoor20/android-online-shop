package mostafa.hafezypoor.shikshap.ui.product;

import static android.content.Context.MODE_PRIVATE;
import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelComments;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;
import mostafa.hafezypoor.shikshap.ui.common.AdapterRequireLogin;
import mostafa.hafezypoor.shikshap.ui.common.BottomSheetDialogLoading;

public class FProductComments extends Fragment {
    private String product_id;
    private String product_name;
    private String comment_id;
    private ProductViewModel productViewModel;
    private RecyclerView list;
    private ExtendedFloatingActionButton extendedFloatingActionButton;
    private Activity activity;

    public FProductComments(String product_id, String product_name,String comment_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.comment_id=comment_id;
        activity=getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fproduct_comments,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=view.findViewById(R.id.list);
        extendedFloatingActionButton=view.findViewById(R.id.fabAddComment);
        productViewModel=new ViewModelProvider(getActivity()).get(ProductViewModel.class);
        getComments(product_id,product_name);
    }

    private void getComments(String product_id,String product_name){
        productViewModel.getCommentsProduct(product_id).observe(getActivity(), new Observer<List<ModelComments>>() {
            @Override
            public void onChanged(List<ModelComments> modelComments) {
                if (modelComments.isEmpty()){
                  list.setLayoutManager(new LinearLayoutManager(getActivity()));
                  list.setAdapter(new AdapterEmptyComment(getActivity(),product_name,getActivity(),product_id));
                  extendedFloatingActionButton.setVisibility(GONE);
                }else{
                    extendedFloatingActionButton.shrink();
                    extendedFloatingActionButton.setVisibility(VISIBLE);
                    Handler handler=new Handler();
                    Runnable runnable=new Runnable() {
                        @Override
                        public void run() {
                                 if (extendedFloatingActionButton.isExtended()){
                                     extendedFloatingActionButton.shrink();
                                 }else{
                                     extendedFloatingActionButton.extend();
                                 }
                                 handler.postDelayed(this,5000);
                        }
                    };
                    handler.postDelayed(runnable,5000);
                    AdapterFProductComments adapterFProductComments=new AdapterFProductComments(getActivity(),modelComments,null);
                    if (comment_id!=null){
                      adapterFProductComments=new AdapterFProductComments(getActivity(),modelComments,comment_id);
                    }
                    list.setLayoutManager(new LinearLayoutManager(getActivity()));
                    list.setAdapter(adapterFProductComments);
                    if (comment_id!=null){
                        list.scrollToPosition(adapterFProductComments.findPositionComment(comment_id));
                    }
                    list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            LinearLayoutManager layoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
                            int totalItemCount=layoutManager.getItemCount();
                            int lastVisibleItem=layoutManager.findLastVisibleItemPosition();
                            if (totalItemCount!=1&&lastVisibleItem==totalItemCount-1){
                                Handler handler=new Handler(Looper.getMainLooper());
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                     extendedFloatingActionButton.post(new Runnable() {
                                         @Override
                                         public void run() {
                                             boolean show=false;
                                             if (show){
                                                 extendedFloatingActionButton.animate().translationY(extendedFloatingActionButton.getHeight()+40).setDuration(300);
                                               show=false;
                                             }else{
                                                 extendedFloatingActionButton.animate().translationY(0).setDuration(300);
                                                 show=true;
                                             }
                                         }
                                     });
                                    }
                                },3000);

                            }else{
                                extendedFloatingActionButton.animate().translationY(0).setDuration(300);
                            }
                        }
                    });
                }
                extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BottomSheetDialog dialogAddComment=new BottomSheetDialog(getActivity());
                        dialogAddComment.setContentView(R.layout.dadd_comment);
                        ((TextView)dialogAddComment.findViewById(R.id.title)).setText(" نوشتن نظر برای "+product_name);
                        ((MaterialButton)dialogAddComment.findViewById(R.id.btnBack)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogAddComment.dismiss();
                            }
                        });
                        TextInputEditText comment=dialogAddComment.findViewById(R.id.comment);
                        comment.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void afterTextChanged(Editable editable) {

                            }

                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                ((TextView)dialogAddComment.findViewById(R.id.textError)).setVisibility(GONE);
                            }
                        });
                        ((MaterialButton)dialogAddComment.findViewById(R.id.sendComment)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (comment.getText().toString().trim().isEmpty()){
                                    ((TextView)dialogAddComment.findViewById(R.id.textError)).setVisibility(VISIBLE);
                                    vibration(300);
                                }else{
                                    addComment(comment.getText().toString().trim(),product_id,dialogAddComment);
                                }
                            }
                        });
                        dialogAddComment.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        dialogAddComment.show();
                    }
                });
            }
        });
    }
    private void addComment(String comment, String product_id, BottomSheetDialog dialogAddComment){
        String token=getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
        BottomSheetDialogLoading loading=new BottomSheetDialogLoading(getActivity(),"درحال ارسال نظر");
        loading.show();
        dialogAddComment.dismiss();
        productViewModel.addComment(token,comment,product_id).observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("200")){
                   loading.dismiss();
                    BottomSheetDialog dialogSuccess=new BottomSheetDialog(getActivity());
                    dialogSuccess.setContentView(R.layout.dsuccess);
                    ((TextView)dialogSuccess.findViewById(R.id.textTitle)).setText("نظر شما با موفقیت دریافت شد . پس از برسی تیم پشتیبانی منتشر خواهد شد");
                    dialogSuccess.show();
                    ((MaterialButton)dialogSuccess.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogSuccess.dismiss();
                        }
                    });
                }
            }
        });
    }
    private void vibration(int duration){
        Vibrator vibrator= (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            vibrator.vibrate(VibrationEffect.createOneShot(duration,DEFAULT_AMPLITUDE));
        }
    }
}
