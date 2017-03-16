package com.tlnacl.letschat.channel.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tlnacl.letschat.R;
import com.tlnacl.letschat.channel.model.Channel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChannelView extends FrameLayout {
    @BindView(R.id.channel_title)
    TextView title;
    @BindView(R.id.lock_icon)
    View lockIcon;

    public ChannelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        View.inflate(getContext(), R.layout.merge_channel_item_view, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void display(Channel channel) {
        title.setText(channel.getName());
        lockIcon.setVisibility(channel.isPrivate() ? VISIBLE : GONE);
    }
}
