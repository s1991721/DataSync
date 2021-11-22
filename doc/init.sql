CREATE TABLE IF NOT EXISTS `t_base_department`
(
    `id`            char(32)  NOT NULL,
    `name`          varchar(100)   DEFAULT NULL,
    `parent_id`     char(32)       DEFAULT NULL,
    `manager_id`    char(32)       DEFAULT NULL,
    `deleted`       tinyint(4)     DEFAULT '0' COMMENT '0：未删除，1：已删除',
    PRIMARY KEY (`id`) USING BTREE,
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4 COMMENT ='部门表';

CREATE TABLE IF NOT EXISTS `t_base_user`
(
    `id`            char(32)  NOT NULL,
    `name`     varchar(100)   DEFAULT NULL,
    `gender`        varchar(10)    DEFAULT NULL,
    `mobile`        varchar(40)    DEFAULT NULL,
    `email`              varchar(100)   DEFAULT NULL,
    `identity_no`   varchar(40)    DEFAULT NULL,
    `birthday`      datetime       DEFAULT NULL,
    `entry_date`         datetime       DEFAULT NULL,
    `position`           varchar(80)    DEFAULT NULL COMMENT '职位',
    `main_department_id` varchar(32)    DEFAULT NULL,
    `img_url`       varchar(200)   DEFAULT NULL,
    `employee_no`        varchar(40)    DEFAULT NULL,
    `office_phone`       varchar(20)    DEFAULT NULL,
    `leader`            tinyint(4)     DEFAULT,
    `deleted`            tinyint(4)     DEFAULT '0' COMMENT '0：未删除，1：已删除',
    PRIMARY KEY (`id`) USING BTREE,
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

CREATE TABLE IF NOT EXISTS `t_base_relation`
(
    `id`            char(32)  NOT NULL,
    `department_id`          varchar(100)   DEFAULT NULL,
    `user_id`     char(32)       DEFAULT NULL,
    `deleted`       tinyint(4)     DEFAULT '0' COMMENT '0：未删除，1：已删除',
    PRIMARY KEY (`id`) USING BTREE,
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4 COMMENT ='关系表';
