package com.jef.datasync.base;

import lombok.Data;

/**
 * @Author: Jef
 * @Date: 2021/11/22 11:42
 * @Description
 */
@Data
public class BaseRelation {

    private String id;

    private String departmentId;

    private String userId;

    private Boolean deleted = false;
}
