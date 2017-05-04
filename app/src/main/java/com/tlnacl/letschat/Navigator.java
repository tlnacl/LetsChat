package com.tlnacl.letschat;

import com.tlnacl.letschat.channel.model.Channel;

import javax.inject.Singleton;

/**
 * Created by tlnacl on 14/03/17.
 */
@Singleton
public interface Navigator {
        void toChannel(Channel channel);

        void toChannels();

        void toCreateChannel();

        void toLogin();

        void toMembersOf(Channel channel);

        void toParent();

        void toChannelWithClearedHistory(Channel channel);

        void toShareInvite(String sharingLink);
}
