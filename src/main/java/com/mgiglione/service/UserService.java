package com.mgiglione.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mgiglione.model.User;
import com.mgiglione.model.UserResult;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String MANGA_SEARCH_URL="https://jsonplaceholder.typicode.com/users/";

    @Autowired
    RestTemplate restTemplate;
    
    
    public User[] getUserByTitle(String id) {
        return restTemplate.getForEntity(MANGA_SEARCH_URL, User[].class).getBody();
    }
    
    public User[] getUserByName(String name) {
        return restTemplate.getForEntity(MANGA_SEARCH_URL.concat("?username=".concat(name)), User[].class).getBody();
    }

}
