var mainApp = angular.module("mainApp", ["angular-loading-bar", "ui.router", "ngAnimate", "ngStorage", "ngGrid", "ui.bootstrap", "ngFileUpload","ng.ueditor"]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
    routeConfig($stateProvider, $urlRouterProvider);
});
// route config
function routeConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    $stateProvider.state("home", {url: "/", templateUrl: './home.htm', controller: "homeController"})
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
        // organization
        .state("organ", {url: "/organ", templateUrl: './_system/organ/organ.htm'})
        .state("organ.list", {
            url: "/list", templateUrl: './_system/organ/organList.htm', controller: "systemOrganListCtl"
        })
        // news
        .state("news", {url: "/news", templateUrl: './_news/news.htm'})
        .state("news.publish", {url: "/publish", controller: "newsPublishCtl"})
        .state("news.add", {url: "/add", templateUrl: './_news/newsEdit.htm', controller: "newsEditCtl"})
        .state("news.edit", {url: "/edit/:number", templateUrl: './_news/newsEdit.htm', controller: "newsEditCtl"})
        .state("news.topic", {url: "/topic", templateUrl: './_news/newsTopic.htm', controller: "newsTopicCtl"})
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
        // jz
        .state("jz", {url: "/jz", templateUrl: './_jz/jz.htm'})
        .state("jz.list", {url: "/list", templateUrl: './_jz/jz.htm'})
        .state("jz.list.watch", {
            url: "/watch", templateUrl: './_jz/watchList.htm', controller: "jzWatchListCtl"
        })
        .state("jz.list.superstar", {
            url: "/superstar", templateUrl: './_jz/superstarList.htm', controller: "jzSuperstarListCtl"
        })
        .state("jz.list.contacts", {
            url: "/contacts", templateUrl: './_jz/contacts.htm', controller: "jzContactsListCtl"
        })
        .state("jz.list.style", {
            url: "/style", templateUrl: './_jz/styleList.htm', controller: "jzStyleListCtl"
        })
        .state("jz.list.styleEdit", {
            url: "/style/:number", templateUrl: './_jz/style.htm', controller: "jzStyleEditListCtl"
        })
        // setting
        .state("setting", {url: "/setting", templateUrl: './_setting/setting.htm'})
        .state("setting.self", {
            url: "/self", templateUrl: './_setting/self.htm', controller: "settingSelfCtl"
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