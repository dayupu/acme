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
anyoneApp.controller("headerController", function ($scope, $location) {
    $scope.gotToUrl = function (url) {
        $location.path(url)
    };

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
            tpxwRoll(data.picNews.length);
            xjmjRoll(data.superstars.length);
            jzfcRoll(data.jzStyles.length);
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
                    return '<img src="' + row.imageBase64 + '" class="xjmj-image" />';
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
        for(index in data.styleLines){
            data.styleLines[index].imageUrl = imageUrl(data.styleLines[index].imageId);
        }
        $scope.style = data;
    });
});