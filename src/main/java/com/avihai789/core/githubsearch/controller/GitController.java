package com.avihai789.core.githubsearch.controller;

import com.avihai789.core.githubsearch.model.Search;
import com.avihai789.core.githubsearch.service.AuthenticationService;
import com.avihai789.core.githubsearch.service.GitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/user")
public class GitController {

    @Autowired
    GitService gitService;
    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/search")
    public String searchPage(HttpSession session) {
        if(authenticationService.isLoggedIn(session)){
            return "user/search";
        }
        return "redirect:/";
    }

    @PostMapping("/search")
    public String findByUserName(@ModelAttribute("username") String userName, Model model, HttpSession session) {
        if (authenticationService.isLoggedIn(session)){
            Search gitUser = gitService.findByUserName(userName);
            log.info("Git User: "+ gitUser);
            model.addAttribute("gitUser", gitUser);
            return "user/search";
        } else {
            return "redirect:/";
        }
    }

}
