mainApp.controller("newsPublishCtl", function ($scope, $state) {
    $state.go("news.add");
});

mainApp.controller("newsEditCtl", function ($scope, $state, $stateParams, $location, mineHttp, mineTree, mineUtil) {
    $scope.news = {};
    $scope.news.canEdit = true;
    $scope.model = "publish";
    if (typeof $stateParams.number == "string") {
        $scope.model = "edit";
        mineHttp.send("GET", "admin/news/" + $stateParams.number, null, function (result) {
            $scope.news = result.content;
        })
    }

    mineHttp.constant("newsTypeTree", function (data) {
        var callback = {
            beforeClick: function (treeId, treeNode, clickFlag) {
                var selectable = (treeNode.pid != null);
                if (selectable) {
                    $scope.hasImage = treeNode.hasImage;
                }
                return selectable;
            }
        };
        mineTree.dropDown($("#newsTypeTree"), data, callback)
    });

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

    $scope.refreshContent = function () {
        $scope.news.content = UE.getEditor('newsEditor').getContent();
    };

    $scope.refreshPage = function (news) {
        if ($scope.model == "publish") {
            $scope.model = "edit";
        }
        $scope.news = news;
    };
    $scope.save = function () {
        $scope.refreshContent();
        if (!$scope.validator()) {
            return;
        }
        mineHttp.send("POST", "admin/news/save", {data: $scope.news}, function (result) {
                $scope.messageStatus = verifyData(result);
                $scope.message = result.message;
                if ($scope.messageStatus) {
                    $scope.refreshPage(result.content);
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
                    $scope.refreshPage(result.content);
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
        var modalInstance = mineUtil.modal("admin/_news/newsPicture.htm", "newsPictureCtl", imageId, "lg");
        modalInstance.result.then(function () {
        }, function () {
        });
    };

    $scope.config = {
        initialFrameHeight: 300,
        enableAutoSave: false,
        autoHeightEnabled: false,
        toolbars: [_ueditorToolbars]
    };
});

mainApp.controller("newsListCtl", function ($scope, $state, mineHttp, mineGrid, mineUtil, mineTree) {
    mineHttp.constant("newsStatus", function (data) {
        $scope.statuses = data.content;
    });
    mineHttp.constant("newsTypeTree", function (data) {
        mineTree.dropDown($("#newsTypeTree"), data)
    });
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
                field: 'type',
                width: 100,
                displayName: '新闻类型',
                cellTemplate: "<div class='mine-table-span'>{{row.entity.typeMessage}}</div>"
            },
            {
                field: 'status',
                displayName: '状态',
                width: 100,
                cellTemplate: "<div class='mine-table-span'>{{row.entity.statusMessage}}</div>"
            },
            {field: 'createdAt', width: 130, displayName: '创建时间'},
            {field: 'updatedAt', width: 130, displayName: '修改时间'},
            {
                field: 'id',
                displayName: '操作',
                width: 200,
                sortable: false,
                cellTemplate: "<div><mine-action ng-show='checkShow(row.entity, 1)' icon='fa fa-edit' action='edit(row.entity)' name='编辑'></mine-action>" +
                "<mine-action icon='fa fa-search' action='preview(row.entity)' name='预览'></mine-action>" +
                "<mine-action ng-show='checkShow(row.entity, 2)' icon='fa fa-trash' action='drop(row.entity)' name='删除'></mine-action></div>"
            }
        ]
    });

    $scope.checkShow = function (news, operate) {
        if (operate == 1) {
            return news.status == 1 || news.status == 4;
        } else if (operate == 2) {
            return news.status == 1 || news.status == 6;
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

mainApp.controller("newsTopicCtl", function ($scope, mineGrid, mineTree, mineHttp, mineUtil) {
    $scope.watch = {};
    $('#newsTopicTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
    mineHttp.constant("simpleStatus", function (data) {
        $scope.statuses = data.content;
    });
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        multiSelect: false,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/news/topic/list"),
        columnDefs: [
            {field: 'name', width: 150, displayName: '专题名称'},
            {field: 'itemCount', width: 60, displayName: '栏目数', sortable: false},
            {
                field: 'status',
                width: 100,
                displayName: '状态',
                cellTemplate: "<div class='mine-table-span'>{{row.entity.statusMessage}}</div>"
            },
            {field: 'description', displayName: '描述', sortable: false},
            {field: 'createdAt', width: 150, displayName: '创建时间'},
            {field: 'createdBy', width: 100, sortable: false, displayName: '创建者'},
            {field: 'updatedAt', width: 150, displayName: '修改时间'},
            {field: 'updatedBy', width: 100, sortable: false, displayName: '修改者'},
            {
                field: 'id',
                displayName: '操作',
                width: 100,
                sortable: false,
                cellTemplate: "<div><mine-action icon='fa fa-edit' action='edit(row.entity.code)' name='编辑'></mine-action></div>"
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

    $scope.edit = function (key) {
        var param = {};
        param.code = (key == "add" ? null : key);
        var modalInstance = mineUtil.modal("admin/_news/newsTopicEdit.htm", "newsTopicController", param);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
            $scope.query();
        });
    };
});

mainApp.controller("newsTopicController", function ($scope, $uibModalInstance, mineHttp, mineMessage, data) {
    mineHttp.constant("simpleStatus", function (data) {
        $scope.statuses = data.content;
    });
    $scope.newsTopic = {};
    $scope.modal = "edit";
    if (data.code == null) {
        $scope.modal = "new";
    } else {
        mineHttp.send("GET", "admin/news/topic/" + data.code, {}, function (result) {
            $scope.messageStatus = verifyData(result);
            if (!$scope.messageStatus) {
                $scope.message = result.message;
                return;
            }
            $scope.newsTopic = result.content;
        });
    }
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
    $scope.ok = function () {
        mineHttp.send("POST", "admin/news/topic/save", {data: $scope.newsTopic}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
            if ($scope.messageStatus) {
                $scope.modal = "edit";
                $scope.newsTopic = result.content;
            }
        });
    };
});