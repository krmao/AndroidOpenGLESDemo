package com.codesdancing.android.opengles.other.view.light;

import android.content.Context;

import com.codesdancing.android.opengles.other.renderer.light.MaterialsRenderer;
import com.codesdancing.android.opengles.other.utils.OpenGLUtil;
import com.codesdancing.android.opengles.other.view.BaseGLView;

/**
 * 材质
 * @author chends create on 2019/12/19.
 */
public class MaterialsView extends BaseGLView {
    public MaterialsView(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        // 设置版本
        //setEGLContextClientVersion(2);
        setEGLContextFactory(OpenGLUtil.createFactory());
        // 设置Renderer
        setRenderer(new MaterialsRenderer(getContext()));
        // 设置渲染模式（默认RENDERMODE_CONTINUOUSLY）
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
