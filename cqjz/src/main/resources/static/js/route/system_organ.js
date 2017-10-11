mainApp.controller("systemOrganListCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage) {
    var organTree;
    $scope.ztreeSelected = function (event, treeId, treeNode) {
        $scope.messageStatus = null;
        mineHttp.send("GET", "admin/organ/" + treeNode.id, {}, function (data) {
            if (verifyData(data)) {
                $scope.organ = data.content;
            } else {
                $scope.organ = null;
            }
        });
    };
    $scope.buildTree = function (callback) {
        mineHttp.send("GET", "admin/organ/treeList", {}, function (data) {
            var options = {
                callback: {
                    onClick: $scope.ztreeSelected
                }
            };
            organTree = mineTree.build($("#organTree"), data, options);
            if (angular.isFunction(callback)) {
                callback();
            }
        });
    };

    $scope.edit = function () {
        var modalInstance = mineUtil.modal("admin/_system/organ/organEdit.htm", "systemOrganEditController", $scope.organ);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };

    $scope.drop = function () {
        mineUtil.confirm("确认删除吗？", function () {
            mineHttp.send("DELETE", "admin/organ/" + $scope.organ.id, {}, function (data) {
                if (!verifyData(data)) {
                    $scope.messageStatus = false;
                    $scope.message = data.message;
                    return;
                }
                mineUtil.alert("删除成功");
                $scope.buildTree(function () {
                    organTree.expandAll(true);
                });
            });
        });
    };
    $scope.add = function (oneLevelFlag) {
        var params = null;
        if (!oneLevelFlag) {
            params = $scope.organ;
        }
        var modalInstance = mineUtil.modal("admin/_system/organ/organAdd.htm", "systemOrganAddController", params);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };

    $scope.buildTree();
    mineMessage.subscribe("systemOrganTreeRefresh", function (event, nodeId) {
        $scope.buildTree(function () {
            var parentNode = organTree.getNodeByParam("id", nodeId);
            organTree.expandNode(parentNode);
        });
    });
});
mainApp.controller("systemOrganAddController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {
    var oneLevelFlag = false;
    $scope.initPage = function () {
        $scope.organ = {};
        if (data == null) {
            $scope.title = "部门";
            oneLevelFlag = true;
        } else {
            $scope.title = "下级部门";
            $scope.organ.parentId = data.id;
            $scope.organ.parentName = data.name;
        }
    };
    $scope.initPage();
    $scope.ok = function () {
        mineHttp.send("POST", "admin/organ", {data: $scope.organ}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
            if ($scope.messageStatus) {
                var nodeId = data == null ? null : data.id;
                mineMessage.publish("systemOrganTreeRefresh", nodeId);
            }
            $scope.initPage();
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

mainApp.controller("systemOrganEditController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {
    mineHttp.send("GET", "admin/organ/" + data.id, {}, function (data) {
        if (verifyData(data)) {
            $scope.organ = data.content;
        } else {
            $scope.organ = null;
        }
    });
    $scope.ok = function () {
        mineHttp.send("PUT", "admin/organ/" + $scope.organ.id, {data: $scope.organ}, function (data) {
            $scope.messageStatus = verifyData(data);
            $scope.message = data.message;
            $scope.organ = data.content;
            if ($scope.messageStatus) {
                mineMessage.publish("systemOrganTreeRefresh", data.content.parentId);
            }
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});