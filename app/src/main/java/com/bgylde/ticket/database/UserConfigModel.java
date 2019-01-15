package com.bgylde.ticket.database;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.bgylde.ticket.database.dao.DaoSession;
import com.bgylde.ticket.database.dao.UserConfigModelDao;

/**
 * Created by wangyan on 2019/1/14
 */
@Entity(active = true, nameInDb = "UserConfigTable", createInDb = true)
public class UserConfigModel {

    @Unique
    @Id(autoincrement = true)
    private long configId;

    @Property(nameInDb = "accountUser")
    private String userName;

    @Property(nameInDb = "accountPwd")
    private String userPasswd;

    @NotNull
    @Property
    private String fromStation;

    @NotNull
    @Property
    private String toStation;

    @Property
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> stationDate;

    @Property
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> trainCodeList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1415928471)
    private transient UserConfigModelDao myDao;

    @Generated(hash = 199283258)
    public UserConfigModel(long configId, String userName, String userPasswd,
            @NotNull String fromStation, @NotNull String toStation, List<String> stationDate,
            List<String> trainCodeList) {
        this.configId = configId;
        this.userName = userName;
        this.userPasswd = userPasswd;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.stationDate = stationDate;
        this.trainCodeList = trainCodeList;
    }

    @Generated(hash = 882213715)
    public UserConfigModel() {
    }

    public long getConfigId() {
        return this.configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public String getFromStation() {
        return this.fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return this.toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public List<String> getStationDate() {
        return this.stationDate;
    }

    public void setStationDate(List<String> stationDate) {
        this.stationDate = stationDate;
    }

    public List<String> getTrainCodeList() {
        return this.trainCodeList;
    }

    public void setTrainCodeList(List<String> trainCodeList) {
        this.trainCodeList = trainCodeList;
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
}
