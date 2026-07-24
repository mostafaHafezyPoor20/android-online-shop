package mostafa.hafezypoor.shikshap.ui.main;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.ui.account.FAccount;
import mostafa.hafezypoor.shikshap.ui.cart.FCart;
import mostafa.hafezypoor.shikshap.ui.cart.FOrderInCart;
import mostafa.hafezypoor.shikshap.ui.chat.FChat;
import mostafa.hafezypoor.shikshap.ui.home.FHome;

public class MainFragment extends Fragment {
    private Fragment fragment;

    public MainFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.main_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView=view.findViewById(R.id.bottomNavigationView);
        if (fragment instanceof FHome){
            bottomNavigationView.setSelectedItemId(R.id.home);
        }else if (fragment instanceof FCart){
            bottomNavigationView.setSelectedItemId(R.id.cart);
        }else if (fragment instanceof FAccount){
            bottomNavigationView.setSelectedItemId(R.id.account);
        }else if (fragment instanceof FChat){
            bottomNavigationView.setSelectedItemId(R.id.chat);
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentFrameLayout,fragment,"fasdfasdfasd").commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.home){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentFrameLayout,new FHome(),"fhome").commit();
                }else if (menuItem.getItemId()==R.id.cart){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentFrameLayout,new FCart(new FOrderInCart()),"fcart").commit();
                }else if (menuItem.getItemId()==R.id.chat){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentFrameLayout,new FChat(),"fchat").commit();
                }
                else if (menuItem.getItemId()==R.id.account){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentFrameLayout,new FAccount(),"faccount").commit();
                }
                return true;
            }
        });

    }
}
