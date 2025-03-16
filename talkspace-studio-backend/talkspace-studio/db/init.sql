use talkspace;

-- 用户表
CREATE TABLE IF NOT EXISTS `user`
(
    `id`          BIGINT       NOT NULL COMMENT '主键ID',
    `username`    VARCHAR(255) NOT NULL COMMENT '用户名',
    `password`    VARCHAR(255) NOT NULL COMMENT '密码',
    `gender`      TINYINT               DEFAULT NULL COMMENT '性别 0:男 1:女',
    `avatar`      VARCHAR(512)          DEFAULT NULL COMMENT '头像地址',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`) COMMENT '用户名唯一索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户表';

-- 项目主表
CREATE TABLE `project`
(
    `id`              BIGINT PRIMARY KEY COMMENT '雪花算法ID',
    `user_id`         BIGINT       NOT NULL COMMENT '用户ID',
    `title`           VARCHAR(255) NOT NULL COMMENT '项目名称',
    `language`        VARCHAR(20)  NOT NULL COMMENT '语言类型',
    `episode_count`   INT          NOT NULL COMMENT '总集数',
    `user_prompt`      VARCHAR(255) NOT NULL COMMENT '用户提示词',
    `status`          TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '项目状态 0:草稿 1:已生成 2: 已发布',
    `single_duration` INT          NOT NULL COMMENT '单集时长（分钟）',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) COMMENT ='播客项目表';

-- 项目角色表
CREATE TABLE `project_role`
(
    `id`             BIGINT PRIMARY KEY,
    `project_id`     BIGINT       NOT NULL,
    `timbre`         VARCHAR(50)  NOT NULL COMMENT '音色标识',
    `character_name` VARCHAR(100) NOT NULL COMMENT '角色名称',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) COMMENT ='项目角色配置';

-- 剧本集表
CREATE TABLE `episode`
(
    `id`          BIGINT PRIMARY KEY,
    `project_id`  BIGINT       NOT NULL,
    `sub_title`   VARCHAR(255) NOT NULL COMMENT '子标题',
    `content`     JSON         NOT NULL COMMENT '剧本内容',
    `duration`    INT COMMENT '音频时长（秒）',
    `audio_url`   VARCHAR(512) COMMENT '音频文件地址',
    `sequence`    INT          NOT NULL COMMENT '集数顺序',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) COMMENT ='播客分集表';

-- 社区发布表
CREATE TABLE `community_publish`
(
    `id`           BIGINT PRIMARY KEY,
    `project_id`   BIGINT   NOT NULL,
    `user_id`      BIGINT   NOT NULL,
    `is_template`  TINYINT(1)        DEFAULT 0 COMMENT '是否作为模板共享',
    `publish_time` DATETIME          DEFAULT CURRENT_TIMESTAMP,
    `create_time`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `uniq_project` (`project_id`)
) COMMENT ='社区发布记录';

-- 社区收藏表
CREATE TABLE `community_collection`
(
    `id`           BIGINT PRIMARY KEY,
    `user_id`      BIGINT   NOT NULL,
    `project_id`   BIGINT   NOT NULL,
    `collect_time` DATETIME          DEFAULT CURRENT_TIMESTAMP,
    `create_time`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `uniq_user_project` (`user_id`, `project_id`)
) COMMENT ='社区收藏记录';
