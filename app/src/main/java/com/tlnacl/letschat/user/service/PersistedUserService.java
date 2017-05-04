package com.tlnacl.letschat.user.service;

import com.tlnacl.letschat.user.database.UserDatabase;
import com.tlnacl.letschat.user.model.User;
import com.tlnacl.letschat.user.model.Users;

import io.reactivex.Observable;

public class PersistedUserService implements UserService {

    private final UserDatabase userDatabase;

    public PersistedUserService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public Observable<Users> getAllUsers() {
        return userDatabase.observeUsers();
    }

    @Override
    public Observable<User> getUser(String userId) {
        return userDatabase.observeUser(userId);
    }

}
