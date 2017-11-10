var _appContextPath = null;
var _ueditorToolbars = ['fullscreen', 'source', '|', 'undo', 'redo', '|', 'bold', 'italic', 'underline', 'fontborder',
    'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain',
    '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|', 'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
    'directionalityltr', 'directionalityrtl', 'indent', '|',
    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
    'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
    'simpleupload', 'insertimage', 'insertcode', 'pagebreak', 'template', 'background', '|',
    'horizontal', 'date', 'time', 'spechars', '|',
    'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol',
    'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
    'print', 'preview', 'searchreplace', 'help'];
$.datetimepicker.setLocale('ch');
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

function imageUrl(imageId) {
    return fullPath("resource/image/" + imageId);
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

// 创建Umeditor
function createUeditor(elementId) {
    umeditorInit($);
    return UM.getEditor(elementId);
}

mainApp.controller("menuController", function ($http, $scope, $location, $sessionStorage, $state, mineMessage, mineHttp) {
    mineHttp.send("GET", "admin/index/user", {}, function (data) {
        $scope.user = data.content;
    });
    $scope.loadMenu = function () {
        mineHttp.send("GET", "admin/index/menuList", {}, function (data) {
            if (verifyData(data)) {
                $scope.buildMenu(data.content);
                $scope.menuListener();
            } else {
                $scope.message = data.message;
            }
        });
    };
    $scope.buildMenu = function (menus) {
        var pNode = $("ul[mark='menuRoot']");
        var html = "";
        for (var i in menus) {
            var menu = menus[i];
            if (menu.subMenus.length > 0) {
                var sub = "<ul class='menu-nav menu-second-ul' style='display:none;'>";
                for (var j in menu.subMenus) {
                    var subMenu = menu.subMenus[j];
                    sub += "<li mark='menuTwo'><a href='javascript:void(0);' class='second-menu-a' url='" + subMenu.url + "'>" + subMenu.name + "</a></li>";
                }
                sub += "</ul>";
            }
            html += "<li mark='menuOne'><a href='javascript:void(0);' class='first-menu-a'><span class='pull-right'>" +
                "<i class='fa fa-angle-right'></i></span>" +
                "<i class='fa fa-circle-o' style='font-size: 12px;color:orange;'></i>&nbsp;&nbsp;&nbsp;&nbsp;" + menu.name + "</span></a>"
                + sub + "</li>";
        }
        $(pNode).append(html);
    };

    $scope.menuArrow = function (obj, scope) {
        if (scope == "auto") {
            if ($(obj).hasClass("fa-angle-right")) {
                $(obj).removeClass("fa-angle-right");
                $(obj).addClass("fa-angle-down");
            } else {
                $(obj).removeClass("fa-angle-down");
                $(obj).addClass("fa-angle-right");
            }
        } else if (scope = "hide") {
            $(obj).removeClass("fa-angle-down");
            $(obj).addClass("fa-angle-right");
        }

    };
    $scope.menuListener = function () {
        var rootNode = $("ul[mark='menuRoot']");
        $(rootNode).children("li[mark='menuOne']").each(function () {
            var oneA = $(this).children("a");
            var oneUL = $(this).children("ul");
            $scope.hoverEvent(oneA);
            $(oneA).click(function () {
                $(rootNode).children("li[mark='menuOne']").each(function () {
                    if (!$(this).is($(oneA).parent())) {
                        $scope.menuArrow($(this).find("i[class^='fa']")[0], "hide");
                        $(this).children("ul").slideUp("fast");
                    }
                });
                $scope.menuArrow($(this).find("i[class^='fa']")[0], "auto");
                $(this).next("ul").slideToggle("fast");
            });
            $(oneUL).children("li[mark='menuTwo']").each(function () {
                var twoA = $(this).children("a");
                $scope.hoverEvent(twoA);
                $(twoA).click(function () {
                    $scope.clickEvent(twoA);
                    $state.go($(this).attr("url"));
                });
            });
        });
    };

    $scope.hoverEvent = function (obj) {
        $(obj).hover(function () {
            if (!$(obj).hasClass("menu-hover")) {
                $(obj).addClass("menu-hover");
            }
        }, function () {
            if ($(obj).hasClass("menu-hover")) {
                $(obj).removeClass("menu-hover");
            }
        });
    };

    $scope.clickEvent = function (obj) {
        if (!$(obj).hasClass("menu-selected")) {
            $(obj).addClass("menu-selected");
        }
        var curParent = $(obj).parent();
        $("li[mark='menuTwo']").not(curParent).each(function () {
            if ($(this).children("a").hasClass("menu-selected")) {
                $(this).children("a").removeClass("menu-selected");
            }
        })
    };
});
mainApp.controller("headerController", function ($http, $scope, $location, $sessionStorage, $state, mineMessage, mineHttp) {
    $scope.user = {};
    $scope.loginOut = function () {
        location.href = fullPath("admin/loginOut");
    };
    $scope.home = function () {
        location.href = fullPath("admin/loginOut");
    };

    $scope.onMouseLeave = function (event) {
        $scope.messageHide();
    };
    $scope.messageHide = function () {
        $("#headerMessage").fadeOut("fast");
        $("#headerMessage").parent().unbind("mouseleave", $scope.onMouseLeave);
    }
    $scope.lookMessage = function () {
        if ($("#headerMessage").css("display") == "none") {
            $("#headerMessage").slideDown("fast");
            $("#headerMessage").parent().bind("mouseleave", $scope.onMouseLeave);
        }
    };
    $scope.getMessage = function () {
        mineHttp.send("GET", "admin/index/message", {}, function (data) {
            $scope.message = data.content;
        });
    };

    $scope.getMessage();
    setInterval($scope.getMessage, 300000);
});
mainApp.controller("contentController", function ($scope, mineMessage, $sessionStorage, $location) {
    mineMessage.subscribe("menuLocation", function (event, menu) {
        $scope.navLocations = menu.locations;
    });
});
mainApp.controller("homeController", function ($scope, mineMessage, mineHttp, $sessionStorage, $location) {
    $scope.newest = {};
    mineHttp.send("GET", "admin/index/newest", {}, function (data) {
        $scope.newest = data.content;
    });
});