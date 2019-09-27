package com.mgiglione.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mgiglione.model.User;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String MANGA_SEARCH_URL="http://jsonplaceholder.typicode.com/users/";
    
    RestTemplate restTemplate;
    
    public UserService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public User[] getUserByTitle(String id) {
        return restTemplate.getForEntity(MANGA_SEARCH_URL, User[].class).getBody();
    }
    
    public User[] getUserByName(String name) {
        return restTemplate.getForEntity(MANGA_SEARCH_URL.concat("?username=".concat(name)), User[].class).getBody();
    }

}
