var appContextPath = null;
var mainApp = angular.module("mainApp", ["angular-loading-bar", "ui.router", "ngAnimate", "ngStorage", "ngGrid", "ui.bootstrap", "ngFileUpload"]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
    routeConfig($stateProvider, $urlRouterProvider);
});
mainApp.controller("asideController", function ($http, $scope, $location, $sessionStorage, $state, mineMessage, mineHttp) {
    $scope.loadMenu = function () {
        mineHttp.send("GET", "admin/index/menuList", {}, function (data) {
            if (verifyData(data)) {
                $scope.menus = data.content;
            } else {
                $scope.message = data.message;
            }
        });
    };
    $scope.menuClick = function (event, menu) {
        var secondMenu = $(event.target).next("ul");
        if ($(secondMenu).length > 0) {
            menu.extend = !menu.extend;
            $(secondMenu).slideToggle("fast");
        } else {
            mineMessage.publish("menuLocation", menu);
            //$location.path(menu.url);
            $state.go(menu.url);
            $sessionStorage.menuLocation = menu;
        }
    }
});
mainApp.controller("headerController", function ($http, $scope, $location, $sessionStorage, $state, mineMessage, mineHttp) {
    $scope.loginOut = function () {
        location.href = fullPath("admin/loginOut");
    }
});
mainApp.controller("contentController", function ($scope, mineMessage, $sessionStorage, $location) {
    mineMessage.subscribe("menuLocation", function (event, menu) {
        $scope.navLocations = menu.locations;
    });
    if ($sessionStorage.menuLocation != null && $location.path() != "/" && $location.path() != "") {
        mineMessage.publish("menuLocation", $sessionStorage.menuLocation);
    }
});
// route config
function routeConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    $stateProvider.state("main", {url: "/", template: '这是首页页面'})
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
        // department
        .state("depart", {url: "/depart", templateUrl: './_system/depart/depart.htm'})
        .state("depart.list", {
            url: "/list",
            templateUrl: './_system/depart/departList.htm',
            controller: "systemDepartListCtl"
        })
        // news
        .state("news", {url: "/news", templateUrl: './_news/news.htm'})
        .state("news.create", {url: "/create", templateUrl: './_news/newsAdd.htm', controller: "newsAddCtl"})
        .state("news.list", {url: "/list", templateUrl: './_news/newsList.htm', controller: "newsListCtl"})
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