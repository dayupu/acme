mainApp.controller("flowSubmitListCtl", function ($scope, mineGrid, mineHttp, mineUtil) {
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        multiSelect: false,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/flow/list/submit"),
        columnDefs: [
            {field: 'processId', width: 100, displayName: '流程号'},
            {field: 'businessSource', width: 70, displayName: '类型'},
            {field: 'taskName', width: 150, displayName: '已完成流程'},
            {field: 'nextTaskName', width: 150, displayName: '当前流程'},
            {
                field: 'subject',
                displayName: '主题',
                cellTemplate: "<mine-action action='businessPreview(row.entity)' name='{{row.entity.subject}}'></mine-action>"
            },
            {field: 'processStartTime', width: 150, displayName: '申请时间'},
            {field: 'processEndTime', width: 150, displayName: '完成时间'},
            {
                field: 'id',
                displayName: '操作',
                width: 100,
                sortable: false,
                cellTemplate: "<mine-action icon='fa fa-sticky-note-o' action='preview(row.entity)' name='查看'></mine-action>"
            }

        ]
    });
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.filter);
    };
    $scope.businessPreview = function (flow) {
        mineUtil.modal("admin/_news/newsPreview.htm", "newsPreviewCtl", flow.businessNumber, "lg");
    };
    $scope.preview = function (flow) {
        mineUtil.modal("admin/_flow/flowPreview.htm", "flowPreviewCtl", flow, "lg");
    };
    $scope.query();
});

mainApp.controller("flowRejectListCtl", function ($scope,$state, mineGrid, mineHttp, mineUtil) {
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        multiSelect: false,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/flow/list/reject"),
        columnDefs: [
            {field: 'processId', width: 100, displayName: '流程号'},
            {field: 'businessSource', width: 70, displayName: '类型'},
            {
                field: 'subject',
                displayName: '主题',
                cellTemplate: "<mine-action action='businessPreview(row.entity)' name='{{row.entity.subject}}'></mine-action>"
            },
            {field: 'rejectTime', width: 150, displayName: '退回时间'},
            {
                field: 'id',
                displayName: '操作',
                width: 200,
                sortable: false,
                cellTemplate: "<div><mine-action icon='fa fa-sticky-note-o' action='preview(row.entity)' name='查看'></mine-action>" +
                "<mine-action icon='fa fa-sticky-note-o' action='edit(row.entity)' name='修改'></mine-action>" +
                "<mine-action icon='fa fa-sticky-note-o' action='preview(row.entity)' name='撤消'></mine-action></div>"
            }
        ]
    })
    ;
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.filter);
    };
    $scope.businessPreview = function (flow) {
        mineUtil.modal("admin/_news/newsPreview.htm", "newsPreviewCtl", flow.businessNumber, "lg");
    };
    $scope.preview = function (flow) {
        mineUtil.modal("admin/_flow/flowPreview.htm", "flowPreviewCtl", flow, "lg");
    };
    $scope.edit = function (flow) {
        $state.go("news.edit", {number: flow.businessNumber});
    };
    $scope.query();
});

mainApp.controller("flowPendingListCtl", function ($scope, $state, mineGrid, mineUtil) {
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        multiSelect: false,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/flow/list/pending"),
        columnDefs: [
            {field: 'processId', width: 150, displayName: '流程号'},
            {field: 'businessSource', width: 150, displayName: '类型'},
            {
                field: 'subject',
                displayName: '主题',
                cellTemplate: "<mine-action action='preview(row.entity)' name='{{row.entity.subject}}'></mine-action>"
            },
            {field: 'applyUser', width: 150, displayName: '申请人'},
            {field: 'applyAt', width: 150, displayName: '申请时间'},
            {field: 'taskCreatedAt', width: 150, displayName: '流转时间'},
            {
                field: 'id',
                displayName: '操作',
                width: 150,
                sortable: false,
                cellTemplate: "<mine-action icon='fa fa-sticky-note-o' action='approve(row.entity)' name='审核'></mine-action>"

            }

        ]
    });
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.filter);
    };

    $scope.approve = function (flow) {
        $state.go("flow.approve", {taskId: flow.taskId, processId: flow.processId});
    };

    $scope.preview = function (flow) {
        mineUtil.modal("admin/_news/newsPreview.htm", "newsPreviewCtl", flow.businessNumber, "lg");
    };

    $scope.query();
});

mainApp.controller("flowApproveCtl", function ($scope, $stateParams, mineHttp, mineUtil) {
    var taskId = $stateParams.taskId;
    var processId = $stateParams.processId;
    $scope.approve = {};
    $scope.approve.taskId = taskId;
    $scope.approve.process = "2";
    $scope.preview = function (flow) {
        mineUtil.modal("admin/_news/newsPreview.htm", "newsPreviewCtl", flow.businessNumber, "lg");
    };
    mineHttp.send("GET", "admin/flow/" + processId + "/approve", null, function (result) {
        $scope.flow = result.content;
    });
    $scope.approveSubmit = function () {
        mineHttp.send("POST", "admin/flow/approve", {data: $scope.approve}, function (result) {
            $scope.flow = result.content;
        });
    }
});

mainApp.controller("flowPreviewCtl", function ($scope, $uibModalInstance, mineHttp, data) {
    mineHttp.send("GET", "admin/flow/" + data.processId, null, function (result) {
        $scope.flow = result.content;
        $scope.approveHistories = result.content.approveHistories;
    });
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});