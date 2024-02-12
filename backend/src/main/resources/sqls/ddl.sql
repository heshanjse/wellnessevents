CREATE TABLE `user`
(
    `user_id`      bigint NOT NULL AUTO_INCREMENT,
    `company_name` varchar(255) DEFAULT NULL,
    `password`     varchar(255) DEFAULT NULL,
    `role`         enum('HR_ADMIN','VENDOR_ADMIN','HR_USER','VENDOR_USER') DEFAULT NULL,
    `username`     varchar(255) DEFAULT NULL,
    PRIMARY KEY (`user_id`)
)

CREATE TABLE `event_type`
(
    `event_type_id` BIGINT NOT NULL AUTO_INCREMENT,
    `event_name`    VARCHAR(255) DEFAULT NULL,
    `user_id`       BIGINT       DEFAULT NULL,
    PRIMARY KEY (`event_type_id`),
    KEY (`user_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `event`
(
    `event_id`       bigint NOT NULL AUTO_INCREMENT,
    `confirmed_date` varchar(255) DEFAULT NULL,
    `created_date`   datetime(6) DEFAULT NULL,
    `location`       varchar(255) DEFAULT NULL,
    `remarks`        varchar(255) DEFAULT NULL,
    `status`         varchar(255) DEFAULT NULL,
    `event_type_id`  bigint       DEFAULT NULL,
    `user_id`        bigint       DEFAULT NULL,
    PRIMARY KEY (`event_id`),
    KEY (`event_type_id`),
    KEY (`user_id`),
    FOREIGN KEY (`event_type_id`) REFERENCES `event_type` (`event_type_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
)

CREATE TABLE `token_seq`
(
    `next_val` bigint DEFAULT NULL
)

CREATE TABLE `token`
(
    `id`         int    NOT NULL,
    `expired`    bit(1) NOT NULL,
    `revoked`    bit(1) NOT NULL,
    `token`      varchar(255) DEFAULT NULL,
    `token_type` enum('BEARER') DEFAULT NULL,
    `user_id`    bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`token`),
    KEY  (`user_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
)
