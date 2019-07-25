package com.example.iot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.iot.Adapter.ListAdapter;
import com.example.iot.ProjectListMVP.ProjectDialog;
import com.example.iot.ProjectListMVP.ProjectListContract;
import com.example.iot.ProjectListMVP.ProjectPresenter;
import com.example.iot.model.DaoSession;
import com.example.iot.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectListActivity extends AppCompatActivity implements ProjectListContract.mvpView {

    private ProjectDialog projectDialog;
    private ProjectListContract.Presenter presenter;

    DaoSession daoSession;

    //views
    private ArrayList<Project> projectArrayList;
    ListView listView;
    private static ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        //MVP
        daoSession=((App)getApplication()).getDaoSession();
        presenter=new ProjectPresenter(this,daoSession,getApplicationContext());


        listView=findViewById(R.id.project_listview);
        projectArrayList=new ArrayList<>();

        listAdapter=new ListAdapter(getApplicationContext(),projectArrayList);
        listView.setAdapter(listAdapter);

        presenter.getprojectlist();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onListitemClickListener(projectArrayList.get(position));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onItemLongClickListener(projectArrayList.get(position));
                return true;
            }
        });

        findViewById(R.id.add_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addProjectdialog();
            }
        });


    }

    @Override
    public void showDialog() {
        projectDialog=new ProjectDialog(this,presenter);
        projectDialog.show();
    }

    @Override
    public void modifyProjectDialog(Project project) {
        projectDialog=new ProjectDialog(this,presenter);
        projectDialog.show();
        projectDialog.loadProject(project);
    }

    @Override
    public void showListView(ArrayList<Project> projectList) {
        projectArrayList.clear();
        this.projectArrayList.addAll(projectList);
        listAdapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.deferNotifyDataSetChanged();

        Log.d("VIEW", "showListView: "+projectArrayList.size());
    }

    @Override
    public void gotoDeviceControllActivity(Project project) {
        Intent intent=new Intent(ProjectListActivity.this,DeviceControllerActivity.class);
        intent.putExtra("token",project.getToken());
        intent.putExtra("name",project.getProjectName());
        startActivity(intent);
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this,info,Toast.LENGTH_SHORT).show();
    }
}
