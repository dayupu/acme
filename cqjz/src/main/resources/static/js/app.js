var menuApp = angular.module("menuApp",[]);
menuApp.directive("myDirective",function(){
  return {
    scope:{
      title:"@"
    },
    template: function(){
      return '{{title}}';
    },
    link: function(scope, element, attrs){
          element.ready(function(){
          alert(scope.title);
        });
    }
   };
});

var mainApp = angular.module("mainApp",["menuApp"]);
var defaultMenus = [{"name":"menu01","childrens":[{"name":"childmenu"}]},{"name":"menu02"},{"name":"menu03"},{"name":"menu04"}]
menuApp.controller("menuCtrl",function($scope){
    $scope.name = "aa";
    $scope.menus=defaultMenus;
});