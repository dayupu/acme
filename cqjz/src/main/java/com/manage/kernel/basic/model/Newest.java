package com.manage.kernel.basic.model;

/**
 * Created by bert on 2017/10/15.
 */
public class Newest<T> {

    private String info;
    private T dto;

    public Newest() {

    }

    public Newest(T dto, String info) {
        this.dto = dto;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getDto() {
        return dto;
    }

    public void setDto(T dto) {
        this.dto = dto;
    }
}
