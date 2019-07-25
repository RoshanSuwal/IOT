package com.example.iot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.example.iot.Adapter.Dialogue;
import com.example.iot.Adapter.RecyclerAdapter;
import com.example.iot.DeviceControllerMVP.DeviceControllerContract;
import com.example.iot.DeviceControllerMVP.DeviceControllerPresenter;
import com.example.iot.model.DaoSession;
import com.example.iot.model.Switch;
import com.example.iot.repository.RestRepository;

import java.util.ArrayList;

public class DeviceControllerActivity extends AppCompatActivity implements DeviceControllerContract.MVPView {


    //MVP
    private DeviceControllerPresenter mPresenter;

    //Blynk rest linking tools
    //String token="1d0d10b70911464fbe5b54c24fee87b8";//Raspbeerry pi
    //String token="c702da0cf9fa4882b1e759bcb859d70d";//node muc
    //String token="x0QJ1sfVvMfuM3Eg5wyUWAcvlzh8lysL";
    String token;
    String projectname;
    RestRepository restRepository;


    GridView gridView;
    RecyclerView recyclerView,recyclergridView;

    ArrayList<Switch> switchArrayList;
    ArrayList<Switch> labelArraylist;

    private static ListAdapter listAdapter;
    private static RecyclerAdapter recyclerAdapter;
    private static RecyclerAdapter recyclergridAdapter;

    public Dialogue dialogue;

    DaoSession daoSession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_controller);

        token=getIntent().getStringExtra("token");
        projectname=getIntent().getStringExtra("name");

        //MVP
        daoSession=((App)getApplication()).getDaoSession();
        mPresenter=new DeviceControllerPresenter(this,daoSession, getApplicationContext());



        //getting retroifit object
        Context context= getApplicationContext();
        restRepository=RestRepository.getInstance();

        gridView=findViewById(R.id.gridview);

        recyclerView=findViewById(R.id.recycler_view);
        recyclergridView=findViewById(R.id.recycler_grid_view);

        //listview management

        switchArrayList=new ArrayList<>();
        labelArraylist=new ArrayList<>();
        //listAdapter=new com.example.iot.Adapter.ListAdapter(getApplicationContext(),switchArrayList);

        findViewById(R.id.floating_action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addDeviceBtnClick();
            }
        });

        recyclerAdapter=new RecyclerAdapter(labelArraylist,mPresenter);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        recyclergridAdapter=new RecyclerAdapter(switchArrayList,mPresenter);
        RecyclerView.LayoutManager layoutManager1=new GridLayoutManager(getApplicationContext(),3);
        recyclergridView.setLayoutManager(layoutManager1);
        recyclergridView.setAdapter(recyclergridAdapter);

        gridView.setAdapter(listAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ON item click", "onItemClick: "+ position);
                mPresenter.listviewItemClickListener(switchArrayList.get(position));

            }
        });

     gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
             mPresenter.listviewItenLongClickListener(switchArrayList.get(position));
             return true;
         }
     });


     mPresenter.checkUserInDao(token);






    }

    @Override
    protected void onStart() {
        super.onStart();
        if (restRepository==null){
            restRepository=RestRepository.getInstance();
        }
        restRepository.isAppConnected(token);

       // requestAllExistingpin(restRepository,token);
    }


    ///MVP view

    @Override
    public void addSwitchDialog() {
        dialogue=new Dialogue(this,mPresenter);
        dialogue.show();
    }

    @Override
    public void noInternetDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(DeviceControllerActivity.this);
        builder.setMessage("please check your network connection");
        builder.setTitle("No Internet Connection");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void showListView(ArrayList<Switch> arrayList) {
        switchArrayList.clear();
        switchArrayList.addAll(arrayList);

        Log.d("Activity", "showListView: "+switchArrayList.size());

        gridView.deferNotifyDataSetChanged();
        gridView.invalidateViews();

        recyclerAdapter.notifyDataSetChanged();
        recyclergridAdapter.notifyDataSetChanged();

    }

    @Override
    public void editSwitch(Switch aSwitch) {
        dialogue=new Dialogue(this,mPresenter);
        dialogue.show();
        dialogue.loadSwitchData(aSwitch);
    }
}
