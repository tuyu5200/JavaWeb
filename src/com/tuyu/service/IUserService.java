package com.tuyu.service;

import com.tuyu.model.UserEntity;

import java.util.List;

/**
 * Created by Tuyu on 2016/7/10 18:32 .
 */
public interface IUserService {
    //add user
    boolean addUser(UserEntity userEntity);

    //查询用户
    List<UserEntity> queryUser(String acount);

    //删除用户
    boolean deleteUser(String account);

    //修改用户
    boolean updateUser(UserEntity userEntity);
}
