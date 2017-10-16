function initDatas($scope, mineHttp) {

    $scope.loadTypes = function () {
        mineHttp.constant("newsType", function (data) {
            $scope.newsTypes = data.content;
        });
    };
    $scope.loadStatus = function () {
        mineHttp.constant("newsStatus", function (data) {
            $scope.statuses = data.content;
        });
    };
}


mainApp.controller("newsPublishCtl", function ($scope, $stateParams, mineHttp, mineUtil) {
    $scope.buttonDisable = false;
    $scope.news = {};
    $scope.newsTypes = [];
    $scope.newsTitle = "新闻发布";
    $scope.pageModel = "new";
    initDatas($scope, mineHttp);
    if (typeof $stateParams.number == "string") {
        $scope.newsTitle = "新闻编辑";
        $scope.pageModel = "edit";
        mineHttp.send("GET", "admin/news/" + $stateParams.number, null, function (result) {
            $scope.news = result.content;
            if (result.content.status != 1 && result.content.status != 4) {
                $scope.buttonDisable = true;
            }
        })
    }

    $scope.setMessage = function (message, status) {
        $scope.messageStatus = status;
        $scope.message = message;
    };

    $scope.validator = function () {
        if (isEmpty($scope.news.title)) {
            $scope.setMessage("请填写新闻标题", false);
            return false;
        }
        if (isEmpty($scope.news.type)) {
            $scope.setMessage("请选择新闻类型", false);
            return false;
        }
        if (($scope.news.type == 10 || $scope.news.type == 17) && isEmpty($scope.news.imageId)) {
            $scope.setMessage("请上传图片", false);
            return false;
        }
        if (isEmpty($scope.news.content)) {
            $scope.setMessage("请填写新闻正文", false);
            return false;
        }
        return true;
    };

    $scope.refreshContent = function(){
        $scope.news.content = UM.getEditor('newsEditor').getContent();
    }

    $scope.save = function () {
        $scope.refreshContent();
        if (!$scope.validator()) {
            return;
        }
        mineHttp.send("POST", "admin/news/save", {data: $scope.news}, function (result) {
                $scope.messageStatus = verifyData(result);
                $scope.message = result.message;
                if ($scope.messageStatus) {
                    $scope.news = result.content;
                }
            }
        );
    };
    $scope.submit = function () {
        $scope.refreshContent();
        if (!$scope.validator()) {
            return;
        }
        mineHttp.send("POST", "admin/news/submit", {data: $scope.news}, function (result) {
                $scope.messageStatus = verifyData(result);
                $scope.message = result.message;
                if ($scope.messageStatus) {
                    $scope.news = result.content;
                    if (result.content.status != 1 && result.content.status != 4) {
                        $scope.buttonDisable = true;
                    }
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
        var modalInstance = mineUtil.modal("admin/_news/picturePreview.htm", "newsPictureCtl", imageId, "lg");
        modalInstance.result.then(function () {
        }, function () {
        });
    };
    $scope.loadTypes();
});

mainApp.controller("newsListCtl", function ($scope, $state, mineHttp, mineGrid, mineUtil) {
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
                cellTemplate: "<div><mine-action ng-show='checkShow(row.entity, 1)' icon='fa fa-edit' action='edit(row.entity)' name='编辑'></mine-action>" +
                "<mine-action icon='fa fa-search' action='preview(row.entity)' name='预览'></mine-action>" +
                "<mine-action ng-show='checkPrivilege(row.entity, 2)' icon='fa fa-times' action='drop(row.entity)' name='删除'></mine-action></div>"
            }

        ]
    });

    $scope.checkShow = function (news, operate) {
        if (operate == 1) {
            return news.status == 1 || news.status == 4;
        } else if (operate == 2) {
            return news.status == 1;
        }
        return false;
    };
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.filter);
    };

    $scope.edit = function (news) {
        $state.go("news.edit", {number: news.number});
    };
    $scope.preview = function (news) {
        $state.go("news.preview", {number: news.number});
    };

    $scope.preview = function (news) {
        mineUtil.modal("admin/_news/newsPreview.htm", "newsPreviewCtl", news.number, "lg");
    };
    $scope.drop = function (news) {
        mineUtil.confirm("确认删除吗？", function () {
            mineHttp.send("DELETE", "admin/news/" + news.number, {}, function (data) {
                if (!verifyData(data)) {
                    mineUtil.alert(data.message);
                    return;
                }
                mineUtil.alert("删除成功");
                $scope.query();
            });
        });
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

mainApp.controller("newsPreviewCtl", function ($scope, $uibModalInstance, mineHttp, data) {
    mineHttp.send("GET", "admin/news/" + data, null, function (result) {
        $scope.news = result.content;
        $("#newsContent").html($scope.news.content);
    });
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});