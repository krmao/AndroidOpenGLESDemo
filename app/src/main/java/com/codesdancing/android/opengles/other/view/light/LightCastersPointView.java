package com.codesdancing.android.opengles.other.view.light;

import android.content.Context;

import com.codesdancing.android.opengles.other.renderer.light.LightCastersPointRenderer;
import com.codesdancing.android.opengles.other.utils.OpenGLUtil;
import com.codesdancing.android.opengles.other.view.BaseGLView;

/**
 * 投光物-点光源
 * @author chends create on 2019/12/27.
 */
public class LightCastersPointView extends BaseGLView {
    public LightCastersPointView(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        // 设置版本
        //setEGLContextClientVersion(2);
        setEGLContextFactory(OpenGLUtil.createFactory());
        // 设置Renderer
        setRenderer(new LightCastersPointRenderer(getContext()));
        // 设置渲染模式（默认RENDERMODE_CONTINUOUSLY）
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
