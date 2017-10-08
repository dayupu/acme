INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 1, '系统设置', NULL, 1, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 2, '角色管理', 1, 1, 'role.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 2, '用户管理', 1, 2, 'user.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 2, '菜单管理', 1, 3, 'menu.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 2, '组织机构', 1, 4, 'depart.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 1, '新闻管理', NULL, 2, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 2, '发布新闻', 6, 1, 'news.publish');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 2, '新闻列表', 6, 2, 'news.list');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 1, '工作流', NULL, 3, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 2, '已审批一览', 9, 3, NULL);
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 2, '已发起一览', 9, 1, 'flow.list.submit');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 2, '待处理一览', 9, 2, 'flow.list.pending');
INSERT INTO ad_menu (id, description, image, "level", "name", parent_id, "sequence", url)
VALUES(nextval('seq_menu'), NULL, NULL, 2, '未通过一览', 9, 4, 'flow.list.reject');