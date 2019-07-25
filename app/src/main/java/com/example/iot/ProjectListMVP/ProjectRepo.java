package com.example.iot.ProjectListMVP;

import android.util.Log;

import com.example.iot.model.DaoSession;
import com.example.iot.model.Project;
import com.example.iot.model.ProjectDao;
import com.example.iot.model.Switch;
import com.example.iot.model.SwitchDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProjectRepo {

    DaoSession daoSession;
    ProjectDao projectDao;

    Project project;
    String token;
    List<Project> projectList;

    private Query<Project> projectQuery;

    public ProjectRepo(DaoSession daoSession) {
        this.daoSession = daoSession;
        projectDao=daoSession.getProjectDao();

        projectList=new ArrayList<>();

        createProjectQuerry();

    }

    public void createNewProjectInDao(Project project){
        List<Project> projects= projectDao.queryBuilder().orderAsc(ProjectDao.Properties.Id).list();
        long id;
        if (projects.size()>0){
            id=projects.get(projects.size()-1).getId()+1;
        }else {
            id=0;
        }
        project.setId(id);
        projectDao.insert(project);
        this.project =project;
        this.token=project.getToken();

        createProjectQuerry();
        Log.d("DAO_REPO", "createNewProjectInDao: ");
    }

    public boolean doseProjectInDaoExixts(String token){
        List<Project> users= projectDao.queryBuilder()
                .where(ProjectDao.Properties.Token.eq(token))
                .list();
        if (users.size()>0){
            project =users.get(0);
            this.token=token;
            createProjectQuerry();
        }
        //switchList=project.getSwitchList();
        return users.size() > 0;
    }

    private void createProjectQuerry(){
        projectQuery=projectDao.queryBuilder().orderAsc(ProjectDao.Properties.Id).build();

    }

    public ArrayList<Project> getAllProrjectFromDao(){
        projectList=new ArrayList<>();
        projectList=projectDao.queryBuilder().build().list();

        Log.d("TAG", "getAllProrjectFromDao: "+projectList.size());
        return (ArrayList<Project>) projectList;
    }

    public void deleteProjectInDao(Project project){
        List<Switch> switches=project.getSwitchList();
        for (int i=0;i<switches.size();i++){
            daoSession.delete(switches.get(i));
        }

        //projectDao.delete(project);
        daoSession.delete(project);

    }

    public void updateProjectInDao(Project project){
        projectDao.update(project);
    }
}
