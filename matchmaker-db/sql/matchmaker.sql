/*
 Navicat Premium Data Transfer

 Source Server         : dev
 Source Server Type    : MySQL
 Source Server Version : 50648
 Source Host           : localhost:3306
 Source Schema         : matchmaker

 Target Server Type    : MySQL
 Target Server Version : 50648
 File Encoding         : 65001

 Date: 01/08/2022 23:41:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '管理员名称',
  `password` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '管理员密码',
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
INSERT INTO `tb_admin` VALUES (1, 'admin', '123456', '2022-07-26 18:33:21', '2022-07-31 15:51:08', 0);

-- ----------------------------
-- Table structure for tb_flipped_mobile_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_flipped_mobile_group`;
CREATE TABLE `tb_flipped_mobile_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flipped_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '心动人手机号',
  `by_flipped_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '被心动人手机号',
  `add_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  `apply` tinyint(1) NULL DEFAULT 0 COMMENT '未报名 ：false 已报名:true',
  `hand` tinyint(1) NULL DEFAULT 0 COMMENT '已牵手为true,未牵手false',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '见面意向表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_flipped_mobile_group
-- ----------------------------
INSERT INTO `tb_flipped_mobile_group` VALUES (1, '13068261277', '13068261275', '2022-07-29 22:45:49', '2022-07-31 16:27:52', 0, 1, 0);
INSERT INTO `tb_flipped_mobile_group` VALUES (2, '13068261278', '2', '2022-07-31 18:33:41', '2022-07-31 18:33:41', 0, 0, 0);
INSERT INTO `tb_flipped_mobile_group` VALUES (3, '13068261278', '2', '2022-07-31 18:33:50', '2022-07-31 18:33:50', 0, 0, 0);
INSERT INTO `tb_flipped_mobile_group` VALUES (4, '13068261278', '2', '2022-07-31 18:33:53', '2022-07-31 18:33:53', 0, 0, 0);
INSERT INTO `tb_flipped_mobile_group` VALUES (5, '13068261278', '2', '2022-07-31 18:34:01', '2022-07-31 19:07:18', 0, 1, 1);

-- ----------------------------
-- Table structure for tb_system
-- ----------------------------
DROP TABLE IF EXISTS `tb_system`;
CREATE TABLE `tb_system`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统配置名',
  `key_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统配置值',
  `add_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_system
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户密码',
  `gender` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '男或者女',
  `age` int(11) NULL DEFAULT 0 COMMENT '年龄',
  `last_login_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最近一次登录时间',
  `last_login_ip` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最近一次登录IP地址',
  `certificate` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '身份证号',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户手机号码',
  `marriage` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '婚姻状况',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户头像图片',
  `weixin_openid` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '微信登录openid',
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `status` int(3) NOT NULL DEFAULT 1 COMMENT '0 可用, 1 禁用, 2 注销',
  `hand_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '已牵手：true 未牵手：false',
  `place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '常住地',
  `education` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '学历',
  `industry` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工作行业类别',
  `income` int(11) NULL DEFAULT 0 COMMENT '月收入',
  `height` int(11) NULL DEFAULT 0 COMMENT '身高',
  `weight` int(11) NULL DEFAULT 0 COMMENT '体重',
  `meet_intention` tinyint(1) NOT NULL DEFAULT 1 COMMENT '有见面意向：true 无：false',
  `quit_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '退出理由',
  `household` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '户籍',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_certificate`(`certificate`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, '安自然', '$2a$10$g.NMZB7TpaaeaRiNuQsCYepSobAvE/CR0BfG/VSaGVD7T9e/7gAgS', '女', 22, '2022-07-29 22:19:00', '0:0:0:0:0:0:0:1', '230182198012251659', '13068261271', '未婚', 'https://img2.baidu.com/it/u=2859542338,3761174075&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500', '', '2022-07-28 22:33:02', '2022-07-31 18:43:25', 0, 2, 0, '北京市海淀区', '初中', '医疗', 3000, 166, 0, 1, '', '');
INSERT INTO `tb_user` VALUES (2, '张芸', '$2a$10$MRm9mA0mWeBAgSV36qqJCuNQTpQdvv4OKpyuW2jjQWeCUBfmHhzga', '女', 19, '2022-07-29 22:19:00', '0:0:0:0:0:0:0:1', '230182198012251659', '13068261272', '已婚', 'https://img2.baidu.com/it/u=3062813899,1142128231&fm=253&fmt=auto&app=138&f=JPEG?w=479&h=500', '', '2022-07-28 22:33:02', '2022-07-31 17:41:21', 0, 0, 0, '青岛市', '高中', '金融', 8000, 176, 0, 1, '', '');
INSERT INTO `tb_user` VALUES (3, '董文慧', '$2a$10$SjdlX1JtGDU2Ca2MGUnqV.gir0AiuVXkBwlg26jkO5PNhBTm4PSMC', '女', 25, '2022-07-29 22:19:00', '0:0:0:0:0:0:0:1', '230182198012251659', '13068261273', '未婚', 'https://img1.baidu.com/it/u=4216761644,15569246&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500', '', '2022-07-28 22:33:02', '2022-07-31 18:43:24', 0, 0, 0, '哈尔滨市', '专科', '互联网', 10000, 190, 0, 1, '', '');
INSERT INTO `tb_user` VALUES (4, '李辉', '$2a$10$cOI3ZkOTHh4F85k7Ko7bRe5T5cCYs5Ja8t609MkDE1iagfn5BNn3q', '女', 28, '2022-07-29 22:19:00', '0:0:0:0:0:0:0:1', '230182198012251659', '13068261274', '未婚', 'https://img2.baidu.com/it/u=4122738859,2522601053&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '', '2022-07-28 22:33:02', '2022-07-31 17:41:22', 0, 2, 0, '济南市', '本科', '医药', 12000, 174, 0, 1, '', '');
INSERT INTO `tb_user` VALUES (5, '王静', '$2a$10$3x3.ftEiiVOgNJpUamxcmOxzbcBMF02USXAD8PXMvc61LA0Hu9afi', '女', 30, '2022-08-01 12:39:27', '192.168.1.6', '230182198012251659', '13068261275', '未婚', 'https://img2.baidu.com/it/u=2362373712,561693695&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '', '2022-07-28 22:33:02', '2022-08-01 12:39:48', 0, 0, 0, '苏州', '博士', '农业', 20000, 155, 20000, 1, '', '');
INSERT INTO `tb_user` VALUES (6, '宋杰', '123456', '男', 23, '2022-08-01 21:32:53', '192.168.1.10', '230182198012251659', '13068261277', '未婚', 'http://i.qqkou.com/i/0a3461223215x2944920869b253.jpg', '', '2022-07-28 22:33:02', '2022-08-01 21:55:41', 0, 0, 0, '北京市海淀区', '硕士', '建筑', 14000, 183, 14000, 1, '', '');

-- ----------------------------
-- Table structure for tb_user_intention
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_intention`;
CREATE TABLE `tb_user_intention`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT 0 COMMENT '用户ID',
  `age_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '年龄范围',
  `minimum_education` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最低学历',
  `height_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '身高范围',
  `weight_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '体重范围',
  `industry` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '职业',
  `income_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '收入范围',
  `place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '常住地',
  `marriage` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '婚姻状况',
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_user_intention
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
