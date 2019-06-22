package com.example.iot;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.iot.model.Switch;
import com.example.iot.repository.BlynkApi;
import com.example.iot.repository.RestRepository;
import com.example.iot.repository.RetrofitService;
import com.example.iot.viewmodel.ControllerViewModel;

public class Controller extends AppCompatActivity {

    String token="1d0d10b70911464fbe5b54c24fee87b8";
    RestRepository restRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        final Button button=findViewById(R.id.switch1);
        final Button button1=findViewById(R.id.getdata_btn);
        final TextView textView=findViewById(R.id.text);

        restRepository= RestRepository.getInstance();

        final ControllerViewModel controllerViewModel= ViewModelProviders.of(this).get(ControllerViewModel.class);

        //initialise the livedate
        controllerViewModel.init();

        //update the pin status initially from server
        controllerViewModel.getPinData(restRepository,token,1);

        //change the switch button background according to the switch status
        controllerViewModel.getSwitch().observe(this, new Observer<Switch>() {
            @Override
            public void onChanged(@Nullable Switch aSwitch) {
                textView.setText("pin_value : "+(aSwitch.getPin_value()));
                if (aSwitch.getPin_value()==1){
                    button.setBackgroundColor(Color.GREEN);
                }else{
                    button.setBackgroundColor(Color.RED);
                }
            }
        });


        //get pin data method call in restRespository
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerViewModel.getPinData(restRepository,token,1);
            }
        });

        //update pin status
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerViewModel.updateData(restRepository,token,1);
            }
        });
    }
}
