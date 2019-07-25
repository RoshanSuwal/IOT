package com.example.iot.ProjectListMVP;

import android.content.Context;

import com.example.iot.model.DaoSession;
import com.example.iot.model.Project;

public class ProjectPresenter implements ProjectListContract.Presenter{
    private ProjectListContract.mvpView mView;
    private ProjectRepo projectRepo;
    private Context context;



    public ProjectPresenter(ProjectListContract.mvpView mView, DaoSession daoSession, Context context) {
        this.mView = mView;
        projectRepo=new ProjectRepo(daoSession);
        this.context = context;
    }


    @Override
    public boolean projectAlreadyExists(String token) {
        if(projectRepo.doseProjectInDaoExixts(token)){
            mView.showInfo("token already used");
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void addProjectdialog() {
        mView.showDialog();
    }

    @Override
    public void getprojectlist() {
        mView.showListView(projectRepo.getAllProrjectFromDao());
    }

    @Override
    public void addproject(String projectname, String token) {
        if(projectRepo.doseProjectInDaoExixts(token)){
            mView.showInfo("project already exists");
        }else {
            projectRepo.createNewProjectInDao(new Project(token,projectname));
        }

        mView.showListView(projectRepo.getAllProrjectFromDao());
    }

    @Override
    public void onListitemClickListener(Project project) {
        mView.gotoDeviceControllActivity(project);
    }

    @Override
    public void onItemLongClickListener(Project project) {
        mView.modifyProjectDialog(project);
    }

    @Override
    public void deleteProject(Project project) {
        projectRepo.deleteProjectInDao(project);
        mView.showListView(projectRepo.getAllProrjectFromDao());
    }

    @Override
    public void updateProject(Project project) {
        projectRepo.updateProjectInDao(project);
        mView.showListView(projectRepo.getAllProrjectFromDao());
    }
}
