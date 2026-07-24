package mostafa.hafezypoor.shikshap.ui.cart;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelOrderInCart;
import mostafa.hafezypoor.shikshap.ui.common.AdapterRequireLogin;

public class FCart extends Fragment {
    private CartViewModel cartViewModel;
    private TabLayout tabLayoutMenu;
    private Fragment fragment;

    public FCart(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fcart,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayoutMenu=view.findViewById(R.id.tabLayoutMenu);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fcartFrameLayout,fragment,"forderInCart").commit();
        if (fragment instanceof FOrderInCart){
            tabLayoutMenu.getTabAt(0).select();
        }else if (fragment instanceof FPayments){
            tabLayoutMenu.getTabAt(1).select();
        }
        cartViewModel=new ViewModelProvider(getActivity()).get(CartViewModel.class);
        tabLayoutMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fcartFrameLayout,new FOrderInCart(),"forderInCart").commit();
                }else if (tab.getPosition()==1){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fcartFrameLayout,new FPayments(null),"fpayments").commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
