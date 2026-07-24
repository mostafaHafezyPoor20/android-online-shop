package mostafa.hafezypoor.shikshap.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import mostafa.hafezypoor.shikshap.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInit {
    private static Retrofit retrofit;
    public static IConnection getInstance(){
        if (retrofit==null){
            Gson gson=new GsonBuilder()
                    .setLenient()
                            .create();
            OkHttpClient okHttpClient=new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60,TimeUnit.SECONDS)
                    .writeTimeout(60,TimeUnit.SECONDS)
                    .build();

            retrofit=new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))

                    .build();
        }
        return retrofit.create(IConnection.class);
    }
}
