package mostafa.hafezypoor.shikshap.ui.cart;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelOrderInCart;
import mostafa.hafezypoor.shikshap.ui.product.ProductViewModel;

public class AdapterFOrderInCart extends RecyclerView.Adapter<AdapterFOrderInCart.ViewHolder> {
    private Context context;
    private List<ModelOrderInCart>modelOrderInCarts;
    private  LifecycleOwner lifecycleOwner;
    private CartViewModel cartViewModel;
    public AdapterFOrderInCart(Context context, List<ModelOrderInCart> modelOrderInCarts, LifecycleOwner lifecycleOwner,IEvent iEvent) {
        this.context = context;
        this.modelOrderInCarts = modelOrderInCarts;
        this.lifecycleOwner = lifecycleOwner;
        cartViewModel=new ViewModelProvider((ViewModelStoreOwner) lifecycleOwner).get(CartViewModel.class);
        this.iEvent=iEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_forder_in_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(modelOrderInCarts.get(position).getProduct_image()).into(holder.imgProduct);
        holder.product_name.setText(modelOrderInCarts.get(position).getProduct_name());
        DecimalFormat decimalFormat=new DecimalFormat("#,###");
        String price=decimalFormat.format(Integer.parseInt(modelOrderInCarts.get(position).getProduct_price()));
        holder.product_price.setText(price+" تومان ");
        holder.product_size.setText(" سایز : "+modelOrderInCarts.get(position).getSize());
        holder.count.setText(modelOrderInCarts.get(position).getCount());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String title="آیا میخواهید"+modelOrderInCarts.get(position).getProduct_name()+" از سبد خرید خود حذف کنید؟ ";
                    deleteOrderInCart(context,title,modelOrderInCarts.get(position).getProduct_id(),position);

            }
        });
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              cartViewModel.addCart(context.getSharedPreferences("save",MODE_PRIVATE).getString("token","null"),modelOrderInCarts.get(position).getProduct_id(),modelOrderInCarts.get(position).getSize()).observe(lifecycleOwner, new Observer<String>() {
                  @Override
                  public void onChanged(String s) {
                      if (s.equals("200")){
                          iEvent.addCart();
                          int count=Integer.parseInt(holder.count.getText().toString().trim())+1;
                          holder.count.setText(count+"");
                      }
                  }
              });
            }
        });
        holder.imgDecreazeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=Integer.parseInt(holder.count.getText().toString().trim())-1;
                if (count==0){
                    String title="آیا میخواهید"+modelOrderInCarts.get(position).getProduct_name()+" از سبد خرید خود حذف کنید؟ ";
                    deleteOrderInCart(context,title,modelOrderInCarts.get(position).getProduct_id(),position);
                }else{
                    cartViewModel.decreazeCart(context.getSharedPreferences("save",MODE_PRIVATE).getString("token","null"),modelOrderInCarts.get(position).getProduct_id(),modelOrderInCarts.get(position).getSize()).observe(lifecycleOwner, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("200")){
                                holder.count.setText(count+"");
                                iEvent.decreazeCart(modelOrderInCarts.size());
                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelOrderInCarts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct,imgAdd,imgDecreazeCart,imgDelete;
        TextView product_name,product_price,count,product_size;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            product_name=itemView.findViewById(R.id.product_name);
            product_price=itemView.findViewById(R.id.product_price);
            count=itemView.findViewById(R.id.count);
            imgAdd=itemView.findViewById(R.id.imgAdd);
            imgDelete=itemView.findViewById(R.id.imgDelete);
            imgDecreazeCart=itemView.findViewById(R.id.imgDecreazeCart);
            product_size=itemView.findViewById(R.id.product_size);
        }
    }
    public interface  IEvent{
        public void addCart();
        public void decreazeCart(int size);
    }
    private IEvent iEvent;
    private void deleteOrderInCart(Context context,String title,String product_id,int position){
        BottomSheetDialog dialog=new BottomSheetDialog(context,R.style.AppBottomSheetDialogTheme);
        dialog.setContentView(R.layout.ddelete_order_in_cart);
        dialog.show();
        ((TextView)dialog.findViewById(R.id.title)).setText(title);
        ((MaterialButton)dialog.findViewById(R.id.btnDismiss)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              dialog.dismiss();
            }
        });
        ((MaterialButton)dialog.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartViewModel.deleteCart(context.getSharedPreferences("save",MODE_PRIVATE).getString("token","null"),product_id,modelOrderInCarts.get(position).getSize()).observe(lifecycleOwner, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if (s.equals("200")){
                            modelOrderInCarts.remove(position);
                            notifyDataSetChanged();
                            dialog.dismiss();
                            iEvent.decreazeCart(modelOrderInCarts.size());
                        }
                    }
                });
            }
        });
    }
}
