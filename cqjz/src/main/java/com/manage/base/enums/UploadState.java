package com.manage.base.enums;

import com.manage.base.database.model.Localizable;

/**
 * Created by bert on 2017/9/1.
 */
public enum UploadState implements Localizable {

    SUCCESS("resource.constant.upload.state.success"),//SUCCESS
    NOFILE("resource.constant.upload.state.fail.nofile"),//未包含文件上传域
    TYPE("resource.constant.upload.state.fail.type"),//不允许的文件格式
    SIZE("resource.constant.upload.state.fail.size"),//文件大小超出限制
    ENTYPE("resource.constant.upload.state.fail.entype"),//请求类型ENTYPE错误
    REQUEST("resource.constant.upload.state.fail.request"),//上传请求异常
    IO("resource.constant.upload.state.fail.io"),//IO异常
    DIR("resource.constant.upload.state.fail.dir"),//目录创建失败
    UNKNOWN("resource.constant.upload.state.fail.unknown");//未知错误

    private String messageKey;

    UploadState(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String messageKey() {
        return messageKey;
    }
}
