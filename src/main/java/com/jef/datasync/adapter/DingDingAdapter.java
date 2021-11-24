package com.jef.datasync.adapter;

import com.jef.datasync.base.BaseDepartment;
import com.jef.datasync.base.BaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jef
 * @Date: 2021/11/18 16:29
 * @Description 默认钉钉实现
 */
public class DingDingAdapter extends BaseAdapter {
    @Override
    public String getId() {
        return "DingDingAdapter";
    }

    @Override
    public List<BaseDepartment> getDepartmentList(String deptId) {
        BaseDepartment department = new BaseDepartment();
        department.setName("父部门");
        department.setId("A");
        department.setParentId("0");

        BaseDepartment department1 = new BaseDepartment();
        department.setName("子部门1");
        department.setId("A1");
        department.setParentId("A");

        BaseDepartment department2 = new BaseDepartment();
        department.setName("子部门2");
        department.setId("A2");
        department.setParentId("A");

        List<BaseDepartment> list = new ArrayList<>(3);
        list.add(department);
        list.add(department1);
        list.add(department2);

        return list;
    }

    @Override
    public List<BaseUser> getUserList(String deptId) {
        List<BaseUser> list = new ArrayList<>(2);

        if ("A".equals(deptId)) {
            BaseUser user = new BaseUser();
            user.setId("111");
            user.setName("张三");

            BaseUser user1 = new BaseUser();
            user.setId("222");
            user.setName("李四");

            list.add(user);
            list.add(user1);
        }

        return list;
    }

    @Override
    public void onDataChange(String dataJson) {
        System.out.println("有数据变更" + dataJson);
        getNotifyManager().sendThirdDataChangeMsg(dataJson);
    }
}
