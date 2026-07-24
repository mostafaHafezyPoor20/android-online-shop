package mostafa.hafezypoor.shikshap.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import mostafa.hafezypoor.shikshap.data.repository.MainActivityRepository;

public class MainActivityViewModel extends ViewModel {
    private final MainActivityRepository mainActivityRepository;

    public MainActivityViewModel() {
        mainActivityRepository=new MainActivityRepository();
    }
    public LiveData<String>version(String version){
        return mainActivityRepository.version(version);
    }
    public LiveData<String>chekToken(String token){
        return mainActivityRepository.checkToken(token);
    }
    public LiveData<String>setUsersNotLoginFirebaseToken(String firebaseToken){
        return mainActivityRepository.setUsersNotLoginFirebaseToken(firebaseToken);
    }
    public LiveData<String>setUserFirebaseToken(String token,String firebaseToken){
        return mainActivityRepository.setUserFirebaseToken(token,firebaseToken);
    }
}
