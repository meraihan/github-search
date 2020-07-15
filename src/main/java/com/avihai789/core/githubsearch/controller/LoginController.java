package com.avihai789.core.githubsearch.controller;

import com.avihai789.core.githubsearch.model.dto.User;
import com.avihai789.core.githubsearch.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/")
    public String dashboard(HttpSession session) {
        if(!authenticationService.isLoggedIn(session)){
            return "login/login";
        }
        return "dashboard/dashboard";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("user") User user, Model model, HttpSession session) {
        if (user != null && authenticationService.loadUserByUsername(user.getUsername(), user.getPassword())) {
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Username or Password is invalid!");
            return "login/login";
        }
    }

    @GetMapping("/logout")
    public String postLogout(HttpSession session) {
        session.setAttribute("user", null);
        return "redirect:/";
    }

    @GetMapping("/404")
    public String error() {
        return "error/404";
    }


}
