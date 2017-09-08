package com.app.test;

/**
 * Created by bert on 17-9-7.
 */
public class DeveloperEmployee extends EmployeeBase {

    @Override
    public EmployeeType getType() {
        return EmployeeType.developer;
    }

    @Override
    public String getPermission() {
        return "开发者权限";
    }
}
