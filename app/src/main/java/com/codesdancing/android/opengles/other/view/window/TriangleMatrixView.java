package com.codesdancing.android.opengles.other.view.window;

import android.content.Context;

import com.codesdancing.android.opengles.other.renderer.window.TriangleMatrixRenderer;
import com.codesdancing.android.opengles.other.utils.OpenGLUtil;
import com.codesdancing.android.opengles.other.view.BaseGLView;

/**
 * @author chends create on 2019/12/10.
 */
public class TriangleMatrixView extends BaseGLView {

    public TriangleMatrixView(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        setEGLContextFactory(OpenGLUtil.createFactory());
        setRenderer(new TriangleMatrixRenderer());
        //setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
