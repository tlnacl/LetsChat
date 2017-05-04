package com.tlnacl.letschat.user.database;


import com.tlnacl.letschat.user.model.User;
import com.tlnacl.letschat.user.model.Users;

import io.reactivex.Observable;

public interface UserDatabase {

    Observable<Users> observeUsers();

    Observable<User> readUserFrom(String userId);

    void writeCurrentUser(User user);

    Observable<User> observeUser(String userId);

}
