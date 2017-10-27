var anyoneApp = angular.module("anyoneApp", ['ngRoute']);
anyoneApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/', {templateUrl: './anyone/home.htm'})
        .when('/contacts', {templateUrl: './anyone/contacts.htm'})
        .when('/jzfz', {templateUrl: './anyone/jzfz.htm'})
        .when('/bmjy', {templateUrl: './anyone/bmjy.htm'})
        .otherwise({redirectTo: '/'});
}]);

/*网页头部操作*/
anyoneApp.controller("headerController", function ($scope, $location) {
    $scope.gotToUrl = function (url) {
        $location.path(url)
    }
});