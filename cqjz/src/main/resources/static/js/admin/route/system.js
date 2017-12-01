/******************菜单设置********************/
mainApp.controller("systemMenuListCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage) {
    var menuTree;
    $scope.ztreeSelected = function (event, treeId, treeNode) {
        $scope.messageStatus = null;
        var url = "admin/menu/" + treeNode.id;
        mineHttp.send("GET", url, {}, function (data) {
            if (verifyData(data)) {
                $scope.menu = data.content;
            } else {
                $scope.menu = null;
            }
        });
    };
    $scope.buildTree = function (callback) {
        mineHttp.send("GET", "admin/menu/treeList", {}, function (data) {
            var options = {
                callback: {
                    onClick: $scope.ztreeSelected
                }
            };
            menuTree = mineTree.build($("#menuTree"), data.content, options);

            if (angular.isFunction(callback)) {
                callback();
            }
        });
    };
    $scope.add = function (oneLevelFlag) {
        var params = null;
        if (!oneLevelFlag) {
            params = $scope.menu;
        }
        var modalInstance = mineUtil.modal("admin/_system/menu/menuAdd.htm", "systemMenuAddController", params);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };
    $scope.edit = function () {
        var modalInstance = mineUtil.modal("admin/_system/menu/menuEdit.htm", "systemMenuEditController", $scope.menu);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };
    $scope.drop = function () {
        mineUtil.confirm("确认删除吗？", function () {
            var url = "admin/menu/" + $scope.menu.id;
            var parentNodeTemp = menuTree.getNodeByParam("id", $scope.menu.id).getParentNode();
            mineHttp.send("DELETE", url, {}, function (data) {

                if (!verifyData(data)) {
                    $scope.messageStatus = false;
                    $scope.message = data.message;
                    return;
                }
                $scope.menu = null;
                mineUtil.alert("删除成功");
                $scope.buildTree(function () {
                    if (parentNodeTemp != null) {
                        var parentNode = menuTree.getNodeByParam("id", parentNodeTemp.id);
                        menuTree.expandNode(parentNode);
                    }
                });
            });
        });
    };
    $scope.buildTree();
    mineMessage.subscribe("systemMenuTreeRefresh", function (event, nodeId) {
        $scope.buildTree(function () {
            var parentNode = menuTree.getNodeByParam("id", nodeId);
            menuTree.expandNode(parentNode);
        });
    });
});

