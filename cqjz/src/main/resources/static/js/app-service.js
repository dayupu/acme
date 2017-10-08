mainApp.service("mineHttp", function ($http, Upload) {
    this.send = function (method, url, params, callback) {
        var setting = {
            method: method,
            url: fullPath(url),
        };
        $.extend(setting, params);
        $http(setting).then(function successCallback(response) {
            callback(response.data);
        }, function errorCallback(response) {
            alert("request error")
        });
    };
    this.upload = function (url, data, callback) {
        Upload.upload({
            url: fullPath(url),
            data: data,
            file: data.file
        }).progress(function (evt) {
            //进度条
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progess:' + progressPercentage + '%' + evt.config.file.name);
        }).success(function (data, status, headers, config) {
            callback(data);
        }).error(function (data, status, headers, config) {
            alert("上传失败");
        });
    };
    this.constant = function (type, callback) {
        var url = "admin/constant/" + type;
        this.send("GET", url, {}, function (data) {
            callback(data)
        });
    }

});
mainApp.service("mineUtil", function ($uibModal) {
    this.confirm = function (message, callback) {
        var confirmModal = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: fullPath('comm/confirm.htm'),
            size: 'sm',
            controller: 'confirmController',
            resolve: {
                data: function () {
                    return message;
                }
            }
        });
        confirmModal.result.then(function () {
            callback();
        }, function () {
        });
    };
    this.alert = function (message) {
        var alertModal = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: fullPath('comm/alert.htm'),
            size: 'sm',
            controller: 'alertController',
            resolve: {
                data: function () {
                    return message;
                }
            }
        });
        alertModal.result.then(function () {
        }, function () {
        });
    };
    this.modal = function (templateUrl, controller, data, size) {
        var myModal = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: fullPath(templateUrl),
            size: size,
            controller: controller,
            resolve: {
                data: function () {
                    return data;
                }
            }
        });
        myModal.rendered.then(function () {//模态窗口打开之后执行的函数
            $(".modal-dialog").draggable({
                handle: ".modal-header"   // 只能点击头部拖动
            });
        });
        return myModal;
    };
});
mainApp.controller("confirmController", function ($scope, $uibModalInstance, data) {
    $scope.message = data;
    $scope.ok = function () {
        $uibModalInstance.close();
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    }
});
mainApp.controller("alertController", function ($scope, $uibModalInstance, data) {
    $scope.message = data;
    $scope.ok = function () {
        $uibModalInstance.dismiss('cancel');
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    }
});
mainApp.service("mineTree", function () {
    var defaultSetting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: null
            }
        }
    };
    this.build = function (obj, nodes, options) {
        var setting = {};
        $.extend(setting, defaultSetting);
        if (typeof options != "undefined") {
            $.extend(setting, options);
        }
        return $.fn.zTree.init(obj, setting, nodes);
    };

    this.buildAsync = function (obj, url, nodes, options) {
        var filter = function (treeId, parentNode, childNodes) {
            return childNodes;
        };
        var setting = {
            async: {
                autoParam: ["id", "name", "level"],
                enable: true,
                type: "GET",
                url: fullPath(url),
                dataFilter: filter,
            }
        };
        $.extend(setting, defaultSetting);
        if (typeof options != "undefined") {
            $.extend(setting, options);
        }
        return $.fn.zTree.init(obj, setting, nodes);
    }
});
mainApp.service("mineGrid", function ($http, $parse) {
    var defaultSetting = {
        requestUrl: undefined,
        requestMethod: "GET",
        multiSelect: false,
        enablePaging: true,
        enableSorting: true,
        useExternalSorting: true,
        showFooter: true,
        i18n: 'zh-cn',
        sortInfo: {fields: [], columns: [], directions: []}
    };

    this.gridInit = function (target, scope, options) {
        if (!angular.isString(target)) {
            console.error("[target] is not string object");
            return;
        }
        if (!angular.isString(options.requestUrl)) {
            console.error("[options.url] require is string");
            return;
        }
        scope.gridSelectedItems = [];
        var scopeSetting = {
            enablePaging: false,
            enableSorting: false,
            useExternalSorting: false,
            showFooter: false,
            selectedItems: scope.gridSelectedItems
        };
        // init angular grid
        var setting = {};
        $.extend(setting, defaultSetting, scopeSetting, options);
        $parse(target).assign(scope, setting);
        scope.gridQuery = function (params) {
            $http({
                method: setting.requestMethod,
                url: setting.requestUrl,
                params: params
            }).then(function successCallback(response) {
                var result = {data: []};
                if (angular.isFunction(scope.gridQueryCallback)) {
                    var callBackResult = scope.gridQueryCallback(response.data);
                    if (angular.isObject(callBackResult)) {
                        $.extend(result, callBackResult);
                    }
                }
                $parse(setting.data).assign(scope, result.data);
            }, function errorCallback(response) {
                console.error("Request failed");
            });
        };
    };
    this.gridPageInit = function (target, scope, options) {
        if (!angular.isString(target)) {
            console.error("[target] is not string object");
            return;
        }
        if (!angular.isString(options.requestUrl)) {
            console.error("[options.url] require is string");
            return;
        }
        scope.gridSelectedItems = [];
        scope.gridTotalServerItems = 0;
        scope.gridPagingOptions = {
            pageSizes: [10, 20, 50],
            pageSize: "10",
            currentPage: 1,
            totalServerItems: 0
        };
        var scopeSetting = {
            pagingOptions: scope.gridPagingOptions,
            selectedItems: scope.gridSelectedItems,
            totalServerItems: 'gridTotalServerItems'
        };
        // init angular grid
        var setting = {};
        $.extend(setting, defaultSetting, scopeSetting, options);
        $parse(target).assign(scope, setting);
        var tempParams = {};
        var tempData = {};
        var queryFlag = false;
        scope.gridPageQuery = function (params, data) {
            tempParams = params;
            tempData = data;
            queryFlag = true;
            scope.gridPagingOptions.currentPage = 1;
            scope.gridPageLoadDataByAsync(scope.gridPagingOptions, scope.sortInfo, params, data);
        };
        scope.gridPageLoadDataByAsync = function (pageInfo, sortInfo, customParams, data) {
            var headers = {
                page_size: pageInfo.pageSize,
                page_number: pageInfo.currentPage
            };
            var params = {};
            if (angular.isObject(customParams)) {
                $.extend(params, customParams)
            }
            if (angular.isObject(sortInfo)) {
                $.extend(headers, {sort_field: sortInfo.field, sort_direction: sortInfo.direction});
            }
            if (!angular.isObject(data)) {
                data = {};
            }
            $http({
                method: setting.requestMethod,
                url: setting.requestUrl,
                headers: headers,
                params: params,
                data: data
            }).then(function successCallback(response) {
                var result = {data: [], total: 0};
                if (angular.isFunction(scope.gridPageQueryCallback)) {
                    var callBackResult = scope.gridPageQueryCallback(response.data);
                    if (angular.isObject(callBackResult)) {
                        $.extend(result, callBackResult);
                    }
                }
                $parse(setting.data).assign(scope, result.data);
                scope.gridTotalServerItems = result.total;
            }, function errorCallback(response) {
                console.error("Request failed");
            });
        };
        // binding event when page changed
        scope.$watch('gridPagingOptions', function (newVal, oldVal) {
            if (queryFlag) {
                queryFlag = false;
                return;
            }
            if (newVal !== oldVal && (newVal.currentPage !== oldVal.currentPage || newVal.pageSize != oldVal.pageSize)) {
                scope.gridPageLoadDataByAsync(scope.gridPagingOptions, scope.gridSortInfo, tempParams, tempData);
            }
        }, true);
        // item select listener
        scope.$watch("gridSelectedItems", function (newValue, oldValue) {
            if (newValue != oldValue && angular.isFunction(scope.gridPageSelectedItems)) {
                scope.gridPageSelectedItems(newValue, oldValue);
            }
        }, true);
        scope.$on('ngGridEventSorted', function (event, sortInfo) {
            if (sortInfo.fields[0] && sortInfo.directions[0]) {
                var sortInfoTemp = {field: sortInfo.fields[0], direction: sortInfo.directions[0]};
                if (scope.gridSortInfo && scope.gridSortInfo.field == sortInfoTemp.field && scope.gridSortInfo.direction == sortInfoTemp.direction) {
                    return;
                }
                scope.gridSortInfo = sortInfoTemp;
                scope.gridPageLoadDataByAsync(scope.gridPagingOptions, sortInfoTemp, tempParams, tempData);
            }
        });
    };
});
