package com.jef.datasync.adapter;

import com.jef.datasync.manager.NotifyManager;

/**
 * @Author: Jef
 * @Date: 2021/11/23 19:51
 * @Description
 */
public abstract class BaseAdapter implements Adapter {

    private NotifyManager notifyManager;

    public NotifyManager getNotifyManager() {
        return notifyManager;
    }

    public void setNotifyManager(NotifyManager notifyManager) {
        this.notifyManager = notifyManager;
    }
}
