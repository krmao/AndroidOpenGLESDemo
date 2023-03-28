package com.codesdancing.android.opengles.other.renderer.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.renderscript.Matrix4f;
import android.text.TextUtils;

import com.codesdancing.android.opengles.R;
import com.codesdancing.android.opengles.other.model.model.ObjectBean;
import com.codesdancing.android.opengles.other.renderer.BaseRenderer;
import com.codesdancing.android.opengles.other.utils.LogUtil;
import com.codesdancing.android.opengles.other.utils.OpenGLUtil;
import com.codesdancing.android.opengles.other.utils.model.LoadObjectUtil;

import java.io.IOException;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author chends create on 2020/1/4.
 */
public class LoadModelTextureRenderer extends BaseRenderer {

    private String dir = "nanosuit";
    public LoadModelTextureRenderer(Context context) {
        super(context);
        bg = Color.GRAY;
        list = LoadObjectUtil.loadObject(dir + "/nanosuit.obj", context.getResources(), dir);

        vertexShaderCode = OpenGLUtil.getShaderFromResources(context, R.raw.model_texture_load_vertex);
        fragmentShaderCode = OpenGLUtil.getShaderFromResources(context, R.raw.model_texture_load_fragment);

        color = new float[]{1f, 1f, 1f, 1f};
        vertexLightShaderCode =
                "uniform mat4 uMVPMatrix;" +
                        "attribute vec3 aPosition;" +
                        "attribute vec3 aColor;" +
                        "varying vec4 resultColor;" +
                        "void main() {" +
                        " gl_Position = uMVPMatrix * vec4(aPosition, 1.0);" +
                        " gl_PointSize = 25.0;" +
                        " resultColor = vec4(aColor, 1.0);" +
                        "}";
        fragmentLightShaderCode =
                "precision mediump float;" +
                        "varying vec4 resultColor;" +
                        "void main() {" +
                        " gl_FragColor = resultColor;" +
                        "}";
    }


    private final float[] mMVPMatrix = new float[16], projectionMatrix = new float[16],
            viewMatrix = new float[16], modelMatrix = new float[16],
            mLightMVPMatrix = new float[16], mLightModelMatrix = new float[16];
    private float angle = 0;
    private List<ObjectBean> list;
    private String vertexLightShaderCode, fragmentLightShaderCode;
    private float[] mLightPosInModelSpace = new float[]{0f, 0f, 5f, 1f};
    private final float[] mLightPosInWorldSpace = new float[4], mLightPosInEyeSpace = new float[4];

