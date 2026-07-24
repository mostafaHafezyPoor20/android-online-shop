package mostafa.hafezypoor.shikshap.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.FHomeModelProduct;

public class ShowAllProductInGroup extends AppCompatActivity {
    private HomeViewModel homeViewModel;
    private RecyclerView list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_product_in_group);
        list=findViewById(R.id.list);
        homeViewModel=new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getProducts(getIntent().getStringExtra("group_id")).observe(this, new Observer<List<FHomeModelProduct>>() {
            @Override
            public void onChanged(List<FHomeModelProduct> fHomeModelProducts) {
                list.setLayoutManager(new LinearLayoutManager(ShowAllProductInGroup.this));
                list.setAdapter(new AdapterShowAllProductInGroup(ShowAllProductInGroup.this,fHomeModelProducts));
                ((TextView)findViewById(R.id.title)).setText(getIntent().getStringExtra("group_name"));
            }
        });
        ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}






