package br.com.spm.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class NetworkConfig {
    public static final String CONTENT_TYPE = "content-type";
    public static final String AUTHORIZATION = "authorization";
    private static String authorization;

    public static OkHttpClient getClient() {
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(requestIntercept)
                .addInterceptor(getLoggingCapableHttpClient())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public static HttpLoggingInterceptor getLoggingCapableHttpClient() {
        HttpLoggingInterceptor mLogging = new HttpLoggingInterceptor();
        mLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return mLogging;
    }

    public static final Interceptor requestIntercept = new Interceptor() {
        public Response intercept(Chain chain) throws IOException {

            final Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();

            if (!isEmpty(authorization)) {
                requestBuilder.addHeader(AUTHORIZATION, authorization);
            }else{
                requestBuilder.addHeader(CONTENT_TYPE, "application/json");
            }

            final Request request = requestBuilder.build();
            final Response response = chain.proceed(request);

            return response;

        }
    };

    public static void setAuthorization(String authorization){
        NetworkConfig.authorization = authorization;
    }

    private static boolean isEmpty(String str) {
        if (str == null) return true;
        return str.trim().length() == 0;
    }
}
