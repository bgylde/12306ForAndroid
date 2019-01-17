package com.bgylde.ticket.database;

import com.bgylde.ticket.MainApplication;
import com.bgylde.ticket.database.dao.DaoMaster;
import com.bgylde.ticket.database.dao.DaoSession;
import com.bgylde.ticket.utils.Constants;

import org.greenrobot.greendao.database.Database;

import java.util.List;

/**
 * Created by wangyan on 2019/1/14
 */
public class UserDbManager {

    private static final String TAG = "CookieDbManager";

    private DaoSession daoSession = null;

    private static UserDbManager manager = null;

    private UserDbManager() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MainApplication.getApplication(), Constants.DATABASE);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static UserDbManager getInstance() {
        if (manager == null) {
            synchronized (UserDbManager.class) {
                if (manager == null) {
                    manager = new UserDbManager();
                }
            }
        }

        return manager;
    }

    public void insertOrReplaceCookie(CookieModel model) {
        if (daoSession != null) {
            daoSession.getCookieModelDao().insertOrReplace(model);
        }
    }

    public long getCurrentCookieCount() {
        long count = -1;

        if (daoSession != null) {
            count = daoSession.getCookieModelDao().queryBuilder().count();
        }

        return count;
    }

    public List<CookieModel> queryCookieModel() {
        return daoSession.getCookieModelDao().queryBuilder().list();
    }
}
