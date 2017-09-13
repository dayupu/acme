var mainApp = angular.module("mainApp", ["angular-loading-bar", "ui.router", "ngAnimate", "ngGrid"]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
    routeConfig($stateProvider, $urlRouterProvider);
});
// route config
function routeConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    $stateProvider.state("event", {url: "/event", templateUrl: './htm/event.htm'})
    .state("event.order", {url: "/order", templateUrl: './htm/event/order.htm',controller: "eventOrderCtl"})
    .state("event.jobSheet", {url: "/jobSheet", templateUrl: './htm/event/jobSheet.htm',controller: "eventJobSheetCtl"})
    .state("event.billing", {url: "/billing", templateUrl: './htm/event/billing.htm',controller: "eventBillingCtl"});

}