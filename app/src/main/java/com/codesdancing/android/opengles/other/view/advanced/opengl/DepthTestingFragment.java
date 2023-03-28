package com.codesdancing.android.opengles.other.view.advanced.opengl;

import android.view.View;

import com.codesdancing.android.opengles.other.view.BaseChangeFragment;

/**
 * @author chends create on 2020/1/6.
 */
public class DepthTestingFragment extends BaseChangeFragment {

    @Override
    protected View onChangeClick(int type) {
        return new DepthTestingView(getContext(), type);
    }

    @Override
    protected int getTypeMax() {
        return 3;
    }
}
