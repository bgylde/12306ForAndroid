package com.bgylde.ticket.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.bgylde.ticket.database.dao.DaoSession;
import com.bgylde.ticket.database.dao.StationInfoModelDao;

/**
 * Created by wangyan on 2019/1/19
 */
@Entity(active = true, nameInDb = "StationTable", createInDb = true)
public class StationInfoModel {

    @Unique
    @Id(autoincrement = false)
    private Long stationId;
    
    @Property
    private String stationName;

    @Property
    private String stationFlag;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1544126798)
    private transient StationInfoModelDao myDao;

    @Generated(hash = 156280383)
    public StationInfoModel(Long stationId, String stationName,
            String stationFlag) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationFlag = stationFlag;
    }

    @Generated(hash = 2128197782)
    public StationInfoModel() {
    }

    public Long getStationId() {
        return this.stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return this.stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationFlag() {
        return this.stationFlag;
    }

    public void setStationFlag(String stationFlag) {
        this.stationFlag = stationFlag;
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
    @Generated(hash = 1151084506)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStationInfoModelDao() : null;
    }
}
