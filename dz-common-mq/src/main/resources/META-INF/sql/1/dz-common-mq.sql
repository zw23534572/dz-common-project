ALTER TABLE `dz_mq_producer` ADD COLUMN `send_third` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '该消息是否发送第三方，如果是第三方只会发送body里的内容' AFTER `queue`;
ALTER TABLE `dz_mq_producer` ADD COLUMN `attachment` text NULL COMMENT '消息附件' AFTER `send_third`;
ALTER TABLE `dz_mq_consumer` ADD COLUMN `attachment` text NULL COMMENT '消息附件' AFTER `body`;