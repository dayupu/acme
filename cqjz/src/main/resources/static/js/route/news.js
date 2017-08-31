mainApp.controller("newsAddCtl", function ($scope, $http, mineTree, mineHttp, mineUtil) {
    mineHttp.menuLocation("news.create", function (data) {
        $scope.menuLocation = data;
    });
    umeditorInit($);
    var um = UM.getEditor('myEditor');
    $scope.images = [];
    $scope.test = function () {
        alert(URL);
        alert(UM.getEditor('myEditor').getContent());
    };
    $scope.submit = function () {
        if ($scope.file) {
            $scope.upload($scope.file);
        }
    };
    $scope.upload = function (file) {
        mineHttp.upload("admin/news/upload", {file: file}, function (data) {
            $scope.images.push(data.content);
        });
    };

    $scope.preview = function (image) {
        var modalInstance = mineUtil.modal("admin/_news/newsPreview.htm", "newsPreviewController", image, "lg");
        modalInstance.result.then(function () {
        }, function () {
        });
    };

});
mainApp.controller("newsListCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage) {
    mineHttp.menuLocation("news.list", function (data) {
        $scope.menuLocation = data;
    });
});

mainApp.controller("newsPreviewController", function ($scope, $uibModalInstance, data) {
    $scope.imgUrl = fullPath(data.accessUrl);
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});