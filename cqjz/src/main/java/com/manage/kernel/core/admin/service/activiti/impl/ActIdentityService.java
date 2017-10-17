package com.manage.kernel.core.admin.service.activiti.impl;

import com.manage.base.database.enums.ApproveRole;
import com.manage.kernel.core.admin.service.activiti.IActIdentityService;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.spring.comm.Messages;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by bert on 2017/10/6.
 */
@Service
public class ActIdentityService implements IActIdentityService{


    @Autowired
    private IdentityService identityService;

    @Override
    @Transactional
    public void saveActUser(AdUser user) {

        String userId = user.getAccount();
        List<User> users = identityService.createUserQuery().userId(userId).list();
        if (CollectionUtils.isEmpty(users)) {
            User actUser = identityService.newUser(user.getAccount());
            actUser.setEmail(user.getEmail());
            actUser.setFirstName(user.getName());
            identityService.saveUser(actUser);
            identityService.createMembership(userId, user.getApproveRole().actGroupId());
            return;
        }

        List<Group> groups = identityService.createGroupQuery().groupMember(userId).list();
        boolean existFlag = false;
        for (Group group : groups) {
            if (group.getId().equals(user.getApproveRole().actGroupId())) {
                existFlag = true;
                continue;
            }
            identityService.deleteMembership(userId, group.getId());
        }
        if (!existFlag) {
            identityService.createMembership(userId, user.getApproveRole().actGroupId());
        }
    }

    @Override
    @Transactional
    public void initActGroup() {
        List<Group> groups = identityService.createGroupQuery().list();
        for (ApproveRole role : ApproveRole.values()) {
            if (isExistApproveRole(role.actGroupId(), groups)) {
                continue;
            }
            Group group = identityService.newGroup(role.actGroupId());
            group.setName(Messages.get(role.messageKey()));
            group.setType("DEFAULT");
            identityService.saveGroup(group);
        }
    }

    private boolean isExistApproveRole(String groupId, List<Group> groups) {
        for (Group group : groups) {
            if (groupId.equals(group.getId())) {
                return true;
            }
        }
        return false;
    }

}
