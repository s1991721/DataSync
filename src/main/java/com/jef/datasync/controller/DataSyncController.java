package com.jef.datasync.controller;

import com.jef.datasync.adapter.Adapter;
import com.jef.datasync.kettle.KettleUtils;
import com.jef.datasync.manager.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Jef
 * @Date: 2021/11/20 14:47
 * @Description
 */
@RestController
@RequestMapping("/dataSync")
public class DataSyncController {


    @Autowired
    private Coordinate coordinate;

    @Autowired
    private KettleUtils kettleUtils;


    @GetMapping("/syncDataFromThird")
    public String syncDataFromThird(String adapterId) {
        coordinate.startFullSync(adapterId);
        return "syncDataFromThird already stared , listen to the message";
    }

    @GetMapping("/notifyFromThird")
    public String notifyFromThird(String url) {
        //todo 处理url识别为具体适配器
        String adapterId = url;
        Adapter adapter = coordinate.getAdapter(adapterId);
        adapter.onDataChange(url);
        return "";
    }

    @GetMapping("/syncDataToService")
    public String syncDataToService(String templateId, Map<String, String> params) {
        kettleUtils.trans(templateId, params);
        return "syncDataToService already stared , listen to the message";
    }


}
