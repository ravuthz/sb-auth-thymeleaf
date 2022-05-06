package com.session.auth.repository;

import com.session.auth.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends FindByNameRepo<Role, Long> {
//
}
