package com.example.desafiofluxitmvvm.api;

import com.example.desafiofluxitmvvm.models.ResultResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    String SERVICE_ENDPOINT = "https://randomuser.me/";


    @GET("api/")
    Call<ResultResponse> getRandomUsers(@Query("results") Integer results);

    @GET("api/")
    Call<ResultResponse> getNetPage(@Query("results") Integer results, @Query("seed") String seed, @Query("page") int page);


    // https://randomuser.me/api/?results=20&seed=eb00a46bf1fef3f1&page=2

    //https://randomuser.me/api/?results=2&seed=eb00a46bf1fef3f1&page=2

    /*@GET("/resolve.json?url={url}&client_id={clientId}")
    Call<Track> getTrack(@Path("url") String url,@Path("clientId") String clientId);*/

}
