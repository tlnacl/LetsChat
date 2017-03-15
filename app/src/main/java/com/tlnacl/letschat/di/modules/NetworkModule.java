package com.tlnacl.letschat.di.modules;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tlnacl.letschat.ApiAdapter;
import com.tlnacl.letschat.MyAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tlnacl on 14/03/17.
 */
@Module
public class NetworkModule {
    private static final String END_POINT = "https://api.github.com";

    @Provides
    @Singleton
    ApiAdapter provideApiAdapter(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(MyAdapterFactory.create())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(END_POINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiAdapter.class);
    }
}
