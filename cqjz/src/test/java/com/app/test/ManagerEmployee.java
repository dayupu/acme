package com.app.test;

/**
 * Created by bert on 17-9-7.
 */
public class ManagerEmployee extends EmployeeBase {

    @Override
    public EmployeeType getType() {
        return EmployeeType.manager;
    }

    @Override
    public String getPermission() {
        return "经理权限";
    }
}
