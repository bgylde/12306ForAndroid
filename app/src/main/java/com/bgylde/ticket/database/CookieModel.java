package com.bgylde.ticket.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.bgylde.ticket.database.dao.DaoSession;
import com.bgylde.ticket.database.dao.CookieModelDao;

/**
 * Created by wangyan on 2019/1/14
 */
@Entity(active = true, nameInDb = "CookieTable", createInDb = true)
public class CookieModel {

    @Id(autoincrement = false)
    private long cookieId;

    @Property(nameInDb = "title")
    private String title;

    @Property(nameInDb = "cookie")
    private String cookie;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 457153774)
    private transient CookieModelDao myDao;

    @Generated(hash = 692824634)
    public CookieModel(long cookieId, String title, String cookie) {
        this.cookieId = cookieId;
        this.title = title;
        this.cookie = cookie;
    }

    @Generated(hash = 1994741640)
    public CookieModel() {
    }

    public long getCookieId() {
        return this.cookieId;
    }

    public void setCookieId(long cookieId) {
        this.cookieId = cookieId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCookie() {
        return this.cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
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
    @Generated(hash = 444255534)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCookieModelDao() : null;
    }
}
