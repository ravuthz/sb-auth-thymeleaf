package com.session.auth.service.roles;

import com.session.auth.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    List<Role> list();
    Role add();
    Role delete();

}
