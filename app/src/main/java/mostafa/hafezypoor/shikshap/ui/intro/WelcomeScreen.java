package mostafa.hafezypoor.shikshap.ui.intro;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import mostafa.hafezypoor.shikshap.ui.main.MainActivity;
import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.IntroModel;

public class WelcomeScreen extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabIndicator;
    ImageView next,previous;
    MaterialButton btnLetsGo;
   private Intent intent;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=new Intent(WelcomeScreen.this,MainActivity.class);
        if (getIntent().getExtras()!=null){
            intent.putExtra("command",getIntent().getExtras().getString("command"));
            if (getIntent().getExtras().get("command")!=null){
                if (getIntent().getExtras().getString("command").equals("comment")){
                    intent.putExtra("product_id",getIntent().getExtras().getString("product_id"));
                    intent.putExtra("comment_id",getIntent().getExtras().getString("comment_id"));
                }else if (getIntent().getExtras().getString("command").equals("product")){
                    intent.putExtra("product_id",getIntent().getExtras().getString("product_id"));
                }else if (getIntent().getExtras().getString("command").equals("group")){
                    intent.putExtra("group_id",getIntent().getExtras().getString("group_id"));
                    intent.putExtra("group_name",getIntent().getExtras().getString("group_name"));
                }else if (getIntent().getExtras().getString("command").equals("payment")){
                    intent.putExtra("payment_id",getIntent().getExtras().getString("payment_id"));
                }
            }
        }

        if (getSharedPreferences("save",MODE_PRIVATE).getBoolean("showIntro",false)==true){
            startActivity(intent);
            finish();
        }getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
   setContentView(R.layout.welcome_screen);
   viewPager=findViewById(R.id.viewPager);
   tabIndicator=findViewById(R.id.tabIndicator);
   next=findViewById(R.id.next);
   previous=findViewById(R.id.previus);
   btnLetsGo=findViewById(R.id.btnLetsGo);
        List<IntroModel>introModels=new ArrayList<>();
        introModels.add(new IntroModel(R.raw.happy_gril,"به دنیای رنگارنگ بچه ها خوش آمدید","جدید ترین لباس های شاد و راحت برای کوچولو شما , در یک آپ!"));
        introModels.add(new IntroModel(R.raw.happy_family,"کیفیت و راحتی","پارچه های نرم و طراحی های شیک که کودک شما عاشقش می شود و شما آرامش خاطر دارید"));
        introModels.add(new IntroModel(R.raw.easy_buy,"خرید آسان و سریع","تنها با چند لمس , لباس دلخواه کودکتان را انتخاب کنید و در کوتاه ترین زمان تحویل بگیرید"));
        viewPager.setAdapter(new IntroAdapter(this,introModels));
        tabIndicator.setupWithViewPager(viewPager,true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            if (position==0){
                previous.setVisibility(INVISIBLE);
            }else{
                previous.setVisibility(VISIBLE);
            }
            if (position==(introModels.size()-1)){
                next.setVisibility(INVISIBLE);
            }else{
                next.setVisibility(VISIBLE);
            }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
     btnLetsGo.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             getSharedPreferences("save",MODE_PRIVATE).edit().putBoolean("showIntro",true).apply();
             startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
             finish();
         }
     });
    }
}