    private float[] mViewPos = new float[]{0f, 0f, 12f, 1f};

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        float ratio = (float) width / height;

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 1f, 1200f);
        Matrix.setLookAtM(viewMatrix, 0, mViewPos[0], mViewPos[1], mViewPos[2],
                0f, 0f, 0f,
                0f, 1.0f, 0.0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);

        angle = (360.0f / 10000.0f) * (SystemClock.uptimeMillis() % 10000L);
        Matrix.setIdentityM(mLightModelMatrix, 0);
        //Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, 1.0f);
        Matrix.rotateM(mLightModelMatrix, 0, angle, 0.0f, 1.0f, 0.0f);
        //Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, 1.0f);

        Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0);
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, viewMatrix, 0, mLightPosInWorldSpace, 0);

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, -12.0f, 0.0f);
        //Matrix.rotateM(modelMatrix, 0, -90, 1.0f, 0.0f, 0.0f);

        drawModel();

        drawLight();
    }

    /**
     * 绘制物体
     */
    private void drawModel() {
        int shaderProgram = OpenGLUtil.createProgram(vertexShaderCode, fragmentShaderCode, new String[]{
                "aPosition", "aNormal", "aTexCoords"});
        GLES20.glUseProgram(shaderProgram);
        // 顶点坐标
        int positionHandle = GLES20.glGetAttribLocation(shaderProgram, "aPosition");
        // 法向量
        int normalHandle = GLES20.glGetAttribLocation(shaderProgram, "aNormal");
        int textHandle = GLES20.glGetAttribLocation(shaderProgram, "aTexCoords");

        int mMVMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "uMVMatrix");
        int mMVPMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "uMVPMatrix");
        int mNormalPosHandle = GLES20.glGetUniformLocation(shaderProgram, "normalMatrix");

        Matrix.multiplyMM(mMVPMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        GLES20.glUniformMatrix4fv(mMVMatrixHandle, 1, false, mMVPMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, projectionMatrix, 0, mMVPMatrix, 0);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        final Matrix4f normalMatrix = new Matrix4f();
        normalMatrix.loadMultiply(new Matrix4f(viewMatrix), new Matrix4f(modelMatrix));
        normalMatrix.inverse();
        normalMatrix.transpose();
        GLES20.glUniformMatrix4fv(mNormalPosHandle, 1, false, normalMatrix.getArray(), 0);

        int materialAmbientPosHandle = GLES20.glGetUniformLocation(shaderProgram, "material.ambient");
        int materialDiffusePosHandle = GLES20.glGetUniformLocation(shaderProgram, "material.diffuse");
        int materialSpecularPosHandle = GLES20.glGetUniformLocation(shaderProgram, "material.specular");
        int materialShininessPosHandle = GLES20.glGetUniformLocation(shaderProgram, "material.shininess");
        int materialAlphaPosHandle = GLES20.glGetUniformLocation(shaderProgram, "material.alpha");

        int lightPosHandle = GLES20.glGetUniformLocation(shaderProgram, "light.position");
        int lightAmbientPosHandle = GLES20.glGetUniformLocation(shaderProgram, "light.ambient");
        int lightDiffusePosHandle = GLES20.glGetUniformLocation(shaderProgram, "light.diffuse");
        int lightSpecularPosHandle = GLES20.glGetUniformLocation(shaderProgram, "light.specular");
        GLES20.glUniform3f(lightPosHandle, mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);

        GLES20.glUniform3f(lightAmbientPosHandle, 0.5f * color[0], 0.5f * color[1], 0.5f * color[2]);
        GLES20.glUniform3f(lightDiffusePosHandle, 0.6f * color[0], 0.6f * color[1], 0.6f * color[2]);
        GLES20.glUniform3f(lightSpecularPosHandle, 1.0f, 1.0f, 1.0f);

        if (list != null && !list.isEmpty()) {
            for (ObjectBean item : list) {
                if (item != null) {
                    GLES20.glEnableVertexAttribArray(positionHandle);
                    GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT,
                            false, 3 * 4, OpenGLUtil.createFloatBuffer(item.aVertices));
                    GLES20.glEnableVertexAttribArray(normalHandle);
                    GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT,
                            false, 3 * 4, OpenGLUtil.createFloatBuffer(item.aNormals));
                    GLES20.glEnableVertexAttribArray(textHandle);
                    GLES20.glVertexAttribPointer(textHandle, 2, GLES20.GL_FLOAT,
                            false, 2 * 4, OpenGLUtil.createFloatBuffer(item.aTexCoords));

                    if (item.mtl != null) {
                        if (!TextUtils.isEmpty(item.mtl.Kd_Texture)) {
                            if (item.diffuse < 0) {
                                try {
                                    Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(
                                            dir + "/" + item.mtl.Kd_Texture));
                                    item.diffuse = OpenGLUtil.createTextureNormal(bitmap);
                                    bitmap.recycle();
                                } catch (IOException e) {
                                    LogUtil.e(e);
                                }
                            }
                        } else{
                            if (item.diffuse < 0) {
                                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_default_texture);
                                item.diffuse = OpenGLUtil.createTextureNormal(bitmap);
                                bitmap.recycle();
                            }
                        }
                        if (TextUtils.equals(item.mtl.Kd_Texture, item.mtl.Ka_Texture)) {
                            // 相同
                            item.ambient = item.diffuse;
                        } else {
                            if (!TextUtils.isEmpty(item.mtl.Ka_Texture)) {
                                if (item.ambient < 0) {
                                    try {
                                        Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(
                                                dir + "/" + item.mtl.Ka_Texture));
                                        item.ambient = OpenGLUtil.createTextureNormal(bitmap);
                                        bitmap.recycle();
                                    } catch (IOException e) {
                                        LogUtil.e(e);
                                    }
                                }
                            } else{
                                if (item.ambient < 0) {
                                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_default_texture);
                                    item.ambient = OpenGLUtil.createTextureNormal(bitmap);
                                    bitmap.recycle();
                                }
                            }
                        }
                        OpenGLUtil.bindTexture(materialAmbientPosHandle, item.ambient, 0);
                        OpenGLUtil.bindTexture(materialDiffusePosHandle, item.diffuse, 0);

                        if (!TextUtils.isEmpty(item.mtl.Ks_Texture)) {
                            if (item.specular < 0) {
                                try {
                                    Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(
                                            dir + "/" + item.mtl.Ks_Texture));
                                    item.specular = OpenGLUtil.createTextureNormal(bitmap);
                                    bitmap.recycle();
                                } catch (IOException e) {
                                    LogUtil.e(e);
                                }
                            }
                        } else {
                            if (item.specular < 0) {
                                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_default_texture);
                                item.specular = OpenGLUtil.createTextureNormal(bitmap);
                                bitmap.recycle();
                            }
                        }
                        OpenGLUtil.bindTexture(materialSpecularPosHandle, item.specular, 1);

                        GLES20.glUniform1f(materialAlphaPosHandle, item.mtl.alpha);
                        GLES20.glUniform1f(materialShininessPosHandle, item.mtl.ns);
                    }
                    // 绘制顶点
                    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, item.aVertices.length / 3);
                }
            }
        }

        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(normalHandle);
        GLES20.glDisableVertexAttribArray(textHandle);
        GLES20.glDeleteProgram(shaderProgram);

    }

    /**
     * 绘制光源
     */
    private void drawLight() {
        // ---------- 绘制光源 ---------------
        int lightProgram = OpenGLUtil.createProgram(vertexLightShaderCode, fragmentLightShaderCode);
        GLES20.glUseProgram(lightProgram);
        // 传入顶点坐标
        int lightPositionHandle = GLES20.glGetAttribLocation(lightProgram, "aPosition");
        int lightColorHandle = GLES20.glGetAttribLocation(lightProgram, "aColor");
        GLES20.glEnableVertexAttribArray(lightPositionHandle);
        GLES20.glVertexAttribPointer(lightPositionHandle, 3, GLES20.GL_FLOAT,
                false, 3 * 4, OpenGLUtil.createFloatBuffer(mLightPosInModelSpace));

        GLES20.glEnableVertexAttribArray(lightColorHandle);
        GLES20.glVertexAttribPointer(lightColorHandle, 3, GLES20.GL_FLOAT,
                false, 3 * 4, OpenGLUtil.createFloatBuffer(color));

        Matrix.multiplyMM(mLightMVPMatrix, 0, viewMatrix, 0, mLightModelMatrix, 0);
        Matrix.multiplyMM(mLightMVPMatrix, 0, projectionMatrix, 0, mLightMVPMatrix, 0);

        int mMVPMatrixHandle = GLES20.glGetUniformLocation(lightProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mLightMVPMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);

        GLES20.glDisableVertexAttribArray(lightPositionHandle);
        GLES20.glDeleteProgram(lightProgram);
    }
}
