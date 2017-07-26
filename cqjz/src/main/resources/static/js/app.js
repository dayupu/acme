var mainApp = angular.module("mainApp", ["ngRoute","ngAnimate"]);
mainApp.config(function ($routeProvider) {
    routeConfig($routeProvider);
});

mainApp.controller("asideController", function ($http, $scope, $location) {
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
            $location.path(menu.url);
        }
    }
});

function requestSuccess(status) {
    if (status == "1000") {
        return true;
    }
    return false;
}
// route config
function routeConfig($routeProvider){
    $routeProvider.when("/", {template: '这是首页页面'})
        .when("/computers", {template: '这是电脑分类页面'})
        .when("/printers", {template: '这是打印机页面'})
        .otherwise({redirectTo: '/computers'});
}