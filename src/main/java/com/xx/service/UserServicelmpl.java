package com.xx.service;

import com.xx.dao.UserDao;
import com.xx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServicelmpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(User user) {
        User login = userDao.login(user.getUsername());
        if (login!=null){
            if (login.getPassword().equals(user.getPassword())){
                return login;
            }else {
                throw new RuntimeException("密码不正确!");
            }
        }else {
            throw new RuntimeException("账号不正确!");
        }
    }
}
