package com.codesdancing.android.opengles.other.view.light;

import android.content.Context;

import com.codesdancing.android.opengles.other.renderer.light.LightMapsRenderer;
import com.codesdancing.android.opengles.other.utils.OpenGLUtil;
import com.codesdancing.android.opengles.other.view.BaseGLView;

/**
 * 光照贴图
 * @author chends create on 2019/12/27.
 */
public class LightMapsView extends BaseGLView {
    public LightMapsView(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        // 设置版本
        //setEGLContextClientVersion(2);
        setEGLContextFactory(OpenGLUtil.createFactory());
        // 设置Renderer
        setRenderer(new LightMapsRenderer(getContext()));
        // 设置渲染模式（默认RENDERMODE_CONTINUOUSLY）
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
