mainApp.controller("systemRoleListCtl", function ($scope, $uibModal, mineHttp, mineGrid, mineUtil) {
    $scope.myData = [];
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        showSelectionCheckbox: true,
        multiSelect: true,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/role/list"),
        columnDefs: [
            {field: 'name', displayName: '角色名'},
            {field: 'description', displayName: '描述'},
            {field: 'createdAt', displayName: '创建时间'},
            {field: 'createdBy', displayName: '创建者'},
            {
                field: 'id',
                displayName: '操作',
                width: 200,
                sortable: false,
                cellTemplate: "<div><mine-action icon='fa fa-edit' action='edit(row.entity)' name='编辑'></mine-action>" +
                "<mine-action icon='fa fa-sticky-note-o' action='detail(row.entity)' name='查看'></mine-action>" +
                "<mine-action icon='fa fa-street-view' action='privilege(row.entity)' name='权限'></mine-action></div>"
            }

        ]
    });
    // init load datas
    $scope.gridPageQuery();
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.roleQuery);
    };
    $scope.add = function () {
        var modalInstance = mineUtil.modal("admin/_system/role/roleAdd.htm", "systemRoleAddController", {});
        modalInstance.result.then(function () {
        }, function () {
            $scope.query();
        });
    };
    $scope.edit = function (role) {
        var modalInstance = mineUtil.modal("admin/_system/role/roleEdit.htm", "systemRoleEditController", role);
        modalInstance.result.then(function () {
        }, function () {
            $scope.query();
        });
    };
    $scope.detail = function (role) {
        var modalInstance = mineUtil.modal("admin/_system/role/roleDetail.htm", "systemRoleDetailController", role);
        modalInstance.result.then(function () {
        }, function () {
        });
    };
    $scope.privilege = function (role) {
        var modalInstance = mineUtil.modal("admin/_system/role/rolePrivilege.htm", "systemRolePrivilegeController", role);
        modalInstance.result.then(function () {
        }, function () {
        });
    }
});
mainApp.controller("systemRoleAddController", function ($scope, $uibModalInstance, mineHttp) {
    $scope.ok = function () {
        mineHttp.send("POST", "admin/role", {data: $scope.role}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
            if ($scope.messageStatus) {
                $scope.role = null;
            }
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

mainApp.controller("systemRoleEditController", function ($scope, $uibModalInstance, mineHttp, data) {
    mineHttp.send("GET", "admin/role/" + data.id, {}, function (result) {
        if (!verifyData(result)) {
            $scope.messageStatus = false;
            $scope.message = result.message;
        }
        $scope.role = result.content;
    });
    $scope.ok = function () {
        mineHttp.send("PUT", "admin/role/" + data.id, {data: $scope.role}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
mainApp.controller("systemRoleDetailController", function ($scope, $uibModalInstance, mineHttp, data) {
    mineHttp.send("GET", "admin/role/" + data.id, {}, function (result) {
        $scope.message = result.message;
        $scope.role = result.content;
    });
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
mainApp.controller("systemRolePrivilegeController", function ($scope, $uibModalInstance, mineHttp, data, mineTree) {
    mineHttp.send("GET", "admin/role/" + data.id, {}, function (result) {
        $scope.message = result.message;
        $scope.role = result.content;
    });
    var menuTree = {};
    var permitTree = {};
    $scope.buildTree = function (callback) {
        mineHttp.send("GET", "admin/role/" + data.id + "/privilege", {}, function (data) {
            var options = {check: {enable: true}};
            menuTree = mineTree.build($("#menuTree"), data.content.roleMenus, options);
            permitTree = mineTree.build($("#functionTree"), data.content.rolePermits, options);
        });
    };
    $scope.buildTree();
    $scope.ok = function () {
        $scope.rolePrivilege = {};
        $scope.rolePrivilege.id = data.id;
        $scope.rolePrivilege.menuIds = new Array();
        $scope.rolePrivilege.permitCodes = new Array();
        var menuNodes = menuTree.getCheckedNodes(true);
        for (var index in menuNodes) {
            $scope.rolePrivilege.menuIds.push(menuNodes[index].id);
        }
        var permitNodes = permitTree.getCheckedNodes(true);
        for (var index in permitNodes) {
            $scope.rolePrivilege.permitCodes.push(permitNodes[index].id);
        }
        mineHttp.send("PUT", "admin/role/" + data.id + "/privilege", {data: $scope.rolePrivilege}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});