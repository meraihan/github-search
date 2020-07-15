package com.avihai789.core.githubsearch.controller;

import com.avihai789.core.githubsearch.model.Search;
import com.avihai789.core.githubsearch.model.SearchHistory;
import com.avihai789.core.githubsearch.service.AuthenticationService;
import com.avihai789.core.githubsearch.service.SearchService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/github")
public class SearchController {

    @Autowired
    SearchService searchService;
    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/search")
    public String searchPage(HttpSession session) {
        if(authenticationService.isLoggedIn(session)){
            return "search/search";
        }
        return "redirect:/";
    }

    @PostMapping("/search")
    public String findByUserName(@ModelAttribute("username") String userName, Model model, HttpSession session) {
        if (authenticationService.isLoggedIn(session)){
            Search gitUser = searchService.findByUserName(userName);
            model.addAttribute("gitUser", gitUser);
            if(gitUser.getLogin()==null){
                model.addAttribute("errorMessage", "NoData");
            }
            return "search/search";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/history")
    public String searchHistory(Model model, HttpSession session) {
        if(authenticationService.isLoggedIn(session)){
            List<SearchHistory> searchHistories = searchService.findPopularSearchHistory();
            model.addAttribute("searchHistories", searchHistories);
            return "search/history";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/delete")
    public String deleteAll(HttpSession session){
        if(authenticationService.isLoggedIn(session)){
            searchService.deleteAll();
            return "search/history";
        } else {
            return "redirect:/";
        }
    }

}
