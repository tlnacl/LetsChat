package com.tlnacl.letschat.channel.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.tlnacl.letschat.R;
import com.tlnacl.letschat.channel.model.Channels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChannelsView extends LinearLayout implements ChannelsDisplayer {

    private final ChannelsAdapter channelsAdapter;
    private ChannelsInteractionListener channelsInteractionListener;

    @BindView(R.id.channels)
    RecyclerView channels;
    @BindView(R.id.new_channel_fab)
    FloatingActionButton newChannelFab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public ChannelsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        channelsAdapter = new ChannelsAdapter(LayoutInflater.from(context));
        setOrientation(VERTICAL);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        View.inflate(getContext(), R.layout.merge_channels_view, this);
        channels.addItemDecoration(new ChannelItemDecoration());
        channels.setAdapter(channelsAdapter);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.channels_menu);
    }

    private int getSpanCount() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 4;
    }

    @Override
    public void display(Channels channels) {
        channelsAdapter.update(channels);
    }

    @Override
    public void attach(ChannelsInteractionListener channelsInteractionListener) {
        this.channelsInteractionListener = channelsInteractionListener;
        channelsAdapter.attach(channelsInteractionListener);
        newChannelFab.setOnClickListener(fabClickListener);
        toolbar.setOnMenuItemClickListener(menuItemClickListener);
    }

    @Override
    public void detach(ChannelsInteractionListener channelsInteractionListener) {
        channelsAdapter.detach(channelsInteractionListener);
        newChannelFab.setOnClickListener(null);
        toolbar.setOnMenuItemClickListener(null);
        this.channelsInteractionListener = null;
    }

    private Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.actionInvite) {
                channelsInteractionListener.onInviteUsersClicked();
                return true;
            }
            return false;
        }
    };

    private final OnClickListener fabClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            channelsInteractionListener.onAddNewChannel();
        }
    };

    private class ChannelItemDecoration extends RecyclerView.ItemDecoration {

        private final int itemPaddingInPixel = getResources().getDimensionPixelOffset(R.dimen.channel_item_padding);
        private final int gridPaddingInPixel = getResources().getDimensionPixelOffset(R.dimen.channel_grid_padding);

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = itemPaddingInPixel;
            outRect.bottom = itemPaddingInPixel;
            outRect.left = itemPaddingInPixel;
            outRect.right = itemPaddingInPixel;

            int position = parent.getChildAdapterPosition(view);
            int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();

            if (isTopRow(position, spanCount)) {
                outRect.top += gridPaddingInPixel;
            } else if (isBottomRow(parent, position, spanCount)) {
                outRect.bottom += gridPaddingInPixel;
            }

            if (isLeftEdge(position, spanCount)) {
                outRect.left += gridPaddingInPixel;
            } else if (isRightEdge(position, spanCount)) {
                outRect.right += gridPaddingInPixel;
            }
        }

        private boolean isTopRow(int position, int spanCount) {
            return position < spanCount;
        }

        private boolean isBottomRow(RecyclerView parent, int position, int spanCount) {
            return position >= parent.getAdapter().getItemCount() - spanCount;
        }

        private boolean isLeftEdge(int position, int spanCount) {
            return (position % spanCount) == 0;
        }

        private boolean isRightEdge(int position, int spanCount) {
            return (position % spanCount) == (spanCount - 1);
        }
    }
}
