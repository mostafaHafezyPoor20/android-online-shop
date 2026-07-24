package mostafa.hafezypoor.shikshap.ui.product;

import static android.content.Context.MODE_PRIVATE;
import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;

public class AdapterEmptyComment extends RecyclerView.Adapter<AdapterEmptyComment.ViewHolder> {
    private Context context;
    private String product_name;
   private LifecycleOwner lifecycleOwner;
   private ProductViewModel productViewModel;
  private String product_id;
    public AdapterEmptyComment(Context context, String product_name, LifecycleOwner lifecycleOwner,String product_id) {
        this.context = context;
        this.product_name = product_name;
        this.lifecycleOwner = lifecycleOwner;
        this.product_id=product_id;
        productViewModel=new ViewModelProvider((ViewModelStoreOwner) context).get(ProductViewModel.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_empty_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialogAddComment=new BottomSheetDialog(context);
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

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        MaterialButton btnAddComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnAddComment=itemView.findViewById(R.id.btnAddComment);
        }
    }
    private void vibration(int duration){
        Vibrator vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            vibrator.vibrate(VibrationEffect.createOneShot(duration,DEFAULT_AMPLITUDE));
        }
    }
    private void addComment(String comment,String product_id,BottomSheetDialog dialogAddComment){
        String token=context.getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
        productViewModel.addComment(token,comment,product_id).observe(lifecycleOwner, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("200")){
                    dialogAddComment.dismiss();
                    BottomSheetDialog dialogSuccess=new BottomSheetDialog(context);
                    dialogSuccess.setContentView(R.layout.dsuccess);
                    ((TextView)dialogSuccess.findViewById(R.id.textTitle)).setText("نظر شما ثبت شد پس از بازبینی و تایید منتشر خواهد شد");
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
}
