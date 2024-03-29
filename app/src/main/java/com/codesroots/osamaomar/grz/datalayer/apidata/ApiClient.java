package com.codesroots.osamaomar.grz.datalayer.apidata;

import androidx.annotation.NonNull;

import com.codesroots.osamaomar.grz.models.helper.MyApplication;
import com.codesroots.osamaomar.grz.models.helper.ResourceUtil;

import java.util.concurrent.TimeUnit;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static final String BASE_URL = "http://grz.codesroots.com/api/";
    //private static final String BASE_URL = "http://grz.codesroots.com/api/";
    private static final int TIMEOUT = 120;
    private static Retrofit retrofit = null;

    @NonNull
    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient()
                .newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder();
                    builder.addHeader("Accept", "application/json");
                    builder.addHeader("Content-Type", "application/json");
                    builder.addHeader("lang", ResourceUtil.getCurrentLanguage(MyApplication.getInstance()));
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                })
                .build();
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory( RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }

}