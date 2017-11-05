mainApp.controller("jzWatchListCtl", function ($scope, mineGrid, mineTree, mineHttp, mineUtil) {
    $scope.watch = {};
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
                cellTemplate: "<div><mine-action icon='fa fa-trash-o' action='drop(row.entity)' name='删除'></mine-action></div>"
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
        if ($scope.watch.watchTime == null || $scope.watch.watchTime == "") {
            return;
        }
        mineHttp.send("POST", "admin/jz/watch/detail", {data: $scope.watch}, function (result) {
            if (result.content == null) {
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

mainApp.controller("jzSuperstarListCtl", function ($scope, mineGrid, mineTree, mineHttp, mineUtil) {
    $scope.months = getMonths();
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        multiSelect: false,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/jz/superstar/list"),
        columnDefs: [
            {field: 'id', width: 100, displayName: '编号'},
            {field: 'year', width: 100, displayName: '年'},
            {field: 'month', width: 100, displayName: '月'},
            {field: 'name', displayName: '姓名'},
            {field: 'honor', displayName: '荣誉名称'},
            {field: 'story', displayName: '简介'},
            {
                field: 'id',
                displayName: '操作',
                width: 200,
                sortable: false,
                cellTemplate: "<div><mine-action icon='fa fa-edit' action='edit(row.entity)' name='编辑'></mine-action>" +
                "<mine-action icon='fa fa-search' action='detail(row.entity)' name='查看'></mine-action>" +
                "<mine-action icon='fa fa-trash-o' action='drop(row.entity)' name='删除'></mine-action></div>"
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

    $scope.add = function () {
        var modalInstance = mineUtil.modal("admin/_jz/superstar.htm", "systemSuperstarController", null);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
            $scope.query();
        });
    };
    $scope.edit = function (superstar) {
        var modalInstance = mineUtil.modal("admin/_jz/superstar.htm", "systemSuperstarController", superstar);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
            $scope.query();
        });
    };
    $scope.detail = function (superstar) {
        var modalInstance = mineUtil.modal("admin/_jz/superstarDetail.htm", "systemSuperstarDetailController", superstar);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };
    $scope.drop = function (entity) {
        mineUtil.confirm("确认删除吗？", function () {
            mineHttp.send("DELETE", "admin/jz/superstar/" + entity.id, null, function (result) {
                $scope.query();
            });
        });
    };
});

mainApp.controller("systemSuperstarController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {
    $scope.months = getMonths();
    $scope.superstar = {};
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    $scope.imageUpload = function () {
        if ($scope.file) {
            $scope.upload($scope.file);
        }
    };
    $scope.upload = function (file) {
        mineHttp.upload("admin/jz/superstar/upload", {file: file}, function (data) {
            $scope.superstar.imageBase64 = data.content;
        });
    };
    if (data != null && data.id != null) {
        mineHttp.send("GET", "admin/jz/superstar/" + data.id, {}, function (result) {
            $scope.messageStatus = verifyData(result);

            if (!$scope.messageStatus) {
                $scope.message = result.message;
                return;
            }
            $scope.superstar = result.content;
        });
    } else {
        mineHttp.send("GET", "admin/jz/superstar/headImage", {}, function (result) {
            $scope.superstar.imageBase64 = result.content;
        });
    }
    $scope.ok = function () {
        mineHttp.send("POST", "admin/jz/superstar", {data: $scope.superstar}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
            if ($scope.messageStatus) {
                $scope.superstar = result.content;
            }
        });
    };
});

mainApp.controller("systemSuperstarDetailController", function ($scope, data, $uibModalInstance, mineHttp) {
    mineHttp.send("GET", "admin/jz/superstar/" + data.id, {}, function (result) {
        $scope.superstar = result.content;
    });
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

mainApp.controller("jzContactsListCtl", function ($scope, mineHttp) {
    $scope.config = {
        initialFrameHeight: 400,
        enableAutoSave: false,
        autoHeightEnabled: false,
        toolbars: [[
            'fullscreen', 'source', '|', 'undo', 'redo', '|', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough',
            'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|',
            'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|', 'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
            'directionalityltr', 'directionalityrtl', 'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
            'simpleupload', 'insertimage', 'insertcode', 'pagebreak', 'template', 'background', '|',
            'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol',
            'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
            'print', 'preview', 'searchreplace', 'help'
        ]]
    };

    mineHttp.send("GET", "admin/jz/contacts", {}, function (result) {
        $scope.contacts = result.content;
    });

    $scope.refreshContent = function () {
        $scope.contacts.content = UE.getEditor('contactsEditor').getContent();
    }

    $scope.setMessage = function (message, status) {
        $scope.messageStatus = status;
        $scope.message = message;
    };

    $scope.validator = function () {
        if (isEmpty($scope.contacts.content)) {
            $scope.setMessage("内容不能为空", false);
            return false;
        }
        return true;
    };
    $scope.save = function () {
        $scope.refreshContent();
        if (!$scope.validator()) {
            return;
        }
        mineHttp.send("POST", "admin/jz/contacts", {data: $scope.contacts}, function (result) {
                $scope.messageStatus = verifyData(result);
                $scope.message = result.message;
                if ($scope.messageStatus) {
                    $scope.contacts = result.content;
                }
            }
        );
    };
});
mainApp.controller("jzStyleListCtl", function ($scope, mineGrid, $state, mineHttp) {
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        multiSelect: false,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/jz/style/list"),
        columnDefs: [
            {field: 'number', width: 100, displayName: '编号'},
            {field: 'title', displayName: '标题'},
            {
                field: 'id',
                displayName: '操作',
                width: 100,
                sortable: false,
                cellTemplate: "<div><mine-action icon='fa fa-trash-o' action='drop(row.entity)' name='删除'></mine-action></div>"
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

    $scope.edit = function (number) {
        $state.go("jz.list.styleEdit", {number: number});
    };

});

mainApp.controller("jzStyleEditListCtl", function ($scope, $stateParams, $compile, mineHttp) {
    var number = $stateParams.number;
    $scope.style = {};
    if (number == "add") {
        $scope.title = "新增风采";
    } else {
        $scope.title = "编辑风采";
    }

    $scope.imageUpload = function () {
        if ($scope.file) {
            $scope.upload($scope.file);
        }
    };
    $scope.upload = function (file) {
        mineHttp.upload("admin/jz/style/image", {file: file}, function (data) {
            $scope.image = data.content;
            $scope.addImageLine($scope.image.imageId);
        });
    };

    $scope.jzStyleImageRemove = function (imageId) {
        $("#styleImagesTable").find("tr[imageId='" + imageId + "']").remove();
    };

    $scope.addImageLine = function (imageId) {
        var accessUrl = imageUrl(imageId);
        var html = "<tr imageId='" + imageId + "'><td><img class='mine-style-image' src='" + accessUrl + "'/></td>" +
            "<td><textarea style='width: 100%; height: 100px;'></textarea></td>" +
            "<td><button class='btn btn-sm btn-warning' ng-click='jzStyleImageRemove(\"" + imageId + "\")'>删除</button></td></tr>"
        angular.element("#styleImagesTable").append($compile(angular.element(html))($scope));
    };

    $scope.save = function () {
        var imageList = [];
        $("#styleImagesTable").find("tr").each(function (index) {
            if (index == 0) {return;}
            var imageLine = {};
            imageLine.imageId = $(this).attr("imageId");
            imageLine.description = $(this).find("textarea").val();
            imageList.push(imageLine);
        });
        $scope.style.styleLines = imageList;
        mineHttp.send("POST", "admin/jz/style", {data: $scope.style}, function (result) {
                $scope.messageStatus = verifyData(result);
                $scope.message = result.message;
                if ($scope.messageStatus) {
                    $scope.style = result.content;
                }
            }
        );
    };
});

