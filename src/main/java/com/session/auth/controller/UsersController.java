package com.session.auth.controller;

import com.session.auth.model.Role;
import com.session.auth.model.User;
import com.session.auth.model.UserAccount;
import com.session.auth.object.PageRequestObject;
import com.session.auth.service.cruds.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final static String FORM_REDIRECT = "redirect:/users";
    private final static String VIEW_SHOW = "users/show";
    private final static String VIEW_FORM = "users/form";
    private final static String VIEW_TABLE = "users/table";
    private final PasswordEncoder encoder;
    private final CrudService<User> userCrudService;
    private final CrudService<Role> roleCrudService;

    @Autowired
    public UsersController(
            PasswordEncoder encoder, CrudService<User> userCrudService, CrudService<Role> roleCrudService) {
        this.encoder = encoder;
        this.userCrudService = userCrudService;
        this.roleCrudService = roleCrudService;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        System.out.println(id);
        model.addAttribute("data", userCrudService.findOrFail(id));
        return VIEW_SHOW;
    }

    @GetMapping("/me")
    public String profile(Principal principal, Model model) {
        UserAccount userAccount = null;
        if (principal != null) {
            userAccount = (UserAccount) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        }
        model.addAttribute("data", userAccount);
        return VIEW_SHOW;
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("data", new User());
        model.addAttribute("roleList", roleCrudService.listAll());
        return VIEW_FORM;
    }

    @GetMapping("")
    public String list(Model model, @Valid PageRequestObject request) {
        model.addAttribute("data", userCrudService.listAll(request.toPageRequest()));
        return VIEW_TABLE;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("data", userCrudService.findOrFail(id));
        model.addAttribute("roleList", roleCrudService.listAll());
        return VIEW_FORM;
    }

    @PostMapping("/new")
    public String store(@Valid @ModelAttribute("data") User data, BindingResult result, Model model) {
        data.setId(null);

        if (StringUtils.isEmpty(data.getPassword())) {
            result.rejectValue("password", null, "must not be empty");
        } else {
            if (data.getPassword().equals(data.getConfirmPassword())) {
                data.setPassword(encoder.encode(data.getPassword()));
            } else {
                result.rejectValue("confirmPassword", null, "must be match");
            }
        }
        return formSave(data, result, model);
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") long id,
                         @Valid @ModelAttribute("data") User data, BindingResult result, Model model) {
        data.setId(id);
        if (!StringUtils.isEmpty(data.getPassword())) {
            if (data.getPassword().equals(data.getConfirmPassword())) {
                data.setPassword(encoder.encode(data.getPassword()));
            } else {
                result.rejectValue("confirmPassword", null, "must be match");
            }
        }
        return formSave(data, result, model);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userCrudService.delete(id);
        return FORM_REDIRECT;
    }

    private String formSave(User data, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("data", data);
            return VIEW_FORM;
        }
        userCrudService.save(data);
        return FORM_REDIRECT;
    }

}
