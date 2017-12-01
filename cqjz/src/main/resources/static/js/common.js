var _appContextPath = null;
// 拼接项目路径
function fullPath(path) {
    if (_appContextPath == null) {
        _appContextPath = $("#appContextPath").val();
    }
    return _appContextPath + path;
}

function imageUrl(imageId) {
    return fullPath("resource/image/" + imageId);
}

function fileUrl(fileId) {
    return fullPath("resource/file/" + fileId);
}

function isEmpty(value) {
    if (typeof value == "number") {
        return false;
    }
    return !value || !value.length;
}

function getYear() {
    return new Date().getFullYear();
}

function today() {
    var date = new Date();
    var seperator = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator + month + seperator + strDate
        + " 00:00:00";
    return currentdate;
}

function getMonths() {
    var months = new Array();
    var fullMonth = function (month) {
        if (month < 10) {
            month = "0" + month;
        }
        return month;
    };

    for (var i = 1; i < 13; i++) {
        var month = {};
        month.key = fullMonth(i);
        month.value = fullMonth(i);
        months.push(month);
    }
    return months;
}

function goto(url) {
    location.href = url;
}