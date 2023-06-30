package com.ecommerce.ecommerce.api.controller.auth;

import com.ecommerce.ecommerce.api.exception.UserExistsException;
import com.ecommerce.ecommerce.api.model.JsonResponse;
import com.ecommerce.ecommerce.api.model.LoginBody;
import com.ecommerce.ecommerce.api.model.RegistrationBody;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserServiceImpl userService;

    public AuthController(UserServiceImpl userService)
    {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<JsonResponse<User>> register(@Validated @RequestBody RegistrationBody registrationBody) throws UserExistsException
    {
        User user = userService.register(registrationBody);
        JsonResponse<User> jsonResponse = new JsonResponse<>();
        jsonResponse.setStatus(true);
        jsonResponse.setMessage("Registered succssfully");
        jsonResponse.setData(user);

        return new ResponseEntity<>(jsonResponse, HttpStatusCode.valueOf(201));

    }

    @PostMapping("/login")
    public ResponseEntity<JsonResponse<String>> login(@Validated @RequestBody LoginBody loginBody)
    {
        JsonResponse<String> jsonResponse = new JsonResponse<>();
        String jwtToken = userService.login(loginBody);
        if (jwtToken == null) {

            jsonResponse.setStatus(false);
            jsonResponse.setMessage("Failed to login");
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        } else {
            jsonResponse.setStatus(true);
            jsonResponse.setMessage("Logged-in successfully");
            jsonResponse.setData(jwtToken);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<JsonResponse<User>> getLoggedInUserProfile(@AuthenticationPrincipal User user)
    {
        JsonResponse<User> jsonResponse = new JsonResponse<>();
        jsonResponse.setStatus(true);
        jsonResponse.setMessage("User data fetched successfully");
        jsonResponse.setData(user);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
}
