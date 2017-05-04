package com.tlnacl.letschat.user.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlnacl.letschat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserView extends FrameLayout {
    @BindView(R.id.user_name)
    TextView name;
    @BindView(R.id.user_image)
    ImageView image;

    public UserView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        View.inflate(getContext(), R.layout.merge_user_item_view, this);
    }

    public void display(UsersView.SelectableUser user) {
        Context context = getContext();
        Glide.with(context)
                .load(user.user.getPhotoUrl())
                .error(R.drawable.ic_person)
                .transform(new CircleCropImageTransformation(context))
                .into(image);
        name.setText(user.user.getName());
        setSelected(user.isSelected);
    }
}
