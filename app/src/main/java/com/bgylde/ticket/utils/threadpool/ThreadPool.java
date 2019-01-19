package com.bgylde.ticket.utils.threadpool;

import com.bgylde.ticket.utils.LogUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wangyan on 2019/1/19
 */
public class ThreadPool {

    private static final String TAG = "ThreadPool";

    private String poolName;
    private ExecutorService executorService;

    protected ThreadPool(ExecutorService executor, String poolName) {
        this.executorService = executor;
        this.poolName = poolName;
    }

    public void execute(Runnable runnable) {
        LogUtils.d(TAG, poolName + " add new task: " + runnable);
        executorService.execute(runnable);
        LogUtils.d(TAG, poolName + " task executing count " + ((ThreadPoolExecutor)executorService).getActiveCount());
        LogUtils.d(TAG, poolName + " task waiting queue count " + ((ThreadPoolExecutor)executorService).getQueue().size());
        LogUtils.d(TAG, poolName + " task completed count " + ((ThreadPoolExecutor)executorService).getCompletedTaskCount());
    }
}
