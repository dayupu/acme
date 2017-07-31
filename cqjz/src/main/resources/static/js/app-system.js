mainApp.controller("systemMenuCtl", function ($scope, $parse, smineGrid) {
    $scope.myData = [{name: "Moroni", age: 50, button: "<button>test</button>"},
        {name: "Tiancum", age: 43, button: "<button>test</button>"},
        {name: "Jacob", age: 27, button: "<button>test</button>"},
        {name: "Nephi", age: 29, button: "<button>test</button>"},
        {name: "Enos", age: 34, button: "<button>test</button>"}];

    smineGrid.pageGrid("gridOptions", $scope, {data: 'myData'});
    $scope.getPagedDataAsync = function (pageSize, page) {
        alert(pageSize + "||" + page);
    };

});
