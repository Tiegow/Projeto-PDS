package org.project.easyf1.services;

import org.project.easyf1.models.entity.User;
import org.project.easyf1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário com username '" + username + "' não encontrado");
        }

        return user;
    }


}
