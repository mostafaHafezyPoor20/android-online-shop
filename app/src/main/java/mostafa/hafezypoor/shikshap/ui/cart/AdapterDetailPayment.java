package mostafa.hafezypoor.shikshap.ui.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelGetDetailPayment;

public class AdapterDetailPayment extends RecyclerView.Adapter<AdapterDetailPayment.ViewHolder>{
    private Context context;
    private List<ModelGetDetailPayment>list;

    public AdapterDetailPayment(Context context, List<ModelGetDetailPayment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_detail_payment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.product_name.setText(list.get(position).getProduct_name());
        DecimalFormat decimalFormat=new DecimalFormat("#,###");
         String price=decimalFormat.format(Integer.parseInt(list.get(position).getProduct_price()));
         holder.product_price.setText(price+" تومان ");
         String count=" تعداد سفارش :  "+list.get(position).getCount();
         holder.product_count.setText(count);
         holder.product_size.setText(" سایز : "+list.get(position).getSize());
        Picasso.get().load(list.get(position).getProduct_image()).into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView product_name,product_price,product_count,product_size;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            product_name=itemView.findViewById(R.id.product_name);
            product_price=itemView.findViewById(R.id.product_price);
            product_count=itemView.findViewById(R.id.product_count);
            product_size=itemView.findViewById(R.id.product_size);
        }
    }
}
