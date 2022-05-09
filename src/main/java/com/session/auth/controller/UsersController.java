package com.session.auth.controller;

import com.session.auth.model.User;
import com.session.auth.model.UserAccount;
import com.session.auth.object.PageRequestObject;
import com.session.auth.repository.UserRepository;
import com.session.auth.service.cruds.CrudService;
import com.session.auth.service.cruds.CrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final static String FORM_REDIRECT = "redirect:/users";
    private final static String VIEW_SHOW = "users/show";
    private final static String VIEW_FORM = "users/form";
    private final static String VIEW_TABLE = "users/table";
    private CrudService<User> crudService;

    @Autowired
    public UsersController(UserRepository userRepository) {
        crudService = new CrudServiceImpl<>();
        crudService.setRepository(userRepository);
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        System.out.println(id);
        model.addAttribute("data", crudService.findOrFail(id));
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
        return VIEW_FORM;
    }

    @GetMapping("")
    public String list(Model model, @Valid PageRequestObject request) {
        model.addAttribute("data", crudService.listAll(request.toPageRequest()));
        return VIEW_TABLE;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("data", crudService.findOrFail(id));
        return VIEW_FORM;
    }

    @PostMapping("/new")
    public String store(@Valid @ModelAttribute("data") User data, BindingResult result, Model model) {
        data.setId(null);
        return formSave(data, result, model);
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") long id,
                         @Valid @ModelAttribute("data") User data, BindingResult result, Model model) {
        data.setId(id);
        return formSave(data, result, model);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        crudService.delete(id);
        return FORM_REDIRECT;
    }

    private String formSave(User data, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("data", data);
            return VIEW_FORM;
        }
        crudService.save(data);
        return FORM_REDIRECT;
    }

}
