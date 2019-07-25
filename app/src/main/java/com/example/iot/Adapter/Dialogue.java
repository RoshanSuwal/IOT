package com.example.iot.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.iot.DeviceControllerMVP.DeviceControllerContract;
import com.example.iot.R;
import com.example.iot.model.Switch;

import java.sql.Array;
import java.util.ArrayList;

public class Dialogue extends Dialog {

    Spinner spinner;
    EditText editText;
    Context context;
    LinearLayout linearLayout;
    Button saveBtn;

    public Switch aSwitch;

    ArrayAdapter<CharSequence> adapter;
    DeviceControllerContract.Presenter presenter;


    public Dialogue(Context context,DeviceControllerContract.Presenter presenter) {
        super(context);
        this.context=context;
        this.presenter=presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_add_switch);

        saveBtn=findViewById(R.id.save_pin);
        spinner=findViewById(R.id.spinner);
        editText=findViewById(R.id.pin_no_input_edit_text);
        linearLayout=findViewById(R.id.updateOptionview);

        adapter=ArrayAdapter.createFromResource(context,
                R.array.pin_no,android.R.layout.simple_dropdown_item_1line);


        spinner.setAdapter(adapter);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Dialogue", "onClick: "+ spinner.getSelectedItem().toString());
                String device_name=editText.getText().toString();
                if (!TextUtils.isEmpty(device_name)){
                    String pin_no=spinner.getSelectedItem().toString();
                    String intValue = pin_no.replaceAll("[^0-9]", "");
                    int id=Integer.parseInt(intValue);
                    Log.i("TAG", "onClick: "+id);
                    if (presenter.checkPinNoAlreadyUsed(spinner.getSelectedItem().toString())){
                        Toast.makeText(context,"Pin number already in use",Toast.LENGTH_LONG).show();

                    }else{

                        presenter.addSwitch(device_name,spinner.getSelectedItem().toString(),id);
                        dismiss();
                    }
                }

            }
        });

        findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteSwitch(aSwitch);
                dismiss();
            }
        });

        findViewById(R.id.updateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText().toString())){
                    if (aSwitch.getPin_number().equals(spinner.getSelectedItem().toString())){
                        aSwitch.setSwitch_name(editText.getText().toString());
                        presenter.updateSwitch(aSwitch);
                        dismiss();
                    }else{
                        String pin_no=spinner.getSelectedItem().toString();
                        String intValue = pin_no.replaceAll("[^0-9]", "");
                        int id=Integer.parseInt(intValue);
                        Log.i("TAG", "onClick: "+id);
                        if (presenter.checkPinNoAlreadyUsed(spinner.getSelectedItem().toString())){
                            Toast.makeText(context,"Pin number already in use",Toast.LENGTH_LONG).show();

                        }else{
                            presenter.deleteSwitch(aSwitch);
                            presenter.addSwitch(editText.getText().toString(),spinner.getSelectedItem().toString(),id);

                            dismiss();
                        }
                    }

                }
            }
        });
    }


    public void loadSwitchData(Switch aSwitch){
        this.aSwitch=aSwitch;
        saveBtn.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        ((EditText) findViewById(R.id.pin_no_input_edit_text)).setText(aSwitch.getSwitch_name());
        spinner.setSelection(adapter.getPosition(aSwitch.getPin_number()));
    }
}
