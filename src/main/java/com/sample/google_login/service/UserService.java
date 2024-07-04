package com.sample.google_login.service;

import model.EmailModel;
import com.sample.google_login.dto.UserDto;
import model.UserModel;

public interface UserService {

    String createUser(UserModel userModel);

    String createGoogleUser(String idTokenString);

    UserDto fetchUserProfile(String userId);

    String checkEmailAndPassword(EmailModel emailModel);

    String setClaims(UserModel userModel, String userId);

}
