mainApp.controller("newsAddCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage, textAngularManager) {
  mineHttp.menuLocation("news.create", function (data) {$scope.menuLocation = data;});

  $scope.test = function(){
    alert($scope.news.context);
  };
});
mainApp.controller("newsListCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage) {
  mineHttp.menuLocation("news.list", function (data) {$scope.menuLocation = data;});
});