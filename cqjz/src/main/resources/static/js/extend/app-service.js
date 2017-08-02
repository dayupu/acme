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
        requestUrl: undefined,
        requestMethod: "GET",
        multiSelect: false,
        enablePaging: true,
        enableSorting: true,
        useExternalSorting: true,
        showFooter: true,
        i18n: 'zh-cn',
        initSearch: true,
        sortInfo: { fields: [], columns: [], directions: [] }
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

        scope.selectedItems = [];
        scope.totalServerItems = 0;
        scope.pagingOptions = defaultPagingOptions;
        var scopeSetting = {
            pagingOptions: scope.pagingOptions,
            selectedItems: scope.selectedItems,
            totalServerItems: 'totalServerItems'
        };
        // init angular grid
        var setting = {};
        $.extend(setting, defaultSetting, scopeSetting, options);
        $parse(target).assign(scope, setting);

        if (setting.initSearch) {
            setTimeout(function () {
                scope.getPagedDataAsync(scope.pagingOptions);
            }, 100);
        }

        scope.pageQuery = function(params){
             scope.getPagedDataAsync(scope.pagingOptions, scope.sortInfo, params);
        }

        scope.getPagedDataAsync = function (pageInfo, sortInfo, customParams) {

            var headers = {
                page_size: pageInfo.pageSize,
                page_number: pageInfo.currentPage
            };

            var params = {}
            if(angular.isObject(customParams)){
               $.extend(params, customParams)
            }
            if(angular.isObject(sortInfo)){
               $.extend(headers, {sort_field: sortInfo.field, sort_direction: sortInfo.direction});
            }
            $http({
                method: setting.requestMethod,
                url: setting.requestUrl,
                headers: headers,
                params: params
            }).then(function successCallback(response) {
                var result = {data: [], total: 0};
                if (angular.isFunction(scope.pageQueryCallback)) {
                    var callBackResult = scope.pageQueryCallback(response.data);
                    if (angular.isObject(callBackResult)) {
                        $.extend(result, callBackResult);
                    }
                }
                $parse(setting.data).assign(scope, result.data);
                scope.totalServerItems = result.total;
            }, function errorCallback(response) {
                console.error("Request failed");
            });
        };

        // binding event when page changed
        scope.$watch('pagingOptions', function (newVal, oldVal) {
            if (newVal !== oldVal && (newVal.currentPage !== oldVal.currentPage || newVal.pageSize != oldVal.pageSize)) {
                scope.getPagedDataAsync(scope.pagingOptions, scope.sortInfo);
            }
        }, true);

        scope.$watch("selectedItems",function(newValue, oldValue ){
            if(newValue != oldValue &&  angular.isFunction(scope.pageSelectedItems)){
                   scope.pageSelectedItems(newValue, oldValue);
            }
        },true);

        scope.$on('ngGridEventSorted', function (event, sortInfo) {
                if(sortInfo.fields[0] && sortInfo.directions[0]){
                     var sortInfoTemp={field:sortInfo.fields[0],direction:sortInfo.directions[0]};
                     if(scope.sortInfo && scope.sortInfo.field == sortInfoTemp.field && scope.sortInfo.direction == sortInfoTemp.direction){
                         return;
                     }
                     scope.sortInfo = sortInfoTemp;
                     scope.getPagedDataAsync(scope.pagingOptions, sortInfoTemp);
                }
         });
    };
});