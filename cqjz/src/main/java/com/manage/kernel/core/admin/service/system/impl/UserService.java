package com.manage.kernel.core.admin.service.system.impl;

import com.manage.base.database.enums.ApproveRole;
import com.manage.base.exception.UserNotFoundException;
import com.manage.base.database.enums.Status;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.Pair;
import com.manage.base.exception.CoreException;
import com.manage.base.supplier.page.TreeNode;
import com.manage.base.supplier.msgs.MessageErrors;
import com.manage.base.utils.StringHandler;
import com.manage.kernel.core.model.dto.UserDto;
import com.manage.kernel.core.model.parser.UserParser;
import com.manage.kernel.core.admin.service.activiti.IActIdentityService;
import com.manage.kernel.core.admin.service.system.IUserService;
import com.manage.kernel.jpa.entity.AdOrganization;
import com.manage.kernel.jpa.entity.AdRole;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.jpa.repository.AdOrganRepo;
import com.manage.kernel.jpa.repository.AdRoleRepo;
import com.manage.kernel.jpa.repository.AdUserRepo;
import com.manage.kernel.spring.comm.SessionHelper;
import com.manage.kernel.spring.config.security.AuthPasswordEncoder;
import com.manage.base.supplier.page.PageQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    private AdUserRepo userRepo;

    @Autowired
    private AuthPasswordEncoder passwordEncoder;

    @Autowired
    private AdRoleRepo roleRepo;

    @Autowired
    private AdOrganRepo organRepo;

    @Autowired
    private IActIdentityService actIdentityService;

    @Autowired
    private AuthPasswordEncoder authPasswordEncoder;


    @Override
    @Transactional
    public UserDto getUser(Long userId) {
        AdUser user = userRepo.findOne(userId);
        if (user == null) {
            LOGGER.info("Not found the user {}", userId);
            throw new UserNotFoundException();
        }
        return UserParser.toDto(user);
    }

    @Override
    @Transactional
    public UserDto getUser(String account) {
        AdUser user = userRepo.findUserByAccount(account);
        if (user == null) {
            LOGGER.info("Not found the user {}", account);
            throw new UserNotFoundException();
        }
        return UserParser.toDto(user);
    }

    @Override
    @Transactional
    public PageResult<UserDto> getUserListByPage(PageQuery pageQuery, UserDto userQuery) {
        Page<AdUser> userPage = userRepo.findAll((Specification<AdUser>) (root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringHandler.isNotBlank(userQuery.getAccount())) {
                list.add(cb.equal(root.get("account"), userQuery.getAccount()));
            }
            if (StringHandler.isNotBlank(userQuery.getName())) {
                list.add(cb.like(root.get("name"), "%" + userQuery.getName() + "%"));
            }
            if (StringHandler.isNotBlank(userQuery.getMobile())) {
                list.add(cb.equal(root.get("mobile"), userQuery.getMobile()));
            }
            if (StringHandler.isNotNull(userQuery.getApproveRole())) {
                list.add(cb.equal(root.get("approveRole"), userQuery.getApproveRole()));
            }
            if (StringHandler.isNotNull(userQuery.getCreatedAt())) {
                list.add(cb.greaterThanOrEqualTo(root.get("createdAt"), userQuery.getCreatedAt()));
            }
            if (StringHandler.isNotNull(userQuery.getCreatedAtEnd())) {
                list.add(cb.lessThanOrEqualTo(root.get("createdAt"), userQuery.getCreatedAtEnd()));
            }
            if (StringHandler.isNotNull(userQuery.getOrganCode())) {
                list.add(cb.equal(root.get("organCode"), userQuery.getOrganCode()));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, pageQuery.sortPageDefault("id"));

        PageResult<UserDto> pageResult = new PageResult<UserDto>();
        pageResult.setTotal(userPage.getTotalElements());
        pageResult.setRows(UserParser.toDtoList(userPage.getContent()));
        return pageResult;
    }

    @Override
    @Transactional
    public void addUser(UserDto userDto) {
        AdUser query = userRepo.findUserByAccount(userDto.getAccount());
        if (query != null) {
            LOGGER.info("The user {} is exists", userDto.getAccount());
            throw new CoreException(MessageErrors.USER_IS_EXISTS);
        }
        AdUser user = new AdUser();
        user.setAccount(userDto.getAccount());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setMobile(userDto.getMobile());
        user.setTelephone(userDto.getTelephone());
        user.setEmail(userDto.getEmail());
        user.setStatus(Status.ENABLE);
        user.setApproveRole(userDto.getApproveRole() == null ? ApproveRole.EMPLOYEE : userDto.getApproveRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedUser(SessionHelper.user());
        setUserOrgan(user, userDto.getOrganCode());
        AdUser savedUser = userRepo.save(user);
        actIdentityService.saveActUser(savedUser);
    }

    @Override
    @Transactional
    public void modifyUser(UserDto userDto) {
        AdUser user = userRepo.findOne(userDto.getId());
        if (user == null) {
            LOGGER.info("Not found the user {}", userDto.getId());
            throw new UserNotFoundException();
        }
        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setMobile(userDto.getMobile());
        user.setTelephone(userDto.getTelephone());
        user.setEmail(userDto.getEmail());
        user.setApproveRole(userDto.getApproveRole() == null ? ApproveRole.EMPLOYEE : userDto.getApproveRole());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedUser(SessionHelper.user());
        setUserOrgan(user, userDto.getOrganCode());
        AdUser updatedUser = userRepo.save(user);
        actIdentityService.saveActUser(updatedUser);
        resetUserRole(userDto);
    }

    @Override
    @Transactional
    public Pair<AdUser, List<Long>> authUserDetail(String account) {

        AdUser user = userRepo.findUserByAccount(account);
        List<Long> roleIds = new ArrayList<Long>();
        for (AdRole role : user.getRoles()) {
            roleIds.add(role.getId());
        }

        return new Pair<>(user, roleIds);
    }

    @Override
    @Transactional
    public Pair<UserDto, List<TreeNode>> userRolePair(Long userId) {
        AdUser user = userRepo.findOne(userId);
        if (user == null) {
            LOGGER.info("Not found the user {}", userId);
            throw new UserNotFoundException();
        }

        List<AdRole> roles = roleRepo.queryListAll();
        List<TreeNode> treeNodes = new ArrayList<>();
        TreeNode treeNode;
        for (AdRole role : roles) {
            treeNode = new TreeNode();
            treeNode.setId(role.getId());
            treeNode.setName(role.getName());

            for (AdRole userRole : user.getRoles()) {
                if (userRole.getId().equals(role.getId())) {
                    treeNode.setChecked(true);
                }
            }
            treeNodes.add(treeNode);
        }
        return new Pair<>(UserParser.toDto(user), treeNodes);
    }

    @Override
    @Transactional
    public void resetUserRole(UserDto userDto) {
        AdUser user = userRepo.findOne(userDto.getId());
        if (user == null) {
            LOGGER.info("Not found the user {}", userDto.getId());
            throw new UserNotFoundException();
        }

        List<AdRole> userRoles = new ArrayList<>();
        if (!userDto.getRoleIds().isEmpty()) {
            userRoles = roleRepo.queryListByIds(userDto.getRoleIds());
        }
        user.setRoles(userRoles);
        userRepo.save(user);

    }

    @Override
    @Transactional
    public void modifyUserStatus(UserDto userDto) {

        List<AdUser> users = userRepo.findListByIds(userDto.getUserIds());
        for (AdUser user : users) {
            if (userDto.isEnabled()) {
                user.setStatus(Status.ENABLE);
            } else {
                user.setStatus(Status.DISABLE);
            }
            userRepo.save(user);
        }
    }

    private void setUserOrgan(AdUser user, String organCode) {
        if (organCode == null) {
            return;
        }
        AdOrganization organ = organRepo.findOne(organCode);
        if (organ == null) {
            return;
        }
        user.setOrgan(organ);
    }


    @Override
    @Transactional
    public UserDto editSessionUser(UserDto userDto) {
        AdUser user = userRepo.findUserByAccount(userDto.getAccount());
        if (user == null) {
            LOGGER.info("Not found the user {}", userDto.getId());
            throw new UserNotFoundException();
        }
        user.setGender(userDto.getGender());
        user.setMobile(userDto.getMobile());
        user.setTelephone(userDto.getTelephone());
        user.setEmail(userDto.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedUser(SessionHelper.user());
        AdUser updatedUser = userRepo.save(user);
        return UserParser.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void changePassword(UserDto userDto) {
        AdUser user = userRepo.findUserByAccount(userDto.getAccount());
        if (user == null) {
            LOGGER.info("Not found the user {}", userDto.getId());
            throw new UserNotFoundException();
        }

        if (!authPasswordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            LOGGER.info("Password error");
            throw new CoreException(MessageErrors.PASSWORD_ERROR);
        }

        String passwordNew = authPasswordEncoder.encode(userDto.getPasswordNew());
        user.setPassword(passwordNew);
        userRepo.save(user);
    }
}
