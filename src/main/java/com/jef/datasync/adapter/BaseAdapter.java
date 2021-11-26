package com.jef.datasync.adapter;

import com.jef.datasync.manager.NotifyManager;

/**
 * @Author: Jef
 * @Date: 2021/11/23 19:51
 * @Description 基础适配器，内置消息通知器
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
