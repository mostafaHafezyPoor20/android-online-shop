package mostafa.hafezypoor.shikshap.ui.product;

import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelAdapterViewPagerImagesProduct;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;
import mostafa.hafezypoor.shikshap.data.model.ModelLogin;
import mostafa.hafezypoor.shikshap.data.model.ModelRegister;
import mostafa.hafezypoor.shikshap.data.model.ModelSizes;
import mostafa.hafezypoor.shikshap.ui.account.FAccount;
import mostafa.hafezypoor.shikshap.ui.cart.FCart;
import mostafa.hafezypoor.shikshap.ui.cart.FOrderInCart;
import mostafa.hafezypoor.shikshap.ui.home.FHome;
import mostafa.hafezypoor.shikshap.ui.main.FConnectionError;
import mostafa.hafezypoor.shikshap.ui.main.MainActivity;
import mostafa.hafezypoor.shikshap.ui.main.MainFragment;
import mostafa.hafezypoor.shikshap.utils.Constants;

public class Product extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private ViewPager viewPager;
    TextView product_name,price;
    TabLayout tabLayoutIndicator,tabLayoutMenu;
    ImageView imgBack,imgShareProduct;
    private TextView btnAddToCart;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private String productI_id="";
    private BottomSheetDialog dialogInternetError;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
        dialogInternetError=new BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme);
        dialogInternetError.setContentView(R.layout.fconnection_error);
        dialogInternetError.setCancelable(false);
        viewPager=findViewById(R.id.viewPagerProductImages);
        product_name=findViewById(R.id.productName);
        imgBack=findViewById(R.id.imgBack);
        price=findViewById(R.id.price);
        imgShareProduct=findViewById(R.id.imageShareProduct);
        tabLayoutMenu=findViewById(R.id.tabLayoutMenu);
        btnAddToCart=findViewById(R.id.btnAddToCart);
        tabLayoutIndicator=findViewById(R.id.tabLayoutIndicator);
        productViewModel=new ViewModelProvider( Product.this).get(ProductViewModel.class);
        String product_id="0";
        if (getIntent().getExtras()!=null){
          product_id= getIntent().getExtras().getString("product_id");
        }else if (getIntent().getData()!=null){
            product_id=getIntent().getData().getQueryParameter("id");
        }
        if(product_id!=null||!product_id.equals("0")){
            getImagesProducts(product_id);
            getDetailProduct(product_id);
            checkToken(product_id);
        }
        this.productI_id=product_id;
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(Product.this, MainActivity.class));
               finish();
            }
        });
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback=new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialogInternetError.dismiss();
                    }
                });
            }
            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialogInternetError.show();
                    }
                });

            }
        };
    }
    private void getImagesProducts(String product_id){
      productViewModel.getImagesProduct(product_id).observe(this, new Observer<List<ModelAdapterViewPagerImagesProduct>>() {
          @Override
          public void onChanged(List<ModelAdapterViewPagerImagesProduct> modelAdapterViewPagerImagesProducts) {
              AdapterViewPagerImagesProduct      adapterViewPagerImagesProduct=new AdapterViewPagerImagesProduct(Product.this,modelAdapterViewPagerImagesProducts);
              viewPager.setAdapter(adapterViewPagerImagesProduct);
              tabLayoutIndicator.setupWithViewPager(viewPager,true);
              Handler handler=new Handler();
              Runnable runnable=new Runnable() {
                  @Override
                  public void run() {
                      int currentItem=viewPager.getCurrentItem();
                      int totalItem=adapterViewPagerImagesProduct.getCount();
                      if (currentItem < totalItem -1){
                          viewPager.setCurrentItem(currentItem+1,true);
                      }else{
                          viewPager.setCurrentItem(0,true);
                      }
                      handler.postDelayed(this,3000);
                  }
              };
              handler.postDelayed(runnable,3000);
              viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                  @Override
                  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                  }

                  @Override
                  public void onPageSelected(int position) {
                      handler.removeCallbacks(runnable);
                      handler.postDelayed(runnable,3000);
                  }

                  @Override
                  public void onPageScrollStateChanged(int state) {

                  }
              });

          }
      });

    }
    private void getDetailProduct(String product_id){
        productViewModel.getDetailProduct(product_id).observe(this, new Observer<ModelDetailProduct>() {
            @Override
            public void onChanged(ModelDetailProduct modelDetailProduct) {
                product_name.setText(modelDetailProduct.getProduct_name());
                DecimalFormat decimalFormat=new DecimalFormat("#,###");
                String string_price=decimalFormat.format(Double.parseDouble(modelDetailProduct.getProduct_price()));
                price.setText(string_price + " تومان ");
                imgShareProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT,modelDetailProduct.getProduct_name());
                        intent.putExtra(Intent.EXTRA_TEXT, Constants.URL_SHARE_PRODUCT+"?id="+product_id);
                        startActivity(Intent.createChooser(intent,"اشتراک گذاری با"));

                    }
                });
                if (getIntent().getExtras()!=null){
                    if (getIntent().getExtras().getString("comment_id")!=null){
                        String comment_id=getIntent().getExtras().getString("comment_id");
                        getSupportFragmentManager().beginTransaction().replace(R.id.productFrameLayout,new FProductComments(modelDetailProduct.getId(),modelDetailProduct.getProduct_name(),comment_id)).commit();
                        tabLayoutMenu.getTabAt(1).select();
                    }else{
                        getSupportFragmentManager().beginTransaction().replace(R.id.productFrameLayout,new FProductDescription(modelDetailProduct.getProduct_description())).commit();
                    }
                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.productFrameLayout,new FProductDescription(modelDetailProduct.getProduct_description())).commit();
                }
                tabLayoutMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (tab.getPosition()==0){
                            getSupportFragmentManager().beginTransaction().replace(R.id.productFrameLayout,new FProductDescription(modelDetailProduct.getProduct_description())).commit();
                        }else if (tab.getPosition()==1){
                            productViewModel.checkToken(getSharedPreferences("save",MODE_PRIVATE).getString("token","null")).observe(Product.this, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    if (s.equals("error")){
                                        getSupportFragmentManager().beginTransaction().replace(R.id.productFrameLayout,new FRequireLogin(new FRequireLogin.IEvent() {
                                            @Override
                                            public void logined() {
                                                getSupportFragmentManager().beginTransaction().replace(R.id.productFrameLayout,new FProductComments(modelDetailProduct.getId(),modelDetailProduct.getProduct_name(),null)).commit();
                                            }
                                        })).commit();
                                    }else if (s.equals("ok")){
                                        getSupportFragmentManager().beginTransaction().replace(R.id.productFrameLayout,new FProductComments(modelDetailProduct.getId(),modelDetailProduct.getProduct_name(),null)).commit();

                                    }
                                }
                            });
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
        });
    }
    private void checkToken(String product_id){
        String token =getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
        productViewModel.checkToken(token).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("ok")){
                 //   checkProductInCart(product_id);
                    addToCart(product_id);
                }else if (s.equals("error")){
                    requireLoginForAddProductToCart();
                }
            }
        });
    }

    private void requireLoginForAddProductToCart(){
    BottomSheetDialog dialogRequireLogin=new BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme);
    dialogRequireLogin.setContentView(R.layout.drequire_login);
    MaterialButton btnLoginOrRegister=dialogRequireLogin.findViewById(R.id.btnLoginOrRegister);
    btnAddToCart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialogRequireLogin.show();
            btnLoginOrRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogRequireLogin.dismiss();
                    BottomSheetDialog dialogLogin=new BottomSheetDialog(Product.this,R.style.AppBottomSheetDialogTheme);
                    BottomSheetDialog dialogRegister=new BottomSheetDialog(Product.this,R.style.AppBottomSheetDialogTheme);
                    dialogRegister.setContentView(R.layout.dregister);
                    dialogLogin.setContentView(R.layout.dlogin);
                    MaterialButton goToRegister=dialogLogin.findViewById(R.id.goToRegister);
                    MaterialButton login=dialogLogin.findViewById(R.id.login);
                    TextInputEditText username=dialogLogin.findViewById(R.id.username);
                    TextInputEditText password=dialogLogin.findViewById(R.id.password);
                    TextView textError=dialogLogin.findViewById(R.id.textError);
                    dialogRegister.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    dialogLogin.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    dialogLogin.show();
                    goToRegister.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogLogin.dismiss();
                            dialogRegister.show();
                            dregister(dialogRegister,dialogLogin);
                        }
                    });
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loginToServer(username,password,dialogLogin,textError);
                        }
                    });
                }
            });

        }
    });

}
    private void vibration(int duration){
        Vibrator vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            vibrator.vibrate(VibrationEffect.createOneShot(duration,DEFAULT_AMPLITUDE));
        }
    }
    private void loginToServer(TextInputEditText username,TextInputEditText password,BottomSheetDialog dialogLogin,TextView textError){
        TextWatcher textInput=new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textError.setVisibility(GONE);
            }
        };
        username.addTextChangedListener(textInput);
        password.addTextChangedListener(textInput);
        if (username.getText().toString().trim().isEmpty()){
            textError.setText("نام کاربری نمیتواند خالی باشد");
            textError.setVisibility(VISIBLE);
            vibration(300);
        }else if (password.getText().toString().trim().isEmpty()){
            textError.setText("کلمه عبور  نمیتواند خالی باشد");
            textError.setVisibility(VISIBLE);
            vibration(300);
        }else{
            productViewModel.login(username.getText().toString().trim(),password.getText().toString()).observe(Product.this, new Observer<ModelLogin>() {
                @Override
                public void onChanged(ModelLogin modelLogin) {
                    if (modelLogin.getStatus().equals("success")){
                        getSharedPreferences("save",MODE_PRIVATE).edit().putString("token",modelLogin.getToken()).apply();
                        dialogLogin.dismiss();
                        showSizesProduct(productI_id);

                    }else{
                        vibration(600);
                        dialogLogin.dismiss();
                        BottomSheetDialog dialogError=new BottomSheetDialog(Product.this,R.style.AppBottomSheetDialogTheme);
                        dialogError.setContentView(R.layout.derror);
                        dialogError.setCancelable(false);
                        dialogError.show();
                        MaterialButton btnTryAgain=dialogError.findViewById(R.id.btnTryAgain);
                        btnTryAgain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogError.dismiss();
                                dialogLogin.show();
                            }
                        });
                    }
                }
            });

        }
    }
    private void dregister(BottomSheetDialog dialogRegister,BottomSheetDialog dialogLogin){
        MaterialButton btnGoToLogin=dialogRegister.findViewById(R.id.btnGoToLogin);
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRegister.dismiss();
                dialogLogin.show();
            }
        });
        MaterialButton btnRegister=dialogRegister.findViewById(R.id.btnRegister);
        TextInputEditText name=dialogRegister.findViewById(R.id.name);
        TextInputEditText username=dialogRegister.findViewById(R.id.username);
        TextInputEditText password=dialogRegister.findViewById(R.id.password);
        TextView textError=dialogRegister.findViewById(R.id.textError);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerToServer(name,username,password,textError,dialogRegister);
            }
        });
    }
    private void registerToServer(TextInputEditText name,TextInputEditText username,TextInputEditText password,TextView textError,BottomSheetDialog dialogRegister){
        if (name.getText().toString().trim().isEmpty()){
            textError.setVisibility(VISIBLE);
            textError.setText("نام و نام خانوادگی نمیتواند خالی باشد");
            vibration(300);
        }else if (username.getText().toString().trim().isEmpty()){
            textError.setVisibility(VISIBLE);
            textError.setText("نام کاربری نمیتواند خالی باشد");
            vibration(300);
        }else if (password.getText().toString().trim().isEmpty()){
            textError.setVisibility(VISIBLE);
            textError.setText("کلمه عبور نمیتواند خالی باشد");
            vibration(300);
        }else{
            productViewModel.register(name.getText().toString().trim(),username.getText().toString().trim(),password.getText().toString().trim()).observe(Product.this, new Observer<ModelRegister>() {
                @Override
                public void onChanged(ModelRegister modelRegister) {
                    if (modelRegister.getStatus().equals("userExist")){
                        BottomSheetDialog dialogError=new BottomSheetDialog(Product.this,R.style.AppBottomSheetDialogTheme);
                        dialogError.setContentView(R.layout.derror);
                        TextView textErrorDialog=dialogError.findViewById(R.id.textErrorDialog);
                        textErrorDialog.setText("نام کاربری "+username.getText().toString().trim()+"قبلا ساخته شده نام کاربری دیگری امتحان کنید");
                        vibration(300);
                        MaterialButton btnTryAgain=dialogError.findViewById(R.id.btnTryAgain);
                        dialogError.setCancelable(false);
                        dialogError.show();
                        btnTryAgain.setText("متوجه شدم");
                        btnTryAgain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogError.dismiss();
                                dialogRegister.show();
                            }
                        });
                    }else if (modelRegister.getStatus().equals("success")){
                        getSharedPreferences("save",MODE_PRIVATE).edit().putString("token",modelRegister.getToken()).apply();
                        dialogRegister.dismiss();
                        showSizesProduct(productI_id);
                    }
                }
            });
        }
    }

    private void addToCart(String product_id) {
        btnAddToCart.setBackgroundColor(getColor(R.color.primary));
        btnAddToCart.setText("افزودن به سبد خرید");
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showSizesProduct(product_id);
                /*productViewModel.addCart(getSharedPreferences("save",MODE_PRIVATE).getString("token","null"),product_id).observe(Product.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if (s.equals("200")){
                            Snackbar snackbar=Snackbar.make(findViewById(R.id.bottom),"به سبد خرید اضافه شد",10000);
                            snackbar.setAction("رفتن به سبد خرید", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Product.this,MainActivity.class);
                                    intent.putExtra("fragment",R.id.cart);
                                    startActivity(intent);
                                }
                            });
                            View sbView=snackbar.getView();
                            ((TextView)sbView.findViewById(com.google.android.material.R.id.snackbar_text)).setTextColor(getColor(R.color.black));
                            sbView.setBackgroundColor(getColor(R.color.white));
                            snackbar.setActionTextColor(getColor(R.color.primary));
                            sbView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            snackbar.show();
                            changeStyleBtnToAddCarted();
                        }
                    }
                });*/
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
    private void showSizesProduct(String product_id){
        productViewModel.getSizesProduct(getSharedPreferences("save",MODE_PRIVATE).getString("token","null"),product_id).observe(Product.this, new Observer<List<ModelSizes>>() {
            @Override
            public void onChanged(List<ModelSizes> modelSizes) {
                BottomSheetDialog dialogSizes=new BottomSheetDialog(Product.this,R.style.AppBottomSheetDialogTheme);
                dialogSizes.setContentView(R.layout.dshow_sizes_product);
                dialogSizes.show();
                RecyclerView listSizes=dialogSizes.findViewById(R.id.list);
                MaterialButton addToCart=dialogSizes.findViewById(R.id.addToCart);
                MaterialButton dismiss=dialogSizes.findViewById(R.id.btnDismiss);
                dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogSizes.dismiss();
                    }
                });
                AdapterDShowSizesProduct adapterDShowSizesProduct=new AdapterDShowSizesProduct(Product.this, modelSizes, new AdapterDShowSizesProduct.IEvent() {
                    @Override
                    public void selectedItems(int itemSelected) {
                        if (itemSelected!=0){
                            addToCart.setVisibility(VISIBLE);
                        }else{
                            addToCart.setVisibility(GONE);
                        }
                    }
                });
                listSizes.setLayoutManager(new LinearLayoutManager(Product.this));
                listSizes.setAdapter(adapterDShowSizesProduct);
                addToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String token=getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
                               addCartServer(token,product_id,adapterDShowSizesProduct.getSelectedSizes());
                               dialogSizes.dismiss();
                    }
                });

            }
        });
    }
    private void addCartServer(String token,String productI_id,List<ModelSizes>sizes){
        for (int i = 0; i < sizes.size(); i++) {
            productViewModel.addCart(token,productI_id,sizes.get(i).getSize()).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    Snackbar snackbar=Snackbar.make(findViewById(R.id.bottom),"به سبد خرید اضافه شد",10000);
                    snackbar.setAction("رفتن به سبد خرید", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Product.this,MainActivity.class);
                            intent.putExtra("fragment",R.id.cart);
                            startActivity(intent);
                        }
                    });
                    View sbView=snackbar.getView();
                    ((TextView)sbView.findViewById(com.google.android.material.R.id.snackbar_text)).setTextColor(getColor(R.color.black));
                    sbView.setBackgroundColor(getColor(R.color.white));
                    snackbar.setActionTextColor(getColor(R.color.primary));
                    sbView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    snackbar.show();
                }
            });
        }
    }
}
