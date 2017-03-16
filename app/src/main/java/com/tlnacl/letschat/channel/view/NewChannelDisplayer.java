package com.tlnacl.letschat.channel.view;

public interface NewChannelDisplayer {

    void attach(ChannelCreationListener channelCreationListener);

    void detach(ChannelCreationListener channelCreationListener);

    void showChannelCreationError();

    interface ChannelCreationListener {

        void onCreateChannelClicked(String channelName, boolean isPrivate);

        void onCancel();
    }
}
