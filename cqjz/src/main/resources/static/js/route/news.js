function initDatas($scope, mineHttp) {
    $scope.loadTypes = function () {
        mineHttp.send("GET", "admin/news/types", {}, function (data) {
            if (verifyData(data)) {
                $scope.newsTypes = data.content;
            }
        });
    };
    $scope.loadStatus = function () {
        mineHttp.send("GET", "admin/news/status", {}, function (data) {
            if (verifyData(data)) {
                $scope.statuses = data.content;
            }
        });
    };
}

mainApp.controller("newsPublishCtl", function ($scope, $http, $stateParams, mineTree, mineHttp, mineUtil) {
    $scope.news = {};
    $scope.newsTypes = [];
    initDatas($scope, mineHttp);
    if (typeof $stateParams.number == "string") {
        mineHttp.send("GET", "admin/news/" + $stateParams.number, null, function (result) {
            $scope.news = result.content;
        })
    }
    $scope.save = function () {
        mineHttp.send("POST", "admin/news/save", {data: $scope.news}, function (result) {
                $scope.messageStatus = verifyData(result);
                $scope.message = result.message;
                if ($scope.messageStatus) {
                    $scope.news = result.content;
                }
            }
        );
    };
    $scope.imageUpload = function () {
        if ($scope.file) {
            $scope.upload($scope.file);
        }
    };
    $scope.upload = function (file) {
        mineHttp.upload("admin/news/picture", {file: file}, function (data) {
            $scope.image = data.content;
            $scope.news.imageId = $scope.image.imageId;
        });
    };
    $scope.preview = function (imageId) {
        var modalInstance = mineUtil.modal("admin/_news/newsPreview.htm", "newsPictureCtl", imageId, "lg");
        modalInstance.result.then(function () {
        }, function () {
        });
    };
    $scope.loadTypes();
});

mainApp.controller("newsListCtl", function ($scope, $uibModal, $state, mineHttp, mineGrid, mineUtil) {
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        multiSelect: false,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/news/list"),
        columnDefs: [
            {field: 'title', displayName: '标题'},
            {field: 'source', width: 150, displayName: '来源'},
            {
                field: 'status',
                displayName: '状态',
                width: 100,
                cellTemplate: "<span class='mine-table-span'>{{row.entity.statusMessage}}</span>"
            },
            {field: 'createdAt', width: 150, displayName: '创建时间'},
            {field: 'updatedAt', width: 150, displayName: '修改时间'},
            {
                field: 'id',
                displayName: '操作',
                width: 200,
                sortable: false,
                cellTemplate: "<div><mine-action icon='fa fa-edit' action='edit(row.entity)' name='编辑'></mine-action>" +
                "<mine-action icon='fa fa-sticky-note-o' action='detail(row.entity)' name='查看'></mine-action></div>"
            }

        ]
    });
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.filter);
    };

    $scope.edit = function (news) {
        $state.go("news.edit", {number: news.number});
    };
    initDatas($scope, mineHttp);
    $scope.loadTypes();
    $scope.loadStatus();
    $scope.query();
});

mainApp.controller("newsPictureCtl", function ($scope, $uibModalInstance, data) {
    $scope.imgUrl = imageUrl(data);
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});