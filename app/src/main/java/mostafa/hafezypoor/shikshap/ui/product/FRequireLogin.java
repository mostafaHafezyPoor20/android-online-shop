package mostafa.hafezypoor.shikshap.ui.product;

import static android.content.Context.MODE_PRIVATE;
import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelLogin;
import mostafa.hafezypoor.shikshap.data.model.ModelRegister;

public class FRequireLogin extends Fragment {
    private MaterialButton btnLoginOrRegister;
    private ProductViewModel viewModel;
    public FRequireLogin(IEvent iEvent){
        this.iEvent=iEvent;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.frequire_login,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel=new ViewModelProvider(getActivity()).get(ProductViewModel.class);
        btnLoginOrRegister=view.findViewById(R.id.btnLoginOrRegister);
        btnLoginOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialogLogin=new BottomSheetDialog(getActivity());
                BottomSheetDialog dialogRegister=new BottomSheetDialog(getActivity());
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
            viewModel.login(username.getText().toString().trim(),password.getText().toString()).observe(getActivity(), new Observer<ModelLogin>() {
                @Override
                public void onChanged(ModelLogin modelLogin) {
                    if (modelLogin.getStatus().equals("success")){
                        getActivity().getSharedPreferences("save",MODE_PRIVATE).edit().putString("token",modelLogin.getToken()).apply();
                        dialogLogin.dismiss();
                        iEvent.logined();
                    }else{
                        vibration(600);
                        dialogLogin.dismiss();
                        BottomSheetDialog dialogError=new BottomSheetDialog(getActivity());
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
            viewModel.register(name.getText().toString().trim(),username.getText().toString().trim(),password.getText().toString().trim()).observe(getActivity(), new Observer<ModelRegister>() {
                @Override
                public void onChanged(ModelRegister modelRegister) {
                    if (modelRegister.getStatus().equals("userExist")){
                        BottomSheetDialog dialogError=new BottomSheetDialog(getActivity());
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
                        getActivity().getSharedPreferences("save",MODE_PRIVATE).edit().putString("token",modelRegister.getToken()).apply();
                        dialogRegister.dismiss();
                        iEvent.logined();
                    }
                }
            });
        }
    }
    private void vibration(int duration){
        Vibrator vibrator= (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            vibrator.vibrate(VibrationEffect.createOneShot(duration,DEFAULT_AMPLITUDE));
        }
    }
    private IEvent iEvent;
    public interface IEvent{
        void logined();
    }
}
