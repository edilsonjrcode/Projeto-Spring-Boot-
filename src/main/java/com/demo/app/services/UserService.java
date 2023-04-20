package com.demo.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.app.models.User;
import com.demo.app.repositories.TaskRepository;
import com.demo.app.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado: " + id + ", Tipo: " + User.class.getName()));
    }

    @Transactional
    public User create(User user) {
        user.setId(null);
        user = this.userRepository.save(user);
        this.taskRepository.saveAll(user.getTasks());
        return user;

    }

    @Transactional
    public User update(User user) {
        User newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());

        return this.userRepository.save(newUser);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relaciondas.");
        }

    }
}
