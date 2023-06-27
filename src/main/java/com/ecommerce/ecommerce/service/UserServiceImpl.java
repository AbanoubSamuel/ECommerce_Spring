package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.api.exception.UserExistsException;
import com.ecommerce.ecommerce.api.model.LoginBody;
import com.ecommerce.ecommerce.api.model.RegistrationBody;
import com.ecommerce.ecommerce.dao.UserDAO;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.security.EncryptionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private EncryptionService encryptionService;
    private UserDAO userDAO;
    private JWTServiceImpl jwtService;

    public UserServiceImpl(EncryptionService encryptionService, UserDAO userDAO, JWTServiceImpl jwtService)
    {
        this.encryptionService = encryptionService;
        this.userDAO = userDAO;
        this.jwtService = jwtService;
    }

    public User register(RegistrationBody registrationBody) throws UserExistsException
    {
        if (userDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()
                && userDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()) {
            throw new UserExistsException();
        }
        User user = new User();
        user.setUsername(registrationBody.getUsername());
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        return userDAO.save(user);
    }

    @Override
    public String login(LoginBody loginBody)
    {
        Optional<User> optUser = userDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if (optUser.isPresent()) {
            User user = optUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }

        return null;
    }
}
