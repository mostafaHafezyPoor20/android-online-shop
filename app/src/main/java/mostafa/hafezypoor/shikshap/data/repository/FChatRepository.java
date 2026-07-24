package mostafa.hafezypoor.shikshap.data.repository;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mostafa.hafezypoor.shikshap.data.model.ModelChat;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;
import mostafa.hafezypoor.shikshap.data.network.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FChatRepository{
    private boolean isRunning=true;
    private Call<ModelChat>currentChat;
    MutableLiveData<ModelChat>mutableLiveDataCurrentChat=new MutableLiveData<>();
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
    public LiveData<List<ModelChat>>getChat(String token){
        MutableLiveData<List<ModelChat>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getChat(token).enqueue(new Callback<List<ModelChat>>() {
            @Override
            public void onResponse(Call<List<ModelChat>> call, Response<List<ModelChat>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelChat>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<ModelChat>sendMessage(String token,String message){
        MutableLiveData<ModelChat>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().sendMessage(token,message).enqueue(new Callback<ModelChat>() {
            @Override
            public void onResponse(Call<ModelChat> call, Response<ModelChat> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelChat> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<ModelChat>longPollingNewMessageStart(String token,String last_id){
        isRunning=true;
        poll(token,last_id);
        return mutableLiveDataCurrentChat;
    }

    private  void poll(String token,String last_id){
      currentChat=  RetrofitInit.getInstance().longPollingNewMessage(token ,last_id);
      currentChat.enqueue(new Callback<ModelChat>() {
          @Override
          public void onResponse(Call<ModelChat> call, Response<ModelChat> response) {
              if (!isRunning||call.isCanceled())return;
              if (response.isSuccessful()&&response.body()!=null){
                  mutableLiveDataCurrentChat.setValue(response.body());
                  poll(token,response.body().getId());
              }else{
                  poll(token,last_id);
              }
          }

          @Override
          public void onFailure(Call<ModelChat> call, Throwable t) {

              if (!isRunning||call.isCanceled())return;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    poll(token,last_id);
                }
            },1000);
          }
      });
    }
    public void stopLongPollingNewMessage(){
        isRunning=false;
        if (currentChat!=null){
            if (!currentChat.isCanceled()){
                currentChat.cancel();
            }
        }
    }
    public LiveData<ModelDetailProduct>getDetailProduct(String product_id){
        MutableLiveData<ModelDetailProduct>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getDetailProduct(product_id).enqueue(new Callback<ModelDetailProduct>() {
            @Override
            public void onResponse(Call<ModelDetailProduct> call, Response<ModelDetailProduct> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelDetailProduct> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
