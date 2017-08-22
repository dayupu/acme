mainApp.directive('mineBreadcrumb', function () {
    return {
        restrict: 'E',
        scope: {
            values: "="
        },
        template: "<div class='mine-location'><ul><li><a href='javascript:void(0)'><i class='fa fa-home'></i></a></li>"+
        "<li ng-repeat='value in values'><a href='javascript:void(0)'>{{value.name}}</a></li></ul></div>",
        replace: true
    };
});
mainApp.directive("mineAction", function () {
    return {
        restrict: 'E',
        scope: {
            name: "@",
            action: "&",
            icon:"@"
        },
        template: "<span class='mine-action'><a href='javascript:void(0);' ng-click='action()'><i ng-if='icon != null' ng-class='icon'></i>{{name}}</a></span>",
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
        template: "<span><span class='help-inline' ng-show='(when == null || when) && !valid'><i class='fa fa-exclamation-circle'></i><span class='message'>{{error}}</span></span>" +
        "<span class='help-inline' style='color:green;' ng-show='(when == null || when) && valid'><i class='fa fa-check-circle'></i></span></span>",
        replace: true
    }
});
