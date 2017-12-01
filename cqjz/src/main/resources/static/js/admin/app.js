$.datetimepicker.setLocale('ch');
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
var mainApp = angular.module("mainApp", ["angular-loading-bar", "ui.router", "ngAnimate", "ngStorage", "ngGrid", "ui.bootstrap", "ngFileUpload","ng.ueditor"]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
    routeConfig($stateProvider, $urlRouterProvider);
});
// route config
function routeConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    $stateProvider.state("home", {url: "/", templateUrl: './home.htm', controller: "homeController"})
    // menu
        .state("menu", {url: "/menu", templateUrl: './_system/menu/menu.htm'})
        .state("menu.list", {url: "/list", templateUrl: './_system/menu/menuList.htm', controller: "systemMenuListCtl"})
        .state("menu.add", {url: "/add", templateUrl: './_system/menu/menuAdd.htm'})
        // role
        .state("role", {url: "/roleList", templateUrl: './_system/role/role.htm'})
        .state("role.list", {url: "/list", templateUrl: './_system/role/roleList.htm', controller: "systemRoleListCtl"})
        // user
        .state("user", {url: "/user", templateUrl: './_system/user/user.htm'})
        .state("user.list", {url: "/list", templateUrl: './_system/user/userList.htm', controller: "systemUserListCtl"})
        // organization
        .state("organ", {url: "/organ", templateUrl: './_system/organ/organ.htm'})
        .state("organ.list", {
            url: "/list", templateUrl: './_system/organ/organList.htm', controller: "systemOrganListCtl"
        })
        // news
        .state("news", {url: "/news", templateUrl: './_news/news.htm'})
        .state("news.publish", {url: "/publish", controller: "newsPublishCtl"})
        .state("news.add", {url: "/add", templateUrl: './_news/newsEdit.htm', controller: "newsEditCtl"})
        .state("news.edit", {url: "/edit/:number", templateUrl: './_news/newsEdit.htm', controller: "newsEditCtl"})
        .state("news.topic", {url: "/topic", templateUrl: './_news/newsTopic.htm', controller: "newsTopicCtl"})
        .state("news.preview", {
            url: "/preview/:number", templateUrl: './_news/preview.htm', controller: "newsPreviewCtl"
        })
        .state("news.list", {url: "/list", templateUrl: './_news/newsList.htm', controller: "newsListCtl"})
        // flow
        .state("flow", {url: "/flow", templateUrl: './_flow/flow.htm'})
        .state("flow.approve", {
            url: "/approve/:processId/:taskId", templateUrl: './_flow/flowApprove.htm', controller: "flowApproveCtl"
        })
        .state("flow.list", {url: "/list", templateUrl: './_flow/flow.htm'})
        .state("flow.list.submit", {
            url: "/submit", templateUrl: './_flow/flowListSubmit.htm', controller: "flowSubmitListCtl"
        })
        .state("flow.list.pending", {
            url: "/pending", templateUrl: './_flow/flowListPending.htm', controller: "flowPendingListCtl"
        })
        .state("flow.list.reject", {
            url: "/reject", templateUrl: './_flow/flowListReject.htm', controller: "flowRejectListCtl"
        })
        .state("flow.list.approve", {
            url: "/approve", templateUrl: './_flow/flowListApprove.htm', controller: "flowApproveListCtl"
        })
        // jz
        .state("jz", {url: "/jz", templateUrl: './_jz/jz.htm'})
        .state("jz.list", {url: "/list", templateUrl: './_jz/jz.htm'})
        .state("jz.list.watch", {
            url: "/watch", templateUrl: './_jz/watchList.htm', controller: "jzWatchListCtl"
        })
        .state("jz.list.superstar", {
            url: "/superstar", templateUrl: './_jz/superstarList.htm', controller: "jzSuperstarListCtl"
        })
        .state("jz.list.contacts", {
            url: "/contacts", templateUrl: './_jz/contacts.htm', controller: "jzContactsListCtl"
        })
        .state("jz.list.style", {
            url: "/style", templateUrl: './_jz/styleList.htm', controller: "jzStyleListCtl"
        })
        .state("jz.list.styleEdit", {
            url: "/style/:number", templateUrl: './_jz/style.htm', controller: "jzStyleEditListCtl"
        })
        // setting
        .state("setting", {url: "/setting", templateUrl: './_setting/setting.htm'})
        .state("setting.self", {
            url: "/self", templateUrl: './_setting/self.htm', controller: "settingSelfCtl"
        })
    ;
}
// factorys
mainApp.factory("mineMessage", function ($rootScope) {
    return {
        publish: function (name, parameters) {
            $rootScope.$emit(name, parameters);
        },
        subscribe: function (name, listener) {
            $rootScope.$on(name, listener);
        }
    };
});

mainApp.controller("menuController", function ($http, $scope, $location, $sessionStorage, $state, mineMessage, mineHttp) {
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
                    sub += "<li mark='menuTwo'><a href='javascript:void(0);' onfocus='this.blur();' class='second-menu-a' url='" + subMenu.url + "'>" + subMenu.name + "</a></li>";
                }
                sub += "</ul>";
            }
            html += "<li mark='menuOne'><a href='javascript:void(0);' onfocus='this.blur();' class='first-menu-a'><span class='pull-right'>" +
                "<i class='fa fa-angle-right'></i></span>" +
                "<i class='fa fa-snapchat' style='font-size: 12px;color:white;'></i>&nbsp;&nbsp;&nbsp;&nbsp;" + menu.name + "</span></a>"
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
    mineHttp.send("GET", "admin/index/user", {}, function (data) {
        $scope.user = data.content;
    });
    $scope.loginOut = function () {
        location.href = fullPath("admin/loginOut");
    };
    $scope.home = function () {
        location.href = fullPath("admin/loginOut");
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
// 验证响应信息
function verifyData(data) {
    if (data.status == "1000") {
        return true;
    }
    return false;
}
// 创建Umeditor
function createUeditor(elementId) {
    umeditorInit($);
    return UM.getEditor(elementId);
}