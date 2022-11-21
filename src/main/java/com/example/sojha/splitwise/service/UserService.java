package com.example.sojha.splitwise.service;

import com.example.sojha.splitwise.model.entity.SplitwiseUser;
import com.example.sojha.splitwise.model.request.user.CreateUser;
import com.example.sojha.splitwise.model.response.user.UserInfo;
import com.example.sojha.splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createUser(CreateUser request) {
        SplitwiseUser user = new SplitwiseUser(request.getName(), request.getEmail());
        userRepository.save(user);
    }

    public Optional<SplitwiseUser> findById(Long id) {
        return userRepository.findById(id);
    }

    public UserInfo getUserInfoById(Long id) {
        Optional<SplitwiseUser> user = findById(id);

        if (user.isPresent()) {
            return new UserInfo(user.get());
        }

        return null;
    }
}
