var asideApp = angular.module("asideApp", []);
var mainApp = angular.module("mainApp", ["asideApp"]);
asideApp.controller("asideController", function ($http, $scope) {
    $scope.loadMenu = function () {
        $http.get("/admin/index/menuList").then(function successCallback(response) {
            if (requestSuccess(response.data.status)) {
                $scope.menus = response.data.content;
            } else {
                $scope.message = response.data.message;
            }
        });
    }
});

function requestSuccess(status) {
    if (status == "1000") {
        return true;
    }
    return false;
}