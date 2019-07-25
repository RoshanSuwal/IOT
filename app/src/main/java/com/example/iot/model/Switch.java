package com.example.iot.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity(active = true,nameInDb = "SWITCHES")
public class Switch {

    @Id(autoincrement = true)
    private long id;

    //@Index(name = "PIN",unique = true)
    private String pin_number;
    private int pin_value;
    private String switch_name;


    private long project_id;


    @ToOne(joinProperty = "project_id")
    private Project project;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 49109082)
    private transient SwitchDao myDao;

    @Generated(hash = 1005767482)
    private transient Long project__resolvedKey;


    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public Switch() {
    }

    public Switch(String pin_number) {
        this.pin_number = pin_number;
        this.pin_value=0;
        this.switch_name="Switch";
    }

    public Switch(String pin_number, int pin_value) {
        this.pin_number = pin_number;
        this.pin_value = pin_value;
        this.switch_name="Switch";
    }

    public Switch(String pin_number, int pin_value, String switch_name) {
        this.pin_number = pin_number;
        this.pin_value = pin_value;
        this.switch_name = switch_name;
    }

    public Switch(String pin_number, int pin_value, String switch_name,long id) {
        this.id = id;
        this.pin_number = pin_number;
        this.pin_value = pin_value;
        this.switch_name = switch_name;
    }

    @Generated(hash = 976692757)
    public Switch(long id, String pin_number, int pin_value, String switch_name,
            long project_id) {
        this.id = id;
        this.pin_number = pin_number;
        this.pin_value = pin_value;
        this.switch_name = switch_name;
        this.project_id = project_id;
    }


    public int getPin_value() { return pin_value; }

    public void setPin_value(int pin_value) { this.pin_value = pin_value; }

    public String getPin_number() { return pin_number; }

    public void setPin_number(String pin_number) { this.pin_number = pin_number; }

    public String getSwitch_name() { return switch_name; }

    public void setSwitch_name(String switch_name) { this.switch_name = switch_name; }

    public void inverseState(){ pin_value=pin_value==1?0:1; }
    public int getInversePinStateValue(){
        return pin_value==1?0:1;
    }

    public boolean hasSamePinNumber(Switch asitch){
        return pin_number.equals(asitch.getPin_number());
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProject_id() {
        return this.project_id;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1962874780)
    public Project getProject() {
        long __key = this.project_id;
        if (project__resolvedKey == null || !project__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProjectDao targetDao = daoSession.getProjectDao();
            Project projectNew = targetDao.load(__key);
            synchronized (this) {
                project = projectNew;
                project__resolvedKey = __key;
            }
        }
        return project;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1149610373)
    public void setProject(@NotNull Project project) {
        if (project == null) {
            throw new DaoException(
                    "To-one property 'project_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.project = project;
            project_id = project.getId();
            project__resolvedKey = project_id;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 251585050)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSwitchDao() : null;
    }



    
}
