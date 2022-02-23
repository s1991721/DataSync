package com.jef.datasync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jef.datasync.base.BaseRelation;

/**
 * @Author: Jef
 * @Date: 2021/11/20 17:33
 * @Description
 */
public interface RelationMapper extends BaseMapper<BaseRelation> {

    void createTemplateTable();

    void invalid();

    void effect(String newTableName);

    void deleteTable();

}
