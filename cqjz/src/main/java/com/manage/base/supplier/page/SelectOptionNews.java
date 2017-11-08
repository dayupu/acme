package com.manage.base.supplier.page;

/**
 * Created by bert on 2017/9/3.
 */
public class SelectOptionNews<K, V> extends SelectOption<K, V> {

    private boolean hasImage = false;

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
}
