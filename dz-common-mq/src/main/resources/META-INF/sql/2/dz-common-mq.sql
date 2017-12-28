ALTER TABLE `dz_mq_producer` ADD COLUMN `attachment` text NULL COMMENT '消息附件' AFTER `send_third`;
ALTER TABLE `dz_mq_consumer` ADD COLUMN `attachment` text NULL COMMENT '消息附件' AFTER `body`;