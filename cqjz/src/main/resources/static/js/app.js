var mainApp = angular.module("mainApp", ["angular-loading-bar","ngRoute", "ngAnimate"]);
mainApp.config(function ($routeProvider) {
    routeConfig($routeProvider);
});

mainApp.controller("asideController", function ($http, $scope, $location, messageSubscribe) {
    $scope.isShow = false;
    $scope.loadMenu = function () {
        $http.get("/admin/index/menuList").then(function successCallback(response) {
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
        }
    }
});

mainApp.controller("contentController", function ($scope, messageSubscribe) {

    messageSubscribe.subscribe("menuLocation", function (event, menu) {
        $scope.navLocations=menu.locations;
    });
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
        .when("/computers", {template: '这是电脑分类页面'})
        .when("/printers", {template: '这是打印机页面'})
        .otherwise({redirectTo: '/computers'});
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
