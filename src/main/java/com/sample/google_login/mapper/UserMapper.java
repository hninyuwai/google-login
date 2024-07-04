package com.sample.google_login.mapper;

import com.sample.google_login.dto.UserDto;
import com.sample.google_login.entity.User;
import model.UserModel;

public class UserMapper {

    public static UserDto entityToUserDto(User user, UserDto userDto) {
        userDto.setId(user.getId());
        userDto.setUserId(user.getUserId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUserName(user.getUserName());
        userDto.setToken(user.getToken());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static UserModel dtoToUserModel(UserDto userDto, UserModel userModel) {
        userModel.setFirstName(userDto.getFirstName());
        userModel.setLastName(userDto.getLastName());
        userModel.setUserName(userDto.getUserName());
        userModel.setEmail(userDto.getEmail());
        return userModel;
    }

    public static UserDto modelToUserDto(UserModel userModel, UserDto userDto) {
        userDto.setUserName(userModel.getUserName());
        userDto.setFirstName(userModel.getFirstName());
        userDto.setLastName(userModel.getLastName());
        userDto.setEmail(userModel.getEmail());
        return userDto;
    }

    public static User modelToUserEntity(UserModel userModel, User user) {
        user.setUserName(userModel.getUserName());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPassword(userModel.getPassword());
        return user;
    }

}
