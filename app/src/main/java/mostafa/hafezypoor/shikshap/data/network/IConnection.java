package mostafa.hafezypoor.shikshap.data.network;

import java.util.List;

import mostafa.hafezypoor.shikshap.data.model.FHomeModelProduct;
import mostafa.hafezypoor.shikshap.data.model.FHomeModelGroup;
import mostafa.hafezypoor.shikshap.data.model.ModelAccount;
import mostafa.hafezypoor.shikshap.data.model.ModelAdapterViewPagerImagesProduct;
import mostafa.hafezypoor.shikshap.data.model.ModelChangePasswordAccount;
import mostafa.hafezypoor.shikshap.data.model.ModelChat;
import mostafa.hafezypoor.shikshap.data.model.ModelComments;
import mostafa.hafezypoor.shikshap.data.model.ModelDetailProduct;
import mostafa.hafezypoor.shikshap.data.model.ModelGetDetailPayment;
import mostafa.hafezypoor.shikshap.data.model.ModelGetImagesPayments;
import mostafa.hafezypoor.shikshap.data.model.ModelGetPayments;
import mostafa.hafezypoor.shikshap.data.model.ModelLogin;
import mostafa.hafezypoor.shikshap.data.model.ModelOrderInCart;
import mostafa.hafezypoor.shikshap.data.model.ModelRegister;
import mostafa.hafezypoor.shikshap.data.model.ModelSizes;
import mostafa.hafezypoor.shikshap.data.model.ModelTotalCart;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IConnection {
    @FormUrlEncoded
    @POST("product/getProducts.php")
    Call<List<FHomeModelProduct>>getProducts(@Field("group_id")String group_id);

    @POST("product/getGroups.php")
    Call<List<FHomeModelGroup>>getGroups();

    @FormUrlEncoded
    @POST("user/checkToken.php")
    Call<String>checkToken(@Field("token")String token);

    @FormUrlEncoded
    @POST("user/login.php")
    Call<ModelLogin>login(@Field("username")String username,@Field("password")String password);

    @FormUrlEncoded
    @POST("user/register.php")
    Call<ModelRegister>register(@Field("name")String name,@Field("username")String username,@Field("password")String password);

    @FormUrlEncoded
    @POST("cart/getOrderInCart.php")
    Call<List<ModelOrderInCart>>getOrderInCart(@Field("token")String token);

    @FormUrlEncoded
    @POST("user/getInformationAccount.php")
    Call<ModelAccount>getInformationAccount(@Field("token")String token);

    @FormUrlEncoded
    @POST("user/changeInformationAccount.php")
    Call<String>changeInformationAccount(@Field("token")String token,@Field("name")String name,@Field("phoneNumber")String phoneNumber,@Field("codePosti")String codePosti,@Field("address")String address);

   @FormUrlEncoded
    @POST("user/changePasswordAccount.php")
    Call<ModelChangePasswordAccount>changePasswordAccount(@Field("token")String token,@Field("currentPassword")String currentPassword,@Field("newPassword")String newPassword,@Field("repeatPassword")String repeatPassword);

   @FormUrlEncoded
   @POST("product/getImagesProduct.php")
   Call<List<ModelAdapterViewPagerImagesProduct>>getImagesProduct(@Field("product_id")String product_id);

   @FormUrlEncoded
   @POST("product/getDetailProduct.php")
   Call<ModelDetailProduct>getDetailProduct(@Field("product_id")String product_id);

   @FormUrlEncoded
   @POST("comments/getCommentsProduct.php")
   Call<List<ModelComments>>getCommentsProduct(@Field("product_id")String product_id);

   @POST("product/getTopShow.php")
    Call<List<FHomeModelProduct>>getTopProductShow();

   @FormUrlEncoded
    @POST("comments/addComment.php")
    Call<String>addComment(@Field("token")String token,@Field("comment")String comment,@Field("product_id")String product_id);

   @FormUrlEncoded
   @POST("cart/checkProductInCart.php")
   Call<String>checkProductInCart(@Field("token")String token,@Field("product_id")String product_id);

   @FormUrlEncoded
   @POST("cart/addCart.php")
   Call<String>addCart(@Field("token")String token,@Field("product_id")String product_id,@Field("size")String size);
   @FormUrlEncoded
    @POST("cart/getPayments.php")
    Call<List<ModelGetPayments>>getPayments(@Field("token")String token);

   @FormUrlEncoded
   @POST("cart/totalCart.php")
   Call<ModelTotalCart>totalCart(@Field("token")String token);

   @FormUrlEncoded
   @POST("cart/decreazeCart.php")
   Call<String>decreazeCart(@Field("token")String token,@Field("product_id")String product_id,@Field("size")String size);

   @FormUrlEncoded
   @POST("cart/deleteCart.php")
   Call<String>deleteCart(@Field("token")String token,@Field("product_id")String product_id,@Field("size")String size);

   @FormUrlEncoded
   @POST("cart/getImagesPayments.php")
   Call<List<ModelGetImagesPayments>>getImagesPayments(@Field("token")String token,@Field("payment_id")String payment_id);

   @FormUrlEncoded
   @POST("cart/getDetailPayment.php")
   Call<List<ModelGetDetailPayment>>getDetailPayment(@Field("token")String token,@Field("payment_id")String payment_id);

   @FormUrlEncoded
   @POST("payment/requestPayment.php")
   Call<String>requestPayment(@Field("token")String token);

   @FormUrlEncoded
   @POST("appSetting/version.php")
   Call<String>version(@Field("version")String version);

   @FormUrlEncoded
   @POST("product/searchProduct.php")
   Call<List<ModelDetailProduct>>search(@Field("search")String search);

   @FormUrlEncoded
   @POST("chat/getChat.php")
   Call<List<ModelChat>>getChat(@Field("token")String token);

   @FormUrlEncoded
   @POST("chat/sendMessage.php")
   Call<ModelChat>sendMessage(@Field("token")String token,@Field("message")String message);

   @FormUrlEncoded
   @POST("chat/longPollingNewMessage.php")
   Call<ModelChat>longPollingNewMessage(@Field("token")String token,@Field("last_id")String last_id);

   @FormUrlEncoded
   @POST("user/setUsersNotLoginFirebaseToken.php")
   Call<String>setUsersNotLoginFirebaseToken(@Field("firebaseToken")String firebaseToken);

   @FormUrlEncoded
   @POST("user/setUserFirebaseToken.php")
   Call<String>setUserFirebaseToken(@Field("token")String token,@Field("firebaseToken")String firebaseToken);

   @FormUrlEncoded
   @POST("product/getSizesProduct.php")
   Call<List<ModelSizes>>getSizesProduct(@Field("token")String token,@Field("product_id")String product_id);
}
