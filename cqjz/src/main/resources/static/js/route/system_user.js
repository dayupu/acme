mainApp.controller("systemUserListCtl", function ($scope, $http, mineHttp, mineGrid, $uibModal) {

    mineHttp.menuLocation("user.list", function (data) {
        $scope.locations = data;
    });

    $scope.permitAdd = false;
    $scope.permitModify = false;
    $scope.permitDelete = false;

    $scope.myData = [];

    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        showSelectionCheckbox:true,
        multiSelect: true,
        selectWithCheckboxOnly:true,
        requestUrl: fullPath("admin/user/list"),
        columnDefs: [{field: 'account', displayName: '账号'},
            {field: 'name', displayName: '姓名'}, {
                field: 'id',
                displayName: '操作',
                width: 200,
                cellTemplate: "<div><mine-action action='edit(row.entity)' name='编辑'></mine-action>" +
                "<mine-action action='edit(row)' name='查看'></mine-action></div>"
            }

        ]
    });
    // init load datas
    $scope.gridPageQuery();
    $scope.test = function () {
        $scope.gridPageQuery({test: "test"});
    };
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

    $scope.edit = function (entity) {
        alert(angular.toJson($scope.gridSelectedItems));
    };



});


