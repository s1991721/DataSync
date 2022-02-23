package com.jef.datasync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jef.datasync.base.BaseDepartment;

/**
 * @Author: Jef
 * @Date: 2021/11/20 17:32
 * @Description
 */
public interface DepartmentMapper extends BaseMapper<BaseDepartment> {

    void createTemplateTable();

    int copyDataToTemplateTable();

    void invalid();

    void effect(String newTableName);

    void deleteTable();

    int deleteAllData();

    void cleanTempData();
}
