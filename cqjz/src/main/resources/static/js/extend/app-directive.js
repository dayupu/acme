// 导航菜单
mainApp.directive('mineBreadcrumb', function () {
    return {
        restrict: 'E',
        scope: {
            values: "="
        },
        template: "<div class='mine-location'><ul ng-if='values != null'><li><i class='fa fa-home' aria-hidden='true'></i></li><li>当前位置：</li>"
        + "<li ng-repeat='value in values'><span ng-if='$index != 0'>&gt;</span>{{value.name}}</li></ul></div>",
        replace: true
    };
});

mainApp.directive("mineAction", function () {
    return {
        restrict: 'E',
        scope: {
            name: "@",
            action: "&"
        },
        template: "<span class='mine-action'><a href='javascript:void(0);' ng-click='action()'>{{name}}</a></span>",
        replace: true
    }
});
mainApp.directive("mineValidator", function () {
    return {
        restrict: 'E',
        scope: {
            error: "@",
            valid:"=",
            when:"="
        },
        template: "<span><span class='help-inline' ng-show='when && !valid'><i class='fa fa-exclamation-circle'></i><span class='message'>{{error}}</span></span>" +
        "<span class='help-inline' style='color:green;' ng-show='when && valid'><i class='fa fa-check-square'></i></span></span>",
        replace: true
    }
});
