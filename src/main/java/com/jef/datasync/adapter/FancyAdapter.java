package com.jef.datasync.adapter;

import javax.sql.DataSource;

/**
 * @Author: Jef
 * @Date: 2021/11/26 11:07
 * @Description 高级适配器，可自定义操作DB
 */
public abstract class FancyAdapter extends BaseAdapter {

    //自定义动作
    public abstract void customAction(DataSource dataSource);

}
