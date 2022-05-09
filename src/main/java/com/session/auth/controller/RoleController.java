package com.session.auth.controller;


import com.session.auth.service.roles.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String index(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNumber) {

        model.addAttribute("roles", roleService.list());
        return "roles/list";
    }

}
