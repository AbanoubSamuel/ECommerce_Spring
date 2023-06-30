package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.api.exception.UserExistsException;
import com.ecommerce.ecommerce.api.model.LoginBody;
import com.ecommerce.ecommerce.api.model.RegistrationBody;
import com.ecommerce.ecommerce.entities.User;

public interface UserService {
    User register(RegistrationBody registrationBody) throws UserExistsException;

    public String login(LoginBody loginBody);
}
