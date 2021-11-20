package com.jef.datasync.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Jef
 * @Date: 2021/11/20 14:47
 * @Description
 */
@RestController
@RequestMapping("/dataSync")
public class DataSyncController {

    @GetMapping()
    public String syncDataFromThird(String adapterId) {
        return "";
    }


}
