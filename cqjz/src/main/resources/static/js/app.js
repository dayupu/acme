var mainApp = angular.module("mainApp", ["angular-loading-bar", "ui.router", "ngAnimate", "ngStorage", "ngGrid", "ui.bootstrap", "ngFileUpload"]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
    routeConfig($stateProvider, $urlRouterProvider);
});
// route config
function routeConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    $stateProvider.state("home", {url: "/", templateUrl: './home.htm'})
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
            url: "/list", templateUrl: './_system/depart/departList.htm', controller: "systemDepartListCtl"
        })
        // news
        .state("news", {url: "/news", templateUrl: './_news/news.htm'})
        .state("news.publish", {url: "/publish", templateUrl: './_news/newsAdd.htm', controller: "newsPublishCtl"})
        .state("news.edit", {url: "/edit/:number", templateUrl: './_news/newsAdd.htm', controller: "newsPublishCtl"})
        .state("news.preview", {
            url: "/preview/:number", templateUrl: './_news/preview.htm', controller: "newsPreviewCtl"
        })
        .state("news.list", {url: "/list", templateUrl: './_news/newsList.htm', controller: "newsListCtl"})
        // flow
        .state("flow", {url: "/flow", templateUrl: './_flow/flow.htm'})
        .state("flow.approve", {
            url: "/approve/:processId/:taskId", templateUrl: './_flow/flowApprove.htm', controller: "flowApproveCtl"
        })
        .state("flow.list", {url: "/list", templateUrl: './_flow/flow.htm'})
        .state("flow.list.submit", {
            url: "/submit", templateUrl: './_flow/flowListSubmit.htm', controller: "flowSubmitListCtl"
        })
        .state("flow.list.pending", {
            url: "/pending", templateUrl: './_flow/flowListPending.htm', controller: "flowPendingListCtl"
        })
        .state("flow.list.reject", {
            url: "/reject", templateUrl: './_flow/flowListReject.htm', controller: "flowRejectListCtl"
        })
        .state("flow.list.approve", {
            url: "/approve", templateUrl: './_flow/flowListApprove.htm', controller: "flowApproveListCtl"
        })
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