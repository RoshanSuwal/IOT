package com.example.iot.repository;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BlynkApi {

    //write a pin value by get
    //..//token/update/pin?value=1
    @GET("/{token}/update/{pin}")
    Call<ResponseBody> updatePin(@Path("token") String token, @Path("pin") String pin, @Query("value")int value);


    //write a pin value by get
    //..//token/update/pin?value=1
    @GET("/{token}/update/{pin}")
    Call<ResponseBody> updatePinlabel(@Path("token") String token, @Path("pin") String pin, @Query("label")String label);

    //get pin value by get
    //..//token/get/pin
    @GET("/{token}/get/{pin}")
    Call<ResponseBody> getPinValue(@Path("token")String token,@Path("pin")String pin);

    //check hardware network status
    //../token/isHardwareConnected
    @GET("/{token}/isHardwareConnected")
    Call<ResponseBody> isHardwareConnected(@Path("token")String token);

    //application network status
    //../token/isAppConnected
    @GET("/{token}/isAppConnected")
    Call<Response> isAppConnected(@Path("token")String token);

    //get project connected at particular token
    //../token/project
    @GET("/{token}/project")
    Call<ResponseBody> getProject(@Path("token")String token);

    //set widget property
    //../token/
}
