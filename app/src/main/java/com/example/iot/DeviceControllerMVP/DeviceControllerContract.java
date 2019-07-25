package com.example.iot.DeviceControllerMVP;

import com.example.iot.model.Switch;

import java.util.ArrayList;

public interface DeviceControllerContract {
    interface MVPView{
        void addSwitchDialog();
        void noInternetDialog();
        void showListView(ArrayList<Switch> arrayList);

        void editSwitch(Switch aSwitch);
    }
    interface Presenter{
        void checkUserInDao(String token);
        boolean checkPinNoAlreadyUsed(String pinNo);
        void listviewItemClickListener(Switch aSwitch);
        void listviewItenLongClickListener(Switch aSwitch);
        void addDeviceBtnClick();
        void addSwitch(String device_name, String pin_value,long id);
        void getAllSwithes();

        void deleteSwitch(Switch aswitch);
        void updateSwitch(Switch aswitch);

    }

    interface RestServiceinterface{
        void addSwitchToDao(Switch aswitch);
        void updateToDao(Switch aswitch);
        void failToUpdate();
        void deleteIndao(Switch aswitch);
    }

}
