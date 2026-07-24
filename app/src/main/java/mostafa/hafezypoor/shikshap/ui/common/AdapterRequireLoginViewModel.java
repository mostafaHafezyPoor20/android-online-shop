package mostafa.hafezypoor.shikshap.ui.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import mostafa.hafezypoor.shikshap.data.model.ModelLogin;
import mostafa.hafezypoor.shikshap.data.model.ModelRegister;
import mostafa.hafezypoor.shikshap.data.repository.AdapterRequireLoginRepository;

public class AdapterRequireLoginViewModel extends ViewModel {
    private final AdapterRequireLoginRepository repository;

    public AdapterRequireLoginViewModel() {
        this.repository =new AdapterRequireLoginRepository();
    }
    public LiveData<ModelLogin>login(String username,String password){
        return repository.login(username,password);
    }
    public LiveData<ModelRegister>register(String name,String username,String password){
        return repository.register(name,username,password);
    }
}
