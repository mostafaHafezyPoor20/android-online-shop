package mostafa.hafezypoor.shikshap.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import mostafa.hafezypoor.shikshap.data.model.ModelAccount;
import mostafa.hafezypoor.shikshap.data.model.ModelChangePasswordAccount;
import mostafa.hafezypoor.shikshap.data.repository.FAccountRepository;

public class AccountViewModel extends ViewModel {
    private final FAccountRepository fAccountRepository;

    public AccountViewModel() {
        fAccountRepository=new FAccountRepository();
    }
    public LiveData<String>checkToken(String token){
        return fAccountRepository.checkToken(token);
    }
    public LiveData<ModelAccount>getInformationAccount(String token){
        return fAccountRepository.getInformationAccount(token);
    }
    public LiveData<String>changeInformationAccount(String token,String name,String phoneNumber,String codePosit,String address){
        return fAccountRepository.changeInformationAccount(token,name,phoneNumber,codePosit,address);
    }
    public LiveData<ModelChangePasswordAccount>changePasswordAccount(String token,String currentPassword,String newPassword,String repeatPassword){
        return fAccountRepository.changePasswordAccount(token,currentPassword,newPassword,repeatPassword);
    }
}
