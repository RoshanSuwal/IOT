package com.example.iot.DeviceControllerMVP;

import android.util.Log;

import com.example.iot.model.DaoSession;
import com.example.iot.model.Project;
import com.example.iot.model.ProjectDao;
import com.example.iot.model.Switch;
import com.example.iot.model.SwitchDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DeviceControlRepo {
    private String token;
    private long projectId;

    DaoSession daoSession;
    ProjectDao projectDao;
    SwitchDao switchDao;
    Project project;
    List<Switch> switchList;


    private Query<Switch> switchQuery;
    private Query<Project> projectQuery;


    public DeviceControlRepo(DaoSession daoSession) {
        this.daoSession=daoSession;
        projectDao =daoSession.getProjectDao();
        switchDao=daoSession.getSwitchDao();

        switchList=new ArrayList<>();
    }

    public boolean pinAlreadyUsed(Switch aswitch){
        for (Switch as:getAllSwitchesFromDao()){
            if (as.hasSamePinNumber(aswitch)){
                return true;
            }
        }
        return false;
    }


    //Dao Query
    public boolean doseProjectInDaoExixts(String token){
        List<Project> users= projectDao.queryBuilder()
                .where(ProjectDao.Properties.Token.eq(token))
                .list();
        if (users.size()>0){
            project =users.get(0);
            projectId= project.getId();
            this.token=token;
            createProjectQuerry();
            getAllSwitchesFromDao();
        }
        //switchList=project.getSwitchList();
        return users.size() > 0;
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
        switchList=project.getSwitchList();
        projectId=project.getId();
        createProjectQuerry();
        getAllSwitchesFromDao();
        Log.d("DAO_REPO", "createNewProjectInDao: ");
    }

    private Query<Project> createProjectQuerry(){
        switchQuery=switchDao.queryBuilder().where(SwitchDao.Properties.Project_id.eq(projectId)).orderAsc(SwitchDao.Properties.Id).build();
        return projectDao.queryBuilder().where(ProjectDao.Properties.Token.eq(token)).build();

    }

    public ArrayList<Switch> getAllSwitchesFromDao(){
        switchList=new ArrayList<>();
        switchList=switchQuery.list();

        Log.d("DAO_REPO", "getAllSwitchesFromDao: "+switchList.size());
        return (ArrayList<Switch>) switchList;

    }


    public boolean addswitches(Switch aswitch){
        long projectId= createProjectQuerry().list().get(0).getId();
        aswitch.setProject_id(projectId);
        aswitch.setId((projectId+1)*100+aswitch.getId());
        Log.d("TAG", "addswitches: "+aswitch.getId());
        switchDao.insert(aswitch);

        return true;

    }

    public void deleteSwitch(Switch aswitch){
        switchDao.delete(aswitch);
    }

    public void updateSwitchStateinDao(Switch aswitch){
        aswitch.inverseState();
        switchDao.update(aswitch);
    }

    public void updateSwitchinDao(Switch aswitch){
        switchDao.update(aswitch);
    }

    public String getToken(){return token;}

}
