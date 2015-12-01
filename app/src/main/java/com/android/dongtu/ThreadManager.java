package com.android.dongtu;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程管理类
 * 线程池管理app内线程的产生/执行和回收
 * 独立于网络通信模块和底层通信模块，仅处理app本身业务逻辑
 * Created by terry on 14-12-16.
 */
public class ThreadManager {

    private static final String TAG = ThreadManager.class.getSimpleName();
    /**
     * 固定线程池大小为4
     */
    private static final int THREAD_POOL_SIZE = 4;

    /**
     * 线程池对象
     */
    private static final ExecutorService backgroundExecutor = Executors.newFixedThreadPool(THREAD_POOL_SIZE, new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "thread #"
                    + mCount.getAndIncrement());
        }
    });

    /**
     * 用于处理ui线程逻辑
     */
    private static Handler handler = new Handler();

    /**
     * 使用非UI线程处理逻辑
     */
    public static void runBg(Runnable r){
        backgroundExecutor.submit(r);
    }

    /**
     * 使用UI线程处理逻辑
     */
    public static void runUI(Runnable r) {
        handler.post(r);
    }

    /**
     * 移除任务
     */
    public static void removeUI(Runnable r) {
        handler.removeCallbacks(r);
    }

    public static void init() {

    }

}
