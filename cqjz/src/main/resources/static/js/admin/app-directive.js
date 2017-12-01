mainApp.directive("mineAction", function () {
    return {
        restrict: 'E',
        scope: {
            name: "@",
            action: "&",
            icon: "@"
        },
        template: "<button type='button' ng-click='action()' class='btn btn-link btn-sm mine-action'><i ng-if='icon != null' ng-class='icon'></i>{{name}}</button>",
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
mainApp.directive("mineMessage", function () {
    return {
        restrict: 'E',
        scope: {
            message: "=",
            status: "="
        },
        template: "<div class='alert' ng-show='status != null && message != null'  ng-class=\"{true:'alert-success',false:'alert-danger'}[status]\">"+
        "<a href='javascript:void(0)' class='close'>&times;</a>"+
        "{{message}}</div>",
        link: function (scope, element, attrs, ngModel) {
           $(element).children("a[class='close']").click(function(){
                scope.status = null;
                scope.message = null;
                scope.$apply();
           });
        },
        replace: true
    }
});
mainApp.directive("mineDatetime", function () {
    return {
        restrict: 'E',
        require: 'ngModel',
        scope: {
            time: "@",
            width: "@",
            textname:"@",
            required:"@"
        },
        template: "<div class='input-group date'>" +
        "<input type='text'class='form-control input-sm' />" +
        "<span class='input-group-addon' style='cursor:pointer;'><span class='glyphicon glyphicon-calendar'></span></span></div>",
        link: function (scope, element, attrs, ngModel) {
            var dateText = $(element).children("input");
            var time = true;
            var formatRegex = "Y-m-d H:i:s";
            var width = 180;
            if (typeof attrs.width != "undefined") {
                width = attrs.width;
            }
            if (typeof attrs.textname != "undefined") {
                $(dateText).attr("name", attrs.textname);
            }
            if (typeof attrs.required != "undefined" && attrs.required == "true") {
                $(dateText).attr("required", true);
            }
            $(element).css("width", width + "px");
            if (typeof attrs.time == "string" && attrs.time == "false") {
                time = false;
                formatRegex = "Y-m-d";
            }

            $(dateText).datetimepicker({format: formatRegex, timepicker: time});
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
            inputId: "@",
            width:"@"
        },
        template: "<div class='input-group mine-dropdown-group'>"
        + "<input type='text' class='form-control input-sm' readonly/>"
        + "<input type='hidden'/>"
        + "<div class='input-group-btn'>"
        + "<button type='button' class='btn btn-default btn-sm'data-toggle='dropdown'>"
        + "<span class='caret'></span>"
        + "</button>"
        + "</div>"
        + "</div>",
        link: function (scope, element, attrs, ngModel) {
            var width = 180;
            if (typeof attrs.width != "undefined") {
                width = attrs.width;
            }
            var inputText = $(element).children("input[type='text']");
            var hiddenText = $(element).children("input[type='hidden']");
            var dropBtn = $(element).find("button[type='button']");
            var inputId = $(element).attr("inputId");
            $(element).css("width", width + "px");
            $(inputText).css("width", (width - 28) + "px");
            $(inputText).attr("id", inputId);
            $(hiddenText).attr("id", inputId + "_hidden");
            $(hiddenText).change(function () {
                ngModel.$setViewValue($(this).val());
            });
            $(dropBtn).click(function () {
                $(inputText).trigger("focus");
            });
            ngModel.$render = function () {
                var value = ngModel.$viewValue;
                if (typeof value == "undefined") {
                    return;
                }
                if (value == null || value == "") {
                    $(inputText).val("");
                    $(hiddenText).val("");
                    return;
                }
                var ztree = $.fn.zTree.getZTreeObj(inputId + "_treeDemo");
                var node = ztree.getNodeByParam("id", value, null);
                if (node == null) {
                    $(inputText).val("");
                    $(hiddenText).val("");
                }
                $(inputText).val(node.name);
                $(hiddenText).val(node.id);
            };
        },
        replace: true
    }
});