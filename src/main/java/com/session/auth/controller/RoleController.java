package com.session.auth.controller;


import com.session.auth.model.Role;
import com.session.auth.service.permissions.PermissionService;
import com.session.auth.service.roles.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public String index(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNumber) {

        model.addAttribute("roles", roleService.list());
        return "roles/list";
    }

    @GetMapping("{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        model.addAttribute("permissions", permissionService.list());
        model.addAttribute("role", roleService.getById(id));
        return "roles/form";
    }

    @GetMapping("create")
    public String create(Model model, @ModelAttribute("role") Role role) {
        model.addAttribute("permissions", permissionService.list());
        model.addAttribute("role", new Role());
        return "roles/form";
    }

    @PostMapping
    public String store(@ModelAttribute("role") Role role) {
        roleService.save(role);
        return "redirect:";
    }
}
