package com.jef.datasync.thread;

import com.jef.datasync.adapter.Adapter;
import com.jef.datasync.base.BaseDepartment;
import com.jef.datasync.base.BaseRelation;
import com.jef.datasync.base.BaseUser;
import com.jef.datasync.mapper.RelationMapper;
import com.jef.datasync.mapper.UserMapper;

import java.util.List;
import java.util.Map;
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

    private Map locks;

    private UserMapper userMapper;

    private RelationMapper relationMapper;

    private int tryCount;

    public Worker(BaseDepartment department, Adapter adapter, CountDownLatch doneCountDownLatch, Map locks, UserMapper userMapper, RelationMapper relationMapper) {
        this.department = department;
        this.adapter = adapter;
        this.doneCountDownLatch = doneCountDownLatch;
        this.locks = locks;
        this.userMapper = userMapper;
        this.relationMapper = relationMapper;
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
        // 以用户id加锁等待
        if (null == locks.put(user.getId(), "")) {

            int effect = userMapper.updateById(user);
            if (0 == effect) {
                userMapper.insert(user);
            }
            // 释放锁
            locks.remove(user.getId());
        } else {
            if (tryCount < 50) {
                try {
                    Thread.sleep(500);
                    tryCount++;
                    insertUpdateUser(user);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // todo 报错
            }

        }


    }

    private void insertRelation(BaseUser user) {
        BaseRelation relation = new BaseRelation();
        relation.setDepartmentId(department.getId());
        relation.setUserId(user.getId());
        relationMapper.insert(relation);
    }

}
