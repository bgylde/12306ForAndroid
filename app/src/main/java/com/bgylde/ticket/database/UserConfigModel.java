package com.bgylde.ticket.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.bgylde.ticket.database.dao.DaoSession;
import com.bgylde.ticket.database.dao.UserConfigModelDao;

/**
 * Created by wangyan on 2019/1/14
 */
@Entity(active = true, nameInDb = "UserConfigTable", createInDb = true)
public class UserConfigModel {

    @Transient
    public static final long USER_UNIQUE_ID = 0x264;

    @Unique
    @Id(autoincrement = false)
    private long userId;

    @Property(nameInDb = "accountUser")
    private String userName;

    @Property(nameInDb = "accountPwd")
    private String userPasswd;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1415928471)
    private transient UserConfigModelDao myDao;

    @Generated(hash = 557190222)
    public UserConfigModel(long userId, String userName, String userPasswd) {
        this.userId = userId;
        this.userName = userName;
        this.userPasswd = userPasswd;
    }

    @Generated(hash = 882213715)
    public UserConfigModel() {
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPasswd() {
        return this.userPasswd;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
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
    @Generated(hash = 1134142712)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserConfigModelDao() : null;
    }
}
