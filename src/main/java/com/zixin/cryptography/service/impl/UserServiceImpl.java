package com.zixin.cryptography.service.impl;

import com.zixin.cryptography.mapper.UserMapper;
import com.zixin.cryptography.pojo.User;
import com.zixin.cryptography.service.UserService;
import com.zixin.cryptography.utils.Md5Util;
import com.zixin.cryptography.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String userName) {
        User u =userMapper.findByUserName(userName);
        return u;
    }

    @Override
    public void register(String userName, String password) {
        //encryption
        String md5String = Md5Util.getMD5String(password);
        //add
        userMapper.add(userName,md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePassword(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePassword(Md5Util.getMD5String(newPwd),id);
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public void updatePasswordByEmail(String email, String newPwd) {
        userMapper.updatePasswordByEmail(newPwd, email);
    }
}
