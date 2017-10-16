/**********个人信息************/
mainApp.controller("settingSelfCtl", function ($scope, mineGrid, mineTree, mineHttp, mineUtil) {
     $scope.refreshUser = function(){
         mineHttp.send("GET", "admin/setting/self", null, function (result) {
                 $scope.user = result.content;
             }
          );
     }
     $scope.edit = function () {
         var modalInstance = mineUtil.modal("admin/_setting/selfEdit.htm", "settingSelfEditController", {});
         modalInstance.result.then(function () {
         }, function () {
            $scope.refreshUser();
         });
     };
     $scope.refreshUser();
});

mainApp.controller("settingSelfEditController", function ($scope, $uibModalInstance, mineHttp, mineTree, data) {
    mineHttp.send("GET", "admin/setting/self", {}, function (result) {
        $scope.user = result.content;
    });
    $scope.ok = function () {
        mineHttp.send("PUT", "admin/setting/self", {data: $scope.user}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});