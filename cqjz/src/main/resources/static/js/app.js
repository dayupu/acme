var mainApp = angular.module("mainApp", ["angular-loading-bar", "ui.router", "ngAnimate", "ngStorage", "ngGrid", "ui.bootstrap", "ngFileUpload"]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
    routeConfig($stateProvider, $urlRouterProvider);
});
// route config
function routeConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    $stateProvider.state("home", {url: "/", templateUrl: './home.htm'})
        .state("menu", {url: "/menu", templateUrl: './_system/menu/menu.htm'})
        .state("menu.list", {url: "/list", templateUrl: './_system/menu/menuList.htm', controller: "systemMenuListCtl"})
        .state("menu.add", {url: "/add", templateUrl: './_system/menu/menuAdd.htm'})
        .state("role", {url: "/roleList", templateUrl: './_system/role/role.htm'})
        .state("role.list", {url: "/list", templateUrl: './_system/role/roleList.htm', controller: "systemRoleListCtl"})
        .state("user", {url: "/user", templateUrl: './_system/user/user.htm'})
        .state("user.list", {url: "/list", templateUrl: './_system/user/userList.htm', controller: "systemUserListCtl"})
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