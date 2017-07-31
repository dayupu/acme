mainApp.service("smineTree", function ($http) {
    var setting = {
        checkbox: false,
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: 0
            }
        }
    };
    this.init = function (id, nodes, options) {
        if (typeof options != "undefined") {
            $.extend(setting, options);
        }
        return $.fn.zTree.init($("#" + id), setting, nodes);
    }
});

mainApp.service("smineGrid", function ($http, $parse) {
    var defaultSetting = {
        multiSelect: false,
        enablePaging: true,
        showFooter: true,
        i18n: 'zh-cn'
    };

    this.pageGrid = function (target, scope, options) {
        if (!angular.isString(target)) {
            console.error("[target] is not string object");
            return;
        }
        scope.selectedNodes = [];
        scope.pagingOptions = {
            pageSizes: [10, 20, 50],
            pageSize: "10",
            currentPage: 1,
            totalServerItems: 0
        };
        var scopeSetting = {
            pagingOptions: scope.pagingOptions,
            selectedItems: scope.selectedNodes
        };
        // build angular grid
        var setting = {};
        $.extend(setting, defaultSetting, scopeSetting, options);
        $parse(target).assign(scope, setting);
        scope.$watch('pagingOptions', function (newVal, oldVal) {
            if (newVal !== oldVal || newVal.currentPage !== oldVal.currentPage || newVal.pageSize != oldVal.pageSize) {
                if (angular.isFunction(scope.getPagedDataAsync)) {
                    scope.getPagedDataAsync(scope.pagingOptions.pageSize, scope.pagingOptions.currentPage);
                }
            }
        }, true);
    };
});