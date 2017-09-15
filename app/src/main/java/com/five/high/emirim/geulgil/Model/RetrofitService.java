package com.five.high.emirim.geulgil.Model;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by doori on 2017-09-13.
 */

public interface RetrofitService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://52.78.168.169/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @GET("request/{response}")
    Call<ApiItem> getApiItem(
            @Path("response") String request);

    @GET("request/{response}")
    Call<SameSounds> getSameSounds(
            @Path("response") String request);


}