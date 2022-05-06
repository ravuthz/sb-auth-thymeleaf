package com.session.auth.repository;

import com.session.auth.model.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends FindByNameRepo<Permission, Long> {
//
}
