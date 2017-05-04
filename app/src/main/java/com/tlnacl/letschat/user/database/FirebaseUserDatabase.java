package com.tlnacl.letschat.user.database;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tlnacl.letschat.rx.FirebaseObservableListeners;
import com.tlnacl.letschat.user.model.User;
import com.tlnacl.letschat.user.model.Users;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class FirebaseUserDatabase implements UserDatabase {

    private final DatabaseReference usersDB;
    private final FirebaseObservableListeners firebaseObservableListeners;

    public FirebaseUserDatabase(FirebaseDatabase firebaseDatabase, FirebaseObservableListeners firebaseObservableListeners) {
        usersDB = firebaseDatabase.getReference("users");
        this.firebaseObservableListeners = firebaseObservableListeners;
    }

    @Override
    public Observable<Users> observeUsers() {
        return firebaseObservableListeners.listenToValueEvents(usersDB, toUsers());
    }

    @Override
    public Observable<User> readUserFrom(String userId) {
        return firebaseObservableListeners.listenToSingleValueEvents(usersDB.child(userId), as(User.class));
    }

    @Override
    public Observable<User> observeUser(String userId) {
        return firebaseObservableListeners.listenToValueEvents(usersDB.child(userId), as(User.class));
    }

    @Override
    public void writeCurrentUser(User user) {
        usersDB.child(user.getId()).setValue(user); //TODO handle errors
    }

    private Func1<DataSnapshot, Users> toUsers() {
        return new Func1<DataSnapshot, Users>() {
            @Override
            public Users call(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                List<User> users = new ArrayList<>();
                for (DataSnapshot child : children) {
                    User message = child.getValue(User.class);
                    users.add(message);
                }
                return new Users(users);
            }
        };
    }

    private <T> Func1<DataSnapshot, T> as(final Class<T> tClass) {
        return new Func1<DataSnapshot, T>() {
            @Override
            public T call(DataSnapshot dataSnapshot) {
                return dataSnapshot.getValue(tClass);
            }
        };
    }
}
