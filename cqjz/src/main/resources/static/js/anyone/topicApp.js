var topicApp = angular.module("topicApp", ['ngRoute']);
topicApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/', {templateUrl: '../anyone/topic/home.htm', controller: 'homeController'})
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
     });
});

function topicUrl(url){
   return "topic/" + topicCode() + "/" + url;
}

function topicCode(){
   return $("#topicCode").val();
}