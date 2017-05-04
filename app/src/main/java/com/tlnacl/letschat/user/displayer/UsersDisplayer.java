package com.tlnacl.letschat.user.displayer;

import com.tlnacl.letschat.user.model.User;
import com.tlnacl.letschat.user.model.Users;

public interface UsersDisplayer {

    void attach(SelectionListener selectionListener);

    void detach(SelectionListener selectionListener);

    void display(Users users);

    void showFailure();

    void displaySelectedUsers(Users selectedUsers);

    interface SelectionListener {

        void onUserSelected(User user);

        void onUserDeselected(User user);

        void onCompleteClicked();
    }
}
