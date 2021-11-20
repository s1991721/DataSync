package com.jef.datasync.controller;

import com.jef.datasync.kettle.KettleUtils;
import com.jef.datasync.manager.Coordinate;
import com.jef.datasync.mq.SimpleProducer;
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
    private SimpleProducer producer;

    @GetMapping("/syncDataFromThird")
    public String syncDataFromThird(String adapterId) {
        coordinate.startFullSync(adapterId);

        producer.sendDirectMsg("同步三方数据成功");
        return "";
    }

    @GetMapping("/syncDataToService")
    public String syncDataToService(String templateId, Map<String, String> params) {
        KettleUtils.trans();
        producer.sendDirectMsg("同步数据至业务服务成功");
        return "";
    }


}
