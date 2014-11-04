/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时扫描任务，并执行
 * 
 * @author zxc Jul 1, 2014 2:08:22 PM
 */
public class TaskManager {

    public interface TCallback {

        public void doTask(Task counter);
    }

    TCallback             tCallback;
    Map<Integer, Task>    contianer = new ConcurrentHashMap<Integer, Task>();
    private long          deplaySecondsInSencondes;
    private long          fixRateInSenconds;
    private int           core_threads;

    private static Logger logger    = LoggerFactory.getLogger(AbstractTask.class);

    /**
     * 默认线程数3个
     * 
     * @param fixRateInSenconds
     * @param deplaySecondsInSencondes
     * @param callback
     */
    public TaskManager(long fixRateInSenconds, long deplaySecondsInSencondes, TCallback callback) {
        this(fixRateInSenconds, deplaySecondsInSencondes, callback, 3);
    }

    public TaskManager(long fixRateInSenconds, long deplaySecondsInSencondes, TCallback callback, int threadCount) {
        this.fixRateInSenconds = fixRateInSenconds;
        this.deplaySecondsInSencondes = deplaySecondsInSencondes;
        this.tCallback = callback;
        this.core_threads = threadCount;
        init();
    }

    private void init() {
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(core_threads);
        newScheduledThreadPool.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    for (Integer key : contianer.keySet()) {
                        doTask(contianer.get(key), false);
                    }
                } catch (Throwable e) {
                    logger.debug(e.getLocalizedMessage());
                }
            }

        }, deplaySecondsInSencondes, fixRateInSenconds, TimeUnit.SECONDS);
    }

    private void doTask(Task task, boolean sync) {
        if (sync || task.getScanedCount() > 1) {
            tCallback.doTask(task);
            contianer.remove(task.getId());
        } else {
            task.increaseScanedCount();
        }
    }

    public TaskManager add(int taskId, boolean isSync) {
        Task task = addNewTask(new Task(taskId, null));
        if (isSync) {
            doTask(task, true);
        }
        return this;
    }

    private Task addNewTask(Task task) {
        Task result = task;
        if (contianer.containsKey(task.getId())) {
            Task old = contianer.get(task.getId());
            old.add(task);
            result = old;
        } else {
            contianer.put(task.getId(), task);
        }
        return result;
    }

    public static class Task implements Serializable {

        private static final long serialVersionUID = 4051051069962650723L;

        private Integer           id;
        // 扫描计数
        private int               scanedCount;
        // 任务计数
        private int               count;
        private List<Object>      data;

        public Task(Integer id, Object data) {
            this.id = id;
            this.data = new ArrayList<Object>();
            if (data != null) {
                this.data.add(data);
            }
            this.count = 1;
        }

        public void increaseScanedCount() {
            this.scanedCount++;
        }

        public Integer getId() {
            return id;
        }

        public void increaseCount() {
            this.count++;
        }

        public void add(Task counter) {
            this.data.addAll(counter.getData());
            this.count += counter.getCount();
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<Object> getData() {
            return this.data;
        }

        public int getScanedCount() {
            return scanedCount;
        }

        public void setScanedCount(int scanedCount) {
            this.scanedCount = scanedCount;
        }
    }
}
