package com.is.retrofit2.tool;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    public static Retrofit retrofit;
    private static OkHttpClient okHttpClient = new OkHttpClient();

    static {
        okHttpClient.newBuilder().connectTimeout(5000, TimeUnit.MILLISECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public static <T> T createService(Class<T> clazz) {
        if (retrofit == null) {
            synchronized (RetrofitUtil.class) {
                Retrofit.Builder builder = new Retrofit.Builder();
                retrofit = builder.baseUrl("http://192.168.100.104:8080/UploadFileServer/")
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
            }
        }
        return retrofit.create(clazz);
    }
}