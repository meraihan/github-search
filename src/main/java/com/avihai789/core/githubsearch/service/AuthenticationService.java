package com.avihai789.core.githubsearch.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AuthenticationService {

    @Value("${regular.user.name}")
    private String userName;
    @Value("${regular.password}")
    private String password;

    public Boolean loadUserByUsername(String nUsername, String nPassword) {
        Boolean isAuthenticated = Boolean.FALSE;
        if (userName.equals(nUsername) && password.equals(nPassword)){
            isAuthenticated = Boolean.TRUE;
        }
        return isAuthenticated;
    }

    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }
}
