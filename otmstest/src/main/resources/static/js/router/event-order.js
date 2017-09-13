mainApp.controller("eventOrderCtl",function($scope, mineGrid){
    $scope.rootUrl = rootUrl();
    $("#eventTimeBegin").datetimepicker({format: 'Y-m-d H:i:s'});
    $("#eventTimeEnd").datetimepicker({format: 'Y-m-d H:i:s'});
    $scope.orderQuery = {};
    $scope.orderQuery.eventTimeBegin = today();
    mineGrid.gridPageInit("gridOptions", $scope, {
            data: 'myData',
            multiSelect: false,
            enableCellSelection: true,
            enableRowSelection: false,
            enableCellEdit: true,
            requestMethod: "POST",
            requestUrl: fullPath("view/order/events"),
            columnDefs: [
                {field: 'mark', displayName: '标记', width:"150"},
                {field: 'eventId', displayName: 'eventId', width:"100"},
                {field: 'orderNumber', displayName: 'orderNumber'},
                {field: 'erpNumber', displayName: 'erpNumber'},
                {field: 'eventType', displayName: 'eventType'},
                {field: 'eventTime', displayName: 'eventTime'},
                {field: 'latitude', displayName: 'latitude'},
                {field: 'longitude', displayName: 'longitude'},
                {field: 'loss', displayName: 'loss',width:"80"},
                {field: 'damage', displayName: 'damage',width:"80"},
                {field: 'fileNames', displayName: 'fileNames'},
                {field: 'truckPlate', displayName: 'truckPlate'},
                {field: 'remark', displayName: 'remark'}
            ]
        });
    $scope.gridPageQueryCallback = function (data) {
            return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.orderQuery);
    };
    $scope.query();
});