package com.session.auth.service.permissions;

import com.session.auth.model.Permission;
import com.session.auth.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> list() {
        return (List<Permission>) permissionRepository.findAll();
    }

    @Override
    public Permission getById(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Permission permission) {
        permissionRepository.save(permission);
    }

}
