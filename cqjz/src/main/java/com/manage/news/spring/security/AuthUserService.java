package com.manage.news.spring.security;

import com.manage.base.bean.Pair;
import com.manage.base.enums.Status;
import com.manage.news.core.admin.service.IUserService;
import com.manage.news.jpa.kernel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class AuthUserService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Pair<User, List<Long>> userPair = userService.authUserDetail(username);
        User user = userPair.getLeft();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        boolean enabled = false;
        if (user.getStatus() == Status.INIT) {
            enabled = true;
        }
        Authority authority = new Authority("ROLE_ADMIN");
        AuthUser userDetails = new AuthUser(user.getId(), user.getAccount(), user.getPassword(), enabled, authority);
        userDetails.setRoleIds(userPair.getRight());
        return userDetails;
    }
}
