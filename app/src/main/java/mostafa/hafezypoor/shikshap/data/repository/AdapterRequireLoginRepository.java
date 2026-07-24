package mostafa.hafezypoor.shikshap.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import mostafa.hafezypoor.shikshap.data.model.ModelLogin;
import mostafa.hafezypoor.shikshap.data.model.ModelRegister;
import mostafa.hafezypoor.shikshap.data.network.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRequireLoginRepository {
    public LiveData<ModelLogin> login(String username,String password){
        MutableLiveData<ModelLogin>mutableLiveDataLogin=new MutableLiveData<>();
        RetrofitInit.getInstance().login(username,password).enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                if (response.isSuccessful()){
                    mutableLiveDataLogin.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {

            }
        });
        return mutableLiveDataLogin;
    }
    public LiveData<ModelRegister>register(String name,String username,String password){
        MutableLiveData<ModelRegister>registerMutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().register(name,username,password).enqueue(new Callback<ModelRegister>() {
            @Override
            public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {
                if (response.isSuccessful()){
                    registerMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelRegister> call, Throwable t) {

            }
        });
        return registerMutableLiveData;
    }
}
