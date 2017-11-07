var _appContextPath = null;
// 拼接项目路径
function fullPath(path) {
    if (_appContextPath == null) {
        _appContextPath = $("#appContextPath").val();
    }
    return _appContextPath + path;
}
function goto(url) {
    location.href = url;
}
function imageUrl(imageId) {
    return fullPath("resource/image/" + imageId);
}
function setHome(obj) {
    var url = window.location.href;
    var webContext = fullPath("");
    url = url.substr(0, url.indexOf(webContext)) + webContext;
    try {
        obj.style.behavior = 'url(#default#homepage)';
        obj.setHomePage(url);
    } catch (e) {
        if (window.netscape) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            } catch (e) {
                alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
            }
        } else {
            alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【" + url + "】设置为首页。");
        }
    }
}
/**图片新闻滚动**/
var _tpxwInterval = null;
function tpxwRoll(count) {
    if (_tpxwInterval != null) {
        clearInterval(_tpxwInterval);
    }
    var callback = function (obj) {
        if(typeof obj != "object"){
            return;
        }
        var number = $(obj).attr("number");
        $("tr[type='tpxw']").each(function () {
            if ($(this).attr("number") == number) {
                $(this).removeClass("news-no-select");
                $(this).addClass("news-selected");
            } else {
                $(this).removeClass("news-selected");
                $(this).addClass("news-no-select");
            }
        });
    };
    _tpxwInterval = imageRoll($(".tpxw-show"), 400, count, 5000, callback);
}

/**星级民警滚动**/
var _xjmjInterval = null;
function xjmjRoll(count) {
    if (_xjmjInterval != null) {
        clearInterval(_xjmjInterval);
    }
    _xjmjInterval = imageRoll($(".xjmj-show"), 150, count, 8000);
}
/**技侦风采滚动**/
var _jzfcInterval = null;
function jzfcRoll(count) {
    if (_jzfcInterval != null) {
        clearInterval(_jzfcInterval);
    }
    _jzfcInterval = imageRoll($(".jzfc-show"), 195, count, 8000);
}

function imageRoll(obj, imageWidth, imageCount, mills, callback){
    if(imageCount <= 1){
       return null;
    }
    var rollLeft = function () {
        var rollUL = $(obj).find(".roll-inbox > ul");
        var first = $(rollUL).children("li").first();;
        var loop = true;
        $(rollUL).children("li").not(":animated").animate({
            left: -imageWidth
        }, 1000, function () {
            if (loop) {
                $(rollUL).append($(first).clone(false));
                $(rollUL).children("li").last().css("left", 0);
                $(first).remove();
                loop = false;
            };
            $(this).css("left", 0);
            if(callback && typeof callback == "function"){
               callback($(rollUL).children("li").first());
            }
        });
    };
    var rollRun = function () {
        var imageWidthTotal = imageWidth * imageCount;
        if($(obj).find(".roll-inbox").width() >= imageWidthTotal){
           return null;
        }
        $(obj).find(".roll-inbox > ul").width(imageWidthTotal);
        return setInterval(function () { rollLeft();}, mills);
    };
    return rollRun();
}