mainApp.controller("systemMenuCtl", function ($scope, $http, smineGrid, $rootScope, $sortService) {

    $scope.permitAdd=true;
    $scope.permitModify=true;
    $scope.permitDelete=true;

    $scope.myData = [];
    smineGrid.pageGrid("gridOptions", $scope, {
        data: 'myData',
        requestUrl: fullPath("admin/system/menuList"),
        columnDefs: [{field: 'name', displayName: 'Name'},
            {field: 'level', displayName: 'level'}]
    });
    $scope.test = function () {
        $scope.pageQuery({test:"test"});
    };
    $scope.pageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.pageSelectedItems = function(newValue, oldValue){
         $scope.permitAdd=false;
         $scope.permitModify=false;
         $scope.permitDelete=false;
    }

});
