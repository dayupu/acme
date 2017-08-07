mainApp.controller("systemMenuListCtl", function ($scope, $http, $uibModal, mineTree, mineHttp, mineUtils) {

    mineHttp.menuLocation("menu.list", function (data) {
        $scope.menuLocation = data;
    });

    // select menu
    $scope.ztreeSelected = function (event, treeId, treeNode) {
        $scope.messageStatus = null;
        var url = "admin/menu/list/" + treeNode.id;
        mineHttp.send("GET", url, {}, function (data) {
            $scope.menu = data.content;
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

    $scope.update = function () {
        var url = "admin/menu/list/" + $scope.menu.id;
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

    // $scope.delete = function () {
    //     var url = "admin/menu/list/" + $scope.menu.id;
    //     var parentId = menuTree.getNodeByParam("id", $scope.menu.id).getParentNode().id;
    //     mineHttp.send("DELETE", url, {}, function (data) {
    //         $scope.messageStatus = verifyData(data);
    //         $scope.message=data.message;
    //         if(!$scope.messageStatus){
    //             return;
    //         }
    //
    //         $scope.menu = null;
    //         $scope.buildTree(function (){
    //              var parentNode = menuTree.getNodeByParam("id", parentId);
    //              menuTree.expandNode(parentNode);
    //         });
    //     });
    // };

    $scope.delete = function () {
         mineUtils.confirm("test");
    };

    // page init
    $scope.buildTree();

    $scope.open = function () {
        var modalInstance = $uibModal.open({
            animation: false,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '_system/menu/menuAdd.htm',
            controller: 'testController',
            resolve: {
                data: function () {
                    return $scope.menu;
                }
            }
        });
        modalInstance.result.then(function (selectedItem) {

        });
    };

});

mainApp.controller("testController", function ($scope, data, $uibModalInstance) {
    $scope.menu = data;
    $scope.ok = function () {
        $uibModalInstance.close();
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
