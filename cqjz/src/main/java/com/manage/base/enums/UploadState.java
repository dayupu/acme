package com.manage.base.enums;

/**
 * Created by bert on 2017/9/1.
 */
public enum UploadState {
    SUCCESS,//SUCCESS
    NOFILE, //未包含文件上传域
    TYPE,//不允许的文件格式
    SIZE, //文件大小超出限制
    ENTYPE,//请求类型ENTYPE错误
    REQUEST,//上传请求异常
    IO, //IO异常
    DIR,//目录创建失败
    UNKNOWN;//未知错误
}
