mainApp.controller("systemMenuListCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage) {

    mineHttp.menuLocation("menu.list", function (data) {
        $scope.menuLocation = data;
    });

    // select menu
    $scope.ztreeSelected = function (event, treeId, treeNode) {
        $scope.messageStatus = null;
        var url = "admin/menu/list/" + treeNode.id;
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
        var url = "admin/menu/" + $scope.menu.id;
        mineHttp.send("PUT", url, {data: $scope.menu}, function (data) {
            $scope.menu = data.content;
            $scope.messageStatus = verifyData(data);
            $scope.message = data.message;
            $scope.buildTree(function () {
                if (data.content.parentId != null) {
                    var parentNode = menuTree.getNodeByParam("id", data.content.parentId);
                    menuTree.expandNode(parentNode);
                }
            });
        });
    };

    $scope.delete = function () {
        mineUtil.confirm("确认删除吗？", function () {
            var url = "admin/menu/" + $scope.menu.id;
            var parentNodeTemp = menuTree.getNodeByParam("id", $scope.menu.id).getParentNode();
            mineHttp.send("DELETE", url, {}, function (data) {
                $scope.messageStatus = verifyData(data);
                $scope.message = data.message;
                if (!$scope.messageStatus) {
                    return;
                }

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

    $scope.addMenu = function () {
        var modalInstance = mineUtil.modal("admin/_system/menu/menuAdd.htm", "systemMenuAddController", $scope.menu);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };

    // page init
    $scope.buildTree();

    mineMessage.subscribe("systemMenuAdd", function (event, nodeId) {
        $scope.buildTree(function () {
            var parentNode = menuTree.getNodeByParam("id", nodeId);
            menuTree.expandNode(parentNode);
        });
    });
});

mainApp.controller("systemMenuAddController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {
    $scope.initPage = function () {
        $scope.menu = {};
        $scope.menu.parentId = data.id;
        $scope.menu.parentName = data.name;
    };
    $scope.initPage();
    $scope.ok = function () {
        mineHttp.send("POST", "admin/menu/addSub", {data: $scope.menu}, function (result) {
            $scope.messageStatus = verifyData(result);
            if ($scope.messageStatus) {
                mineMessage.publish("systemMenuAdd", data.id);
            }
            $scope.message = result.message;
            $scope.initPage();
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
