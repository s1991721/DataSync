package com.jef.datasync.manager;

import com.jef.datasync.adapter.Adapter;
import com.jef.datasync.adapter.BaseAdapter;
import com.jef.datasync.adapter.DingDingAdapter;
import com.jef.datasync.base.BaseDepartment;
import com.jef.datasync.mapper.DepartmentMapper;
import com.jef.datasync.mapper.RelationMapper;
import com.jef.datasync.mapper.UserMapper;
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

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private NotifyManager notifyManager;

    public Coordinate() {
        setAdapter(new DingDingAdapter());
    }

    //内存锁
    private static Map<String, String> locks = new ConcurrentHashMap<>();


    private ExecutorService executor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(),
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    private Map<String, Adapter> adapterMap = new HashMap<>();

    public Adapter getAdapter(String adapterId) {
        return adapterMap.get(adapterId);
    }

    public void setAdapter(BaseAdapter adapter) {
        adapter.setNotifyManager(notifyManager);
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
    public boolean startFullSync(String adapterId) {
        Adapter adapter = getAdapter(adapterId);
        if (null == adapter) {
            return false;
        } else {
            new Thread(() -> startSync(adapter, "rootId")).start();
            return true;
        }
    }

    public boolean startSubSync(String adapterId, String deptId) {
        Adapter adapter = getAdapter(adapterId);
        if (null == adapter) {
            return false;
        } else {
            new Thread(() -> startSync(adapter, deptId)).start();
            return true;
        }
    }

    private void startSync(Adapter adapter, String deptId) {
        if (null == adapter) {
            //todo 错误信息
            return;
        }

        try {
            copyData();
        } catch (Exception e) {
            //复制本地数据出错
            e.printStackTrace();
            producer.sendDirectMsg("同步三方数据失败");
            return;
        }

        try {
            enteringData(adapter, deptId);
        } catch (Exception e) {
            //三方数据落库出错
            e.printStackTrace();
            producer.sendDirectMsg("同步三方数据失败");
            return;
        }

        //清理同步已经失效的数据
        cleanData();

        // 切表
        try {
            switchData();
        } catch (Exception e) {
            //数据切换失败
            e.printStackTrace();
            producer.sendDirectMsg("同步三方数据失败");
            return;
        }

        producer.sendDirectMsg("同步三方数据成功");
    }

    private void copyData() throws Exception {
        //部门处理
        try {
            departmentMapper.createTemplateTable();
        } catch (Exception e) {
            //todo 表已存在
        }

        try {
            departmentMapper.copyDataToTemplateTable();
        } catch (Exception e) {
            //todo 复制数据出错
        }

        int deptCount = departmentMapper.deleteAllData();

        //员工处理
        try {
            userMapper.createTemplateTable();
        } catch (Exception e) {
            //todo 表已存在
        }

        try {
            userMapper.copyDataToTemplateTable();
        } catch (Exception e) {
            //todo 复制数据出错
        }

        //部门员工关系处理
        try {
            relationMapper.createTemplateTable();
        } catch (Exception e) {
            //todo 表已存在
        }

        int userCount = userMapper.deleteAllData();

        System.out.println(deptCount + userCount);
    }

    private void enteringData(Adapter adapter, String deptId) throws Exception {

        List<BaseDepartment> departmentList = adapter.getDepartmentList(deptId);

        // 组织数据落库
        for (BaseDepartment department : departmentList) {
            int effect = departmentMapper.updateById( department);
            if (0 == effect) {
                departmentMapper.insert( department);
            }
        }

        CountDownLatch doneCountDownLatch = new CountDownLatch(departmentList.size());
        for (BaseDepartment department : departmentList) {
            Worker worker = new Worker(department, adapter, doneCountDownLatch, locks, userMapper, relationMapper);
            executor.submit(worker);
        }
        try {
            doneCountDownLatch.await();
        } catch (InterruptedException e) {
            // 同步三方出错
            e.printStackTrace();
            throw e;
        }

    }

    private void cleanData() {
        departmentMapper.cleanTempData();
        userMapper.cleanTempData();
    }

    public void switchData() throws Exception {
        //todo 加锁
        try {

            departmentMapper.invalid();
            departmentMapper.effect("base_department_temp");
            userMapper.invalid();
            userMapper.effect("base_user_temp");
            relationMapper.invalid();
            relationMapper.effect("base_relation_temp");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        //todo 释放锁
        departmentMapper.deleteTable();
        userMapper.deleteTable();
        relationMapper.deleteTable();
    }

}
