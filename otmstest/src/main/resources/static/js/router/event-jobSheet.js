mainApp.controller("eventJobSheetCtl",function($scope, mineGrid){
    $("#eventTimeBegin").datetimepicker({format: 'Y-m-d H:i:s'});
    $("#eventTimeEnd").datetimepicker({format: 'Y-m-d H:i:s'});
     mineGrid.gridPageInit("gridOptions", $scope, {
            data: 'myData',
            multiSelect: false,
            selectWithCheckboxOnly: true,
            requestMethod: "POST",
            requestUrl: fullPath("view/jobsheet/list"),
            columnDefs: [
                {field: 'mark', displayName: '标记', width:"150", sortable: false},
                {field: 'eventId', displayName: 'eventId'},
                {field: 'eventType', displayName: 'eventType'},
                {field: 'jobSheetNumber', displayName: 'jobSheetNumber', sortable: false},
                {field: 'externalShipmentId', displayName: 'externalShipmentId', sortable: false},
                {field: 'eventTime', displayName: 'eventTime'}
            ]
        });
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.jobSheetQuery);
    };

    $scope.query();
});