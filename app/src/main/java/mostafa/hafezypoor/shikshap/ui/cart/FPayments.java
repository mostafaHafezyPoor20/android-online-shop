package mostafa.hafezypoor.shikshap.ui.cart;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelGetPayments;
import mostafa.hafezypoor.shikshap.ui.common.AdapterRequireLogin;

public class FPayments extends Fragment {
    private CartViewModel cartViewModel;
    private RecyclerView list;
    private String payment_id;

    public FPayments(String payment_id) {
        this.payment_id = payment_id;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fpayments,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(new AdapterLoadingFPayment(getActivity()));
        cartViewModel=new ViewModelProvider(getActivity()).get(CartViewModel.class);
        cartViewModel.chekcToken(getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null")).observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("ok")){
                    getPayments();
                }else if (s.equals("error")){
                    list.setLayoutManager(new LinearLayoutManager(getActivity()));
                    list.setAdapter(new AdapterRequireLogin(getContext(), "میخوای خرید هات رو ببینی ؟ اول وارد حساب کاربریت شو", getActivity(), new AdapterRequireLogin.IEvent() {
                        @Override
                        public void loginOrRegisterd() {
                            getPayments();
                        }
                    }));
                }
            }
        });
    }
    private void getPayments(){
        cartViewModel.getPayments(getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null")).observe(getActivity(), new Observer<List<ModelGetPayments>>() {
            @Override
            public void onChanged(List<ModelGetPayments> modelGetPayments) {
                if (modelGetPayments.isEmpty()){
                    list.setLayoutManager(new LinearLayoutManager(getActivity()));
                    list.setAdapter(new AdapterEmptyCart(getActivity(),"فعلا هیچ خریدی از شیک شاپ انجام ندادی!"));
                }else{
                    AdapterFPayments adapterFPayments=new AdapterFPayments(getActivity(),modelGetPayments,getActivity(),payment_id);
                  list.setLayoutManager(new LinearLayoutManager(getActivity()));
                  list.setAdapter(adapterFPayments);
                  if (payment_id!=null){
                      list.scrollToPosition(adapterFPayments.findPayment(payment_id));
                  }
                }
            }
        });
    }
}
