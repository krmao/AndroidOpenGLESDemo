package com.codesdancing.android.opengles.other.view.light;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.codesdancing.android.opengles.other.renderer.light.LightRenderer;
import com.codesdancing.android.opengles.other.utils.OpenGLUtil;
import com.codesdancing.android.opengles.other.view.BaseGLView;

/**
 * @author chends create on 2019/12/19.
 */
public class LightView extends BaseGLView {
    public LightView(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        // 设置版本
        //setEGLContextClientVersion(2);
        setEGLContextFactory(OpenGLUtil.createFactory());
        // 设置Renderer
        setRenderer(new LightRenderer(getContext()));
        // 设置渲染模式（默认RENDERMODE_CONTINUOUSLY）
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
