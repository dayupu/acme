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
        template: "<span class='mine-validator'><span class='help-inline' style='color:#a94442;' ng-show='(when == null || when) && !valid'><i class='fa fa-exclamation-circle'></i><span class='message'>{{error}}</span></span>" +
        "<span ng-if='right' class='help-inline' style='color:green;' ng-show='(when == null || when) && valid'><i class='fa fa-check-circle-o'></i><span class='message'>{{ok}}</span></span></span>",
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
mainApp.directive("mineKey", function () {
    return {
        restrict: 'E',
        scope: {
            name: "@",
            required: "@"
        },
        template: "<div class='mine-win-key'><span ng-if='required == \"\"' class='mine-win-required'>*</span>&nbsp;{{name}}</div>",
        replace: true
    }
});
mainApp.directive("mineDate", function () {
    return {
        restrict: 'E',
        require: 'ngModel',
        template: "<div class='input-group date' style='width: 162px;'>" +
        "<input style='width: 142px;' type='text'class='form-control input-sm' />" +
        "<span class='input-group-addon' style='cursor:pointer;'><span class='glyphicon glyphicon-calendar'></span></span></div>",
        link: function (scope, element, attrs, ngModel) {
            var dateText = $(element).children("input");
            $(dateText).datetimepicker({format: 'Y-m-d H:i:s'});
            $(dateText).change(function () {
                ngModel.$setViewValue($(this).val());
                console.log($(this).val());
            });
            ngModel.$render = function () {
                $(dateText).val(ngModel.$viewValue);
            };
            $(element).find("span[class='input-group-addon']").click(function () {
                $(dateText).trigger("focus");
            });
        },
        replace: true
    }
});
mainApp.directive("mineDropdown", function (mineTree) {
    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            inputId: "@"
         },
        template: "<div class='input-group'>"
                   +"<input type='text' class='form-control input-sm' style='width: 152px;' readonly/>"
                   +"<input type='hidden'/>"
                   +"<div class='input-group-btn'>"
                       +"<button type='button' class='btn btn-default btn-sm'data-toggle='dropdown'>"
                           +"<span class='caret'></span>"
                       +"</button>"
                   +"</div>"
                   +"</div>",
        link: function (scope, element, attrs, ngModel) {
             var inputText = $(element).children("input[type='text']");
             var hiddenText = $(element).children("input[type='hidden']");
             var dropBtn = $(element).find("button[type='button']");
             $(inputText).attr("id",$(element).attr("inputId"));
             $(hiddenText).attr("id",$(element).attr("inputId")+"_hidden");
             $(hiddenText).change(function () {
                 ngModel.$setViewValue($(this).val());
             });
             $(dropBtn).click(function(){
                $(inputText).trigger("focus");
             });
        },
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
                            ctrl.editorInstance.setContent(ngModel.$viewValue || '', false);
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