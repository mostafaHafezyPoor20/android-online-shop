package mostafa.hafezypoor.shikshap.ui.cart;

import static android.content.Context.MODE_PRIVATE;
import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.List;

import mostafa.hafezypoor.shikshap.R;
import mostafa.hafezypoor.shikshap.data.model.ModelAccount;
import mostafa.hafezypoor.shikshap.data.model.ModelOrderInCart;
import mostafa.hafezypoor.shikshap.data.model.ModelTotalCart;
import mostafa.hafezypoor.shikshap.ui.common.AdapterRequireLogin;
import mostafa.hafezypoor.shikshap.ui.common.BottomSheetDialogLoading;

public class FOrderInCart extends Fragment {
    private RecyclerView list;
    private CartViewModel cartViewModel;
    private MaterialButton btnPayment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.forder_in_cart,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartViewModel=new ViewModelProvider(getActivity()).get(CartViewModel.class);
        list=view.findViewById(R.id.list);
        btnPayment=view.findViewById(R.id.btnPayment);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(new AdapterLoadingFOrderInCart(getContext()));
        cartViewModel.chekcToken(getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null")).observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("error")){
                    list.setLayoutManager(new LinearLayoutManager(getActivity()));
                    list.setAdapter(new AdapterRequireLogin(getActivity(), "میخوای سفارش های در انتظار پرداختت رو ببینی؟ اول وارد حساب کاربریت شو", getActivity(), new AdapterRequireLogin.IEvent() {
                        @Override
                        public void loginOrRegisterd() {
                           getOrders(view);
                        }
                    }));
                }else if (s.equals("ok")){
                    getOrders(view);
                }
            }
        });
    }
    private void getOrders(View view){
        String token=getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null");
        cartViewModel.getOrders(token).observe(getActivity(), new Observer<List<ModelOrderInCart>>() {
            @Override
            public void onChanged(List<ModelOrderInCart> modelOrderInCarts) {
               if (modelOrderInCarts.isEmpty()){
                   list.setLayoutManager(new LinearLayoutManager(getActivity()));
                   list.setAdapter(new AdapterEmptyCart(getActivity(),"سبد خریدت خالیه!فعلا محصولی برای پرداخت نداری"));
                   ((CardView)view.findViewById(R.id.bottom)).setVisibility(GONE);
               }else{
                  checkInformationAccount();
                   ((CardView)view.findViewById(R.id.bottom)).setVisibility(VISIBLE);
                   list.setLayoutManager(new LinearLayoutManager(getActivity()));
                   list.setAdapter(new AdapterFOrderInCart(getActivity(), modelOrderInCarts, getActivity(), new AdapterFOrderInCart.IEvent() {
                       @Override
                       public void addCart() {
                           totalCart(view);
                       }

                       @Override
                       public void decreazeCart(int size) {
                       if (size==0){
                           list.setLayoutManager(new LinearLayoutManager(getActivity()));
                           list.setAdapter(new AdapterEmptyCart(getActivity(),"سبد خریدت خالیه!فعلا محصولی برای پرداخت نداری"));
                           ((CardView)view.findViewById(R.id.bottom)).setVisibility(GONE);
                       }
                           totalCart(view);
                       }
                   }));
                   totalCart(view);
               }
            }
        });
    }
    private void totalCart(View view){
        cartViewModel.totalCart(getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null")).observe(getActivity(), new Observer<ModelTotalCart>() {
            @Override
            public void onChanged(ModelTotalCart modelTotalCart) {
                DecimalFormat decimalFormat=new DecimalFormat("#,###");
                String price=decimalFormat.format(Integer.parseInt(modelTotalCart.getTotalPrice()));
                ((TextView)view.findViewById(R.id.price)).setText(price+" تومان ");
                ((TextView)view.findViewById(R.id.orders)).setText(modelTotalCart.getCountOrder());


            }
        });
    }
    private void payment(BottomSheetDialogLoading loading){
        loading.setTitle("درحال اتصال به درگاه پرداخت");
                 cartViewModel.requestPayment(getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null")).observe(getActivity(), new Observer<String>() {
                     @Override
                     public void onChanged(String s) {
                         loading.dismiss();
                         Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://sandbox.zarinpal.com/pg/StartPay/"+s));
                         startActivity(intent);
                     }
                 });
             }


    private void checkInformationAccount(){
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialogCheckInformationAccount=new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
                dialogCheckInformationAccount.setCancelable(false);
                dialogCheckInformationAccount.setContentView(R.layout.dcheck_information_account);
                dialogCheckInformationAccount.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                TextInputEditText name=dialogCheckInformationAccount.findViewById(R.id.name);
                TextInputEditText phoneNumber=dialogCheckInformationAccount.findViewById(R.id.phoneNumber);
                TextInputEditText codePosti=dialogCheckInformationAccount.findViewById(R.id.codePosti);
                TextInputEditText address=dialogCheckInformationAccount.findViewById(R.id.address);
                TextView textError=dialogCheckInformationAccount.findViewById(R.id.textError);
                dialogCheckInformationAccount.show();
                cartViewModel.getInformationAccount(getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null")).observe(getActivity(), new Observer<ModelAccount>() {
                    @Override
                    public void onChanged(ModelAccount modelAccount) {
                        name.setText(modelAccount.getName());
                        phoneNumber.setText(modelAccount.getPhoneNumber());
                        codePosti.setText(modelAccount.getCodePosti());
                        address.setText(modelAccount.getAddress());
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
                        ((MaterialButton)dialogCheckInformationAccount.findViewById(R.id.btnDismiss)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogCheckInformationAccount.dismiss();
                            }
                        });
                        ((MaterialButton)dialogCheckInformationAccount.findViewById(R.id.saveAndContinue)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                BottomSheetDialogLoading bottomSheetDialogLoading=new BottomSheetDialogLoading(getContext(),"درحال ارتباط با درگاه بانک");
                                if (name.getText().toString().trim().isEmpty()){
                                    textError.setVisibility(VISIBLE);
                                    textError.setText("نام نمیتواند خالی باشد");
                                    vibration(250);
                                }else if (phoneNumber.getText().toString().trim().isEmpty()){
                                    textError.setVisibility(VISIBLE);
                                    textError.setText("تلفن همراه نمیتواند خالی باشد");
                                    vibration(250);
                                }else if (codePosti.getText().toString().trim().isEmpty()){
                                    textError.setVisibility(VISIBLE);
                                    textError.setText("کد پستی نمیتواند خالی باشد");
                                    vibration(250);
                                }else if (address.getText().toString().trim().isEmpty()){
                                    textError.setVisibility(VISIBLE);
                                    textError.setText("آدرس نمیتواند خالی باشد");
                                    vibration(250);
                                }else{
                                    bottomSheetDialogLoading.show();
                                    cartViewModel.changeInformationAccount(getActivity().getSharedPreferences("save",MODE_PRIVATE).getString("token","null"),name.getText().toString().trim(),phoneNumber.getText().toString().trim(),codePosti.getText().toString().trim(),address.getText().toString().trim()).observe(getActivity(), new Observer<String>() {
                                        @Override
                                        public void onChanged(String s) {
                                            if (s.equals("200")){
                                                bottomSheetDialogLoading.setTitle("درحال ثبت سفارش");
                                                payment(bottomSheetDialogLoading);
                                                dialogCheckInformationAccount.dismiss();
                                            }
                                        }
                                    });
                                }
                            }
                        });
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

