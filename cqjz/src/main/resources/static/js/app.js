var login = angular.module('loginApp', []);
var apiConfig = {header: {"content-type": "application/json"}};
login.controller('loginCtl', function ($scope, $http) {
    $scope.login = function () {

        $http.post("/admin/user/login", {params:$scope.user}, apiConfig)
            .then(function successCallback(response) {
                alert(response);
            });
    };
});