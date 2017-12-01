/**********个人信息************/
mainApp.controller("settingSelfCtl", function ($scope, mineGrid, mineTree, mineHttp, mineUtil) {
    $scope.refreshUser = function () {
        mineHttp.send("GET", "admin/setting/self", null, function (result) {
                $scope.user = result.content;
            }
        );
    };
    $scope.edit = function () {
        var modalInstance = mineUtil.modal("admin/_setting/selfEdit.htm", "settingSelfEditController", {});
        modalInstance.result.then(function () {
        }, function () {
            $scope.refreshUser();
        });
    };
    $scope.changePwd = function () {
        var modalInstance = mineUtil.modal("admin/_setting/selfPwd.htm", "settingSelfPwdController", {});
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

mainApp.controller("settingSelfPwdController", function ($scope, $uibModalInstance, mineHttp) {
    mineHttp.send("GET", "admin/setting/self", {}, function (result) {
        $scope.user = result.content;
    });

    $scope.setMessage = function (message, status) {
        $scope.messageStatus = status;
        $scope.message = message;
    };
    $scope.ok = function () {
        if ($scope.user.passwordNew != $scope.user.passwordNew2) {
            $scope.setMessage("两次输入的密码不一致", false);
            return;
        }
        mineHttp.send("PUT", "admin/setting/self/pwd", {data: $scope.user}, function (result) {
            $scope.message = result.message;
            $scope.messageStatus = verifyData(result);
            $scope.user.password = null;
            $scope.user.passwordNew = null;
            $scope.user.passwordNew2 = null;
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});