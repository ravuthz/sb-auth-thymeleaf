package com.session.auth.controller;

import com.session.auth.model.Permission;
import com.session.auth.service.permissions.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public String index(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNumber) {

        model.addAttribute("permissions", permissionService.list());
        return "permissions/list";
    }

    @GetMapping("{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        model.addAttribute("permission", permissionService.getById(id));
        return "permissions/form";
    }

    @GetMapping("create")
    public String create(Model model, @ModelAttribute("role") Permission permission) {

        model.addAttribute("permission", new Permission());
        return "permissions/form";
    }

    @PostMapping
    public String store(@ModelAttribute("role") Permission permission) {
        permissionService.save(permission);
        return "redirect:";
    }
}
