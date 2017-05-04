package com.tlnacl.letschat.user.service;

import com.tlnacl.letschat.user.model.User;
import com.tlnacl.letschat.user.model.Users;

import io.reactivex.Observable;

public interface UserService {

    Observable<Users> getAllUsers();

    Observable<User> getUser(String userId);

}
