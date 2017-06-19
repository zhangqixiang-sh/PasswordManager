package com.zqx.pwd.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZhangQixiang on 2017/2/10.
 */
public class Run {
    private static Handler  sHandler  = new Handler(Looper.getMainLooper());//获取主线程的Looper
    private static Executor sExecutor = new ThreadPoolExecutor(
            1, 10, 7, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    ToastUtil.show("操作太频繁");
                }
            }
    );

    public static void onSub(Runnable runnable) {
        sExecutor.execute(runnable);
        Executors.newSingleThreadExecutor();
    }

    public static void onMain(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void onMain(Runnable runnable, long delay) {
        sHandler.postDelayed(runnable, delay);
    }

}
