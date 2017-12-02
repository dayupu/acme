var anyoneApp = angular.module("anyoneApp", ['ngRoute']);
anyoneApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/', {templateUrl: './anyone/home.htm', controller: 'homeController'})
        .when('/contacts', {templateUrl: './anyone/contacts.htm', controller: 'contactsController'})
        .when('/newsList/:type', {templateUrl: './anyone/newsList.htm', controller: 'newsListController'})
        .when('/superstarList', {templateUrl: './anyone/superstarList.htm', controller: 'superstarListController'})
        .when('/newsInfo/:number', {templateUrl: './anyone/newsInfo.htm', controller: 'newsInfoController'})
        .when('/search/:searchText', {templateUrl: './anyone/search.htm', controller: 'searchController'})
        .when('/style/:number', {templateUrl: './anyone/styleInfo.htm', controller: 'styleInfoController'})
        .otherwise({redirectTo: '/'});
}]);

anyoneApp.service("mineHttp", function ($http) {
    this.send = function (method, url, params, callback) {
        var setting = {
            method: method,
            url: fullPath(url)
        };
        $.extend(setting, params);
        $http(setting).then(function successCallback(response) {
            callback(response.data);
        }, function errorCallback(response) {
            alert("请求异常！")
        });
    };
});

/*网页头部操作*/
anyoneApp.controller("headerController", function ($scope, $location, mineHttp) {
    $scope.gotToUrl = function (url) {
        $location.path(url)
    };
    $scope.gotToUrlBlank = function (url) {
        window.open(url);
    };
    mineHttp.send("GET", "topic/types", null, function (data) {
        $scope.topics = data;
    });

    $scope.search = function () {
        if (typeof $scope.searchText != "string" || $scope.searchText == "") {
            return;
        }
        $location.path("/search/" + encodeURIComponent($scope.searchText));
    }
});

/*首页*/
anyoneApp.controller("homeController", function ($scope, mineHttp) {
    $scope.home = {};
    $scope.loadDatas = function () {
        mineHttp.send("GET", "free/home", null, function (data) {
            for (var i = 0; i < data.picNews.length; i++) {
                data.picNews[i].imageUrl = imageUrl(data.picNews[i].imageId);
            }
            for (var i = 0; i < data.jzStyles.length; i++) {
                data.jzStyles[i].imageUrl = imageUrl(data.jzStyles[i].imageId);
            }
            $scope.home = data;
            tpxwRoll(data.picNews.length);//图片滚动
            xjmjRoll(data.superstars.length);//图片滚动
            jzfcRoll(data.jzStyles.length);//图片滚动
        });
    };
    $scope.loadDatas();
});
/*新闻列表*/
anyoneApp.controller("newsListController", function ($scope, $routeParams) {
    /*alert($routeParams.type);*/
    if ($routeParams.type == "tpxw") {
        $scope.newsType = "图片新闻";
    } else if ($routeParams.type == "jqkx") {
        $scope.newsType = "警情快讯";
    } else if ($routeParams.type == "dwjs") {
        $scope.newsType = "队伍建设";
    } else if ($routeParams.type == "bmdt") {
        $scope.newsType = "部门动态";
    } else if ($routeParams.type == "xxyd") {
        $scope.newsType = "学习园地";
    } else if ($routeParams.type == "whsb") {
        $scope.newsType = "网海拾贝";
    } else if ($routeParams.type == "kjlw") {
        $scope.newsType = "科技瞭望";
    } else {
        return;
    }

    $("#newsListTable").bootstrapTable({
        url: fullPath("free/newsList/" + $routeParams.type),
        dataType: "json",
        method: "post",
        singleSelect: false,
        sidePagination: "server", //服务端处理分页
        pageNumber: 1,
        pageSize: 20,
        pageList: [20, 50],
        pagination: true, //分页
        search: false,
        queryParamsType: "",
        queryParams: function (params) {
            return $.extend({}, params);
        },
        sortable: true,
        sortName: "publishTime",
        sortOrder: "desc",
        columns: [
            {
                title: '标题',
                field: 'title',
                align: 'left',
                formatter: function (value, row, index) {
                    return '<a href="#/newsInfo/' + row.number + '">' + row.title + '</a>';
                }
            },
            {
                title: '发布日期',
                field: 'publishTime',
                align: 'center',
                width: 150,
                sortable: true
            }
        ]
    });

});

