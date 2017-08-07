var basePath = "/sp/";
var mainApp = angular.module("mainApp", ["angular-loading-bar", "ui.router", "ngAnimate", "ngStorage", "ngGrid", "ui.bootstrap"]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
    routeConfig($stateProvider, $urlRouterProvider);
});

mainApp.controller("asideController", function ($http, $scope, $location, $sessionStorage, $state, messageSubscribe, mineHttp) {
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
            messageSubscribe.publish("menuLocation", menu);
            //$location.path(menu.url);
            $state.go(menu.url);
            $sessionStorage.menuLocation = menu;
        }
    }
});

mainApp.controller("contentController", function ($scope, messageSubscribe, $sessionStorage, $location) {
    messageSubscribe.subscribe("menuLocation", function (event, menu) {
        $scope.navLocations = menu.locations;
    });
    if ($sessionStorage.menuLocation != null && $location.path() != "/" && $location.path() != "") {
        messageSubscribe.publish("menuLocation", $sessionStorage.menuLocation);
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
        .state("menu.addSub", {url: "/addSub", templateUrl: './_system/menu/menuAddSub.htm'})
        // role
        .state("role", {url: "/roleList", templateUrl: './_system/roleList.htm'})
        // user
        .state("user", {url: "/user", templateUrl: './_system/user/user.htm'})
        .state("user.list", {
            url: "/list",
            templateUrl: './_system/user/userList.htm',
            controller: "systemUserListCtl"
        });
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

function fullPath(path) {
    return basePath + path;
}

function verifyData(data) {
    if (data.status == "1000") {
        return true;
    }
    return false;
}