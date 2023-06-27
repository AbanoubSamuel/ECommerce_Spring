package com.ecommerce.ecommerce.api.controller.auth;

import com.ecommerce.ecommerce.api.exception.UserExistsException;
import com.ecommerce.ecommerce.api.model.RegistrationBody;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.service.UserServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserServiceImpl userService;

    public AuthController(UserServiceImpl userService)
    {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegistrationBody registrationBody) throws UserExistsException
    {
        User user = userService.registerUser(registrationBody);
        System.out.println(user.getUsername());
    }
}
