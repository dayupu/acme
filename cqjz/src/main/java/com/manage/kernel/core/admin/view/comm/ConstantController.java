package com.manage.kernel.core.admin.view.comm;

import com.manage.base.act.ActSource;
import com.manage.base.database.enums.ApproveRole;
import com.manage.base.database.enums.NewsStatus;
import com.manage.base.database.enums.NewsType;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.supplier.page.SelectOption;
import com.manage.base.supplier.page.TreeNode;
import com.manage.kernel.jpa.entity.Department;
import com.manage.kernel.jpa.entity.News;
import com.manage.kernel.spring.comm.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/10/6.
 */
@RestController
@RequestMapping("/admin/constant")
public class ConstantController {

    @GetMapping("/newsType")
    public ResponseInfo newsType() {
        ResponseInfo response = new ResponseInfo();
        List<SelectOption> options = new ArrayList<>();
        SelectOption<Integer, String> option;
        for (NewsType type : NewsType.values()) {
            option = new SelectOption<>();
            option.setKey(type.getConstant());
            option.setValue(Messages.get(type));
            options.add(option);
        }
        response.wrapSuccess(options);
        return response;
    }

    @GetMapping("/newsStatus")
    public ResponseInfo newsStatus() {
        ResponseInfo response = new ResponseInfo();
        List<SelectOption> options = new ArrayList<>();
        SelectOption<Integer, String> option;
        for (NewsStatus type : NewsStatus.values()) {
            if (type == NewsStatus.DELETE) {
                continue;
            }
            option = new SelectOption<>();
            option.setKey(type.getConstant());
            option.setValue(Messages.get(type));
            options.add(option);
        }
        response.wrapSuccess(options);
        return response;
    }

    @GetMapping("/actType")
    public List<TreeNode> actType() {
        TreeNode treeNode;
        List<TreeNode> treeNodes = new ArrayList<>();
        for (ActSource source : ActSource.values()) {
            switch (source) {
            case NEWS:
                treeNode = new TreeNode();
                treeNode.setId(source.getKey());
                treeNode.setName(Messages.get(source.messageKey()));
                treeNodes.add(treeNode);
                for (NewsType type : NewsType.values()) {
                    treeNode = new TreeNode();
                    treeNode.setId(source.getKey() + "_" + type.getConstant());
                    treeNode.setName(Messages.get(type.messageKey()));
                    treeNode.setPid(source.getKey());
                    treeNodes.add(treeNode);
                }
                break;
            default:
                break;
            }
        }
        return treeNodes;
    }

    @GetMapping("/approveRole")
    public ResponseInfo approveRole() {
        ResponseInfo response = new ResponseInfo();
        List<SelectOption> options = new ArrayList<>();
        SelectOption<Integer, String> option;
        for (ApproveRole role : ApproveRole.values()) {
            option = new SelectOption<>();
            option.setKey(role.getConstant());
            option.setValue(Messages.get(role));
            options.add(option);
        }
        response.wrapSuccess(options);
        return response;
    }

}