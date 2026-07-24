package mostafa.hafezypoor.shikshap.ui.common;

import static android.content.Context.MODE_PRIVATE;
import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;
import static android.os.VibrationEffect.EFFECT_CLICK;
import static android.os.VibrationEffect.EFFECT_DOUBLE_CLICK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_LONG;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelLogin;
import mostafa.hafezypoor.shikshap.data.model.ModelRegister;

public class AdapterRequireLogin extends RecyclerView.Adapter<AdapterRequireLogin.ViewHolder> {
    private Context context;
    private String titleText;
    private AdapterRequireLoginViewModel viewModel;
    LifecycleOwner owner;
    public AdapterRequireLogin(Context context, String titleText, LifecycleOwner  owner,IEvent iEvent) {
        this.context = context;
        this.titleText = titleText;
        this.owner=owner;
        this.iEvent=iEvent;
        viewModel=new ViewModelProvider((ViewModelStoreOwner) owner).get(AdapterRequireLoginViewModel.class);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_require_login,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           holder.textTitle.setText(titleText);
           holder.btnLoginOrRegister.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   BottomSheetDialog dialogLogin=new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
                   BottomSheetDialog dialogRegister=new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
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

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle;
        MaterialButton btnLoginOrRegister;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle=itemView.findViewById(R.id.textTitle);
            btnLoginOrRegister=itemView.findViewById(R.id.btnLoginOrRegister);
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
          viewModel.login(username.getText().toString().trim(),password.getText().toString()).observe(owner, new Observer<ModelLogin>() {
              @Override
              public void onChanged(ModelLogin modelLogin) {
               if (modelLogin.getStatus().equals("success")){
                   context.getSharedPreferences("save",MODE_PRIVATE).edit().putString("token",modelLogin.getToken()).apply();
                   dialogLogin.dismiss();
                   iEvent.loginOrRegisterd();
               }else{
                vibration(600);
                dialogLogin.dismiss();
                BottomSheetDialog dialogError=new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
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

    private void vibration(int duration){
        Vibrator vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            vibrator.vibrate(VibrationEffect.createOneShot(duration,DEFAULT_AMPLITUDE));
        }
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
          viewModel.register(name.getText().toString().trim(),username.getText().toString().trim(),password.getText().toString().trim()).observe(owner, new Observer<ModelRegister>() {
              @Override
              public void onChanged(ModelRegister modelRegister) {
                  if (modelRegister.getStatus().equals("userExist")){
                      BottomSheetDialog dialogError=new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
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
                      context.getSharedPreferences("save",MODE_PRIVATE).edit().putString("token",modelRegister.getToken()).apply();
                      dialogRegister.dismiss();
                      iEvent.loginOrRegisterd();
                  }
              }
          });
       }
    }
    private IEvent iEvent;
    public interface IEvent{
        public void loginOrRegisterd();
    }
}
