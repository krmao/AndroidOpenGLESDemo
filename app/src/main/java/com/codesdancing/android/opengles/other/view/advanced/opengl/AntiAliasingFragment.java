package com.codesdancing.android.opengles.other.view.advanced.opengl;

import android.view.View;

import com.codesdancing.android.opengles.other.view.BaseChangeFragment;

/**
 * 抗锯齿
 * @author chends create on 2020/3/12.
 */
public class AntiAliasingFragment extends BaseChangeFragment {

    private final String[] title = new String[]{"立方体", "立方体（开启MSAA）", "离屏MSAA"};

    @Override
    protected View onChangeClick(int type) {
        return new AntiAliasingView(getContext(), type);
    }

    @Override
    protected int getTypeMax() {
        return title.length;
    }

    @Override
    protected CharSequence getTypeText(int type) {
        return title[type];
    }
}

