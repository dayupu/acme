mainApp.controller("systemRoleListCtl", function ($scope, $uibModal, mineHttp, mineGrid, mineUtil) {

    mineHttp.menuLocation("role.list", function (data) {
        $scope.locations = data;
    });

    $scope.permitAdd = false;
    $scope.permitModify = false;
    $scope.permitDelete = false;

    $scope.myData = [];
    $("#userQueryCreatedAt").datetimepicker({format: 'Y-m-d H:i:s'});
    $("#userQueryCreatedAtEnd").datetimepicker({format: 'Y-m-d H:i:s'});
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        showSelectionCheckbox: true,
        multiSelect: true,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/user/list"),
        columnDefs: [{field: 'account', displayName: '账号'},
            {field: 'name', displayName: '姓名'},
            {field: 'gender', displayName: '性别'},
            {field: 'email', displayName: '电子邮箱'},
            {field: 'mobile', displayName: '联系电话'},
            {field: 'createdAt', displayName: '创建时间'},
            {
                field: 'id',
                displayName: '操作',
                width: 200,
                sortable: false,
                cellTemplate: "<div><mine-action icon='fa fa-edit' action='edit(row.entity)' name='编辑'></mine-action>" +
                "<mine-action icon='fa fa-sticky-note-o' action='detail(row.entity)' name='查看'></mine-action></div>"
            }

        ]
    });
    // init load datas
    $scope.gridPageQuery();
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.gridPageSelectedItems = function (newValue, oldValue) {
        if (newValue[0].level == "1") {
            $scope.permitAdd = true;
        } else {
            $scope.permitAdd = false;
        }
        $scope.permitModify = true;
        $scope.permitDelete = true;
    };

    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.userQuery);
    };

    $scope.add = function () {
        var modalInstance = mineUtil.modal("admin/_system/user/userAdd.htm", "systemUserAddController", $scope.menu);
        modalInstance.result.then(function () {
        }, function () {
            $scope.query();
        });
    };
    $scope.edit = function (user) {
        var modalInstance = mineUtil.modal("admin/_system/user/userModify.htm", "systemUserModifyController", user);
        modalInstance.result.then(function () {
        }, function () {
            $scope.query();
        });
    };
    $scope.detail = function (user) {
        var modalInstance = mineUtil.modal("admin/_system/user/userDetail.htm", "systemUserDetailController", user);
        modalInstance.result.then(function () {
        }, function () {
        });
    }

});

mainApp.controller("systemUserAddController", function ($scope, $uibModalInstance, mineHttp) {
    $scope.ok = function () {
        mineHttp.send("POST", "admin/user", {data: $scope.user}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
            if ($scope.messageStatus) {
                $scope.user = null;
            }
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

mainApp.controller("systemUserModifyController", function ($scope, $uibModalInstance, mineHttp, data) {

    mineHttp.send("GET", "admin/user/" + data.id, {}, function (result) {
        if (!verifyData(result)) {
            $scope.messageStatus = false;
            $scope.message = result.message;
        }
        $scope.user = result.content;
    });

    $scope.ok = function () {
        mineHttp.send("PUT", "admin/user/1000", {data: $scope.user}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

mainApp.controller("systemUserDetailController", function ($scope, $uibModalInstance, mineHttp, data) {

    mineHttp.send("GET", "admin/user/" + data.id, {}, function (result) {
        $scope.message = result.message;
        $scope.user = result.content;
    });

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
