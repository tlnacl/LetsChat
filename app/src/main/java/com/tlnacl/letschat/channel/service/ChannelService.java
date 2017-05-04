package com.tlnacl.letschat.channel.service;


import com.tlnacl.letschat.DatabaseResult;
import com.tlnacl.letschat.channel.model.Channel;
import com.tlnacl.letschat.channel.model.Channels;
import com.tlnacl.letschat.user.model.User;
import com.tlnacl.letschat.user.model.Users;

import io.reactivex.Observable;

public interface ChannelService {

    Observable<Channels> getChannelsFor(User user);

    Observable<DatabaseResult<Channel>> createPublicChannel(Channel newChannel);

    Observable<DatabaseResult<Channel>> createPrivateChannel(Channel newChannel, User owner);

    Observable<DatabaseResult<User>> addOwnerToPrivateChannel(Channel channel, User newOwner);

    Observable<DatabaseResult<User>> removeOwnerFromPrivateChannel(Channel channel, User removedOwner);

    Observable<DatabaseResult<Users>> getOwnersOfChannel(Channel channel);
}
