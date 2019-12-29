/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : studentmanager

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 29/12/2019 17:35:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gradeId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `gradeId`(`gradeId`) USING BTREE,
  CONSTRAINT `clazz_ibfk_1` FOREIGN KEY (`gradeId`) REFERENCES `grade` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES (4, '二年4班', '', 28);
INSERT INTO `clazz` VALUES (5, '移动应用开发1班', '55人', 30);
INSERT INTO `clazz` VALUES (6, '前端开发', '39人', 29);
INSERT INTO `clazz` VALUES (7, 'Java开发', '60人', 30);

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (27, '一年级', '');
INSERT INTO `grade` VALUES (28, '二年级', '3个班');
INSERT INTO `grade` VALUES (29, '软件工程', '软件工程2011');
INSERT INTO `grade` VALUES (30, '计算机系', '移动应用开发');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clazzId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `clazzId`(`clazzId`) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`clazzId`) REFERENCES `clazz` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (4, 'S1574150393204', 'safa', 'wewrq', '男', '/photo/student.jpg', 'weqe', 4);
INSERT INTO `student` VALUES (7, 'S1574154618032', '小明', '123', '男', '/upload/1574158223586.jpg', '', 7);
INSERT INTO `student` VALUES (8, 'S1574158286507', '小红', '111', '女', '/upload/1574158262776.jpg', '三好学生', 6);
INSERT INTO `student` VALUES (11, 'S1574868521799', 'lis', '123', '男', '/upload/1574868503314.jpeg', '', 7);
INSERT INTO `student` VALUES (12, 'S1574868689229', '汪六', '147', '男', '/upload/1574868666660.jpg', '', 4);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123');
INSERT INTO `user` VALUES (2, '123', '123');
INSERT INTO `user` VALUES (3, 'asasd', 'asda');
INSERT INTO `user` VALUES (4, 'asaaa', 'asda');
INSERT INTO `user` VALUES (5, 'asdad', 'dsad');
INSERT INTO `user` VALUES (6, 'sdsd', 'wewew');
INSERT INTO `user` VALUES (7, 'weewre', 'weqeq');
INSERT INTO `user` VALUES (8, 'wsdwdad', 'wewe');
INSERT INTO `user` VALUES (13, 'sffr', 'fgrtyr');
INSERT INTO `user` VALUES (21, 'fasfaswe', 'qwewew');
INSERT INTO `user` VALUES (24, '双方的', 'wrfe3');

SET FOREIGN_KEY_CHECKS = 1;
