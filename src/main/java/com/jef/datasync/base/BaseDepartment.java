package com.jef.datasync.base;

import java.util.List;

/**
 * @Author: Jef
 * @Date: 2021/11/18 14:25
 * @Description
 */
public class BaseDepartment {

    private String id;

    private String name;

    private String parentId;

    private Integer sortKey;

    private String managerId;

    private List<BaseDepartment> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSortKey() {
        return sortKey;
    }

    public void setSortKey(Integer sortKey) {
        this.sortKey = sortKey;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public List<BaseDepartment> getChildren() {
        return children;
    }

    public void setChildren(List<BaseDepartment> children) {
        this.children = children;
    }
}
