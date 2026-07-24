package mostafa.hafezypoor.shikshap.ui.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelSizes;

public class AdapterDShowSizesProduct extends RecyclerView.Adapter<AdapterDShowSizesProduct.ViewHolder>{
    private Context context;
    private List<ModelSizes>list;

    public AdapterDShowSizesProduct(Context context, List<ModelSizes> list,IEvent iEvent) {
        this.context = context;
        this.list = list;
        this.iEvent=iEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_dshow_sizes_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textSize.setText(" سایز :  "+list.get(position).getSize());

        if (list.get(position).isSelected()){
            holder.textSize.setTextColor(context.getColor(R.color.white));
            holder.card.setCardBackgroundColor(context.getColor(R.color.green));
            holder.card.setStrokeColor(context.getColor(R.color.white));
        }else{
            holder.textSize.setTextColor(context.getColor(R.color.black));
            holder.card.setCardBackgroundColor(context.getColor(R.color.white));
            holder.card.setStrokeColor(context.getColor(R.color.black));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).isSelected()){
                    list.get(position).setSelected(false);
                }else{
                    list.get(position).setSelected(true);
                }

                notifyItemChanged(position);
                int countSelected=0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelected()){
                        countSelected++;
                    }
                }
                iEvent.selectedItems(countSelected);
            }
        });
        if (list.get(position).getInCart().equals("true")){
            holder.textSize.setTextColor(context.getColor(R.color.black));
            holder.card.setCardBackgroundColor(context.getColor(R.color.white));
            holder.card.setStrokeColor(context.getColor(R.color.white));
            holder.card.setStrokeWidth(0);
            holder.itemView.setClickable(false);
            holder.textSize.append(" ( قبلا به سبد خرید اضافه شده )");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textSize;
        MaterialCardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textSize=itemView.findViewById(R.id.textSize);
            card=itemView.findViewById(R.id.card);
        }
    }
    private IEvent iEvent;
    public interface IEvent{
        void selectedItems(int itemSelected);
    }
    public List<ModelSizes>getSelectedSizes(){
        List<ModelSizes>sizes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()){
                sizes.add(list.get(i));
            }
        }
        return sizes;
    }
}
