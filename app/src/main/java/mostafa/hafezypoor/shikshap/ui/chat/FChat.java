package mostafa.hafezypoor.shikshap.ui.chat;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelChat;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;
import mostafa.hafezypoor.shikshap.data.network.RetrofitInit;
import mostafa.hafezypoor.shikshap.ui.common.AdapterRequireLogin;
import mostafa.hafezypoor.shikshap.ui.common.BottomSheetDialogLoading;
import mostafa.hafezypoor.shikshap.utils.Constants;

public class FChat extends Fragment {
    private ChatViewModel chatViewModel;
    private RecyclerView list;
    private AdapterChat adapterChat;
    private BottomSheetDialogLoading bottomSheetDialogLoading;
    private Activity activity;
    String token;
    private  TextInputEditText edtMessage;
    private MaterialCardView  bottomRelativeLayout;
    ImageView imageSend;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fchat,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity=getActivity();
        token=activity.getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
        list=view.findViewById(R.id.list);
        edtMessage=view.findViewById(R.id.edtMessage);
        imageSend=view.findViewById(R.id.imageSend);
        bottomRelativeLayout=view.findViewById(R.id.bottomRelativeLayout);
        list.setLayoutManager(new LinearLayoutManager(activity));
        list.setAdapter(new AdapterChatLoading(activity));
        bottomSheetDialogLoading=new BottomSheetDialogLoading(activity,"درحال ارسال پیام");
        Picasso.get().load(Constants.BASE_URL+"admin.jpg").error(R.drawable.icon).into((ImageView)view.findViewById(R.id.imageSupportCollape));
        ((MaterialButton)view.findViewById(R.id.callBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel","09159342537",null));
                startActivity(intent);
            }
        });
        chatViewModel=new ViewModelProvider(getActivity()).get(ChatViewModel.class);
        chatViewModel.chekcToken(getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null")).observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("ok")){
                    getChat();
                    sendMessage();
                    ((LinearLayout)view.findViewById(R.id.top)).setVisibility(VISIBLE);
                    bottomRelativeLayout.setVisibility(VISIBLE);
                    ((View)view.findViewById(R.id.v)).setVisibility(VISIBLE);
                }else if (s.equals("error")){
                    ((LinearLayout)view.findViewById(R.id.top)).setVisibility(GONE);
                    bottomRelativeLayout.setVisibility(GONE);
                    ((View)view.findViewById(R.id.v)).setVisibility(GONE);
                    list.setLayoutManager(new LinearLayoutManager(getContext()));
                    list.setAdapter(new AdapterRequireLogin(getContext(), "میخوای با پشتیبانی صحبت کنی؟ اول وارد حساب کاربریت شو", getActivity(), new AdapterRequireLogin.IEvent() {
                        @Override
                        public void loginOrRegisterd() {
                            token=activity.getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
                            ((LinearLayout)view.findViewById(R.id.top)).setVisibility(VISIBLE);
                            bottomRelativeLayout.setVisibility(VISIBLE);
                            ((View)view.findViewById(R.id.v)).setVisibility(VISIBLE);
                            getChat();
                            sendMessage();
                        }
                    }));
                }
            }
        });
    }
    private void getChat(){
        chatViewModel.getChat(activity.getSharedPreferences("save",MODE_PRIVATE).getString("token","null")).observe((LifecycleOwner) activity, new Observer<List<ModelChat>>() {
            @Override
            public void onChanged(List<ModelChat> modelChats) {
                adapterChat=new AdapterChat(getContext(), modelChats, new AdapterChat.IEvent() {
                    @Override
                    public void newMessage(String last_id) {
                        // longPollingNewMessage(last_id);
                    }

                    @Override
                    public void getDetailMessageProduct(String product_id,ImageView imageProduct, TextView product_price, TextView product_name) {
                             getDetailProductInChat(product_id,imageProduct,product_price,product_name);
                    }
                });
                if (modelChats.isEmpty()){
                    longPollingNewMessage("null",adapterChat);
                    list.setLayoutManager(new LinearLayoutManager(getContext()));
                    list.setAdapter(new AdapterEmptyChat(getContext()));
                }else{
                    longPollingNewMessage(modelChats.get(modelChats.size()-1).getId(),adapterChat);
                    list.setLayoutManager(new LinearLayoutManager(getContext()));
                    list.setAdapter(adapterChat);
                    list.scrollToPosition(adapterChat.sizeList()-1);
                }
            }
        });

    }
    private void sendMessage(){
        String token=activity.getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
       edtMessage.addTextChangedListener(new TextWatcher() {
           @Override
           public void afterTextChanged(Editable editable) {

           }

           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              if (!charSequence.toString().trim().isEmpty()){
                  imageSend.setImageTintList(getActivity().getColorStateList(R.color.black));
                  imageSend.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          bottomSheetDialogLoading.show();
                          bottomSheetDialogLoading.setCancelable(false);
                          edtMessage.clearFocus();
                          chatViewModel.sendMessage(token,edtMessage.getText().toString().trim()).observe(getActivity(), new Observer<ModelChat>() {
                              @Override
                              public void onChanged(ModelChat modelChat) {
                                      edtMessage.setText("");
                                      bottomSheetDialogLoading.dismiss();
                              }
                          });
                      }
                  });
              }else{
               imageSend.setImageTintList(getActivity().getColorStateList(R.color.gray));
              }
           }
       });
    }
    private void longPollingNewMessage(String last_id,AdapterChat adapterChat){
        chatViewModel.longPollingNewMessageStart(token,last_id).observe((LifecycleOwner) activity, new Observer<ModelChat>() {
            @Override
            public void onChanged(ModelChat modelChat) {
                if (modelChat!=null){
                    if (list.getAdapter() instanceof AdapterEmptyChat){
                        list.setLayoutManager(new LinearLayoutManager(getContext()));
                        list.setAdapter(adapterChat);
                    }
                    adapterChat.addNewMessage(modelChat);
                    list.scrollToPosition((adapterChat.sizeList()-1));
                }
            }
        });
    }
private void getDetailProductInChat(String product_id,ImageView imageProduct,TextView product_price,TextView product_name){
        chatViewModel.getDetailProduct(product_id).observe((LifecycleOwner) activity, new Observer<ModelDetailProduct>() {
            @Override
            public void onChanged(ModelDetailProduct modelDetailProduct) {
                Picasso.get().load(modelDetailProduct.getProduct_image()).error(R.drawable.icon).into(imageProduct);
                String price=new DecimalFormat("#,###").format(Integer.parseInt(modelDetailProduct.getProduct_price()));
                product_name.setText(modelDetailProduct.getProduct_name());
                product_price.setText(price+" تومان ");
            }
        });
}
    @Override
    public void onDestroy() {
        super.onDestroy();
        chatViewModel.stopLongPollingNewMessage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        chatViewModel.stopLongPollingNewMessage();
    }
}
