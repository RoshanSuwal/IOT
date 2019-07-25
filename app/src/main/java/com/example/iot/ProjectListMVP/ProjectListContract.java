package com.example.iot.ProjectListMVP;

import com.example.iot.model.Project;

import java.util.ArrayList;

public interface ProjectListContract {

    interface mvpView{
        void showDialog();
        void modifyProjectDialog(Project project);
        void showListView(ArrayList<Project> projectArrayList);
        void gotoDeviceControllActivity(Project project);
        void showInfo(String info);

    }

    interface Presenter{
        boolean projectAlreadyExists(String token);
        void addProjectdialog();
        void getprojectlist();
        void addproject(String projectname,String token);
        void onListitemClickListener(Project project);
        void onItemLongClickListener(Project project);
        void deleteProject(Project project);
        void updateProject(Project project);
    }
}
