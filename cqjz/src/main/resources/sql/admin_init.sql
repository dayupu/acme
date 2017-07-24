-- admin_user
INSERT INTO admin_user (id, created_at, created_by, status, updated_by, updated_at, account, email, mobile, "name", password, telephone, dept_id) VALUES(0, NULL, NULL, 0, NULL, NULL, 'admin', NULL, NULL, NULL, '707f8e47f7fab4f5cb2f7733e2a8afe017d64897e072ad09814d9fcc6fddcb440a2e8346a16a4c5a', NULL, NULL);
-- admin_role
INSERT INTO admin_role (id, created_at, created_by, status, updated_by, updated_at, description, "name", parent_id) VALUES(nextval('seq_admin_role'), now(), 0, 0, 0, now(), NULL, '管理员', null);
-- admin_menu
INSERT INTO admin_menu(id, description, image, "level", "name", parent_id, "sequence", url)VALUES(nextval('seq_admin_menu'), NULL, NULL, 1, '系统设置', NULL, 1, NULL);
INSERT INTO admin_menu(id, description, image, "level", "name", parent_id, "sequence", url)VALUES(nextval('seq_admin_menu'), NULL, NULL, 2, '角色管理', 1, 1, NULL);
INSERT INTO admin_menu(id, description, image, "level", "name", parent_id, "sequence", url)VALUES(nextval('seq_admin_menu'), NULL, NULL, 2, '用户管理', 1, 2, NULL);
INSERT INTO admin_menu(id, description, image, "level", "name", parent_id, "sequence", url)VALUES(nextval('seq_admin_menu'), NULL, NULL, 2, '菜单管理', 1, 3, NULL);
-- admin_role_menu
INSERT INTO admin_role_menu(role_id, menu_id)VALUES(1, 4);
INSERT INTO admin_role_menu(role_id, menu_id)VALUES(1, 3);
INSERT INTO admin_role_menu(role_id, menu_id)VALUES(1, 2);
INSERT INTO admin_role_menu(role_id, menu_id)VALUES(1, 1);
-- admin_user_role
INSERT INTO admin_user_role(user_id, role_id)VALUES(0, 1);