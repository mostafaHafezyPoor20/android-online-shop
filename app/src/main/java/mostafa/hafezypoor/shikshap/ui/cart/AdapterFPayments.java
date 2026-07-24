package mostafa.hafezypoor.shikshap.ui.cart;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelGetDetailPayment;
import mostafa.hafezypoor.shikshap.data.model.ModelGetImagesPayments;
import mostafa.hafezypoor.shikshap.data.model.ModelGetPayments;

public class AdapterFPayments extends RecyclerView.Adapter<AdapterFPayments.ViewHolder> {
    private Context context;
    private List<ModelGetPayments>list;
    private CartViewModel cartViewModel;
    private String payment_id;
    public AdapterFPayments(Context context, List<ModelGetPayments> list, LifecycleOwner lifecycleOwner,String payment_id) {
        this.context = context;
        this.list = list;
        this.payment_id=payment_id;
        cartViewModel=new ViewModelProvider((ViewModelStoreOwner) context).get(CartViewModel.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_fpayments,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat decimalFormat=new DecimalFormat("#,###");
        String price=decimalFormat.format(Integer.parseInt(list.get(position).getAmount()));
         holder.amount.setText(price+" تومان ");
         holder.ref_id.setText(list.get(position).getRef_id());
         if (list.get(position).getStatus().equals("sended")){
             holder.status.setText("ارسال شده");
         }else if (list.get(position).getStatus().equals("pending")){
             holder.status.setText("در حال آماده سازی سفارش برای ارسال");
         }
         if (payment_id!=null){
             if (list.get(position).getId().equals(payment_id)){
                 animationItem(holder.itemView);
             }
         }
     getImagesPayment(list.get(position).getId(),holder.listImages);
         holder.btnShowDetails.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
              showDetailsPayment(list.get(position));
             }
         });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView amount,ref_id,status;
        RecyclerView listImages;
        MaterialButton btnShowDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amount=itemView.findViewById(R.id.amount);
            ref_id=itemView.findViewById(R.id.ref_id);
            status=itemView.findViewById(R.id.status);
            listImages=itemView.findViewById(R.id.listImages);
            btnShowDetails=itemView.findViewById(R.id.btnShowDetails);
        }
    }
    private void getImagesPayment(String payment_id,RecyclerView listImages){
        String token=context.getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
        cartViewModel.getImagesPayments(token,payment_id).observe((LifecycleOwner) context, new Observer<List<ModelGetImagesPayments>>() {
            @Override
            public void onChanged(List<ModelGetImagesPayments> modelGetImagesPayments) {
              listImages.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
              listImages.setAdapter(new AdapterListImagesPayment(context,modelGetImagesPayments));
            }
        });
    }
    private void showDetailsPayment(ModelGetPayments modelGetPayments){
        BottomSheetDialog dialogDetails=new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        dialogDetails.setContentView(R.layout.dshow_details);
        dialogDetails.show();
        ((TextView)dialogDetails.findViewById(R.id.name)).setText(modelGetPayments.getName());
        ((TextView)dialogDetails.findViewById(R.id.phoneNumber)).setText(modelGetPayments.getPhoneNumber());
        DecimalFormat decimalFormat=new DecimalFormat("#,###");
        String amount=decimalFormat.format(Integer.parseInt(modelGetPayments.getAmount()));
        ((TextView)dialogDetails.findViewById(R.id.amount)).setText(amount+" تومان ");
        ((TextView)dialogDetails.findViewById(R.id.ref_id)).setText(modelGetPayments.getRef_id());
        if (modelGetPayments.getStatus().equals("sended")){
            ((LinearLayout)dialogDetails.findViewById(R.id.linearTrackingCode)).setVisibility(VISIBLE);
            ((TextView)dialogDetails.findViewById(R.id.status)).setText("ارسال شده");
            ((TextView)dialogDetails.findViewById(R.id.trackingCode)).setText(modelGetPayments.getTrackingPost());
        }else if (modelGetPayments.getStatus().equals("pending")){
            ((TextView)dialogDetails.findViewById(R.id.status)).setText("سفارش شما درحال آماده سازی برای ارسال میباشد");
            ((LinearLayout)dialogDetails.findViewById(R.id.linearTrackingCode)).setVisibility(GONE);
        }
        ((TextView)dialogDetails.findViewById(R.id.address)).setText(modelGetPayments.getAddress());
        ((MaterialButton)dialogDetails.findViewById(R.id.btnDismiss)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDetails.dismiss();
            }
        });
          cartViewModel.getDetailPayment(context.getSharedPreferences("save",MODE_PRIVATE).getString("token","null"),modelGetPayments.getId()).observe((LifecycleOwner) context, new Observer<List<ModelGetDetailPayment>>() {
              @Override
              public void onChanged(List<ModelGetDetailPayment> modelGetDetailPayments) {
                  ((RecyclerView)dialogDetails.findViewById(R.id.listProduct)).setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                  ((RecyclerView)dialogDetails.findViewById(R.id.listProduct)).setAdapter(new AdapterDetailPayment(context,modelGetDetailPayments));
              }
          });
    }
public int findPayment(String payment_id){
        int position=0;
    for (int i = 0; i < list.size(); i++) {
        if (list.get(i).getId().equals(payment_id)){
            position=i;
            break;
        }
    }
    return position;
}
    public void animationItem(View itemView){
        ObjectAnimator scaleUpx=ObjectAnimator.ofFloat(itemView,"scaleX",1f,1.08f);
        ObjectAnimator scaleUpY=ObjectAnimator.ofFloat(itemView,"scaleY",1f,1.08f);
        scaleUpx.setDuration(300);
        scaleUpY.setDuration(300);

        ObjectAnimator shakeX=ObjectAnimator.ofFloat(itemView,"translationX",0,15,-15,10,-10,5,-5,0);
        shakeX.setDuration(600);

        ObjectAnimator scaleDownX=ObjectAnimator.ofFloat(itemView,"scaleX",1.08f,1f);
        ObjectAnimator scaleDownY=ObjectAnimator.ofFloat(itemView,"scaleY",1.08f,1f);
        scaleDownX.setDuration(300);
        scaleDownY.setDuration(300);

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(scaleUpx).with(scaleUpY);
        animatorSet.play(shakeX).with(scaleUpx);
        animatorSet.play(scaleDownX).with(scaleDownY).after(shakeX);
        animatorSet.start();


    }
}
