mainApp.controller("systemMenuCtl", function ($scope, $http, smineGrid, $rootScope) {


    $scope.myData = [];
    $scope.getPagedDataCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.test = function () {
        alert($scope.gridOptions.sortInfo);
    };
    smineGrid.pageGrid("gridOptions", $scope, {
        data: 'myData',
        requestUrl: fullPath("admin/system/menuList"),
        columnDefs: [{field: 'name', displayName: 'Name'},
            {field: 'level', displayName: 'level'}]
    });

});
