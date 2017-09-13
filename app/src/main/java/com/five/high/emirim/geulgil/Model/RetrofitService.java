package com.five.high.emirim.geulgil.Model;

import com.five.high.emirim.geulgil.Model.ApiItem;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by doori on 2017-09-13.
 */

public interface RetrofitService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://52.78.168.169/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @GET("request/{response}")
    Call<ApiItem> getItem(
            @Path("response") String request);

}