package com.bgylde.ticket.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.bgylde.ticket.database.dao.DaoSession;
import com.bgylde.ticket.database.dao.OrderModelDao;

/**
 * Created by wangyan on 2019/1/17
 */
@Entity(active = true, nameInDb = "OrderTable", createInDb = true)
public class OrderModel {

    @Unique
    @Id(autoincrement = true)
    private long orderId;

    @NotNull
    @Property
    private String date;

    @NotNull
    @Property
    private String trainCode;

    @NotNull
    @Property
    private String fromStation;

    @NotNull
    @Property
    private String toStation;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 751840869)
    private transient OrderModelDao myDao;

    @Generated(hash = 1185888835)
    public OrderModel(long orderId, @NotNull String date, @NotNull String trainCode,
            @NotNull String fromStation, @NotNull String toStation) {
        this.orderId = orderId;
        this.date = date;
        this.trainCode = trainCode;
        this.fromStation = fromStation;
        this.toStation = toStation;
    }

    @Generated(hash = 1409243198)
    public OrderModel() {
    }

    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrainCode() {
        return this.trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
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
    @Generated(hash = 62411597)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrderModelDao() : null;
    }
}
