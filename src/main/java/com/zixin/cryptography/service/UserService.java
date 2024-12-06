package com.zixin.cryptography.service;

import com.zixin.cryptography.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findByUserName(String userName);

    void register(String userName, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePassword(String newPwd);

    User findByEmail(String email);

    void updatePasswordByEmail(String email, String newPwd);

    List<User> list();
}
