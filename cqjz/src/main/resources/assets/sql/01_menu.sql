-- 基本信息管理
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(1, NULL, NULL, 1, '信息管理', NULL, 1, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(4, NULL, NULL, 2, '角色信息', 1, 1, 'role.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(5, NULL, NULL, 2, '用户信息', 1, 2, 'user.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(6, NULL, NULL, 2, '菜单信息', 1, 3, 'menu.list');
-- INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
-- VALUES(7, NULL, NULL, 2, '组织机构', 1, 4, 'depart.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(14, NULL, NULL, 2, '组织机构', 1, 5, 'organ.list');
-- 工作流任务
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(2, NULL, NULL, 1, '我的任务', NULL, 2, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(8, NULL, NULL, 2, '已发起', 2, 1, 'flow.list.submit');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(9, NULL, NULL, 2, '待处理', 2, 2, 'flow.list.pending');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(11, NULL, NULL, 2, '未通过', 2, 3, 'flow.list.reject');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(10, NULL, NULL, 2, '已处理', 2, 4, 'flow.list.approve');
-- 新闻管理
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(3, NULL, NULL, 1, '新闻管理', NULL, 3, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(12, NULL, NULL, 2, '发布新闻', 3, 1, 'news.publish');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(13, NULL, NULL, 2, '新闻列表', 3, 2, 'news.list');
-- 工作事项
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(15, NULL, NULL, 1, '工作事项', NULL, 4, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(16, NULL, NULL, 2, '值班信息', 15, 1, 'jz.list.watch');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(17, NULL, NULL, 2, '技侦明星', 15, 2, 'jz.list.superstar');
-- 我的设置
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(18, NULL, NULL, 1, '我的设置', NULL, 5, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(19, NULL, NULL, 2, '个人信息', 18, 1, 'setting.self');

