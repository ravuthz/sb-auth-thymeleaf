package com.session.auth.controller;

import com.session.auth.model.Permission;
import com.session.auth.object.PageRequestObject;
import com.session.auth.service.cruds.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("permissions")
public class PermissionController {
    private final CrudService<Permission> permissionCrudService;

    @Autowired
    public PermissionController(CrudService<Permission> permissionCrudService) {
        this.permissionCrudService = permissionCrudService;
    }

    @GetMapping
    public String index(Model model, @Valid PageRequestObject request) {
        model.addAttribute("permissions", permissionCrudService.listAll(request.toPageRequest()));
        return "permissions/list";
    }

    @GetMapping("{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        model.addAttribute("permission", permissionCrudService.findOrFail(id));
        return "permissions/form";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("permission", new Permission());
        return "permissions/form";
    }

    @PostMapping
    public String store(@ModelAttribute("role") Permission permission) {
        permissionCrudService.save(permission);
        return "redirect:";
    }
}