/*搜索结果*/
anyoneApp.controller("searchController", function ($scope, $routeParams) {
    var searchText = decodeURIComponent($routeParams.searchText);
    $("#searchListTable").bootstrapTable({
        url: fullPath("free/search/news"),
        dataType: "json",
        method: "post",
        singleSelect: false,
        sidePagination: "server", //服务端处理分页
        pageNumber: 1,
        pageSize: 20,
        pageList: [20, 50],
        pagination: true, //分页
        search: false,
        queryParamsType: "",
        queryParams: function (params) {
            return $.extend({}, params, {"searchText": searchText});
        },
        sortable: true,
        sortName: "publishTime",
        sortOrder: "desc",
        columns: [
            {
                title: '标题',
                field: 'title',
                align: 'left',
                formatter: function (value, row) {
                    return '<a href="#/newsInfo/' + row.number + '">' + row.title + '</a>';
                }
            },
            {
                title: '发布日期',
                field: 'publishTime',
                align: 'center',
                width: 150,
                sortable: true
            }
        ]
    });

});
/*技侦明星列表*/
anyoneApp.controller("superstarListController", function ($scope, mineHttp) {
    $("#superstarListTable").bootstrapTable({
        url: fullPath("free/superstarList/"),
        dataType: "json",
        method: "post",
        singleSelect: false,
        sidePagination: "server", //服务端处理分页
        pageNumber: 1,
        pageSize: 5,
        pageList: [5, 10],
        pagination: true, //分页
        search: false,
        queryParamsType: "",
        queryParams: function (params) {
            return $.extend({}, params);
        },
        sortable: true,
        sortName: "year",
        sortOrder: "desc",
        columns: [
            {
                title: '相片',
                field: 'imageBase64',
                align: 'left',
                width: 150,
                formatter: function (value, row) {
                    return '<img src="' + row.imageBase64 + '" class="superstar-image" />';
                }
            },
            {
                title: '姓名',
                field: 'name',
                align: 'center',
                width: 100
            },
            {
                title: '年月',
                field: 'year',
                align: 'center',
                width: 100,
                sortable: true,
                formatter: function (value, row) {
                    return row.year + "-" + row.month;
                }
            },
            {
                title: '荣誉',
                field: 'honor',
                width: 200
            },
            {
                title: '事迹',
                field: 'story'
            }

        ]
    });

});
/*新闻查看*/
anyoneApp.controller("newsInfoController", function ($scope, $routeParams, mineHttp) {
    var number = $routeParams.number;
    if (typeof number != "string") {
        return;
    }
    mineHttp.send("GET", "free/newsInfo/" + number, null, function (data) {
        $scope.news = data;
        $("#newsContent").html(data.content);
        for (index in $scope.news.attachments) {
            $scope.news.attachments[index].fileUrl = fileUrl($scope.news.attachments[index].fileId);
        }
    });
});
/*通讯录*/
anyoneApp.controller("contactsController", function ($scope, mineHttp) {
    mineHttp.send("GET", "free/contacts", null, function (data) {
        $("#contactsContent").html(data.content);
    });
});
/*新闻查看*/
anyoneApp.controller("styleInfoController", function ($scope, $routeParams, mineHttp) {
    var number = $routeParams.number;
    if (typeof number != "string") {
        return;
    }
    mineHttp.send("GET", "free/styleInfo/" + number, null, function (data) {
        for (index in data.styleLines) {
            data.styleLines[index].imageUrl = imageUrl(data.styleLines[index].imageId);
        }
        $scope.style = data;
    });
});

/*设置主页*/
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
        if (typeof obj != "object") {
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

function imageRoll(obj, imageWidth, imageCount, mills, callback) {
    if (imageCount <= 1) {
        return null;
    }
    var rollLeft = function () {
        var rollUL = $(obj).find(".roll-inbox > ul");
        var first = $(rollUL).children("li").first();
        ;
        var loop = true;
        $(rollUL).children("li").not(":animated").animate({
            left: -imageWidth
        }, 1000, function () {
            if (loop) {
                $(rollUL).append($(first).clone(false));
                $(rollUL).children("li").last().css("left", 0);
                $(first).remove();
                loop = false;
            }
            ;
            $(this).css("left", 0);
            if (callback && typeof callback == "function") {
                callback($(rollUL).children("li").first());
            }
        });
    };
    var rollRun = function () {
        var imageWidthTotal = imageWidth * imageCount;
        if ($(obj).find(".roll-inbox").width() >= imageWidthTotal) {
            return null;
        }
        $(obj).find(".roll-inbox > ul").width(imageWidthTotal);
        return setInterval(function () {
            rollLeft();
        }, mills);
    };
    return rollRun();
}