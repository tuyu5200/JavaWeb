package com.tuyu.service.impl;

import com.tuyu.model.UserEntity;
import com.tuyu.service.IUserService;

import java.util.List;

/**
 * 方法用途：
 *${PACKAGE_NAME}
 * Created by Tuyu on 2016/7/10 18:34  18:38 .
 */
public class UserServiceImpl implements IUserService {

    @Override
    public boolean updateUser(UserEntity userEntity) {


    }

    @Override
    public boolean addUser(UserEntity userEntity) {
        return false;
    }

    @Override
    public List<UserEntity> queryUser(String acount) {
        return null;
    }

    @Override
    public boolean deleteUser(String account) {
        return false;
    }
}
