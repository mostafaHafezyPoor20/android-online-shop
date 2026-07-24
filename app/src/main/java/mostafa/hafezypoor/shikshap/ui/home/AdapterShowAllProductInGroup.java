package mostafa.hafezypoor.shikshap.ui.home;

import android.content.Context;
import android.content.Intent;
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
import mostafa.hafezypoor.shikshap.data.model.FHomeModelProduct;
import mostafa.hafezypoor.shikshap.ui.product.Product;

public class AdapterShowAllProductInGroup extends RecyclerView.Adapter<AdapterShowAllProductInGroup.ViewHolder> {
    private Context context;
    private List<FHomeModelProduct>list;

    public AdapterShowAllProductInGroup(Context context, List<FHomeModelProduct> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_show_all_product_in_group,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getProduct_image()).into(holder.imgProduct);
        holder.product_name.setText(list.get(position).getProduct_name());
        String price=new DecimalFormat("#,###").format(Integer.parseInt(list.get(position).getProduct_price()))+" تومان ";
        holder.product_price.setText(price);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Product.class);
                intent.putExtra("product_id",list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView product_name,product_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            product_name=itemView.findViewById(R.id.product_name);
            product_price=itemView.findViewById(R.id.product_price);
        }
    }
}
