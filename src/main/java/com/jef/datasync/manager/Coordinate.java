package com.jef.datasync.manager;

import com.jef.datasync.adapter.Adapter;
import com.jef.datasync.base.BaseDepartment;
import com.jef.datasync.mq.SimpleProducer;
import com.jef.datasync.thread.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: Jef
 * @Date: 2021/11/18 16:44
 * @Description
 */
@Component
public class Coordinate {

    @Autowired
    private SimpleProducer producer;


    private ExecutorService executor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(),
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    private Map<String, Adapter> adapterMap = new HashMap<>();

    public Adapter getAdapter(String adapterId) {
        return adapterMap.get(adapterId);
    }

    public void setAdapter(Adapter adapter) {
        adapterMap.put(adapter.getId(), adapter);
    }

    public void removeAdapter(String adapterId) {
        adapterMap.remove(adapterId);
    }

    /**
     * 选择适配器从三方同步数据
     *
     * @param adapterId 适配器id
     */
    public void startFullSync(String adapterId) {
        new Thread(() -> startSync(adapterId, "rootId")).start();
    }

    public void startSubSync(String adapterId, String deptId) {
        new Thread(() -> startSync(adapterId, deptId)).start();
    }

    private void startSync(String adapterId, String deptId) {
        Adapter adapter = getAdapter(adapterId);
        List<BaseDepartment> departmentList = adapter.getDepartmentList(deptId);

        //todo 组织数据落库

        CountDownLatch doneCountDownLatch = new CountDownLatch(departmentList.size());
        for (BaseDepartment department : departmentList) {
            Worker worker = new Worker(department, adapter, doneCountDownLatch);
            executor.submit(worker);
        }
        try {
            doneCountDownLatch.await();
        } catch (InterruptedException e) {
            //todo 同步三方出错
            e.printStackTrace();
        }

        //todo 切表

        producer.sendDirectMsg("同步三方数据成功");
    }


}
