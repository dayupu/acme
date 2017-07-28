var mainApp = angular.module("mainApp", ["angular-loading-bar","ngRoute", "ngAnimate","ngStorage"]);
var basePath = "/sp/"
mainApp.config(function ($routeProvider) {
    routeConfig($routeProvider);
});

mainApp.controller("asideController", function ($http, $scope, $location, messageSubscribe, $sessionStorage) {
    $scope.loadMenu = function () {
        $http.get(fullPath("admin/index/menuList")).then(function successCallback(response) {
            if (requestSuccess(response.data.status)) {
                $scope.menus = response.data.content;
            } else {
                $scope.message = response.data.message;
            }
        });
    }
    $scope.menuClick = function (event, menu) {
        var secondMenu = $(event.target).next("ul");
        if ($(secondMenu).length > 0) {
            menu.extend = !menu.extend;
            $(secondMenu).slideToggle("fast");
        } else {
            messageSubscribe.publish("menuLocation", menu);
            $location.path(menu.url);
            $sessionStorage.menuLocation=menu;
        }
    }
});

mainApp.controller("contentController", function ($scope, messageSubscribe, $sessionStorage, $location) {


    messageSubscribe.subscribe("menuLocation", function (event, menu) {
        $scope.navLocations=menu.locations;
    });

    if($sessionStorage.menuLocation != null && $location.path() != "/" && $location.path() != ""){
       messageSubscribe.publish("menuLocation", $sessionStorage.menuLocation);
    }
});

function requestSuccess(status) {
    if (status == "1000") {
        return true;
    }
    return false;
}

// route config
function routeConfig($routeProvider) {
    $routeProvider.when("/", {template: '这是首页页面'})
        .when("/menuList", {templateUrl: '/admin/system/menuList.htm'})
        .when("/roleList", {templateUrl: '/admin/system/roleList.htm'})
        .when("/userList", {templateUrl: '/admin/system/userList.htm'})
        .otherwise({redirectTo: '/'});
}

// factorys
mainApp.factory("messageSubscribe", function ($rootScope) {
    return {
        publish: function (name, parameters) {
            $rootScope.$emit(name, parameters);
        },
        subscribe: function (name, listener) {
            $rootScope.$on(name, listener);
        }
    };
});

// directives
mainApp.directive('menuNav', function () {
    return {
        restrict: 'E',
        scope: {
            values:"="
        },
        template: "<div><ul class='breadcrumb' ng-if='values != null'><li>当前位置：</li>"
                  +"<li ng-repeat='value in values'><span class='divider' ng-if='$index != 0'>/</span>{{value.name}}</li></ul></div>",
        replace: true
    };
});

function fullPath(path){
    return basePath + path;
}