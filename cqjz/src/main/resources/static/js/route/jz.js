// 已提交一览
mainApp.controller("jzWatchListCtl", function ($scope, mineGrid,mineTree, mineHttp, mineUtil) {
    $('#watchTab a').click(function (e) {
          e.preventDefault();
          $(this).tab('show');
    })

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
                    "<mine-action icon='fa fa-sticky-note-o' action='preview(row.entity)' name='预览'></mine-action>" +
                    "<mine-action ng-show='checkPrivilege(row.entity, 2)' icon='fa fa-times' action='drop(row.entity)' name='删除'></mine-action></div>"
                }

            ]
        });
});