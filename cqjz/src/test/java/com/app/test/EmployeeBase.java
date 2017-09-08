package com.app.test;

/**
 * Created by bert on 17-9-7.
 */
public abstract class EmployeeBase implements Employee{
    @Override
    public String getPermission() {
        return "默认权限";
    }
}
