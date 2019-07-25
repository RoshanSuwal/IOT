package com.example.iot.DeviceControllerMVP;

import android.content.Context;
import android.util.Log;

import com.example.iot.App;
import com.example.iot.model.DaoSession;
import com.example.iot.model.Switch;
import com.example.iot.model.Project;
import com.example.iot.repository.RestRepository;

public class DeviceControllerPresenter implements DeviceControllerContract.Presenter,DeviceControllerContract.RestServiceinterface{

    private DeviceControllerContract.MVPView mView;
    private DeviceControlRepo deviceControlRepo;
    private Context context;

    RestRepository restRepository;

    public DeviceControllerPresenter(DeviceControllerContract.MVPView view, DaoSession daoSession, Context context) {
        mView=view;
        deviceControlRepo=new DeviceControlRepo(daoSession);

        restRepository=RestRepository.getInstance();
        this.context=context;
    }

    @Override
    public void checkUserInDao(String token) {
        boolean exists=deviceControlRepo.doseProjectInDaoExixts(token);
        if (!exists){
            deviceControlRepo.createNewProjectInDao(new Project(token,"project"));
        }else {
            mView.showListView(deviceControlRepo.getAllSwitchesFromDao());
        }


        Log.d("DAO", "checkUserInDao: "+exists);
    }

    @Override
    public boolean checkPinNoAlreadyUsed(String pinNo) {
        return deviceControlRepo.pinAlreadyUsed(new Switch(pinNo));
    }

    @Override
    public void listviewItemClickListener(Switch aswitch) {
        // deviceControlRepo.updateSwitchStateinDao(aswitch);
       //  mView.showListView(deviceControlRepo.getAllSwitchesFromDao());
        Log.d("PRESENTER", "listviewItemClickListener: "+((App)context).isNetworkConnected());
        if (((App)context).isNetworkConnected()){
            restRepository.updateSwitchPinValue(aswitch,deviceControlRepo.getToken(),this);
        }else {
            mView.noInternetDialog();
        }

    }

    @Override
    public void listviewItenLongClickListener(Switch aSwitch) {
        mView.editSwitch(aSwitch);
    }

    @Override
    public void addDeviceBtnClick() {
        Log.d("Presenter", "addDeviceBtnClick: ");
        mView.addSwitchDialog();
    }

    @Override
    public void addSwitch(String device_name, String pin_value,long id) {
        Log.d("Presenter", "addSwitch: ");

        restRepository.addSwitch(new Switch(pin_value,0,device_name,id),deviceControlRepo.getToken(),this);
    }

    @Override
    public void getAllSwithes() {
        Log.d("Presenter", "getAllSwithes: ");
        mView.showListView(deviceControlRepo.getAllSwitchesFromDao());
    }

    @Override
    public void deleteSwitch(Switch aswitch) {
        //deviceControlRepo.deleteSwitch(aswitch);
       // mView.showListView(deviceControlRepo.getAllSwitchesFromDao());
        restRepository.deleteSwitch(aswitch,deviceControlRepo.getToken(),this);
    }

    @Override
    public void updateSwitch(Switch aswitch) {
        deviceControlRepo.updateSwitchinDao(aswitch);
        mView.showListView(deviceControlRepo.getAllSwitchesFromDao());
    }

    //--------------------REST REPOSITORY INTERFACE IMPLEMENTATION------------//

    @Override
    public void addSwitchToDao(Switch aswitch) {
        deviceControlRepo.addswitches(aswitch);
        mView.showListView(deviceControlRepo.getAllSwitchesFromDao());
    }

    @Override
    public void updateToDao(Switch aswitch) {
        deviceControlRepo.updateSwitchStateinDao(aswitch);
        mView.showListView(deviceControlRepo.getAllSwitchesFromDao());
    }

    @Override
    public void failToUpdate() {

    }

    @Override
    public void deleteIndao(Switch aswitch) {
        deviceControlRepo.deleteSwitch(aswitch);
        mView.showListView(deviceControlRepo.getAllSwitchesFromDao());
    }
}
