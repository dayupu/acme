var topicApp = angular.module("topicApp", ['ngRoute']);
topicApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/', {templateUrl: '../anyone/topic/home.htm', controller: 'homeController'})
        .when('/newsList/:type', {templateUrl: '../anyone/topic/newsList.htm', controller: 'newsListController'})
        .when('/newsInfo/:number', {templateUrl: '../anyone/topic/newsInfo.htm', controller: 'newsInfoController'})
        .otherwise({redirectTo: '/'});
}]);
topicApp.service("mineHttp", function ($http) {
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
/*头部*/
topicApp.controller("headerController", function ($scope, mineHttp) {
    mineHttp.send("GET", topicUrl("detail"), {}, function (data) {
        $scope.imageUrl = imageUrl(data.imageId);
    });
});
/*首页*/
topicApp.controller("homeController", function ($scope, mineHttp) {
    $scope.home = {};
    mineHttp.send("GET", topicUrl("home"), {}, function (data) {
        $scope.home = data;
        for (index in $scope.home.imgColumns) {
            for (imgIndex in $scope.home.imgColumns[index].newses) {
                var imageId = $scope.home.imgColumns[index].newses[imgIndex].imageId;
                $scope.home.imgColumns[index].newses[imgIndex].imageUrl = imageUrl(imageId);
            }
        }

        var imageRoll = function () {
            $("div[class^='topic-image']").each(function () {
                var imageList = $(this).children('.image-list');
                var imagePage = $(this).children('.image-page');
                var showNext = function () {
                    var currImage = $(imageList).children(".show");
                    $(currImage).removeClass('show');
                    $(currImage).addClass('hide');
                    var nextImage = $(currImage).next();
                    if ($(nextImage).length == 0) {
                        nextImage = $(imageList).children().first();
                    }
                    $(nextImage).addClass('show');
                    $(nextImage).removeClass('hide');
                    var index = $(nextImage).index();
                    $(imagePage).children().each(function (i) {
                        if (i == index) {
                            $(this).addClass("page-selected")
                            $(this).removeClass("page-unselected")
                        } else {
                            $(this).removeClass("page-selected")
                            $(this).addClass("page-unselected")
                        }
                    });
                };
                setInterval(function () {
                    showNext();
                }, 5000);
            });
        };
        setTimeout(imageRoll, 2000);
    });
});

topicApp.controller("newsListController", function ($scope, $routeParams) {
    $("#newsListTable").bootstrapTable({
        url: fullPath("topic/newsList/" + $routeParams.type),
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
                title: '&nbsp;&nbsp;标题',
                field: 'title',
                align: 'left',
                formatter: function (value, row, index) {
                    return '<a href="#/newsInfo/' + row.number + '" class="link-a"><i class="fa fa-caret-right"></i>&nbsp;'
                        + row.title + '</a>';
                }
            },
            {
                title: '发布日期',
                field: 'publishTime',
                align: 'center',
                width: 120,
                formatter: function (value, row, index) {
                    return '<span class="other-font">' + row.publishTime + '</span>';
                }
            }
        ]
    });

});

/*新闻查看*/
topicApp.controller("newsInfoController", function ($scope, $routeParams, mineHttp) {
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

function topicUrl(url) {
    return "topic/" + topicCode() + "/" + url;
}

function topicCode() {
    return $("#topicCode").val();
}