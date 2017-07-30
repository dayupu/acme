mainApp.service("smineTree", function ($http) {
    var setting = {
        checkbox:false,
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