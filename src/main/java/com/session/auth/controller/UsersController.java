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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final static String FORM_REDIRECT = "/users"; // ""redirect:/users";
    private final static String VIEW_SHOW = "users/show";
    private final static String VIEW_FORM = "users/form";
    private final static String VIEW_TABLE = "users/table";
    private final PasswordEncoder encoder;
    private final CrudService<User> userCrudService;
    private final CrudService<Role> roleCrudService;

    // TODO: Create all delete views and actions for user, role, permission
    // TODO: Create share function for check and manage permission. Ex: hasPermission('A', 'B')
    // TODO: Check CRUD view permission using share function ( Ex. hasPermission('CREATE_USER') )

    @Autowired
    public UsersController(
            PasswordEncoder encoder, CrudService<User> userCrudService, CrudService<Role> roleCrudService) {
        this.encoder = encoder;
        this.userCrudService = userCrudService;
        this.roleCrudService = roleCrudService;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
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
        return renderUserView(model, new User());
    }

    @GetMapping("")
    public String list(Model model, @Valid PageRequestObject request) {
        model.addAttribute("data", userCrudService.listAll(request.toPageRequest()));
        return VIEW_TABLE;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        return renderUserView(model, userCrudService.findOrFail(id));
    }

    @PostMapping("/new")
    public String store(@Valid @ModelAttribute("data") User data, BindingResult result, Model model) {
        data.setId(null);
        if (StringUtils.isEmpty(data.getPassword())) {
            result.rejectValue("password", "", "must not be empty");
        }
        return formSave(data, result, model);
    }

    @PatchMapping("/{id}/edit")
    public String update(@PathVariable("id") long id,
                         @Valid @ModelAttribute("data") User data, BindingResult result, Model model) {
        data.setId(id);
        return formSave(data, result, model);
    }

    @DeleteMapping("/{id}")
    public RedirectView delete(@PathVariable("id") long id, RedirectAttributes redirect) {
        userCrudService.delete(id);
        redirect.addFlashAttribute("infoMessage", "Deleted user successfully.");
        return new RedirectView(FORM_REDIRECT);
    }

    private String renderUserView(Model model, User data) {
        model.addAttribute("data", data);
        model.addAttribute("roleList", roleCrudService.listAll());
        return VIEW_FORM;
    }

    private String formSave(User data, BindingResult result, Model model) {
        if (!StringUtils.isEmpty(data.getPassword())) {
            if (data.getPassword().equals(data.getConfirmPassword())) {
                data.setPassword(encoder.encode(data.getPassword()));
            } else {
                result.rejectValue("confirmPassword", "", "must be match");
            }
        }
        if (result.hasErrors()) {
            return renderUserView(model, data);
        }
        userCrudService.save(data);
        return "redirect:" + FORM_REDIRECT;
    }

}
