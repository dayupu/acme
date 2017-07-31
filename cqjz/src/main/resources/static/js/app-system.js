mainApp.controller("systemMenuCtl", function ($scope) {
    $scope.myData = [{name: "Moroni", age: 50},
        {name: "Tiancum", age: 43},
        {name: "Jacob", age: 27},
        {name: "Nephi", age: 29},
        {name: "Enos", age: 34}];

        alert($scope.myData);
        alert($scope.$eval('myData'))
    $scope.gridOptions = { data: 'myData' };
});
