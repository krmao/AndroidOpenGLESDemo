package com.codesdancing.android.opengles.other.view.advanced.opengl;

import android.view.View;

import com.codesdancing.android.opengles.other.view.BaseChangeFragment;

/**
 * 几何着色器
 * @author chends create on 2020/3/3.
 */
public class GeometryShaderFragment extends BaseChangeFragment {

    private final String[] title = new String[]{"点", "线", "房子", "纳米装爆炸", "法向量可视化"};

    @Override
    protected View onChangeClick(int type) {
        return new GeometryShaderView(getContext(), type);
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
