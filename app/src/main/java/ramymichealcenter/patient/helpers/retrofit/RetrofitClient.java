package ramymichealcenter.patient.helpers.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import ramymichealcenter.patient.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by mhasan on 4/16/2017.
 */

public class RetrofitClient {

    private static OkHttpClient okHttpClient;

    public static MichealServices create() {

        /*-------------------*/
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .setPrettyPrinting() // Pretty print
                .create();
        /*-------------------*/

        okHttpClient = buildClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        return retrofit.create(MichealServices.class);
    }

    /**
     * Method to build and return an OkHttpClient so we can set/get
     * headers quickly and efficiently.
     *
     * @return
     */
    private static OkHttpClient buildClient() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
                return chain.proceed(request);
            }
        }).addNetworkInterceptor(httpLoggingInterceptor);

        return builder.connectTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).build();
    }

}
