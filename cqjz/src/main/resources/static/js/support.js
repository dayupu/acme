function fullPath(path) {
    if (appContextPath == null) {
        appContextPath = $("#appContextPath").val();
    }
    return appContextPath + path;
}
function verifyData(data) {
    if (data.status == "1000") {
        return true;
    }
    return false;
}
function createUeditor(elementId){
    umeditorInit($);
    return UM.getEditor(elementId);
}