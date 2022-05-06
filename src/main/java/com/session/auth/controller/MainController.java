package com.session.auth.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collection;

@Controller
public class MainController {

    @RequestMapping("/")
    public String home(Principal principal) {
        return "home.html";
    }

    @RequestMapping("/user")
    public String user(Principal principal) {
        if (principal != null) {
            System.out.println(principal.getName());

            Collection<GrantedAuthority> authorities = ((UsernamePasswordAuthenticationToken) principal).getAuthorities();
            System.out.println(authorities);

//            UserDetails userDetails = (UserDetails) principal;
//            System.out.println(userDetails);
//            System.out.println(userDetails.getAuthorities());
        }
        return "home.html";
    }

    @RequestMapping("/admin")
    public String admin(Principal principal) {
        if (principal != null) {
            System.out.println(principal.getName());

            Collection<GrantedAuthority> authorities = ((UsernamePasswordAuthenticationToken) principal).getAuthorities();
            System.out.println(authorities);

//            UserDetails userDetails = (UserDetails) principal;
//            System.out.println(userDetails);
//            System.out.println(userDetails.getAuthorities());
        }
        return "home.html";
    }

    @RequestMapping("/login")
    public String login() {
        return "login.html";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        return "login.html";
    }

}
