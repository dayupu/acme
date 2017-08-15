package com.manage.kernel.core.admin.parser;

import com.manage.kernel.core.admin.dto.RoleDto;
import com.manage.kernel.jpa.news.entity.Role;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-8-15.
 */
public class RoleParser {

    public static RoleDto toRoleDto(Role role) {
        return toRoleDto(role, new RoleDto());
    }

    public static List<RoleDto> toRoleDtoList(List<Role> roles) {
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role role : roles) {
            roleDtos.add(toRoleDto(role, new RoleDto()));
        }
        return roleDtos;
    }

    private static RoleDto toRoleDto(Role role, RoleDto roleDto) {
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setDescription(role.getDescription());
        roleDto.setCreatedAt(role.getCreatedAt());
        roleDto.setCreatedBy(role.getCreatedUserName());
        roleDto.setUpdatedAt(role.getUpdatedAt());
        roleDto.setUpdatedBy(role.getUpdatedUserName());
        return roleDto;
    }
}
