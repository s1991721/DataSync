package com.jef.datasync.thread;

import com.jef.datasync.adapter.Adapter;
import com.jef.datasync.base.BaseDepartment;
import com.jef.datasync.base.BaseUser;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: Jef
 * @Date: 2021/11/19 17:52
 * @Description 部门内部处理
 */
public class Worker implements Runnable {

    private BaseDepartment department;

    private Adapter adapter;

    private CountDownLatch doneCountDownLatch;

    public Worker(BaseDepartment department, Adapter adapter, CountDownLatch doneCountDownLatch) {
        this.department = department;
        this.adapter = adapter;
        this.doneCountDownLatch = doneCountDownLatch;
    }

    @Override
    public void run() {
        List<BaseUser> userList = adapter.getUserList(department.getId());
        for (BaseUser user : userList) {
            insertUpdateUser(user);
            insertRelation(user);
        }
        doneCountDownLatch.countDown();
    }

    private void insertUpdateUser(BaseUser user) {
        //todo 以用户id加锁等待


        //todo 释放锁

    }

    private void insertRelation(BaseUser user) {

    }

}
