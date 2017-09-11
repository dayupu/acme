var mainApp = angular.module("mainApp", ["angular-loading-bar", "ui.router", "ngAnimate", "ngGrid"]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
    routeConfig($stateProvider, $urlRouterProvider);
});
// route config
function routeConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    $stateProvider.state("event", {url: "/event", templateUrl: './event.htm'})
    .state("event.order", {url: "/order", templateUrl: './event/order.htm',controller: "eventOrderCtl"});
}