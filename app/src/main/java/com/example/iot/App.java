package com.example.iot;

import android.app.Application;
import android.net.ConnectivityManager;

import com.example.iot.model.DaoMaster;
import com.example.iot.model.DaoSession;
import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"user-db");
        Database db=helper.getWritableDb();

        daoSession=new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public boolean isNetworkConnected(){
        ConnectivityManager cm= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo()!=null;
    }
}
