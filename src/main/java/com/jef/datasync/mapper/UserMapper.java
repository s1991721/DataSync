package com.jef.datasync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jef.datasync.base.BaseUser;

/**
 * @Author: Jef
 * @Date: 2021/11/20 17:33
 * @Description
 */
public interface UserMapper extends BaseMapper<BaseUser> {

    void createTemplateTable();

    int copyDataToTemplateTable();

    void invalid();

    void effect(String newTableName);

    void deleteTable();

    int deleteAllData();

    void cleanTempData();

}
