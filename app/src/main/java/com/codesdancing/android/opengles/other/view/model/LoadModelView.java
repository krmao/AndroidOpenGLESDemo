package com.codesdancing.android.opengles.other.view.model;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.codesdancing.android.opengles.other.renderer.model.LoadModelRenderer;
import com.codesdancing.android.opengles.other.utils.OpenGLUtil;
import com.codesdancing.android.opengles.other.view.BaseGLView;

/**
 * @author chends create on 2020/1/2.
 */
public class LoadModelView extends BaseGLView {
    public LoadModelView(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        // 设置版本
        //setEGLContextClientVersion(2);
        setEGLContextFactory(OpenGLUtil.createFactory());
        // 设置Renderer
        setRenderer(new LoadModelRenderer(getContext()));
        // 设置渲染模式（默认RENDERMODE_CONTINUOUSLY）
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}