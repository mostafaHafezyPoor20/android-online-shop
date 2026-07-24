package mostafa.hafezypoor.shikshap.ui.account;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelAccount;
import mostafa.hafezypoor.shikshap.data.model.ModelChangePasswordAccount;
import mostafa.hafezypoor.shikshap.data.network.RetrofitInit;
import mostafa.hafezypoor.shikshap.ui.common.AdapterRequireLogin;

public class FAccount extends Fragment {
    RecyclerView list;
private  AccountViewModel accountViewModel;
private TextView username,textError;
private TextInputEditText name,phoneNumber,codePosti,address,password;
private MaterialButton saveInformationAccount,btnChangePassword;
private LinearLayout linearAccount;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.faccount,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=view.findViewById(R.id.list);
        username=view.findViewById(R.id.username);
        name=view.findViewById(R.id.name);
        textError=view.findViewById(R.id.textError);
        phoneNumber=view.findViewById(R.id.phoneNumber);
        codePosti=view.findViewById(R.id.codePosti);
        address=view.findViewById(R.id.address);
        linearAccount=view.findViewById(R.id.linearAccount);
        saveInformationAccount=view.findViewById(R.id.saveInformationAccount);
        password=view.findViewById(R.id.password);
        btnChangePassword=view.findViewById(R.id.btnChangePassword);
        linearAccount.setVisibility(GONE);
        list.setVisibility(VISIBLE);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new FAccountLoading(getContext()));
        accountViewModel=new ViewModelProvider(getActivity()).get(AccountViewModel.class);
        saveInformationAccount();
        changePassword();
        accountViewModel.checkToken(getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null")).observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("error")){
                    linearAccount.setVisibility(GONE);
                    list.setVisibility(VISIBLE);
                    list.setLayoutManager(new LinearLayoutManager(getContext()));
                    list.setAdapter(new AdapterRequireLogin(getContext(), "برای دیدن حساب کاربری اول وارد حساب کاربری خودتون بشید", getActivity(), new AdapterRequireLogin.IEvent() {
                        @Override
                        public void loginOrRegisterd() {
                            linearAccount.setVisibility(VISIBLE);
                            list.setVisibility(GONE);
                            getInformationAccount();
                        }
                    }));
                }else if (s.equals("ok")){
                    linearAccount.setVisibility(VISIBLE);
                    list.setVisibility(GONE);
                           getInformationAccount();
                }
            }
        });
    }
    private void getInformationAccount(){
        accountViewModel.getInformationAccount(getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null")).observe(getActivity(), new Observer<ModelAccount>() {
            @Override
            public void onChanged(ModelAccount modelAccount) {
                username.setText(modelAccount.getUsername());
                name.setText(modelAccount.getName());
                phoneNumber.setText(modelAccount.getPhoneNumber());
                address.setText(modelAccount.getAddress());
                codePosti.setText(modelAccount.getCodePosti());
            }
        });
    }
    private void saveInformationAccount(){
        saveInformationAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().isEmpty()){
                    textError.setVisibility(VISIBLE);
                    textError.setText("نام و نام خانوادگی نمیتواند خالی باشد");
                    vibration(300);
                }else if (phoneNumber.getText().toString().isEmpty()){
                    textError.setVisibility(VISIBLE);
                    textError.setText("تلفن همراه نمیتواند خالی باشد");
                    vibration(300);
                }else if (codePosti.getText().toString().trim().isEmpty()){
                    textError.setVisibility(VISIBLE);
                    textError.setText("کد پستی نمیتواند خالی باشد");
                    vibration(300);
                }else if (address.getText().toString().trim().isEmpty()){
                    textError.setVisibility(VISIBLE);
                    textError.setText("آدرس نمیتواند خالی باشد");
                    vibration(300);
                }else{
                    String token=getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
                    accountViewModel.changeInformationAccount(token,name.getText().toString().trim(),phoneNumber.getText().toString().trim(),codePosti.getText().toString().trim(),address.getText().toString().trim()).observe(getActivity(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("200")){
                                BottomSheetDialog dialogSuccess=new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
                                dialogSuccess.setCancelable(true);
                                dialogSuccess.setContentView(R.layout.dsuccess);
                                MaterialButton btnOk=dialogSuccess.findViewById(R.id.btnOk);
                                dialogSuccess.show();
                                btnOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogSuccess.dismiss();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        TextWatcher textWatcher=new TextWatcher() {
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
        name.addTextChangedListener(textWatcher);
        phoneNumber.addTextChangedListener(textWatcher);
        codePosti.addTextChangedListener(textWatcher);
        address.addTextChangedListener(textWatcher);
    }
    private void changePassword(){
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText currentPassword,newPassword,repeatPassword;
                BottomSheetDialog dialogChangePassword=new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
                BottomSheetDialog dialogError=new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
                dialogError.setContentView(R.layout.derror);
                TextView textErrorDialog=dialogError.findViewById(R.id.textErrorDialog);
                MaterialButton btnDismissError=dialogError.findViewById(R.id.btnTryAgain);
                dialogChangePassword.setContentView(R.layout.dchange_password);
                dialogChangePassword.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                dialogChangePassword.show();
                MaterialButton btnBack=dialogChangePassword.findViewById(R.id.back);
                MaterialButton savePassword=dialogChangePassword.findViewById(R.id.savePassword);
                currentPassword=dialogChangePassword.findViewById(R.id.currentPassword);
                newPassword=dialogChangePassword.findViewById(R.id.newPassword);
                repeatPassword=dialogChangePassword.findViewById(R.id.repeatPassword);
                TextWatcher textWatcher=new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable editable) {

                    }

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        ((TextView)dialogChangePassword.findViewById(R.id.textError)).setVisibility(GONE);
                    }
                };
                currentPassword.addTextChangedListener(textWatcher);
                newPassword.addTextChangedListener(textWatcher);
                repeatPassword.addTextChangedListener(textWatcher);
                savePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentPassword.getText().toString().trim().isEmpty()){
                            ((TextView)dialogChangePassword.findViewById(R.id.textError)).setText("کلمه عبور فعلی نمیتواند خالی باشد");
                            ((TextView)dialogChangePassword.findViewById(R.id.textError)).setVisibility(VISIBLE);
                            vibration(200);
                        }else if (newPassword.getText().toString().trim().isEmpty()){
                            ((TextView)dialogChangePassword.findViewById(R.id.textError)).setText("کلمه عبور جدید نمیتواند خالی باشد");
                            ((TextView)dialogChangePassword.findViewById(R.id.textError)).setVisibility(VISIBLE);
                            vibration(200);
                        } else if (repeatPassword.getText().toString().trim().isEmpty()) {
                            ((TextView)dialogChangePassword.findViewById(R.id.textError)).setText("تکرار عبور جدید نمیتواند خالی باشد");
                            ((TextView)dialogChangePassword.findViewById(R.id.textError)).setVisibility(VISIBLE);
                            vibration(200);
                        } else if (!newPassword.getText().toString().trim().equals(repeatPassword.getText().toString().trim())){
                            dialogError.show();
                            textErrorDialog.setText("کلمه عبور جدید با تکرار آن همخوانی ندارد");
                            dialogChangePassword.dismiss();
                            btnDismissError.setOnClickListener((ve)->{
                                dialogError.dismiss();
                                dialogChangePassword.show();
                            });
                        }else {
                            String token=getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
                            accountViewModel.changePasswordAccount(token,currentPassword.getText().toString().trim(),newPassword.getText().toString().trim(),repeatPassword.getText().toString().trim()).observe(getActivity(), new Observer<ModelChangePasswordAccount>() {
                                @Override
                                public void onChanged(ModelChangePasswordAccount modelChangePasswordAccount) {
                                    if (modelChangePasswordAccount.getStatus().equals("invalidPassword")){
                                        vibration(300);
                                        dialogError.setCancelable(false);
                                        dialogChangePassword.dismiss();
                                        textErrorDialog.setText("کلمه عبور فعلی اشتباه است");
                                        dialogError.show();
                                        btnDismissError.setOnClickListener((v)->{
                                            dialogError.dismiss();
                                            dialogChangePassword.show();
                                        });
                                    }else if (modelChangePasswordAccount.getStatus().equals("success")){
                                        dialogChangePassword.dismiss();
                                        getActivity().getSharedPreferences("save",MODE_PRIVATE).edit().putString("token",modelChangePasswordAccount.getToken()).apply();
                                        BottomSheetDialog dialogSuccess=new BottomSheetDialog(getActivity());
                                        dialogSuccess.setContentView(R.layout.dsuccess);
                                        dialogSuccess.show();
                                        TextView textTitle=dialogSuccess.findViewById(R.id.textTitle);
                                        textTitle.setText("کلمه عبور  با موفقیت تغییر کرد");
                                        MaterialButton btnDismissSuccess=dialogSuccess.findViewById(R.id.btnOk);
                                        btnDismissSuccess.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialogSuccess.dismiss();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogChangePassword.dismiss();

                    }
                });
            }
        });
    }
    private void vibration(int duration){
        Vibrator vibrator= (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            vibrator.vibrate(VibrationEffect.createOneShot(duration,DEFAULT_AMPLITUDE));
        }
    }
}
