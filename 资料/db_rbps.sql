/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : db_rbps

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 16/03/2020 09:40:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_auth
-- ----------------------------
DROP TABLE IF EXISTS `t_auth`;
CREATE TABLE `t_auth`  (
  `authId` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号（主键）',
  `authName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `authPath` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `parentId` int(0) NULL DEFAULT NULL COMMENT '父菜单编号',
  `authDescription` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `state` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `iconCls` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单样式',
  PRIMARY KEY (`authId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;
INSERT  INTO `t_auth` VALUES(1,'某系统','',-1,NULL,'closed','icon-home');
INSERT  INTO `t_auth` VALUES(2,'权限管理','',1,NULL,'closed','icon-permission');
INSERT  INTO `t_auth` VALUES(3,'学生管理','',1,NULL,'closed','icon-student');
INSERT  INTO `t_auth` VALUES(4,'课程管理','',1,NULL,'closed','icon-course');
INSERT  INTO `t_auth` VALUES(5,'住宿管理','zsgl.html',3,NULL,'open','icon-item');
INSERT  INTO `t_auth` VALUES(6,'学生信息管理','xsxxgl.html',3,NULL,'open','icon-item');
INSERT  INTO `t_auth` VALUES(7,'学籍管理','xjgl.html',3,NULL,'open','icon-item');
INSERT  INTO `t_auth` VALUES(8,'奖惩管理','jcgl.html',3,NULL,'open','icon-item');
INSERT  INTO `t_auth` VALUES(9,'课程设置','kcsz.html',4,NULL,'open','icon-item');
INSERT  INTO `t_auth` VALUES(10,'选课情况','xkqk.html',4,NULL,'open','icon-item');
INSERT  INTO `t_auth` VALUES(11,'成绩录入','cjlr.html',4,NULL,'open','icon-item');
INSERT  INTO `t_auth` VALUES(12,'用户管理','userManage.html',2,NULL,'open','icon-userManage');
INSERT  INTO `t_auth` VALUES(13,'角色管理','roleManage.html',2,NULL,'open','icon-roleManage');
INSERT  INTO `t_auth` VALUES(14,'菜单管理','menuManage.html',2,NULL,'open','icon-menuManage');
INSERT  INTO `t_auth` VALUES(15,'修改密码','',1,NULL,'open','icon-modifyPassword');
INSERT  INTO `t_auth` VALUES(16,'安全退出','',1,NULL,'open','icon-exit');
-- ----------------------------
-- Records of t_auth
-- ----------------------------

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `roleId` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号（主键）',
  `roleName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `authIds` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单权限编号集合',
  `roleDescription` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`roleId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;
INSERT INTO t_role VALUES(1,'超级管理员','1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16','具有最高权限');
INSERT INTO t_role VALUES(2,'宿舍管理员','1,3,5,6,7,8,15,16','管理学生宿舍信息');
INSERT INTO t_role VALUES(3,'辅导员','','负责学生的生活，学习及心里问题');
INSERT INTO t_role VALUES(8,'教师','','讲课的');
INSERT INTO t_role VALUES(9,'学生','','你懂的');
INSERT INTO t_role VALUES(18,'辅导员','','略');
-- ----------------------------
-- Records of t_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `userId` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号（主键）',
  `userName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `userType` tinyint(0) NULL DEFAULT NULL COMMENT '用户名',
  `roleId` int(0) NULL DEFAULT NULL COMMENT '用户类型',
  `userDescription` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`userId`) USING BTREE,
  INDEX `FK_t_user`(`roleId`) USING BTREE,
  CONSTRAINT `FK_t_user` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`roleId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
insert  into t_user values (1,'java1234','123456',1,1,'超级管理员，拥有最高权限');
INSERT INTO t_user VALUES(2,'marry','123456',2,2,'宿舍管理员，管理学生宿舍信息');

SET FOREIGN_KEY_CHECKS = 1;
