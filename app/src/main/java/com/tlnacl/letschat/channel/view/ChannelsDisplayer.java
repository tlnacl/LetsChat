package com.tlnacl.letschat.channel.view;

import com.tlnacl.letschat.channel.model.Channel;
import com.tlnacl.letschat.channel.model.Channels;

public interface ChannelsDisplayer {

    void display(Channels channels);

    void attach(ChannelsInteractionListener channelsInteractionListener);

    void detach(ChannelsInteractionListener channelsInteractionListener);

    interface ChannelsInteractionListener {
        void onChannelSelected(Channel channel);

        void onAddNewChannel();

        void onInviteUsersClicked();
    }
}
