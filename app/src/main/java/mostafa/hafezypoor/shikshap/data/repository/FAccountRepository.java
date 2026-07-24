package mostafa.hafezypoor.shikshap.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import mostafa.hafezypoor.shikshap.data.model.ModelAccount;
import mostafa.hafezypoor.shikshap.data.model.ModelChangePasswordAccount;
import mostafa.hafezypoor.shikshap.data.network.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAccountRepository {
    public LiveData<String>checkToken(String token){
        MutableLiveData<String>mutableLiveDataToken=new MutableLiveData<>();
        RetrofitInit.getInstance().checkToken(token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    mutableLiveDataToken.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return mutableLiveDataToken;
    }
    public LiveData<ModelAccount>getInformationAccount(String token){
        MutableLiveData<ModelAccount>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getInformationAccount(token).enqueue(new Callback<ModelAccount>() {
            @Override
            public void onResponse(Call<ModelAccount> call, Response<ModelAccount> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelAccount> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<String>changeInformationAccount(String token,String name,String phoneNumber,String codePosti,String address){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().changeInformationAccount(token,name,phoneNumber,codePosti,address).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<ModelChangePasswordAccount>changePasswordAccount(String token,String currentPassword,String newPassword,String repeatPassword){
        MutableLiveData<ModelChangePasswordAccount>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().changePasswordAccount(token,currentPassword,newPassword,repeatPassword).enqueue(new Callback<ModelChangePasswordAccount>() {
            @Override
            public void onResponse(Call<ModelChangePasswordAccount> call, Response<ModelChangePasswordAccount> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelChangePasswordAccount> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
