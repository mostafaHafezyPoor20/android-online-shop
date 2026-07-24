package mostafa.hafezypoor.shikshap.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import mostafa.hafezypoor.shikshap.data.network.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityRepository {
    public LiveData<String>version(String version){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().version(version).enqueue(new Callback<String>() {
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
    public LiveData<String> checkToken(String token){
        MutableLiveData<String> mutableLiveDataCheckToken=new MutableLiveData<>();
        RetrofitInit.getInstance().checkToken(token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()){
                    mutableLiveDataCheckToken.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return mutableLiveDataCheckToken;
    }
    public LiveData<String>setUsersNotLoginFirebaseToken(String firebaseToken){
       MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
       RetrofitInit.getInstance().setUsersNotLoginFirebaseToken(firebaseToken).enqueue(new Callback<String>() {
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
    public LiveData<String>setUserFirebaseToken(String token,String firebaseToken){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().setUserFirebaseToken(token,firebaseToken).enqueue(new Callback<String>() {
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
}
