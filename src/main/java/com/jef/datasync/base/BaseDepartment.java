package com.jef.datasync.base;

import lombok.Data;

import java.util.List;

/**
 * @Author: Jef
 * @Date: 2021/11/18 14:25
 * @Description
 */
@Data
public class BaseDepartment {

    private String id;

    private String name;

    private String parentId;

    private String managerId;

    private List<BaseDepartment> children;

    private Boolean deleted = false;
}
