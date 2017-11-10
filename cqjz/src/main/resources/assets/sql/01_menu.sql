-- 基本信息管理
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(1, NULL, NULL, 1, '系统管理', NULL, 1, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(10, NULL, NULL, 2, '角色信息', 1, 1, 'role.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(11, NULL, NULL, 2, '用户信息', 1, 2, 'user.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(12, NULL, NULL, 2, '菜单信息', 1, 3, 'menu.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(13, NULL, NULL, 2, '组织机构', 1, 4, 'organ.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(14, NULL, NULL, 2, '新闻专题', 1, 5, 'news.topic');

-- 工作流任务
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(2, NULL, NULL, 1, '我的任务', NULL, 2, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(20, NULL, NULL, 2, '已发起', 2, 1, 'flow.list.submit');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(21, NULL, NULL, 2, '待处理', 2, 2, 'flow.list.pending');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(22, NULL, NULL, 2, '未通过', 2, 3, 'flow.list.reject');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(23, NULL, NULL, 2, '已处理', 2, 4, 'flow.list.approve');
-- 新闻管理
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(3, NULL, NULL, 1, '新闻管理', NULL, 3, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(30, NULL, NULL, 2, '发布新闻', 3, 1, 'news.publish');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(31, NULL, NULL, 2, '我的新闻', 3, 2, 'news.list');
-- 工作事项
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(4, NULL, NULL, 1, '工作事项', NULL, 4, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(40, NULL, NULL, 2, '值班信息', 4, 1, 'jz.list.watch');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(41, NULL, NULL, 2, '技侦明星', 4, 2, 'jz.list.superstar');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(42, NULL, NULL, 2, '技侦风采', 4, 3, 'jz.list.style');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(43, NULL, NULL, 2, '通讯录', 4, 4, 'jz.list.contacts');

-- 我的设置
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(5, NULL, NULL, 1, '我的设置', NULL, 5, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(50, NULL, NULL, 2, '个人信息', 5, 1, 'setting.self');
