// my tree base on zTree
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
// my grid base on ng-grid
mainApp.service("smineGrid", function ($http, $parse) {
    var defaultSetting = {
        multiSelect: false,
        enablePaging: true,
        enableSorting:true,
        useExternalSorting: true,
        showFooter: true,
        i18n: 'zh-cn'
    };
    var defaultPagingOptions = {
        pageSizes: [10, 20, 50],
        pageSize: "10",
        currentPage: 1,
        totalServerItems: 0
    };
    this.pageGrid = function (target, scope, options) {
        if (!angular.isString(target)) {
            console.error("[target] is not string object");
            return;
        }
        scope.selectedNodes = [];
        scope.pagingOptions = defaultPagingOptions;
        var scopeSetting = {
            pagingOptions: scope.pagingOptions,
            selectedItems: scope.selectedNodes
        };
        // init angular grid
        var setting = {};
        $.extend(setting, defaultSetting, scopeSetting, options);
        $parse(target).assign(scope, setting);
        // binding event when page changed
        scope.$watch('pagingOptions', function (newVal, oldVal) {
            if (newVal !== oldVal || newVal.currentPage !== oldVal.currentPage || newVal.pageSize != oldVal.pageSize) {
                if (angular.isFunction(scope.getPagedDataAsync)) {
                    scope.getPagedDataAsync(scope.pagingOptions.pageSize, scope.pagingOptions.currentPage);
                }
            }
        }, true);
    };
});