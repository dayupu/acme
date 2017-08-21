mainApp.controller("systemDepartListCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage) {

    mineHttp.menuLocation("depart.list", function (data) {
        $scope.menuLocation = data;
    });

    // select menu
    $scope.ztreeSelected = function (event, treeId, treeNode) {
        mineHttp.send("GET", "admin/depart/" + treeNode.id, {}, function (data) {
            if (verifyData(data)) {
                $scope.depart = data.content;
            } else {
                $scope.depart = null;
            }
        });
    };

    // build menu tree
    var departTree;
    $scope.buildTree = function (callback) {
        mineHttp.send("GET", "admin/depart/rootTree", {}, function (data) {
            var options = {
                callback: {
                    onClick: $scope.ztreeSelected
                }
            };
            departTree = mineTree.buildAsync($("#departTree"), "admin/depart/asyncTree", data, options);
            if (angular.isFunction(callback)) {
                callback();
            }
        });
    };

    $scope.edit = function () {
        var modalInstance = mineUtil.modal("admin/_system/depart/departEdit.htm", "systemDepartEditController", $scope.depart);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };

    $scope.delete = function () {
        mineUtil.confirm("确认删除吗？", function () {
            var nodes = departTree.getSelectedNodes();
            var parentNode = departTree.getNodeByTId(nodes[0].parentTId);
            mineHttp.send("DELETE", "admin/depart/" + $scope.depart.code, {}, function (data) {
                if (!verifyData(data)) {
                    $scope.messageStatus = false;
                    $scope.message = data.message;
                    return;
                }

                mineUtil.alert("删除成功");
                $scope.depart = null;
                departTree.reAsyncChildNodes(parentNode, "refresh", false);
            });
        });
    };

    $scope.add = function (flag) {
        var params = null;
        if (flag == 2) {
            params = $scope.depart;
        }
        var modalInstance = mineUtil.modal("admin/_system/depart/departAdd.htm", "systemDepartAddController", params);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };

    // page init
    $scope.buildTree();

    mineMessage.subscribe("systemDepartTreeRefresh", function (event, type) {
        var nodes = departTree.getSelectedNodes();
        if (type == "add") {
            departTree.reAsyncChildNodes(nodes[0], "refresh", false);
        } else {
            var parentNode = departTree.getNodeByTId(nodes[0].parentTId);
            departTree.reAsyncChildNodes(parentNode, "refresh", false);
        }
    });
});

mainApp.controller("systemDepartAddController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {
    $scope.initPage = function () {
        $scope.depart = {};
        if (data == null) {
            $scope.title = "顶级机构";
        } else {
            $scope.title = "下级机构";
            $scope.depart.parentCode = data.code;
            $scope.depart.parentName = data.name;
        }
    };

    $scope.initPage();
    $scope.ok = function () {
        mineHttp.send("POST", "admin/depart", {data: $scope.depart}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
            if ($scope.messageStatus) {
                mineMessage.publish("systemDepartTreeRefresh", "add");
            }
            $scope.initPage();
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

mainApp.controller("systemDepartEditController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {

    mineHttp.send("GET", "admin/depart/" + data.code, {}, function (data) {
        if (verifyData(data)) {
            $scope.depart = data.content;
        } else {
            $scope.depart = null;
        }
    });

    $scope.ok = function () {
        mineHttp.send("PUT", "admin/depart/" + $scope.depart.code, {data: $scope.depart}, function (data) {
            $scope.messageStatus = verifyData(data);
            $scope.message = data.message;
            $scope.depart = data.content;
            if ($scope.messageStatus) {
                mineMessage.publish("systemDepartTreeRefresh", "edit");
            }
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});