package mostafa.hafezypoor.shikshap.ui.product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mostafa.hafezypoor.shikshap.data.model.ModelAdapterViewPagerImagesProduct;
import mostafa.hafezypoor.shikshap.data.model.ModelComments;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;
import mostafa.hafezypoor.shikshap.data.model.ModelLogin;
import mostafa.hafezypoor.shikshap.data.model.ModelRegister;
import mostafa.hafezypoor.shikshap.data.model.ModelSizes;
import mostafa.hafezypoor.shikshap.data.repository.ProductRepository;

public class ProductViewModel extends ViewModel {
    private final ProductRepository productRepository;

    public ProductViewModel() {
        productRepository = new ProductRepository();
    }

    public LiveData<List<ModelAdapterViewPagerImagesProduct>> getImagesProduct(String product_id) {
        return productRepository.getImagesProduct(product_id);
    }

    public LiveData<ModelDetailProduct> getDetailProduct(String product_id) {
        return productRepository.getDetailProduct(product_id);
    }

    public LiveData<List<ModelComments>> getCommentsProduct(String product_id) {
        return productRepository.getCommentsProduct(product_id);
    }
   public LiveData<String>checkToken(String token){
        return productRepository.checkToken(token);
   }
    public LiveData<ModelLogin>login(String username,String password){
        return productRepository.login(username,password);
    }
    public LiveData<ModelRegister>register(String name, String username, String password){
        return productRepository.register(name,username,password);
    }
    public LiveData<String>addComment(String token,String comment,String product_id){
        return productRepository.addComment(token,comment,product_id);
    }
    public LiveData<String>checkProductInCart(String token,String product_id){
        return productRepository.checkProductInCart(token,product_id);
    }
    public LiveData<String>addCart(String token,String product_id,String size){
        return productRepository.addCart(token,product_id,size);
    }
    public LiveData<List<ModelSizes>>getSizesProduct(String token,String product_id){
        return productRepository.getSizesProduct(token,product_id);
    }
}