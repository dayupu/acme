package com.manage.base.supplier;

/**
 * Created by bert on 2017/8/5.
 */
public class TreeNode<T> {

    private T id;
    private T pid;
    private String name;
    private boolean checked = false;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public T getPid() {
        return pid;
    }

    public void setPid(T pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
