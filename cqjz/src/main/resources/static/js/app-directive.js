mainApp.directive("mineAction", function () {
    return {
        restrict: 'E',
        scope: {
            name: "@",
            action: "&",
            icon: "@"
        },
        template: "<span class='mine-action'><a href='javascript:void(0);' ng-click='action()'><i ng-if='icon != null' ng-class='icon'></i>{{name}}</a></span>",
        replace: true
    }
});
mainApp.directive("mineValidator", function () {
    return {
        restrict: 'E',
        scope: {
            ok: "@",
            error: "@",
            valid: "=",
            when: "=",
            right: "="
        },
        template: "<span><span class='help-inline' style='color:#a94442;' ng-show='(when == null || when) && !valid'><i class='fa fa-exclamation-circle'></i><span class='message'>{{error}}</span></span>" +
        "<span ng-if='right' class='help-inline' style='color:green;' ng-show='(when == null || when) && valid'><i class='fa fa-check-circle-o'></i><span class='message'>{{ok}}</span></span></span>",
        replace: true
    }
});
mainApp.directive("mineRequired", function () {
    return {
        restrict: 'EA',
        template: "<span class='mine-required'>*</span>",
        replace: true
    }
});
mainApp.directive("mineLabel", function () {
    return {
        restrict: 'E',
        scope: {
            name: "@",
            required: "@"
        },
        template: "<label class='acme-label'>{{name}}<span ng-if='required == \"\"' class='acme-required'>*</span></label>",
        replace: true
    }
});
mainApp.directive("mineUmeditor", function ($rootScope) {
    return {
        restrict: 'EA',
        require: 'ngModel',
        link: function (scope, ele, attrs, ngModel) {
            var ctrl = {
                initialized: false,
                editorInstance: null,
                placeholder: attrs['metaUmeditorPlaceholder'] || '',
                focus: false
            };

            var height = attrs["height"];
            if (typeof height == "undefined") {
                height = 200;
            }
            var setting = {initialFrameHeight: height};
            ctrl.init = function () {
                ctrl.createEditor();
                ngModel.$render = function () {
                    if (ctrl.initialized) {
                        ctrl.editorInstance.ready(function () {
                            ctrl.editorInstance.setContent(ngModel.$viewValue || '');
                            ctrl.checkPlaceholder();
                        });
                    }
                };
            };
            ctrl.createEditor = function () {
                if (!ctrl.initialized) {
                    umeditorInit($);
                    ctrl.editorInstance = UM.getEditor(attrs['id'], setting);
                    ctrl.initialized = true;
                    ctrl.initListener();
                }
            };
            //修改ngModel Value
            ctrl.updateModelView = function () {
                var modelContent = ctrl.editorInstance.getContent();
                ngModel.$setViewValue(modelContent);
                if (!scope.$root.$$phase) {
                    scope.$apply();
                }
            };
            //监听多个事件
            ctrl.initListener = function () {
                ctrl.editorInstance.addListener('contentChange', function () {
                    scope.$evalAsync(ctrl.updateModelView);
                });
                ctrl.editorInstance.addListener('fullscreenchanged', function (event, isFullScreen) {
                    if (!isFullScreen) {
                        ctrl.editorInstance.setHeight(height);
                        $("body").css("overflow-y", "auto");
                    }
                });
            };
            ctrl.checkPlaceholder = function () {
            };
            ctrl.init();
            $rootScope.$on('$locationChangeStart', function (event) {
                ctrl.editorInstance && ctrl.editorInstance.destroy();
            });
        }
    }
});