package com.session.auth.service.permissions;

import com.session.auth.model.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionService {

    List<Permission> list();

    Permission add();

    Permission delete();

}
