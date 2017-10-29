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
    var picRollLeft = function () {
        var first = $('.tpxw-pic li').first();
        var loop = true;
        $('.tpxw-pic li').not(":animated").animate({
            left: -400
        }, 1000, function () {
            if (loop) {
                $('.tpxw-pic').append($(first).clone(false));
                $(first).remove();
                loop = false;
                newsSelected($('.tpxw-pic li').first());
            };
            $(this).css("left", 0);
        });
    };

    var newsSelected = function (obj) {
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

    var pickRoll = function () {
        $('.tpxw-pic').width(400 * count);
        if ($("tr[type='tpxw']").length > 0) {
            newsSelected($("tr[type='tpxw']").eq(0));
        }
        _tpxwInterval = setInterval(function () {
            picRollLeft();
        }, 5000);
    };
    setTimeout(pickRoll, 1000);
}