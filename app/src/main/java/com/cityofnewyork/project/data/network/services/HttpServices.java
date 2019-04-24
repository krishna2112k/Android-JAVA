package com.cityofnewyork.project.data.network.services;

import android.support.annotation.Nullable;
import com.cityofnewyork.project.data.network.interfaces.HttpInterface;
import com.cityofnewyork.project.data.network.interfaces.callbacks.GetSATDetails;
import com.cityofnewyork.project.data.network.services.Models.SATDetailsResponse;
import com.cityofnewyork.project.utils.BaseClass;

import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpServices {


    public void getSATDetails(String limit, String offset, @Nullable final GetSATDetails callbacks) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(45, TimeUnit.SECONDS)
                .build();
        HttpInterface httpInterface = new Retrofit.Builder().baseUrl(BaseClass.CityOfNewyorkTimes)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(HttpInterface.class);


        Call<List<SATDetailsResponse>> call = httpInterface.getSATDetails(limit, offset);
        call.enqueue(new Callback<List<SATDetailsResponse>>() {
            @Override
            public void onResponse(Call<List<SATDetailsResponse>> call, Response<List<SATDetailsResponse>> response) {
                try {
                    if (response.code() == 200) {
                        callbacks.onSuccess(response.body());
                    }
                    else {
                        callbacks.onError(new NoSuchFieldError());
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<SATDetailsResponse>> call, Throwable t) {
                callbacks.onError(new Exception(t));
            }
        });
    }


    public void getSATDetailsBySchoolName(String schoolName, @Nullable final GetSATDetails callbacks) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(45, TimeUnit.SECONDS)
                .build();
        HttpInterface httpInterface = new Retrofit.Builder().baseUrl(BaseClass.CityOfNewyorkTimes)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(HttpInterface.class);


        Call<List<SATDetailsResponse>> call = httpInterface.getSATDetailsBySchoolName(schoolName);
        call.enqueue(new Callback<List<SATDetailsResponse>>() {
            @Override
            public void onResponse(Call<List<SATDetailsResponse>> call, Response<List<SATDetailsResponse>> response) {
                try {
                    if (response.code() == 200) {
                        callbacks.onSuccess(response.body());
                    }
                    else {
                        callbacks.onError(new NoSuchFieldError());
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<SATDetailsResponse>> call, Throwable t) {
                callbacks.onError(new Exception(t));
            }
        });
    }
}
