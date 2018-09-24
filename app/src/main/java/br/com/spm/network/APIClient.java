package br.com.spm.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static String API_BASE_URL = "https://dev.people.com.ai/mobile/api/v2/";

    private static OkHttpClient httpClient = NetworkConfig.getClient();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
