package com.mgiglione.service.test.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.mgiglione.model.User;
import com.mgiglione.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest {
    
	@Autowired
    private UserService userService;
    
    @Test
    public void testGetMangasByTitle() {
    	User[] userByTitle = userService.getUserByName("Bret");
        assertThat(userByTitle).isNotNull().isNotEmpty();
    }

}
