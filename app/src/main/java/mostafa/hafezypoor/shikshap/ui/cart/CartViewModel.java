package mostafa.hafezypoor.shikshap.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mostafa.hafezypoor.shikshap.data.model.ModelAccount;
import mostafa.hafezypoor.shikshap.data.model.ModelGetDetailPayment;
import mostafa.hafezypoor.shikshap.data.model.ModelGetImagesPayments;
import mostafa.hafezypoor.shikshap.data.model.ModelGetPayments;
import mostafa.hafezypoor.shikshap.data.model.ModelOrderInCart;
import mostafa.hafezypoor.shikshap.data.model.ModelTotalCart;
import mostafa.hafezypoor.shikshap.data.repository.FCartRepository;

public class CartViewModel extends ViewModel {
    private final FCartRepository fCartRepository;
    private LiveData<String>checkToken;

    public CartViewModel() {
        this.fCartRepository=new FCartRepository();
    }
    public LiveData<String> chekcToken(String token){
        return     fCartRepository.checkToken(token);
    }
    public LiveData<List<ModelOrderInCart>>getOrders(String token){
        return fCartRepository.getOrderInCart(token);
    }
    public LiveData<List<ModelGetPayments>>getPayments(String token){
        return fCartRepository.getPayments(token);
    }
    public LiveData<ModelTotalCart>totalCart(String token){
        return fCartRepository.totalCart(token);
    }
    public LiveData<String>addCart(String token,String product_id,String size){
        return fCartRepository.addCart(token,product_id,size);
    }
    public LiveData<String>decreazeCart(String token,String product_id,String size){
        return fCartRepository.decreazeCart(token,product_id,size);
    }
    public LiveData<String>deleteCart(String token,String product_id,String size){
        return fCartRepository.deleteCart(token,product_id,size);
    }
    public LiveData<List<ModelGetImagesPayments>>getImagesPayments(String token,String payment_id){
        return fCartRepository.getImagesPayments(token,payment_id);
    }
    public LiveData<List<ModelGetDetailPayment>>getDetailPayment(String token,String payment_id){
        return fCartRepository.getDetailPayment(token,payment_id);
    }
    public LiveData<String>requestPayment(String token){
        return fCartRepository.requestPayment(token);
    }
    public LiveData<ModelAccount>getInformationAccount(String token){
        return fCartRepository.getInformationAccount(token);
    }
    public LiveData<String>changeInformationAccount(String token,String name,String phoneNumber,String codePosit,String address){
        return fCartRepository.changeInformationAccount(token,name,phoneNumber,codePosit,address);
    }
}
