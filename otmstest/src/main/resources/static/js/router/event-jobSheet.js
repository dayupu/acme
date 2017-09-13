mainApp.controller("eventJobSheetCtl",function($scope, mineGrid){
    $scope.rootUrl = rootUrl();
    $("#eventTimeBegin").datetimepicker({format: 'Y-m-d H:i:s'});
    $("#eventTimeEnd").datetimepicker({format: 'Y-m-d H:i:s'});
    $scope.jobSheetQuery = {};
    $scope.jobSheetQuery.eventTimeBegin = today();
    mineGrid.gridPageInit("gridOptions", $scope, {
            data: 'myData',
            multiSelect: false,
            enableCellSelection: true,
            enableRowSelection: false,
            enableCellEdit: true,
            requestMethod: "POST",
            requestUrl: fullPath("view/jobsheet/events"),
            columnDefs: [
                {field: 'mark', displayName: '标记', width:"200", sortable: false},
                {field: 'eventId', displayName: 'eventId', width:"150"},
                {field: 'eventType', displayName: 'eventType', width:"150"},
                {field: 'jobSheetNumber', displayName: 'jobSheetNumber', sortable: false},
                {field: 'externalShipmentId', displayName: 'externalShipmentId', sortable: false},
                {field: 'eventTime', displayName: 'eventTime'}
            ]
        });
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.jobSheetQuery, 1);
    };

    $scope.query();
});