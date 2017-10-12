mainApp.controller("jzWatchListCtl", function ($scope, mineGrid, mineTree, mineHttp, mineUtil) {
    $('#watchTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        multiSelect: false,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/news/list"),
        columnDefs: [
            {field: 'title', width: 100, displayName: '值班时间'},
            {field: 'source', displayName: '值班领导'},
            {field: 'createdAt', displayName: '值班班长'},
            {field: 'updatedAt', displayName: '值班民警'},
            {field: 'updatedAt', displayName: '值班电话'},
        ]
    });
});