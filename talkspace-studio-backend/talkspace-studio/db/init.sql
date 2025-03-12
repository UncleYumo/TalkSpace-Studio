use talkspace;

CREATE TABLE IF NOT EXISTS `user` (
                                      `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                      `username` VARCHAR(255) NOT NULL COMMENT '用户名',
                                      `password` VARCHAR(255) NOT NULL COMMENT '密码',
                                      `gender` TINYINT DEFAULT NULL COMMENT '性别 0:男 1:女',
                                      `avatar` VARCHAR(512) DEFAULT NULL COMMENT '头像地址',
                                      `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `uk_username` (`username`) COMMENT '用户名唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';