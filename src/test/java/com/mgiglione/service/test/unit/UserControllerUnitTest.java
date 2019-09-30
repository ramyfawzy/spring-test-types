package com.mgiglione.service.test.unit;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.mgiglione.controller.UserController;
import com.mgiglione.model.User;
import com.mgiglione.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerUnitTest {

    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    UserController userController;

    @MockBean
    UserService userService;
    
    private User[] users;

    @Before
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(this.userController).build();// Standalone context
        // mockMvc = MockMvcBuilders.webAppContextSetup(wac)
        // .build();
        User user1 = User.builder().id(1).name("Ramy").username("ribrahim").build();
        User user2 = User.builder().id(2).name("Karim").username("kfawzy").build();
        users = new User[3];
        users[0] = user1;
        users[1] = user2;
    }

    @Test
    public void testSearchSync() throws Exception {
        
        // Mocking service
        when(userService.getUserByName(any(String.class))).thenReturn(users);

        mockMvc.perform(get("/users/sync/ribrahim").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name", is("Ramy")))
            .andExpect(jsonPath("$[1].name", is("Karim")));
    }

    @Test
    public void testSearchASync() throws Exception {
       

        // Mocking service
        when(userService.getUserByName(any(String.class))).thenReturn(users);

        MvcResult result = mockMvc.perform(get("/users/async/ramy").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(request().asyncStarted())
            .andDo(print())
            // .andExpect(status().is2xxSuccessful()).andReturn();
            .andReturn();

        // result.getRequest().getAsyncContext().setTimeout(10000);

        mockMvc.perform(asyncDispatch(result))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name", is("Ramy")));

    }
}
