package mostafa.hafezypoor.shikshap.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mostafa.hafezypoor.shikshap.data.model.FHomeModelProduct;
import mostafa.hafezypoor.shikshap.data.model.FHomeModelGroup;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;
import mostafa.hafezypoor.shikshap.data.network.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FHomeRepository {
    public LiveData<List<FHomeModelProduct>>getFHome(String group_id){
        MutableLiveData<List<FHomeModelProduct>>productsFHomeLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getProducts(group_id).enqueue(new Callback<List<FHomeModelProduct>>() {
            @Override
            public void onResponse(Call<List<FHomeModelProduct>> call, Response<List<FHomeModelProduct>> response) {
                if (response.isSuccessful()){
                    productsFHomeLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<FHomeModelProduct>> call, Throwable t) {

            }
        });
        return productsFHomeLiveData;
    }
    public LiveData<List<FHomeModelGroup>>getGroups(){
        MutableLiveData<List<FHomeModelGroup>>groupMutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getGroups().enqueue(new Callback<List<FHomeModelGroup>>() {
            @Override
            public void onResponse(Call<List<FHomeModelGroup>> call, Response<List<FHomeModelGroup>> response) {
                groupMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<FHomeModelGroup>> call, Throwable t) {
                Log.i("TAG12345", "onFailure: "+t.getMessage());
            }
        });
        return groupMutableLiveData;
    }
    public LiveData<List<FHomeModelProduct>>getTopShowProduct(){
        MutableLiveData<List<FHomeModelProduct>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getTopProductShow().enqueue(new Callback<List<FHomeModelProduct>>() {
            @Override
            public void onResponse(Call<List<FHomeModelProduct>> call, Response<List<FHomeModelProduct>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<FHomeModelProduct>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<List<ModelDetailProduct>>search(String search){
        MutableLiveData<List<ModelDetailProduct>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().search(search).enqueue(new Callback<List<ModelDetailProduct>>() {
            @Override
            public void onResponse(Call<List<ModelDetailProduct>> call, Response<List<ModelDetailProduct>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelDetailProduct>> call, Throwable t) {
                Log.i("TAG12345", "onFailure: "+t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
