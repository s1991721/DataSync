package com.jef.datasync.adapter;

import com.jef.datasync.base.BaseDepartment;
import com.jef.datasync.base.BaseUser;

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
        return null;
    }

    @Override
    public List<BaseUser> getUserList(String deptId) {
        return null;
    }

    @Override
    public void onDataChange(String dataJson) {
        System.out.println("有数据变更" + dataJson);
        getNotifyManager().sendThirdDataChangeMsg(dataJson);
    }
}
