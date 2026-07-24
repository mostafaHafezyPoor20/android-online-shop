package mostafa.hafezypoor.shikshap.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelChat;
import mostafa.hafezypoor.shikshap.ui.home.ShowAllProductInGroup;
import mostafa.hafezypoor.shikshap.ui.product.Product;

public class AdapterChat extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int TYPE_MESSAGE_USER=0;
    private final int TYPE_MESSAGE_ADMIN=1;
    private final int TYPE_MESSAGE_ADMIN_GROUP=2;
    private final int TYPE_MESSAGE_ADMIN_PRODUCT=3;

    public AdapterChat(Context context, List<ModelChat> list,IEvent iEvent) {
        this.context = context;
        this.list = list;
        this.iEvent=iEvent;
    }

    private Context context;
    private List<ModelChat>list;

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType().equals("message")){
            if (list.get(position).getSender().trim().equals("user")){
                return TYPE_MESSAGE_USER;
            }else if (list.get(position).getSender().trim().equals("admin")){
                return TYPE_MESSAGE_ADMIN;
            }
        }else if (list.get(position).getType().equals("group")){
            return TYPE_MESSAGE_ADMIN_GROUP;
        }else if (list.get(position).getType().equals("product")){
            return TYPE_MESSAGE_ADMIN_PRODUCT;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_MESSAGE_USER) {
           return new ViewHolderMessageUser(LayoutInflater.from(context).inflate(R.layout.view_holder_message_user,parent,false));
        }else if (viewType==TYPE_MESSAGE_ADMIN){
            return new ViewHolderMessageAdmin(LayoutInflater.from(context).inflate(R.layout.view_holder_message_admin,parent,false));
        }else if (viewType==TYPE_MESSAGE_ADMIN_GROUP){
            return new ViewHolderMessageAdminGroup(LayoutInflater.from(context).inflate(R.layout.view_holder_message_admin_group,parent,false));
        }else if (viewType==TYPE_MESSAGE_ADMIN_PRODUCT){
            return new ViewHolderMessageAdminProduct(LayoutInflater.from(context).inflate(R.layout.view_holder_message_admin_product,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
           if (holder instanceof ViewHolderMessageUser){
               ViewHolderMessageUser h= (ViewHolderMessageUser) holder;
               h.message.setText(list.get(position).getMessage().replace("\\n"," ").replace("n\\"," "));
           }else if (holder instanceof ViewHolderMessageAdmin){
               ViewHolderMessageAdmin h= (ViewHolderMessageAdmin) holder;
               h.message.setText(list.get(position).getMessage().replace("\\n"," ").replace("n\\"," "));
           }else if (holder instanceof ViewHolderMessageAdminGroup){
               ViewHolderMessageAdminGroup h= (ViewHolderMessageAdminGroup) holder;
               h.message.setText(list.get(position).getMessage());
               h.showGroup.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent=new Intent(context, ShowAllProductInGroup.class);
                       intent.putExtra("group_id",list.get(position).getType_value());
                       intent.putExtra("group_name",list.get(position).getMessage());
                       context.startActivity(intent);
                   }
               });
           }else if (holder instanceof ViewHolderMessageAdminProduct){
               ViewHolderMessageAdminProduct h= (ViewHolderMessageAdminProduct) holder;
               iEvent.getDetailMessageProduct(list.get(position).getType_value(),h.imageProduct,h.product_price,h.product_name);
               h.btnShowProduct.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent=new Intent(context, Product.class);
                       intent.putExtra("product_id",list.get(position).getType_value());
                       context.startActivity(intent);
                   }
               });

           }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolderMessageUser extends RecyclerView.ViewHolder{
        TextView message;
        public ViewHolderMessageUser(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message);
        }
    }
    class ViewHolderMessageAdmin extends RecyclerView.ViewHolder{
        TextView message;
        public ViewHolderMessageAdmin(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message);
        }
    }
    class ViewHolderMessageAdminGroup extends RecyclerView.ViewHolder{
        TextView message;
        MaterialButton showGroup;
        public ViewHolderMessageAdminGroup(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message);
            showGroup=itemView.findViewById(R.id.showGroup);
        }
    }
    class ViewHolderMessageAdminProduct extends RecyclerView.ViewHolder{
        ImageView imageProduct;
        TextView product_name,product_price;
        MaterialButton btnShowProduct;
        public ViewHolderMessageAdminProduct(@NonNull View itemView) {
            super(itemView);
            imageProduct=itemView.findViewById(R.id.imageProduct);
            product_name=itemView.findViewById(R.id.product_name);
            product_price=itemView.findViewById(R.id.product_price);
            btnShowProduct=itemView.findViewById(R.id.btnShowProduct);
        }
    }
    private IEvent iEvent;
    public interface IEvent{
          void newMessage(String last_id);
          void getDetailMessageProduct(String product_id,ImageView imageProduct,TextView product_price,TextView product_name);
    }
    public void addNewMessage(ModelChat modelChat){
            list.add(modelChat);
            iEvent.newMessage(modelChat.getId());
            notifyItemInserted(list.size()-1);
    }
    public int sizeList(){
        return list.size();
    }
}
