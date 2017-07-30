// 导航菜单
mainApp.directive('mineMenu', function () {
    return {
        restrict: 'E',
        scope: {
            values:"="
        },
        template: "<div><ul class='breadcrumb' ng-if='values != null'><li>当前位置：</li>"
        +"<li ng-repeat='value in values'><span class='divider' ng-if='$index != 0'>/</span>{{value.name}}</li></ul></div>",
        replace: true
    };
});
