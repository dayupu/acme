mainApp.controller("newsAddCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage, textAngularManager, Upload) {
  mineHttp.menuLocation("news.create", function (data) {$scope.menuLocation = data;});

  $scope.test = function(){
    alert($scope.news.context);
  };
  $scope.submit = function() {
        if ($scope.file) {
            $scope.upload($scope.file);
          }
        };
        $scope.upload = function (file) {
        Upload.upload({
            url: fullPath("admin/news/upload"),
            data: {file: file, 'username': $scope.username},
            file: file
        }).then(function (resp) {
            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
  };
});
mainApp.controller("newsListCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage) {
  mineHttp.menuLocation("news.list", function (data) {$scope.menuLocation = data;});
});