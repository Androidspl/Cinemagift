package com.skyfilm.owner.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.Process;

public class CsqThreadFactory {
	/** 任务执行器. */
	public static Executor mExecutorService = null;

	/** 保存线程数量 . */
	private static final int CORE_POOL_SIZE = 5;

	/** 最大线程数量 . */
	private static final int MAXIMUM_POOL_SIZE = 64;

	/** 活动线程数量 . */
	private static final int KEEP_ALIVE = 5;


	/** 线程工厂 . */
	private static final ThreadFactory mThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "CSQThread #" + mCount.getAndIncrement());
		}
	};

	/** 队列. */
	private static final BlockingQueue<Runnable> mPoolWorkQueue =
			new LinkedBlockingQueue<Runnable>(10);

	private static class ExecutorHolder{
		private static final int numCores = AppUtils.getNumCores();
		private static final Executor INSTANCE  = new ThreadPoolExecutor(numCores * CORE_POOL_SIZE,numCores * MAXIMUM_POOL_SIZE,numCores * KEEP_ALIVE,
				TimeUnit.SECONDS, mPoolWorkQueue, mThreadFactory); 
	}

	/**
	 * 获取执行器.
	 *
	 * @return the executor service
	 */
	public  static Executor getExecutorService() { 
		mExecutorService = ExecutorHolder.INSTANCE;
		Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
		return mExecutorService;
	} 
	/**
	 * 推送，版本检测，起线程。
	 * @param runnable
	 */
	public static void execute(Runnable runnable){
		getExecutorService().execute(runnable);
	}
}
