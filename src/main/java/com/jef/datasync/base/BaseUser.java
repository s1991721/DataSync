package com.jef.datasync.base;

import lombok.Data;

import java.time.LocalDate;

/**
 * @Author: Jef
 * @Date: 2021/11/18 14:25
 * @Description
 */
@Data
public class BaseUser {

    private String id;

    private String name;

    private GenderType gender;

    private String mobile;

    private String email;

    private String identityNo;

    private LocalDate birthDay;

    private LocalDate entryDate;

    private String position;

    private String mainDepartmentId;

    private String imgUrl;

    private String employeeNo;

    private String officePhone;

    private Boolean leader;

    private Boolean deleted = false;
}
