package org.project.framework.services;

import java.util.Optional;
import java.util.Set;

import org.project.easyf1.models.dto.UserDTO;
import org.project.easyf1.models.entity.Driver;
import org.project.easyf1.models.entity.Team;
import org.project.easyf1.models.entity.User;
import org.project.framework.repositories.DriverRepository;
import org.project.framework.repositories.TeamRepository;
import org.project.framework.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public UserService(UserRepository userRepository, DriverRepository driverRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public UserDTO getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com ID '" + userId + "' não encontrado"));

        UserDTO userDTO = new UserDTO(user);
        return userDTO;
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário com username '" + username + "' não encontrado");
        }

        return user;
    }

    public void addFavoriteDriver(String username, Integer driverNumber) {
        try {
            User user = loadUserByUsername(username);
            Driver driver = driverRepository.findFirstByDriverNumber(driverNumber);

            // Alguns pilotos podem vir com atributos nulos
            // Essa parte busca por uma instância mais bem apresentável do piloto (com pelo menos o nome não nulo)
            if (driver.getFirstName() == null) {
                Optional<Driver> bestDriver = driverRepository.findByDriverNumber(driverNumber).stream()
                    .filter(d -> d.getFirstName() != null)
                    .findFirst();
                
                driver = bestDriver.orElse(driver);
            }

            user.getFavoriteDrivers().add(driver);
            userRepository.save(user);
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar o motorista favorito: " + e.getMessage(), e);
        }
    }

    public void removeFavoriteDriver(String username, Integer driverNumber) {
        try {
            User user = loadUserByUsername(username);
            Set<Driver> favoriteDrivers = user.getFavoriteDrivers();

            for (Driver d : favoriteDrivers) {
                if (d.getDriverNumber() == driverNumber) {
                    user.getFavoriteDrivers().remove(d);
                    userRepository.save(user);

                    return;
                }
            }            
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover o motorista favorito: " + e.getMessage(), e);
        }
    }

    public void addFavoriteTeam(String username, Long teamId) {
        try {
            User user = loadUserByUsername(username);
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada com ID: " + teamId));

            user.getFavoriteTeams().add(team);
            userRepository.save(user);

        } catch (UsernameNotFoundException e) {
            throw e; 
        } catch (EntityNotFoundException e) {
            throw e; 
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar a equipe favorita: " + e.getMessage(), e);
        }
    }

    public void removeFavoriteTeam(String username, Long teamId) {
        try {
            User user = loadUserByUsername(username);
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada com ID: " + teamId));

            boolean removed = user.getFavoriteTeams().remove(team);

            if (removed) {
                userRepository.save(user);
            } else {
                throw new IllegalStateException("A equipe não estava entre os favoritos do usuário.");
            }

        } catch (UsernameNotFoundException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover a equipe favorita: " + e.getMessage(), e);
        }
    }
}
