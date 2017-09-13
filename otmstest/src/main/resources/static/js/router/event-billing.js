mainApp.controller("eventBillingCtl",function($scope, mineGrid){
    $scope.rootUrl = rootUrl();
    $("#createdOnBegin").datetimepicker({format: 'Y-m-d H:i:s'});
    $scope.billingQuery = {};
    $scope.billingQuery.createdOnBegin = today();
    $("#createdOnEnd").datetimepicker({format: 'Y-m-d H:i:s'});
    mineGrid.gridPageInit("gridOptions", $scope, {
            data: 'myData',
            multiSelect: false,
            enableCellSelection: true,
            enableRowSelection: false,
            enableCellEdit: true,
            requestMethod: "POST",
            requestUrl: fullPath("view/billing/events"),
            columnDefs: [
                {field: 'mark', displayName: '标记', width:"150"},
                {field: 'createdOn', displayName: '接收时间', width:"150"},
                {field: 'message', displayName: '推送内容'}
            ]
        });
    $scope.gridPageQueryCallback = function (data) {
            return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.billingQuery);
    };
    $scope.query();
});