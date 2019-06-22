package com.example.iot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.iot.repository.RestRepository;
import com.example.iot.repository.UiInterface;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity  {
    Button button;
    RestRepository restRepository;
    String token="1d0d10b70911464fbe5b54c24fee87b8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.login_btn);
        restRepository=RestRepository.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result=restRepository.updatePin(token,"D6",1);
                Log.i("TAG", "onClick: "+result);
                startActivity(new Intent(MainActivity.this,Controller.class));
            }
        });
    }
}
