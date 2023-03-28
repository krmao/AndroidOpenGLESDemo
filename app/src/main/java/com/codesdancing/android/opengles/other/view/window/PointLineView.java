package com.codesdancing.android.opengles.other.view.window;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.codesdancing.android.opengles.other.Constant;
import com.codesdancing.android.opengles.other.renderer.window.PointLineRenderer;
import com.codesdancing.android.opengles.other.utils.JniRendererUtil;
import com.codesdancing.android.opengles.other.utils.OpenGLUtil;
import com.codesdancing.android.opengles.other.view.BaseGLView;

/**
 * @author chends create on 2019/12/7.
 */
public class PointLineView extends BaseGLView {
    public PointLineView(Context context) {
        super(context);
    }

    public PointLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        setEGLContextFactory(OpenGLUtil.createFactory());
        if (Constant.UserJni) {
            setRenderer(JniRendererUtil.create(Constant.PointLine));
        } else {
            setRenderer(new PointLineRenderer());
        }
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
