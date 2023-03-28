package com.codesdancing.android.opengles.other.view.advanced.opengl;

import android.view.View;

import com.codesdancing.android.opengles.other.view.BaseChangeFragment;

/**
 * @author chends create on 2020/1/11.
 */
public class CubeMapsEnvironmentFragment extends BaseChangeFragment {

    private CubeMapsEnvironmentView view;

    @Override
    protected View onChangeClick(int type) {
        view = new CubeMapsEnvironmentView(getContext(), type);
        view.onResume();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (view != null) {
            view.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (view != null) {
            view.onPause();
        }
    }

    @Override
    protected int getTypeMax() {
        return 2;
    }
}
