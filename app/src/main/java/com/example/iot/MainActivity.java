package com.example.iot;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.iot.repository.RestRepository;

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
               // boolean result=restRepository.updatePin(token,"D6",1);
                // Log.i("TAG", "onClick: "+result);
                startActivity(new Intent(MainActivity.this,ProjectListActivity.class));
                Log.d("TAG", "onCreate: "+((App)getApplication()).isNetworkConnected());
            }
        });

        findViewById(R.id.newaccount_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DeviceControllerActivity.class);
                intent.putExtra("token","c702da0cf9fa4882b1e759bcb859d70d");
                intent.putExtra("name","IOT");
                startActivity(intent);
            }
        });

        Log.d("TAG", "onCreate: "+isNetworkConnected());
    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo()!=null;
    }
}
