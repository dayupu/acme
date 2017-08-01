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
        enableSorting: true,
        useExternalSorting: true,
        showFooter: true,
        i18n: 'zh-cn',
        initSearch: true,
        requestUrl: undefined,
        requestMethod: "GET"
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
        if (!angular.isString(options.requestUrl)) {
            console.error("[options.url] require is string");
            return;
        }

        scope.selectedNodes = [];
        scope.totalServerItems = 0;
        scope.pagingOptions = defaultPagingOptions;
        var scopeSetting = {
            pagingOptions: scope.pagingOptions,
            selectedItems: scope.selectedNodes,
            totalServerItems: 'totalServerItems'
        };
        // init angular grid
        var setting = {};
        $.extend(setting, defaultSetting, scopeSetting, options);
        $parse(target).assign(scope, setting);

        if (setting.initSearch) {
            setTimeout(function () {
                scope.getPagedDataAsync(scope.pagingOptions.pageSize, scope.pagingOptions.currentPage);
            }, 100);
        }

        scope.getPagedDataAsync = function (pageSize, pageNumber) {
            $http({
                method: setting.requestMethod,
                url: setting.requestUrl,
                headers: {
                    page_size: pageSize,
                    page_number: pageNumber
                }
            }).then(function successCallback(response) {
                var result = {data: [], total: 0};
                if (angular.isFunction(scope.getPagedDataCallback)) {
                    var callBackResult = scope.getPagedDataCallback(response.data);
                    if (angular.isObject(callBackResult)) {
                        $.extend(result, callBackResult);
                    }
                }
                scope.myData = result.data;
                scope.totalServerItems = result.total;
            }, function errorCallback(response) {
                console.error("Request failed");
            });
        };

        // binding event when page changed
        scope.$watch('pagingOptions', function (newVal, oldVal) {
            if (newVal !== oldVal || newVal.currentPage !== oldVal.currentPage || newVal.pageSize != oldVal.pageSize) {
                scope.getPagedDataAsync(scope.pagingOptions.pageSize, scope.pagingOptions.currentPage);
            }
        }, true);
    };
});