SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  DBTableInfo structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  id1                   bigint(20) not null auto_increment,
  name                 varchar(20) default NULL comment '用户姓名',
  ext_name             varchar(20) default NULL comment '用户姓名_扩展',
  status               int(4) default 0 comment '状态：0:创建中 1：创建成功， 2:创建失败',
  age                  int(4) default NULL comment '用户性别',
  primary key (id1)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user`(id1,name,ext_name,status,age)
VALUES
('1', 'zhouwei','用户姓名_扩展', 0,'3'),
('2', 'shengbin', NULL ,1,'2'),
('100', 'zhouwei','用户姓名_扩展', 0,'3'),
('200', 'shengbin', NULL ,1,'2');
COMMIT;


DROP TABLE IF EXISTS `trader_order`;
CREATE TABLE `trader_order` (
  `id`                  bigint(20) not null auto_increment,
  `order_no`            VARCHAR(20)  not NULL comment '@no订单号码',
  `order_name`          varchar(20) not NULL comment '订单名称',
  `status`              int(4) default 0 comment '@status(0=创建中,1=创建成功,2=创建失败)',
  `create_date`         datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user`         varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_date`         datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user`         varchar(20) DEFAULT NULL COMMENT '更新人',
  `yn`                  tinyint(4) DEFAULT '1' COMMENT '@yn 1:有效 0:无效',
  primary key (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO yunying.trader_order (order_no, order_name, status, create_date, create_user, update_date, update_user, yn)
VALUES ('n001', '订单1号', 1, '2018-03-08 19:41:25', null, '2018-03-08 19:41:25', null, 1);
INSERT INTO yunying.trader_order (order_no, order_name, status, create_date, create_user, update_date, update_user, yn)
VALUES ('n002', '订单2号', 1, '2018-03-08 19:41:25', null, '2018-03-08 19:41:25', null, 1);
INSERT INTO yunying.trader_order (order_no, order_name, status, create_date, create_user, update_date, update_user, yn)
VALUES ('n003', '订单3号', 1, '2018-03-10 19:41:25', null, '2018-03-10 19:41:25', null, 1);
INSERT INTO yunying.trader_order (order_no, order_name, status, create_date, create_user, update_date, update_user, yn)
VALUES ('n004', '订单4号', 1, '2018-03-10 19:41:25', null, '2018-03-10 19:41:25', null, 1);
INSERT INTO yunying.trader_order (order_no, order_name, status, create_date, create_user, update_date, update_user, yn)
VALUES ('n005', '订单5号', 1, '2018-03-10 19:41:25', null, '2018-03-10 19:41:25', null, 1);
INSERT INTO yunying.trader_order (order_no, order_name, status, create_date, create_user, update_date, update_user, yn)
VALUES ('n006', '订单6号', 2, '2018-03-10 19:41:25', null, '2018-03-10 19:41:25', null, 1);
INSERT INTO yunying.trader_order (order_no, order_name, status, create_date, create_user, update_date, update_user, yn)
VALUES ('n007', '订单7号', 2, '2018-03-11 19:41:25', null, '2018-03-11 19:41:25', null, 1);
INSERT INTO yunying.trader_order (order_no, order_name, status, create_date, create_user, update_date, update_user, yn)
VALUES ('n008', '订单8号', 2, '2018-03-11 19:41:25', null, '2018-03-11 19:41:25', null, 1);
INSERT INTO yunying.trader_order (order_no, order_name, status, create_date, create_user, update_date, update_user, yn)
VALUES ('n009', '订单9号', 3, '2018-03-11 19:41:25', null, '2018-03-11 19:41:25', null, 1);
INSERT INTO yunying.trader_order (order_no, order_name, status, create_date, create_user, update_date, update_user, yn)
VALUES ('n010', '订单10号', 3, '2018-03-10 19:41:25', null, '2018-03-10 19:41:25', null, 1);
INSERT INTO yunying.trader_order (order_no, order_name, status, create_date, create_user, update_date, update_user, yn)
VALUES ('n011', '订单11号', 3, '2018-03-10 19:41:25', null, '2018-03-10 19:41:25', null, 1);


SET FOREIGN_KEY_CHECKS = 1;
