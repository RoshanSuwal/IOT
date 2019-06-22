package com.example.iot.repository;

import android.content.Context;
import android.util.Log;

import com.example.iot.MainActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestRepository {
    private static RestRepository restRepository;

    public static RestRepository getInstance(){
        if (restRepository==null){
            restRepository=new RestRepository();
        }
        return restRepository;
    }

    private BlynkApi blynkApi;

    public RestRepository(){
        blynkApi=RetrofitService.createService(BlynkApi.class);
    }


//update the pin value
    public boolean updatePin(String token,String pin,int value){
        final boolean[] result = new boolean[1];
        blynkApi.updatePin(token,pin,value).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (call.isExecuted() && response.isSuccessful()){
                    try {

                        Log.i("TAG", "onResponse:"+new String(response.body().bytes()));
                        Log.i("TAG", call.request().toString());
                        result[0] =true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                result[0] =false;
            }
        });

        return result[0];
    }

    //get pin status from blynk server and update to the controller UI through inteface
    public int getPin(String token, String pin, final UiInterface uiInterface){
        final int[] i = new int[1];
        blynkApi.getPinValue(token,pin).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String string=new String(response.body().bytes());
                        i[0] = Integer.parseInt(string.replaceAll("\\D+",""));
                        Log.i("TAG", "getPin: "+i[0]);
                        uiInterface.setPinMethod(1,i[0]);
                        Log.i("TAG", "onResponse: "+ call.request());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    i[0]=100;
            }
        });
        Log.i("TAG", "getPin: "+i[0]);
        return i[0];
    }


}
