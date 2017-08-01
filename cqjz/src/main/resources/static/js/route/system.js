mainApp.controller("systemMenuCtl", function ($scope, $parse, smineGrid,$rootScope) {


    $scope.myData = [{name: "Moroni", age: 50},
        {name: "Tiancum", age: 43},
        {name: "Jacob", age: 27},
        {name: "Nephi", age: 29},
        {name: "Enos", age: 34}];

    smineGrid.pageGrid("gridOptions", $scope, {data: 'myData', columnDefs: [{field: 'name', displayName: 'Name'}, {field:'age', displayName:'Age'}]});
    $scope.getPagedDataAsync = function (pageSize, page) {
        alert(pageSize + "||" + page);
    };
    $scope.test = function(){
       alert($scope.gridOptions.sortInfo);
    }

});
