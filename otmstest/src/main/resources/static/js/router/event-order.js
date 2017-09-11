mainApp.controller("eventOrderCtl",function($scope, mineGrid){
    $("#pushAt").datetimepicker({format: 'Y-m-d H:i:s'});
    $("#pushEnd").datetimepicker({format: 'Y-m-d H:i:s'});
     mineGrid.gridPageInit("gridOptions", $scope, {
            data: 'myData',
            multiSelect: true,
            selectWithCheckboxOnly: true,
            requestMethod: "POST",
            requestUrl: fullPath("admin/user/list"),
            columnDefs: [
                {field: 'eventId', displayName: 'eventId'},
                {field: 'orderNumber', displayName: 'orderNumber'},
                {field: 'erpNumber', displayName: 'erpNumber'},
                {field: 'eventType', displayName: 'eventType'},
                {field: 'eventTime', displayName: 'eventTime'},
                {field: 'latitude', displayName: 'latitude'},
                {field: 'longitude', displayName: 'longitude'},
                {field: 'loss', displayName: 'loss'},
                {field: 'damage', displayName: 'damage'},
                {field: 'truckPlate', displayName: 'truckPlate'},
                {field: 'remark', displayName: 'remark'}
            ]
        });
})