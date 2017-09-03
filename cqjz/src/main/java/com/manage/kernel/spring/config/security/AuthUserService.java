package com.manage.kernel.spring.config.security;

import com.manage.base.supplier.Pair;
import com.manage.kernel.core.admin.service.system.IUserService;
import com.manage.kernel.jpa.news.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.List;

public class AuthUserService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Pair<User, List<Long>> userPair = userService.authUserDetail(username);
        User user = userPair.getLeft();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        boolean enabled = false;
        if (user.getStatus().isEnabled()) {
            enabled = true;
        }
        Authority authority = new Authority("ROLE_ADMIN");
        AuthUser userDetails = new AuthUser(user.getId(), user.getAccount(), user.getPassword(), enabled, authority);
        userDetails.setRoleIds(userPair.getRight());
        return userDetails;
    }
}
