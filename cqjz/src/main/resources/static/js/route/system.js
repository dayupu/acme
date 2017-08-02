mainApp.controller("systemMenuCtl", function ($scope, $http, smineGrid, $rootScope, $sortService) {

    $scope.permitAdd=false;
    $scope.permitModify=false;
    $scope.permitDelete=false;

    $scope.myData = [];
    smineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        requestUrl: fullPath("admin/system/menuList"),
        columnDefs: [{field: 'name', displayName: 'Name'},
            {field: 'level', displayName: 'level'}]
    });
    // init load datas
    $scope.gridPageQuery();
    $scope.test = function () {
        $scope.gridPageQuery({test:"test"});
    };
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.gridPageSelectedItems = function(newValue, oldValue){
         if(newValue[0].level == "1"){
            $scope.permitAdd=true;
         }else{
            $scope.permitAdd=false;
         }
         $scope.permitModify=true;
         $scope.permitDelete=true;
    }

});
