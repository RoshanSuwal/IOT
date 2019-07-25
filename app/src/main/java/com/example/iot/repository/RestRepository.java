package com.example.iot.repository;

import android.util.Log;

import com.example.iot.DeviceControllerMVP.DeviceControllerContract;
import com.example.iot.model.Switch;

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



    //check that application is connected to the server and has active project with provided token
    public void isAppConnected(String token){
        blynkApi.isAppConnected(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()){
                        String string=new String(response.body().bytes());
                        Log.d("IOT", "onResponse: "+string);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    //////////////////////////////////Presenter

    public void updateSwitchPinValue(final Switch asitch, String token, final DeviceControllerContract.RestServiceinterface restServiceinterface){
        blynkApi.updatePin(token,asitch.getPin_number(),asitch.getInversePinStateValue()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if ( call.isExecuted() && response.isSuccessful()){
                    restServiceinterface.updateToDao(asitch);
                    Log.d("RestApi",call.request().toString()+" : successful");
                }else {
                    Log.d("RestApi",call.request().toString()+" : failure");
                    restServiceinterface.failToUpdate();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                restServiceinterface.failToUpdate();
            }
        });
    }

    public void addSwitch(final Switch aswitch, String token, final DeviceControllerContract.RestServiceinterface restServiceinterface){
        blynkApi.updatePin(token,aswitch.getPin_number(),aswitch.getPin_value()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (call.isExecuted() && response.isSuccessful()){
                    restServiceinterface.addSwitchToDao(aswitch);
                    Log.d("RestApi","ADD SWITCH"+call.request().toString()+" : successful");
                }else{
                    restServiceinterface.failToUpdate();
                    Log.d("RestApi","ADD SWITCH"+call.request().toString()+" : failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                restServiceinterface.failToUpdate();
            }
        });
    }

    public void deleteSwitch(final Switch aswitch, String token, final DeviceControllerContract.RestServiceinterface restServiceinterface){
        blynkApi.updatePin(token,aswitch.getPin_number(),-10).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (call.isExecuted() && response.isSuccessful()){
                    restServiceinterface.deleteIndao(aswitch);
                    Log.d("RestApi","Delete SWITCH"+call.request().toString()+" : successful");
                }else {
                    restServiceinterface.failToUpdate();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                restServiceinterface.failToUpdate();
            }
        });
    }



}
