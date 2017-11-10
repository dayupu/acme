-- ad_user
INSERT INTO ad_user(id, created_at, created_by, updated_by, updated_at, status, account, approve_role, email, gender, mobile, "name", password, telephone)VALUES(0, NULL, NULL, 0, now(), 1, 'admin', 100, NULL, 1, NULL, '管理员', '707f8e47f7fab4f5cb2f7733e2a8afe017d64897e072ad09814d9fcc6fddcb440a2e8346a16a4c5a', NULL);
-- ad_role
INSERT INTO ad_role (id, created_at, created_by, status, updated_by, updated_at, description, "name") VALUES(0, now(), 0, 0, 0, now(), NULL, '管理员');
-- ad_user_role
INSERT INTO ad_user_role(user_id, role_id)VALUES(0, 0);
-- ad_role_menu
INSERT INTO ad_role_menu(role_id, menu_id) select 0, id from ad_menu;
