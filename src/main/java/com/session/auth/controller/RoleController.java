package com.session.auth.controller;


import com.session.auth.model.Permission;
import com.session.auth.model.Role;
import com.session.auth.object.PageRequestObject;
import com.session.auth.service.cruds.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("roles")
public class RoleController {
    private final CrudService<Role> roleCrudService;
    private final CrudService<Permission> permissionCrudService;

    @Autowired
    public RoleController(CrudService<Role> roleCrudService, CrudService<Permission> permissionCrudService) {
        this.roleCrudService = roleCrudService;
        this.permissionCrudService = permissionCrudService;
    }

    @GetMapping
    public String index(Model model, @Valid PageRequestObject request) {
        model.addAttribute("roles", roleCrudService.listAll(request.toPageRequest()));
        return "roles/list";
    }

    @GetMapping("{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        model.addAttribute("permissions", permissionCrudService.listAll());
        model.addAttribute("role", roleCrudService.findOrFail(id));
        return "roles/form";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("permissions", permissionCrudService.listAll());
        model.addAttribute("role", new Role());
        return "roles/form";
    }

    @PostMapping
    public String store(@ModelAttribute("role") Role role) {
        roleCrudService.save(role);
        return "redirect:";
    }
}