mainApp.controller("systemMenuAddController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {
    $scope.initPage = function () {
        $scope.menu = {};
        if (data == null) {
            $scope.title = "一级菜单";
        } else {
            $scope.title = "二级菜单";
            $scope.menu.parentId = data.id;
            $scope.menu.parentName = data.name;
        }
    };
    $scope.initPage();
    $scope.ok = function () {
        mineHttp.send("POST", "admin/menu", {data: $scope.menu}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
            if ($scope.messageStatus) {
                var nodeId = data == null ? null : data.id;
                mineMessage.publish("systemMenuTreeRefresh", nodeId);
            }
            $scope.initPage();
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

mainApp.controller("systemMenuEditController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {
    mineHttp.send("GET", "admin/menu/" + data.id, {}, function (data) {
        if (verifyData(data)) {
            $scope.menu = data.content;
        } else {
            $scope.menu = null;
        }
    });
    $scope.ok = function () {
        mineHttp.send("PUT", "admin/menu/" + $scope.menu.id, {data: $scope.menu}, function (data) {
            $scope.menu = data.content;
            $scope.messageStatus = verifyData(data);
            $scope.message = data.message;
            if ($scope.messageStatus) {
                mineMessage.publish("systemMenuTreeRefresh", data.content.parentId);
            }
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

/******************组织设置********************/
mainApp.controller("systemOrganListCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage) {
    var organTree;
    $scope.ztreeSelected = function (event, treeId, treeNode) {
        $scope.messageStatus = null;
        mineHttp.send("GET", "admin/organ/" + treeNode.id, {}, function (data) {
            if (verifyData(data)) {
                $scope.organ = data.content;
            } else {
                $scope.organ = null;
            }
        });
    };
    $scope.buildTree = function (callback) {
        mineHttp.send("GET", "admin/organ/treeList", {}, function (data) {
            var options = {
                callback: {
                    onClick: $scope.ztreeSelected
                }
            };
            organTree = mineTree.build($("#organTree"), data, options);
            if (angular.isFunction(callback)) {
                callback();
            }
        });
    };

    $scope.edit = function () {
        var modalInstance = mineUtil.modal("admin/_system/organ/organEdit.htm", "systemOrganEditController", $scope.organ);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };

    $scope.drop = function () {
        mineUtil.confirm("确认删除吗？", function () {
            mineHttp.send("DELETE", "admin/organ/" + $scope.organ.code, {}, function (data) {
                if (!verifyData(data)) {
                    $scope.messageStatus = false;
                    $scope.message = data.message;
                    return;
                }
                mineUtil.alert("删除成功");
                $scope.buildTree(function () {
                    organTree.expandAll(true);
                    $scope.organ = {};
                });
            });
        });
    };
    $scope.add = function (oneLevelFlag) {
        var params = null;
        if (!oneLevelFlag) {
            params = $scope.organ;
        }
        var modalInstance = mineUtil.modal("admin/_system/organ/organAdd.htm", "systemOrganAddController", params);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };

    $scope.buildTree();
    mineMessage.subscribe("systemOrganTreeRefresh", function (event, nodeId) {
        $scope.buildTree(function () {
            var parentNode = organTree.getNodeByParam("id", nodeId);
            organTree.expandNode(parentNode);
        });
    });
});
mainApp.controller("systemOrganAddController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {
    var oneLevelFlag = false;
    $scope.initPage = function () {
        $scope.organ = {};
        if (data == null) {
            $scope.title = "部门";
            oneLevelFlag = true;
        } else {
            $scope.title = "下级部门";
            $scope.organ.parentCode = data.code;
            $scope.organ.parentName = data.name;
        }
    };
    $scope.initPage();
    $scope.ok = function () {
        mineHttp.send("POST", "admin/organ", {data: $scope.organ}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
            if ($scope.messageStatus) {
                var nodeId = data == null ? null : data.code;
                mineMessage.publish("systemOrganTreeRefresh", nodeId);
            }
            $scope.initPage();
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

mainApp.controller("systemOrganEditController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {
    mineHttp.send("GET", "admin/organ/" + data.code, {}, function (data) {
        if (verifyData(data)) {
            $scope.organ = data.content;
        } else {
            $scope.organ = null;
        }
    });
    $scope.ok = function () {
        mineHttp.send("PUT", "admin/organ/" + $scope.organ.code, {data: $scope.organ}, function (data) {
            $scope.messageStatus = verifyData(data);
            $scope.message = data.message;
            $scope.organ = data.content;
            if ($scope.messageStatus) {
                mineMessage.publish("systemOrganTreeRefresh", data.content.parentCode);
            }
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
/*****************角色设置*********************/
mainApp.controller("systemRoleListCtl", function ($scope, $uibModal, mineHttp, mineGrid, mineUtil) {
    $scope.myData = [];
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        showSelectionCheckbox: true,
        multiSelect: true,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/role/list"),
        columnDefs: [
            {field: 'name', displayName: '角色名'},
            {field: 'description', displayName: '描述'},
            {field: 'createdAt', displayName: '创建时间'},
            {field: 'createdBy', displayName: '创建者'},
            {
                field: 'id',
                displayName: '操作',
                width: 220,
                sortable: false,
                cellTemplate: "<div><mine-action icon='fa fa-edit' action='edit(row.entity)' name='编辑'></mine-action>" +
                "<mine-action icon='fa fa-search' action='detail(row.entity)' name='查看'></mine-action>" +
                "<mine-action icon='fa fa-street-view' action='privilege(row.entity)' name='权限'></mine-action>" +
                "<mine-action icon='fa fa-trash' action='drop(row.entity)' name='删除'></mine-action></div>"
            }

        ]
    });
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.roleQuery);
    };
    $scope.add = function () {
        var modalInstance = mineUtil.modal("admin/_system/role/roleAdd.htm", "systemRoleAddController", {});
        modalInstance.result.then(function () {
        }, function () {
            $scope.query();
        });
    };
    $scope.edit = function (role) {
        var modalInstance = mineUtil.modal("admin/_system/role/roleEdit.htm", "systemRoleEditController", role);
        modalInstance.result.then(function () {
        }, function () {
            $scope.query();
        });
    };
    $scope.detail = function (role) {
        var modalInstance = mineUtil.modal("admin/_system/role/roleDetail.htm", "systemRoleDetailController", role);
        modalInstance.result.then(function () {
        }, function () {
        });
    };
    $scope.privilege = function (role) {
        var modalInstance = mineUtil.modal("admin/_system/role/rolePrivilege.htm", "systemRolePrivilegeController", role);
        modalInstance.result.then(function () {
        }, function () {
        });
    };
    $scope.drop = function (role) {
        mineUtil.confirm("确认删除吗？", function () {
            mineHttp.send("DELETE", "admin/role/" + role.id, {}, function (data) {
                if (!verifyData(data)) {
                    return;
                }
                mineUtil.alert("删除成功");
                $scope.query();
            });
        });
    };
    $scope.query();
});
mainApp.controller("systemRoleAddController", function ($scope, $uibModalInstance, mineHttp) {
    $scope.ok = function () {
        mineHttp.send("POST", "admin/role", {data: $scope.role}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
            if ($scope.messageStatus) {
                $scope.role = null;
            }
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

mainApp.controller("systemRoleEditController", function ($scope, $uibModalInstance, mineHttp, data) {
    mineHttp.send("GET", "admin/role/" + data.id, {}, function (result) {
        if (!verifyData(result)) {
            $scope.messageStatus = false;
            $scope.message = result.message;
        }
        $scope.role = result.content;
    });
    $scope.ok = function () {
        mineHttp.send("PUT", "admin/role/" + data.id, {data: $scope.role}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
mainApp.controller("systemRoleDetailController", function ($scope, $uibModalInstance, mineHttp, data) {
    mineHttp.send("GET", "admin/role/" + data.id, {}, function (result) {
        $scope.message = result.message;
        $scope.role = result.content;
    });
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
mainApp.controller("systemRolePrivilegeController", function ($scope, $uibModalInstance, mineHttp, data, mineTree) {
    mineHttp.send("GET", "admin/role/" + data.id, {}, function (result) {
        $scope.message = result.message;
        $scope.role = result.content;
    });
    var menuTree = {};
    var permitTree = {};
    $scope.buildTree = function (callback) {
        mineHttp.send("GET", "admin/role/" + data.id + "/privilege", {}, function (data) {
            var options = {check: {enable: true}};
            menuTree = mineTree.build($("#menuTree"), data.content.roleMenus, options);
            permitTree = mineTree.build($("#functionTree"), data.content.rolePermits, options);
        });
    };
    $scope.buildTree();
    $scope.ok = function () {
        $scope.rolePrivilege = {};
        $scope.rolePrivilege.id = data.id;
        $scope.rolePrivilege.menuIds = new Array();
        $scope.rolePrivilege.permitCodes = new Array();
        var menuNodes = menuTree.getCheckedNodes(true);
        for (var index in menuNodes) {
            $scope.rolePrivilege.menuIds.push(menuNodes[index].id);
        }
        var permitNodes = permitTree.getCheckedNodes(true);
        for (var index in permitNodes) {
            $scope.rolePrivilege.permitCodes.push(permitNodes[index].id);
        }
        mineHttp.send("PUT", "admin/role/" + data.id + "/privilege", {data: $scope.rolePrivilege}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
/*****************用户设置*********************/
mainApp.controller("systemUserListCtl", function ($scope, $uibModal, mineHttp, mineGrid, mineUtil, mineTree) {
    mineHttp.constant("approveRole", function (data) {
        $scope.approveRoles = data.content;
    });
    mineHttp.constant("organs", function (data) {
        mineTree.dropDown($("#organDropdown"), data)
    });
    $scope.selectedFlag = false;
    mineGrid.gridPageInit("gridOptions", $scope, {
        data: 'myData',
        showSelectionCheckbox: true,
        multiSelect: true,
        selectWithCheckboxOnly: true,
        requestMethod: "POST",
        requestUrl: fullPath("admin/user/list"),
        columnDefs: [{field: 'account', displayName: '账号'},
            {field: 'name', displayName: '姓名'},
            {
                field: 'gender',
                displayName: '性别',
                cellTemplate: "<div class='mine-table-span'>{{row.entity.genderMessage}}</div>"
            },
            {
                field: 'approveRole',
                displayName: '审批身份',
                cellTemplate: "<div class='mine-table-span'>{{row.entity.approveRoleMessage}}</div>"
            },
            {field: 'email', displayName: '电子邮箱'},
            {field: 'mobile', displayName: '联系电话'},
            {field: 'organName', displayName: '所属部门', sortable: false},
            {field: 'status', displayName: '状态'},
            {field: 'createdAt', displayName: '创建时间'},
            {
                field: 'id',
                displayName: '操作',
                width: 200,
                sortable: false,
                cellTemplate: "<div><mine-action icon='fa fa-edit' action='edit(row.entity)' name='编辑'></mine-action>" +
                "<mine-action icon='fa fa-search' action='detail(row.entity)' name='查看'></mine-action>" +
                "<mine-action icon='fa fa-user-o' action='setRole(row.entity)' name='角色'></mine-action></div>"
            }

        ]
    });
    // init load datas
    $scope.gridPageQueryCallback = function (data) {
        return {data: data.content.rows, total: data.content.total};
    };
    $scope.gridPageSelectedItems = function (newValue, oldValue) {
        if (newValue != 0) {
            $scope.selectedFlag = true;
        } else {
            $scope.selectedFlag = false;
        }
    };
    $scope.query = function () {
        $scope.gridPageQuery({}, $scope.userQuery);
    };
    $scope.query();
    $scope.edit = function (user) {
        if (user == null) {
            user = {};
        }
        var modalInstance = mineUtil.modal("admin/_system/user/userEdit.htm", "systemUserEditController", user);
        modalInstance.result.then(function () {
        }, function () {
            $scope.query();
        });
    };
    $scope.detail = function (user) {
        var modalInstance = mineUtil.modal("admin/_system/user/userDetail.htm", "systemUserDetailController", user);
        modalInstance.result.then(function () {
        }, function () {
        });
    };
    $scope.setRole = function (user) {
        var modalInstance = mineUtil.modal("admin/_system/user/userRole.htm", "systemUserRoleController", user);
        modalInstance.result.then(function () {
        }, function () {
        });
    };
    $scope.enable = function (enabled) {
        var selectedItems = $scope.gridSelectedItems;
        if (selectedItems == null || selectedItems.length == 0) {
            return;
        }
        $scope.userSelect = {};
        $scope.userSelect.enabled = enabled;
        $scope.userSelect.userIds = new Array();
        for (var index in selectedItems) {
            $scope.userSelect.userIds.push(selectedItems[index].id);
        }
        mineHttp.send("PUT", "admin/user/list/status", {data: $scope.userSelect}, function (result) {
            if (verifyData(result)) {
                $scope.query();
            }
        });
    }
});

mainApp.controller("systemUserEditController", function ($scope, $uibModalInstance, mineHttp, mineTree, data) {
    $scope.user = {};
    mineHttp.constant("approveRole", function (data) {
        $scope.approveRoles = data.content;
    });
    mineHttp.constant("organs", function (data) {
        mineTree.dropDown($("#organDropdown"), data)
    });
    var roleTree;
    var roleTreeUrl = "admin/user/roleTree";
    if (typeof data.id != "number") {
        $scope.model = "new";
        roleTreeUrl = "admin/user/roleTree";
    } else {
        $scope.model = "edit";
        roleTreeUrl = "admin/user/roleTree/" + data.id;
        mineHttp.send("GET", "admin/user/" + data.id, {}, function (result) {
            if (!verifyData(result)) {
                $scope.messageStatus = false;
                $scope.message = result.message;
            }
            $scope.user = result.content;
        });
    }
    mineHttp.send("GET", roleTreeUrl, {}, function (result) {
        var options = {check: {enable: true}};
        roleTree = mineTree.build($("#roleTree"), result.content.roleTree, options);
    });

    $scope.ok = function () {
        var roleNodes = roleTree.getCheckedNodes(true);
        if(roleNodes.length == 0){
            $scope.messageStatus = false;
            $scope.message = "用户未指定角色";
            return;
        }

        $scope.user.roleIds = [];
        for (var index in roleNodes) {
            $scope.user.roleIds.push(roleNodes[index].id);
        }
        mineHttp.send("PUT", "admin/user", {data: $scope.user}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
            if ($scope.messageStatus) {
                $scope.model = "edit";
                $scope.user = result.content;
            }
        });
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
mainApp.controller("systemUserDetailController", function ($scope, $uibModalInstance, mineHttp, data) {
    mineHttp.send("GET", "admin/user/" + data.id, {}, function (result) {
        $scope.message = result.message;
        $scope.user = result.content;
    });
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
mainApp.controller("systemUserRoleController", function ($scope, $uibModalInstance, mineHttp, mineTree, data) {
    var roleTree = {};
    mineHttp.send("GET", "admin/user/roleTree/" + data.id, {}, function (result) {
        $scope.message = result.message;
        $scope.user = result.content.user;
        var options = {check: {enable: true}};
        roleTree = mineTree.build($("#roleTree"), result.content.roleTree, options);
    });
    $scope.ok = function () {
        $scope.userRole = {};
        $scope.userRole.id = data.id;
        $scope.userRole.roleIds = new Array();
        var roleNodes = roleTree.getCheckedNodes(true);
        for (var index in roleNodes) {
            $scope.userRole.roleIds.push(roleNodes[index].id);
        }
        mineHttp.send("PUT", "admin/user/" + data.id + "/role", {data: $scope.userRole}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
/*****************机构设置*********************/
mainApp.controller("systemDepartListCtl", function ($scope, $http, mineTree, mineHttp, mineUtil, mineMessage) {
    var departTree;
    $scope.ztreeSelected = function (event, treeId, treeNode) {
        mineHttp.send("GET", "admin/depart/" + treeNode.id, {}, function (data) {
            if (verifyData(data)) {
                $scope.depart = data.content;
            } else {
                $scope.depart = null;
            }
        });
    };
    $scope.buildTree = function (callback) {
        mineHttp.send("GET", "admin/depart/rootTree", {}, function (data) {
            var options = {
                callback: {
                    onClick: $scope.ztreeSelected
                }
            };
            departTree = mineTree.buildAsync($("#departTree"), "admin/depart/asyncTree", data, options);
            if (angular.isFunction(callback)) {
                callback();
            }
        });
    };

    $scope.edit = function () {
        var modalInstance = mineUtil.modal("admin/_system/depart/departEdit.htm", "systemDepartEditController", $scope.depart);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };

    $scope.drop = function () {
        mineUtil.confirm("确认删除吗？", function () {
            var nodes = departTree.getSelectedNodes();
            var parentNode = departTree.getNodeByTId(nodes[0].parentTId);
            mineHttp.send("DELETE", "admin/depart/" + $scope.depart.code, {}, function (data) {
                if (!verifyData(data)) {
                    $scope.messageStatus = false;
                    $scope.message = data.message;
                    return;
                }
                mineUtil.alert("删除成功");
                $scope.depart = null;
                departTree.reAsyncChildNodes(parentNode, "refresh", false);
            });
        });
    };
    $scope.add = function (oneLevelFlag) {
        var params = null;
        if (!oneLevelFlag) {
            params = $scope.depart;
        }
        var modalInstance = mineUtil.modal("admin/_system/depart/departAdd.htm", "systemDepartAddController", params);
        modalInstance.result.then(function (selectedItem) {
        }, function () {
        });
    };

    $scope.buildTree();
    mineMessage.subscribe("systemDepartTreeRefresh", function (event, make) {

        if (make.addFlag && make.oneLevelFlag) {
            $scope.buildTree();
        } else {
            var nodes = departTree.getSelectedNodes();
            var parentNode = departTree.getNodeByTId(nodes[0].parentTId);
            departTree.reAsyncChildNodes(parentNode, "refresh", false);
        }
    });
});
mainApp.controller("systemDepartAddController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {
    var oneLevelFlag = false;
    $scope.initPage = function () {
        $scope.depart = {};
        if (data == null) {
            $scope.title = "顶级机构";
            oneLevelFlag = true;
        } else {
            $scope.title = "下级机构";
            $scope.depart.parentCode = data.code;
            $scope.depart.parentName = data.name;
        }
    };
    $scope.initPage();
    $scope.ok = function () {
        mineHttp.send("POST", "admin/depart", {data: $scope.depart}, function (result) {
            $scope.messageStatus = verifyData(result);
            $scope.message = result.message;
            if ($scope.messageStatus) {
                mineMessage.publish("systemDepartTreeRefresh", {addFlag: true, oneLevelFlag: oneLevelFlag});
            }
            $scope.initPage();
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

mainApp.controller("systemDepartEditController", function ($scope, data, $uibModalInstance, mineHttp, mineMessage) {
    mineHttp.send("GET", "admin/depart/" + data.code, {}, function (data) {
        if (verifyData(data)) {
            $scope.depart = data.content;
        } else {
            $scope.depart = null;
        }
    });
    $scope.ok = function () {
        mineHttp.send("PUT", "admin/depart/" + $scope.depart.code, {data: $scope.depart}, function (data) {
            $scope.messageStatus = verifyData(data);
            $scope.message = data.message;
            $scope.depart = data.content;
            if ($scope.messageStatus) {
                mineMessage.publish("systemDepartTreeRefresh", {addFlag: false});
            }
        });
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});