package mostafa.hafezypoor.shikshap.data.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import mostafa.hafezypoor.shikshap.data.model.ModelAccount;
import mostafa.hafezypoor.shikshap.data.model.ModelGetDetailPayment;
import mostafa.hafezypoor.shikshap.data.model.ModelGetImagesPayments;
import mostafa.hafezypoor.shikshap.data.model.ModelGetPayments;
import mostafa.hafezypoor.shikshap.data.model.ModelOrderInCart;
import mostafa.hafezypoor.shikshap.data.model.ModelTotalCart;
import mostafa.hafezypoor.shikshap.data.network.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FCartRepository {
    public LiveData<String>checkToken(String token){
        MutableLiveData<String>mutableLiveDataCheckToken=new MutableLiveData<>();
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
    public LiveData<List<ModelOrderInCart>>getOrderInCart(String token){
        MutableLiveData<List<ModelOrderInCart>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getOrderInCart(token).enqueue(new Callback<List<ModelOrderInCart>>() {
            @Override
            public void onResponse(Call<List<ModelOrderInCart>> call, Response<List<ModelOrderInCart>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelOrderInCart>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<List<ModelGetPayments>>getPayments(String token){
        MutableLiveData<List<ModelGetPayments>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getPayments(token).enqueue(new Callback<List<ModelGetPayments>>() {
            @Override
            public void onResponse(Call<List<ModelGetPayments>> call, Response<List<ModelGetPayments>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelGetPayments>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<ModelTotalCart>totalCart(String token){
        MutableLiveData<ModelTotalCart>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().totalCart(token).enqueue(new Callback<ModelTotalCart>() {
            @Override
            public void onResponse(Call<ModelTotalCart> call, Response<ModelTotalCart> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelTotalCart> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<String>addCart(String token,String product_id,String size){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().addCart(token,product_id,size).enqueue(new Callback<String>() {
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
    public LiveData<String>decreazeCart(String token,String product_id,String size){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().decreazeCart(token,product_id,size).enqueue(new Callback<String>() {
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
    public LiveData<String>deleteCart(String token,String product_id,String size){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().deleteCart(token,product_id,size).enqueue(new Callback<String>() {
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
    public LiveData<List<ModelGetImagesPayments>>getImagesPayments(String token,String payment_id){
        MutableLiveData<List<ModelGetImagesPayments>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getImagesPayments(token,payment_id).enqueue(new Callback<List<ModelGetImagesPayments>>() {
            @Override
            public void onResponse(Call<List<ModelGetImagesPayments>> call, Response<List<ModelGetImagesPayments>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelGetImagesPayments>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<List<ModelGetDetailPayment>>getDetailPayment(String token,String payment_id){
        MutableLiveData<List<ModelGetDetailPayment>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getDetailPayment(token,payment_id).enqueue(new Callback<List<ModelGetDetailPayment>>() {
            @Override
            public void onResponse(Call<List<ModelGetDetailPayment>> call, Response<List<ModelGetDetailPayment>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelGetDetailPayment>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<String>requestPayment(String token){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().requestPayment(token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Log.i("TAG12345", "onResponse: "+response.body());
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("TAG12345", "onFailure: "+t.getMessage());
            }
        });
        return mutableLiveData;
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
}
