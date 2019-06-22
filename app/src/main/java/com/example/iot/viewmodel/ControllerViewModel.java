package com.example.iot.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.iot.model.Switch;
import com.example.iot.repository.RestRepository;
import com.example.iot.repository.UiInterface;

import java.util.ArrayList;

public class ControllerViewModel extends ViewModel implements UiInterface{
   // RestRepository restRepository;
    ArrayList<Switch> switchArrayList=new ArrayList<>();
    UiInterface uiInterface=this;



    private MutableLiveData<Switch> mutableLiveData=new MutableLiveData<>();

    private MutableLiveData<ArrayList<Switch>> arrayListMutableLiveData=new MutableLiveData<>();

    public void  init(){
        mutableLiveData=new MutableLiveData<>();
        mutableLiveData.setValue(new Switch("D6",0));

    }

    public MutableLiveData<Switch> getSwitch(){
        if (mutableLiveData==null){
            mutableLiveData.setValue(new Switch("D6",0));
        }

        Log.i("TAG", "getSwitch: "+mutableLiveData.getValue().getPin_value());

        return mutableLiveData;
    }

    public MutableLiveData<ArrayList<Switch>> getSwitches(){
        if (arrayListMutableLiveData==null){

            arrayListMutableLiveData=new MutableLiveData<>();
        }
        return  arrayListMutableLiveData;
    }

    public void updateData(RestRepository restRepository, String token, int switch_no){
        Switch aSwitch1switch=getSwitch().getValue();
        if (aSwitch1switch.getPin_value() == 1){
           restRepository.updatePin(token,aSwitch1switch.getPin_number(),0);
           getPinData(restRepository,token,1);
         //  mutableLiveData.setValue(new Switch("D6",0));
            Log.i("TAG", "updateData: 0");

        }else {
            restRepository.updatePin(token,aSwitch1switch.getPin_number(),1);
            getPinData(restRepository,token,1);
           // mutableLiveData.setValue(new Switch("D6",1));
           // Log.i("TAG", "updateData: 1");

        }
    }

    public void getPinData(RestRepository restRepository,String token,int switch_no){

            int i = restRepository.getPin(token, getSwitch().getValue().getPin_number(),uiInterface);
            if (i == 0) {
                mutableLiveData.setValue(new Switch("D6", i));
            } else if (i == 1) {
                mutableLiveData.setValue(new Switch("D6", i));
            } else {
               // i=100;

            }

            Log.i("TAG", "getPinData:"+i);
    }


    @Override
    public void setPinMethod(int pin_no, int pin_value) {
        mutableLiveData.setValue(new Switch("D6", pin_value));
    }
}
