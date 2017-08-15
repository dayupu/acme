mainApp.controller("systemMenuListCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage) {

    mineHttp.menuLocation("menu.list", function (data) {
        $scope.menuLocation = data;
    });

    // select menu
    $scope.ztreeSelected = function (event, treeId, treeNode) {
        $scope.messageStatus = null;
        var url = "admin/menu/" + treeNode.id;
        mineHttp.send("GET", url, {}, function (data) {
            if (verifyData(data)) {
                $scope.menu = data.content;
            } else {
                $scope.menu = null;
            }
        });
    };

    // build menu tree
    var menuTree;
    $scope.buildTree = function (callback) {
        mineHttp.send("GET", "admin/menu/treeList", {}, function (data) {
            var options = {
                callback: {
                    onClick: $scope.ztreeSelected
                }
            };
            menuTree = mineTree.build($("#menuTree"), data.content, options);

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
        if (flag == 2) {
            params = $scope.menu;
        }
        var modalInstance = mineUtil.modal("admin/_system/menu/menuAdd.htm", "systemMenuAddController", params);
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

mainApp.controller("systemMenuAddController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {


    $scope.initPage = function () {
        $scope.menu = {};
        if (data == null) {
            $scope.title = "一级菜单";
        } else {
            $scope.title = "二级菜单";
            $scope.menu.parentId = data.id;
            $scope.menu.parentName = data.name;
        }
    };

    $scope.initPage();
    $scope.ok = function () {
        mineHttp.send("POST", "admin/menu", {data: $scope.menu}, function (result) {
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
                mineMessage.publish("systemMenuTreeRefresh", data.content.id);
            }
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});