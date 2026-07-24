package mostafa.hafezypoor.shikshap.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.logging.Handler;

import mostafa.hafezypoor.shikshap.data.model.ModelChat;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;
import mostafa.hafezypoor.shikshap.data.repository.FChatRepository;

public class ChatViewModel extends ViewModel {
    private final FChatRepository fChatRepository;
    public ChatViewModel(){
        fChatRepository=new FChatRepository();
    }
    public LiveData<String> chekcToken(String token){
        return     fChatRepository.checkToken(token);
    }
    public LiveData<List<ModelChat>>getChat(String token){
        return fChatRepository.getChat(token);
    }
    public LiveData<ModelChat>sendMessage(String token,String message){
        return fChatRepository.sendMessage(token,message);
    }
    public LiveData<ModelChat>longPollingNewMessageStart(String token, String last_id){
        return fChatRepository.longPollingNewMessageStart(token,last_id);
    }
    public void stopLongPollingNewMessage(){
        fChatRepository.stopLongPollingNewMessage();
    }
    public LiveData<ModelDetailProduct> getDetailProduct(String product_id) {
        return fChatRepository.getDetailProduct(product_id);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        stopLongPollingNewMessage();
    }
}
