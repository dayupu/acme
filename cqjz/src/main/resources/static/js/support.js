var _appContextPath = null;

// 拼接项目路径
function fullPath(path) {
    if (_appContextPath == null) {
        _appContextPath = $("#appContextPath").val();
    }
    return _appContextPath + path;
}

// 验证响应信息
function verifyData(data) {
    if (data.status == "1000") {
        return true;
    }
    return false;
}

// 创建Umeditor
function createUeditor(elementId){
    umeditorInit($);
    return UM.getEditor(elementId);
}