mainApp.controller("jzWatchListCtl", function ($scope, mineGrid, mineTree, mineHttp, mineUtil) {
    $scope.watch={};
    $('#watchTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        multiSelect: false,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/jz/watch/list"),
        columnDefs: [
            {field: 'watchTime', width: 100, displayName: '值班时间'},
            {field: 'leader', displayName: '值班领导'},
            {field: 'captain', displayName: '值班班长'},
            {field: 'worker', displayName: '值班民警'},
            {field: 'phone', displayName: '值班电话'},
            {
                field: 'id',
                displayName: '操作',
                width: 100,
                sortable: false,
                cellTemplate: "<div><mine-action icon='fa fa-edit' action='drop(row.entity)' name='删除'></mine-action></div>"
            }
        ]
    });

    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.filter);
    };
    $scope.query();

    $scope.save = function () {
        mineHttp.send("POST", "admin/jz/watch", {data: $scope.watch}, function (result) {
           $scope.messageStatus = verifyData(result);
           $scope.message = result.message;
           if ($scope.messageStatus) {
               $scope.news = null;
           }
       });
    };
    $scope.detail = function () {
        if($scope.watch.watchTime == null || $scope.watch.watchTime == ""){
            return;
        }
        mineHttp.send("POST", "admin/jz/watch/detail", {data: $scope.watch}, function (result) {
          if(result.content == null){
             result.content = {};
             result.content.watchTime = $scope.watch.watchTime;
          }
          $scope.watch = result.content;
       });
    };
    $scope.drop = function (entity) {
         mineUtil.confirm("确认删除吗？", function () {
            mineHttp.send("DELETE", "admin/jz/watch/" + entity.id, null, function (result) {
                  $scope.query();
            });
         });
    };
});