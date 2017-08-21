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
    var menuTree;
    $scope.buildTree = function (callback) {
        mineHttp.send("GET", "admin/depart/rootTree", {}, function (data) {
            var options = {
                callback: {
                    onClick: $scope.ztreeSelected
                }
            };
            menuTree = mineTree.buildAsync($("#menuTree"), "admin/depart/asyncTree", data, options);
            if (angular.isFunction(callback)) {
                callback();
            }
        });
    };

    $scope.edit = function () {
        var modalInstance = mineUtil.modal("admin/_system/menu/menuEdit.htm", "systemMenuEditController", $scope.menu);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };

    $scope.delete = function () {
        mineUtil.confirm("确认删除吗？", function () {
            var url = "admin/menu/" + $scope.menu.id;
            var parentNodeTemp = menuTree.getNodeByParam("id", $scope.menu.id).getParentNode();
            mineHttp.send("DELETE", url, {}, function (data) {

                if (!verifyData(data)) {
                    $scope.messageStatus = false;
                    $scope.message = data.message;
                    return;
                }

                mineUtil.alert("删除成功");
                $scope.menu = null;
                $scope.buildTree(function () {
                    if (parentNodeTemp != null) {
                        var parentNode = menuTree.getNodeByParam("id", parentNodeTemp.id);
                        menuTree.expandNode(parentNode);
                    }
                });
            });
        });
    };

    $scope.add = function (flag) {
        var params = null;
        if(flag == 2){
           params = $scope.depart;
        }
        var modalInstance = mineUtil.modal("admin/_system/depart/departAdd.htm", "systemDepartAddController", params);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };

    // page init
    $scope.buildTree();

    mineMessage.subscribe("systemMenuTreeRefresh", function (event, nodeId) {
        $scope.buildTree(function () {
            var parentNode = menuTree.getNodeByParam("id", nodeId);
            menuTree.expandNode(parentNode);
        });
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
                var nodeId = data == null ? null : data.id;
                mineMessage.publish("systemMenuTreeRefresh", nodeId);
            }
            $scope.initPage();
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

mainApp.controller("systemMenuEditController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {

    mineHttp.send("GET", "admin/menu/" + data.id, {}, function (data) {
        if (verifyData(data)) {
            $scope.menu = data.content;
        } else {
            $scope.menu = null;
        }
    });

    $scope.ok = function () {
        mineHttp.send("PUT", "admin/menu/" + $scope.menu.id, {data: $scope.menu}, function (data) {
            $scope.menu = data.content;
            $scope.messageStatus = verifyData(data);
            $scope.message = data.message;
            if ($scope.messageStatus) {
                mineMessage.publish("systemMenuTreeRefresh", data.content.parentId);
            }
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});