package com.manage.base.supplier.page;

/**
 * Created by bert on 2017/8/5.
 */
public class TreeNodeNews<T> extends TreeNode<T>{

    private boolean hasImage = false;

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
}
