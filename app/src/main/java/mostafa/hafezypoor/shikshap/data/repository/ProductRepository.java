package mostafa.hafezypoor.shikshap.data.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mostafa.hafezypoor.shikshap.data.model.ModelAdapterViewPagerImagesProduct;
import mostafa.hafezypoor.shikshap.data.model.ModelComments;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;
import mostafa.hafezypoor.shikshap.data.model.ModelLogin;
import mostafa.hafezypoor.shikshap.data.model.ModelRegister;
import mostafa.hafezypoor.shikshap.data.model.ModelSizes;
import mostafa.hafezypoor.shikshap.data.network.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class ProductRepository {
    public LiveData<List<ModelAdapterViewPagerImagesProduct>>getImagesProduct(String product_id){
        MutableLiveData<List<ModelAdapterViewPagerImagesProduct>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getImagesProduct(product_id).enqueue(new Callback<List<ModelAdapterViewPagerImagesProduct>>() {
            @Override
            public void onResponse(Call<List<ModelAdapterViewPagerImagesProduct>> call, Response<List<ModelAdapterViewPagerImagesProduct>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelAdapterViewPagerImagesProduct>> call, Throwable t) {

            }
        });
        return mutableLiveData;
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
    public LiveData<List<ModelComments>>getCommentsProduct(String product_id){
        MutableLiveData<List<ModelComments>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getCommentsProduct(product_id).enqueue(new Callback<List<ModelComments>>() {
            @Override
            public void onResponse(Call<List<ModelComments>> call, Response<List<ModelComments>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelComments>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<String>checkToken(String token){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().checkToken(token).enqueue(new Callback<String>() {
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
    public LiveData<ModelLogin> login(String username, String password){
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
    public LiveData<ModelRegister>register(String name, String username, String password){
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
    public LiveData<String>addComment(String token,String comment,String product_id){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().addComment(token,comment,product_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("TAG12345", "onResponse: "+response.body());
                if (response.isSuccessful()){
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
    public LiveData<String>checkProductInCart(String token,String product_id){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().checkProductInCart(token,product_id).enqueue(new Callback<String>() {
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
    public LiveData<List<ModelSizes>>getSizesProduct(String token,String product_id){
        MutableLiveData<List<ModelSizes>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getSizesProduct(token,product_id).enqueue(new Callback<List<ModelSizes>>() {
            @Override
            public void onResponse(Call<List<ModelSizes>> call, Response<List<ModelSizes>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSizes>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
