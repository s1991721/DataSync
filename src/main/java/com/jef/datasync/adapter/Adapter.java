package com.jef.datasync.adapter;

import com.jef.datasync.base.BaseDepartment;
import com.jef.datasync.base.BaseUser;

import java.util.List;

/**
 * @Author: Jef
 * @Date: 2021/11/18 14:18
 * @Description 同步数据的适配器
 */
public interface Adapter {

    //唯一标识
    String getId();

    //获取根组织数据数据接口
    List<BaseDepartment> getDepartmentList(String deptId);

    //获取节点数据数据接口
    List<BaseUser> getUserList(String deptId);

    //数据变动监听
    void onDataChange(String dataJson);

}
