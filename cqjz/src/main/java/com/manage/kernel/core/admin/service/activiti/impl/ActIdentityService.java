package com.manage.kernel.core.admin.service.activiti.impl;

import com.manage.base.database.enums.ApproveRole;
import com.manage.base.utils.CoreUtil;
import com.manage.kernel.core.admin.service.activiti.IActIdentityService;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.spring.comm.Messages;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by bert on 2017/10/6.
 */
@Service
public class ActIdentityService implements IActIdentityService {

    @Autowired
    private IdentityService identityService;

    @Override
    @Transactional
    public void saveActUser(AdUser user) {

        String userId = user.getAccount();
        String groupId = actGroup(user.getApproveRole(), user.getOrganCode());
        User actUser = identityService.createUserQuery().userId(userId).singleResult();
        if (actUser == null) {
            actUser = identityService.newUser(user.getAccount());
            actUser.setEmail(user.getEmail());
            actUser.setFirstName(user.getName());
            identityService.saveUser(actUser);
            identityService.createMembership(userId, groupId);
            return;
        }

        List<Group> groups = identityService.createGroupQuery().groupMember(userId).list();
        boolean existFlag = false;
        for (Group group : groups) {
            if (group.getId().equals(groupId)) {
                existFlag = true;
                continue;
            }
            identityService.deleteMembership(userId, group.getId());
        }
        if (!existFlag) {
            identityService.createMembership(userId, groupId);
        }
    }



    private String actGroup(ApproveRole approveRole, String organCode) {
        String actGroupId = CoreUtil.actGroupId(approveRole, organCode);
        Group group = identityService.createGroupQuery().groupId(actGroupId).singleResult();
        if (group == null) {
            group = identityService.newGroup(actGroupId);
            group.setName(Messages.get(approveRole.messageKey()));
            group.setType(organCode);
            identityService.saveGroup(group);
        }
        return actGroupId;
    }
}
