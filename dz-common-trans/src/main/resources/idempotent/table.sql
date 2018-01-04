CREATE TABLE `idempotent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `idempotent_id` varchar(64) NOT NULL COMMENT '防重id',
  `return_info` text COMMENT '返回信息',
  `return_byte` blob COMMENT '返回信息的二进制存储',
  `return_class` varchar(200) DEFAULT NULL COMMENT '返回类型',
  `status` varchar(20) DEFAULT NULL COMMENT '状态:PROCESSING|SUCCESS|FAIL',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `uniq_id_bid` (`idempotent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;