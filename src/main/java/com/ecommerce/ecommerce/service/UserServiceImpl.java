package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.api.exception.UserExistsException;
import com.ecommerce.ecommerce.api.model.RegistrationBody;
import com.ecommerce.ecommerce.dao.UserDAO;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.security.EncryptionService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private EncryptionService encryptionService;
    private UserDAO userDAO;

    public UserServiceImpl(EncryptionService encryptionService, UserDAO userDAO)
    {
        this.encryptionService = encryptionService;
        this.userDAO = userDAO;
    }

    public User registerUser(RegistrationBody registrationBody) throws UserExistsException
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
        user.setPassword(this.encryptionService.encryptPassword(registrationBody.getPassword()));
        return userDAO.save(user);
    }
}
