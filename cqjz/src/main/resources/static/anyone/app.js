var anyoneApp = angular.module("anyoneApp",['ngRoute']);
anyoneApp.config(['$routeProvider', function($routeProvider){
    $routeProvider
        .when('/',{templateUrl:'./anyone/home.htm'})
        .otherwise({redirectTo:'/'});
}]);