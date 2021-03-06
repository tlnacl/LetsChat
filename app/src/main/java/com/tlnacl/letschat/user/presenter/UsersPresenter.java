package com.tlnacl.letschat.user.presenter;

import com.tlnacl.letschat.DatabaseResult;
import com.tlnacl.letschat.Navigator;
import com.tlnacl.letschat.analytics.Analytics;
import com.tlnacl.letschat.analytics.ErrorLogger;
import com.tlnacl.letschat.channel.model.Channel;
import com.tlnacl.letschat.channel.service.ChannelService;
import com.tlnacl.letschat.user.displayer.UsersDisplayer;
import com.tlnacl.letschat.user.model.User;
import com.tlnacl.letschat.user.model.Users;
import com.tlnacl.letschat.user.service.UserService;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UsersPresenter {
    private final UserService userService;
    private final ChannelService channelService;
    private final UsersDisplayer usersDisplayer;
    private final Channel channel;
    private final Navigator navigator;
    private final ErrorLogger errorLogger;
    private final Analytics analytics;
    private CompositeDisposable subscriptions = new CompositeDisposable();

    public UsersPresenter(UserService userService,
                          ChannelService channelService,
                          UsersDisplayer usersDisplayer,
                          Channel channel,
                          Navigator navigator,
                          ErrorLogger errorLogger,
                          Analytics analytics) {
        this.userService = userService;
        this.channelService = channelService;
        this.usersDisplayer = usersDisplayer;
        this.channel = channel;
        this.navigator = navigator;
        this.errorLogger = errorLogger;
        this.analytics = analytics;
    }

    public void startPresenting() {
        usersDisplayer.attach(selectionListener);

        subscriptions.add(
                userService.getAllUsers().subscribe(new Consumer<Users>() {
                    @Override
                    public void accept(Users users) throws Exception {
                        usersDisplayer.display(users);
                    }
                }
        ));
        subscriptions.add(
                channelService.getOwnersOfChannel(channel)
                        .subscribe(new Consumer<DatabaseResult<Users>>() {
                            @Override
                            public void accept(DatabaseResult<Users> databaseResult) throws Exception {
                                if (databaseResult.isSuccess()) {
                                    usersDisplayer.displaySelectedUsers(databaseResult.getData());
                                } else {
                                    errorLogger.reportError(databaseResult.getFailure(), "Cannot fetch channel owners");
                                    usersDisplayer.showFailure();
                                }
                            }
                        }));
    }

    public void stopPresenting() {
        usersDisplayer.detach(selectionListener);
        subscriptions.clear();
        subscriptions = new CompositeDisposable();
    }

    private UsersDisplayer.SelectionListener selectionListener = new UsersDisplayer.SelectionListener() {
        @Override
        public void onUserSelected(final User user) {
            analytics.trackAddChannelOwner(channel.getName(), user.getId());
            channelService.addOwnerToPrivateChannel(channel, user)
                    .subscribe(updateOnActionResult());
        }

        @Override
        public void onUserDeselected(User user) {
            analytics.trackRemoveChannelOwner(channel.getName(), user.getId());
            channelService.removeOwnerFromPrivateChannel(channel, user)
                    .subscribe(updateOnActionResult());
        }

        @Override
        public void onCompleteClicked() {
            navigator.toParent();
        }
    };

    private Consumer<DatabaseResult<User>> updateOnActionResult() {
        return new Consumer<DatabaseResult<User>>() {
            @Override
            public void accept(DatabaseResult<User> userDatabaseResult) {
                if (!userDatabaseResult.isSuccess()) {
                    errorLogger.reportError(userDatabaseResult.getFailure(), "Cannot update channel owners");
                    usersDisplayer.showFailure();
                }
            }
        };
    }

}
