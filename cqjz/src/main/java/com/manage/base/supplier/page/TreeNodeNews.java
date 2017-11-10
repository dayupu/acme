package com.manage.base.supplier.page;

/**
 * Created by bert on 2017/8/5.
 */
public class TreeNodeNews<T> extends TreeNode<T> {

    private boolean hasImage = false;
    private boolean enabled = true;

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
