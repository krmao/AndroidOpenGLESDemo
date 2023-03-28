package com.codesdancing.android.opengles.other.view.advanced.opengl;

import android.content.Context;
import android.util.AttributeSet;

import com.codesdancing.android.opengles.other.renderer.advanced.opengl.FaceCullingRenderer;
import com.codesdancing.android.opengles.other.utils.OpenGLUtil;
import com.codesdancing.android.opengles.other.view.BaseGLView;

/**
 * 面剔除
 * @author chends create on 2020/1/7.
 */
public class FaceCullingView extends BaseGLView {

    public FaceCullingView(Context context) {
        this(context, null);
    }

    public FaceCullingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        setEGLContextFactory(OpenGLUtil.createFactory());
        setRenderer(new FaceCullingRenderer());
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}