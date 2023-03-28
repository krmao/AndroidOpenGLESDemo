package com.codesdancing.android.opengles.other.view.window;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.codesdancing.android.opengles.other.renderer.window.TriangleColorRenderer;
import com.codesdancing.android.opengles.other.utils.OpenGLUtil;
import com.codesdancing.android.opengles.other.view.BaseGLView;

/**
 * @author chends create on 2019/12/10.
 */
public class TriangleColorView extends BaseGLView {

    public TriangleColorView(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        setEGLContextFactory(OpenGLUtil.createFactory());
        setRenderer(new TriangleColorRenderer());
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
