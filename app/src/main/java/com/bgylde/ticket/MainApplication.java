package com.bgylde.ticket;

import android.app.Application;

import com.bgylde.ticket.database.StationInfoModel;
import com.bgylde.ticket.database.UserDbManager;
import com.bgylde.ticket.http.RetrofitHttpUtils;
import com.bgylde.ticket.utils.LogUtils;
import com.bgylde.ticket.utils.StringUtils;
import com.bgylde.ticket.utils.threadpool.ThreadPoolManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangyan on 2019/1/7
 */
public class MainApplication extends Application {

    private static final String TAG = "MainApplication";

    public static MainApplication application = null;

    public MainApplication() {
        super();
        application = this;
        LogUtils.d(TAG, "MainApplication");
    }

    public static MainApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG, "MainApplication onCreate");
        // 获取站点信息，保存本地数据库
        initStationInfo();
    }

    public static void initStationInfo() {
        if (UserDbManager.getInstance().stationInfoIsExist()) {
            return;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                RetrofitHttpUtils.sendByGet(application, "index/script/core/common/station_name_v10015.js", null, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            ResponseBody body = response.body();
                            if (body != null) {
                                String originString = body.string();
                                LogUtils.d(TAG, "" + originString);
                                originString = originString.substring(20, originString.length() - 3);
                                List<StationInfoModel> stationInfoList = getStationMap(originString);
                                UserDbManager.getInstance().insertStationInfo(stationInfoList);
                                body.close();
                            }
                        } catch (IOException e) {
                            LogUtils.e(TAG, e);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        LogUtils.e(TAG, t);
                    }
                });
            }
        };

        ThreadPoolManager.getInstance().addAppInitTask(runnable);
    }

    private static List<StationInfoModel> getStationMap(String originString) {
        List<StationInfoModel> resultList = new ArrayList<>();
        if (originString == null) {
            return resultList;
        }

        try {
            String[] stations = originString.split("@");
            for (String station : stations) {
                if (StringUtils.isNotBlank(station)) {
                    String[] stationSplit = station.split("\\|");
                    StationInfoModel model = new StationInfoModel();
                    model.setStationId(Long.parseLong(stationSplit[5]));
                    model.setStationName(stationSplit[1]);
                    model.setStationFlag(stationSplit[2]);
                    resultList.add(model);
                }
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }

        return resultList;
    }
}
