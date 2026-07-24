package mostafa.hafezypoor.shikshap.ui.main;

import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.messaging.FirebaseMessaging;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.ui.account.FAccount;
import mostafa.hafezypoor.shikshap.ui.cart.FCart;
import mostafa.hafezypoor.shikshap.ui.cart.FOrderInCart;
import mostafa.hafezypoor.shikshap.ui.cart.FPayments;
import mostafa.hafezypoor.shikshap.ui.chat.FChat;
import mostafa.hafezypoor.shikshap.ui.home.FHome;
import mostafa.hafezypoor.shikshap.ui.home.ShowAllProductInGroup;
import mostafa.hafezypoor.shikshap.ui.product.Product;
import mostafa.hafezypoor.shikshap.utils.Constants;

public class MainActivity extends AppCompatActivity {
private MainActivityViewModel mainActivityViewModel;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private Fragment currentFragmentMainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionNotification();
        mainActivityViewModel=new ViewModelProvider(this).get(MainActivityViewModel.class);
        checkToken();
        mainActivityViewModel.version(Constants.VERSION).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("true")){
                    Uri data=getIntent().getData();
                    if (data!=null){
                        String paymentStatus=data.getQueryParameter("payment");
                        BottomSheetDialog dialogPayment=new BottomSheetDialog(MainActivity.this, R.style.AppBottomSheetDialogTheme);
                        dialogPayment.setCancelable(false);
                        dialogPayment.setContentView(R.layout.dpayment);
                        if (paymentStatus.equals("cancelled")){
                            dialogPayment.show();
                            vibration(100);
                            ((MaterialButton)dialogPayment.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogPayment.dismiss();
                                }
                            });
                        }else if(paymentStatus.equals("faildPayment")){
                            dialogPayment.show();
                            vibration(100);
                            ((TextView)dialogPayment.findViewById(R.id.title)).setText("فرآیند پرداخت با خطا مواجه شد!");
                            ((MaterialButton)dialogPayment.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogPayment.dismiss();
                                }
                            });
                        }else if(paymentStatus.equals("success")){
                            dialogPayment.show();
                            dialogPayment.setCancelable(false);
                            vibration(100);
                            ((LottieAnimationView)dialogPayment.findViewById(R.id.animation)).setAnimation(R.raw.success_animation);
                            ((TextView)dialogPayment.findViewById(R.id.title)).setText("از خرید شما متشکریم! برای دیدن وضعیت خرید به قسمت (خریداری شده) مراجعه کنید");
                            ((MaterialButton)dialogPayment.findViewById(R.id.btnOk)).setBackgroundColor(getColor(R.color.primary));
                            ((MaterialButton)dialogPayment.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogPayment.dismiss();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new MainFragment(new FCart(new FPayments(null))),"mainFragment").commit();
                                }
                            });
                        }
                    }
                    if (getIntent()!=null&&getIntent().getExtras()!=null) {
                        if (getIntent().getExtras().getString("command")!=null){
                            if (getIntent().getExtras().getString("command").equals("message")){
                                getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new MainFragment(new FChat()),"fchat").commit();
                            }else if (getIntent().getExtras().getString("command").equals("comment")){
                                Intent intent=new Intent(MainActivity.this, Product.class);
                                intent.putExtra("product_id",getIntent().getExtras().getString("product_id"));
                                intent.putExtra("comment_id",getIntent().getExtras().getString("comment_id"));
                                startActivity(intent);
                            }else if (getIntent().getExtras().getString("command").equals("product")){
                                Intent intent=new Intent(MainActivity.this, Product.class);
                                intent.putExtra("product_id",getIntent().getExtras().getString("product_id"));
                                startActivity(intent);
                            }else if (getIntent().getExtras().getString("command").equals("group")){
                                Intent intent=new Intent(MainActivity.this, ShowAllProductInGroup.class);
                                intent.putExtra("group_id",getIntent().getExtras().getString("group_id"));
                                intent.putExtra("group_name",getIntent().getExtras().getString("group_name"));
                                startActivity(intent);
                            }else if (getIntent().getExtras().getString("command").equals("payment")){
                                getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new MainFragment(new FCart(new FPayments(getIntent().getExtras().getString("payment_id")))),"mainFragment").commit();
                            }
                        }else    if (getIntent().getExtras().getInt("fragment")==R.id.cart){
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new MainFragment(new FCart(new FOrderInCart())),"mainFragment").commit();
                        }else{
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new MainFragment(new FHome()),"mainFragment").commit();
                        }
                    }else{
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new MainFragment(new FHome()),"mainFragment").commit();
                    }
                }else if (s.equals("false")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FUpdate(),"fupdate").commit();

                }
            }
        });
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback=new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                if (currentFragmentMainActivity != null) {
                    if (currentFragmentMainActivity instanceof FHome) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout, new MainFragment(new FHome()), "currentFragment").commit();
                    }else if (currentFragmentMainActivity instanceof FCart){
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout, new MainFragment(new FCart(new FOrderInCart())), "currentFragment").commit();
                    }else if (currentFragmentMainActivity instanceof FAccount){
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout, new MainFragment(new FAccount()), "currentFragment").commit();
                    }else if (currentFragmentMainActivity instanceof FChat){
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout, new MainFragment(new FChat()), "currentFragment").commit();
                    }
                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout, new MainFragment(new FHome()), "currentFragment").commit();
                }
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                currentFragmentMainActivity=getSupportFragmentManager().findFragmentById(R.id.mainFragmentFrameLayout);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FConnectionError(),"error").commit();
            }
        };
    }
    private void vibration(int duration){
        Vibrator vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            vibrator.vibrate(VibrationEffect.createOneShot(duration,DEFAULT_AMPLITUDE));
        }
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
    private void checkToken(){
     String token=getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
     mainActivityViewModel.chekToken(token).observe(this, new Observer<String>() {
         @Override
         public void onChanged(String s) {
             if (s.equals("ok")){
              FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                  @Override
                  public void onComplete(@NonNull Task<String> task) {
                 mainActivityViewModel.setUserFirebaseToken(token,task.getResult()).observe(MainActivity.this, new Observer<String>() {
                     @Override
                     public void onChanged(String s) {

                     }
                 });
                  }
              });
             }else if (s.equals("error")){
                 FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                     @Override
                     public void onComplete(@NonNull Task<String> task) {
                    mainActivityViewModel.setUsersNotLoginFirebaseToken(task.getResult()).observe(MainActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {

                        }
                    });
                     }
                 });
             }
         }
     });
    }
    private void checkPermissionNotification(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                BottomSheetDialog dialogNotification=new BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme);
                dialogNotification.setContentView(R.layout.dcheck_permission_notification);
                dialogNotification.show();
                ((MaterialButton)dialogNotification.findViewById(R.id.btnDismiss)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogNotification.dismiss();
                    }
                });
                ((MaterialButton)dialogNotification.findViewById(R.id.enableNotification)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},1001);
                        dialogNotification.dismiss();
                    }
                });
            }
        }


    }
}