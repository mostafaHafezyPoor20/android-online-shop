package mostafa.hafezypoor.shikshap.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mostafa.hafezypoor.shikshap.data.model.FHomeModelProduct;
import mostafa.hafezypoor.shikshap.data.model.FHomeModelGroup;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;
import mostafa.hafezypoor.shikshap.data.repository.FHomeRepository;

public class HomeViewModel extends ViewModel {
    private final  FHomeRepository fHomeRepository;

    private LiveData<List<FHomeModelProduct>>products;
    private LiveData<List<FHomeModelGroup>>groups;

    public HomeViewModel() {
        fHomeRepository=new FHomeRepository();
    }

    public LiveData<List<FHomeModelProduct>>getProducts(String group_id){
        products=fHomeRepository.getFHome(group_id);
        return products;
    }
    public LiveData<List<FHomeModelGroup>>getGroups(){
        groups=fHomeRepository.getGroups();
        return groups;
    }
    public LiveData<List<FHomeModelProduct>>getTopShow(){
        return fHomeRepository.getTopShowProduct();
    }
    public LiveData<List<ModelDetailProduct>>search(String search){
        return fHomeRepository.search(search);
    }
}
