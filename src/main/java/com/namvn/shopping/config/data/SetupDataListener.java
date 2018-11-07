package com.namvn.shopping.config.data;

import com.namvn.shopping.persistence.entity.Privilege;
import com.namvn.shopping.persistence.entity.Role;
import com.namvn.shopping.persistence.entity.User;
import com.namvn.shopping.persistence.repository.PrivilegeDao;
import com.namvn.shopping.persistence.repository.RoleDao;
import com.namvn.shopping.persistence.repository.UserDao;
import com.namvn.shopping.service.PrivilegeService;
import com.namvn.shopping.service.RoleService;
import com.namvn.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Class used to set role, privile
 * Based on {@code org.springframework.context.ApplicationListener} interface
 *
 * @param "ContextRefreshedEvent" publish an event when starting and refreshing
 * @see "creating" role and privile
 */
@Component
public class SetupDataListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private boolean alreadySetup = false;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) {
            return;
        }
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege updatePrivilege = createPrivilegeIfNotFound("UPDATE_PRIVILEGE");
        final Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
        final List<Privilege> adminPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, writePrivilege, updatePrivilege, deletePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, passwordPrivilege));
        createRoleIfNotFound("ROLE_USER", userPrivileges);
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createUserIfNotFound("hahehiho95@gmail.com",  "10101995", new ArrayList<Role>(Arrays.asList(adminRole)));
//        if (readPrivilege != null && writePrivilege != null && updatePrivilege != null && deletePrivilege != null && passwordPrivilege != null) {
//
//
//            if (adminPrivileges != null)
//
//        }
//        if (readPrivilege != null && passwordPrivilege != null) {
//
//
//        } // == create initial roles
        // final List<Privilege> managerPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, updatePrivilege,passwordPrivilege));


        alreadySetup = true;
    }


    Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeService.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            try {
                privilegeService.add(privilege);
                return privilege;
            } catch (Exception e) {
                e.printStackTrace();
                return privilege;
            }
        }
        return privilege;
    }


    Role createRoleIfNotFound(final String name, final List<Privilege> privileges) {
        Role role = roleService.findByName(name);
        if (role == null) {
            role = new Role(name);
            try {
                role.setPrivileges(privileges);
                roleService.add(role);
                return role;
            } catch (RuntimeException e) {
                return role;
            }
        }
        return role;

    }

    User createUserIfNotFound(final String email, final String password, final Collection<Role> roles) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setEnabled(true);
            try {
                user.setRoles(roles);
                userService.saveRegisteredUser(user);
                return user;
            } catch (RuntimeException e) {
                return user;
            }
        }
        return user;
    }
}
