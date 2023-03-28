package com.codesdancing.android.opengles.other.view.advanced.opengl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.Matrix;
import android.util.AttributeSet;
import android.util.Log;

import com.codesdancing.android.opengles.other.renderer.advanced.opengl.CubeMapsRenderer;
import com.codesdancing.android.opengles.other.utils.OpenGLUtil;
import com.codesdancing.android.opengles.other.view.BaseGLView;

import org.json.JSONArray;

/**
 * 立方体贴图
 * @author chends create on 2020/1/15.
 */
public class CubeMapsView extends BaseGLView implements SensorEventListener {
    private CubeMapsRenderer renderer;
    private SensorManager sensorManager;
    private Sensor rotationSensor;
    private float[] rotationMatrix = new float[16];

    public CubeMapsView(Context context) {
        this(context, null);
    }

    public CubeMapsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        }
        Matrix.setIdentityM(rotationMatrix, 0);
    }

    @Override
    protected void init() {
        setEGLContextFactory(OpenGLUtil.createFactory());
        setRenderer(renderer = new CubeMapsRenderer(getContext()));
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null && rotationSensor != null) {
            sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("onSensorChanged2","event.values x="+ event.values[0] +", y="+ event.values[1]+", z="+ event.values[2]);
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        queueEvent(new Runnable() {
            @Override
            public void run() {
                if (renderer != null) {
                    renderer.rotation(rotationMatrix);
                }
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}