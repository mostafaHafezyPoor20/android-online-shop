package mostafa.hafezypoor.shikshap.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
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

public class AdapterShowProductInGroup extends RecyclerView.Adapter<AdapterShowProductInGroup.ViewHolder>{
    private Context context;
    private List<FHomeModelProduct> list;

    public AdapterShowProductInGroup(Context context, List<FHomeModelProduct> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_show_product_in_grorup,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getProduct_image()).into(holder.imageProduct);
        holder.nameProduct.setText(list.get(position).getProduct_name());
        DecimalFormat format=new DecimalFormat("#,###");
        String price=format.format(Integer.parseInt(list.get(position).getProduct_price()));
        holder.priceProduct.setText(price+" تومان ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Product.class);
                Bundle bundle=new Bundle();
                bundle.putString("product_id",list.get(position).getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageProduct;
        TextView nameProduct,priceProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct=itemView.findViewById(R.id.imageProduct);
            nameProduct=itemView.findViewById(R.id.nameProduct);
            priceProduct=itemView.findViewById(R.id.priceProduct);
        }
    }
}
