var menuApp = angular.module("menuApp",[]);
var mainApp = angular.module("mainApp",["menuApp"]);

menuApp.controller("menuCtrl",function($scope){
    $scope.name = "aa";
});