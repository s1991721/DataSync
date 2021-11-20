package com.jef.datasync.controller;

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

    @GetMapping("/syncDataFromThird")
    public String syncDataFromThird(String adapterId) {
        coordinate.startFullSync(adapterId);
        return "";
    }

    @GetMapping("/syncDataToService")
    public String syncDataToService(String templateId, Map<String, String> params) {
        KettleUtils.trans();

        return "";
    }


}
