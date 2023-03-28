package com.codesdancing.android.opengles

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codesdancing.android.opengles.databinding.ActivityMainBinding
import com.codesdancing.android.opengles.other.utils.OpenGLUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isSupportOpenGLES20000()) {
            Toast.makeText(this, "当前设备不支持OpenGL ES 2.0", Toast.LENGTH_SHORT).show();
            finish()
            return
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        initGLSurfaceView(binding.glSurfaceView)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI() + "\n" + "isSupportOpenGLES20000:" + isSupportOpenGLES20000()


    }

    private fun isSupportOpenGLES20000(): Boolean = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).deviceConfigurationInfo.reqGlEsVersion >= 0x20000;

    private var mRendererSet = false
    private fun initGLSurfaceView(glSurfaceView: GLSurfaceView) {
        glSurfaceView.debugFlags = if (BuildConfig.DEBUG) GLSurfaceView.DEBUG_LOG_GL_CALLS else GLSurfaceView.DEBUG_CHECK_GL_ERROR
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        glSurfaceView.setEGLContextFactory(OpenGLUtil.createFactory())
        glSurfaceView.setRenderer(CoordinateRenderer())
//        glSurfaceView.setRenderer(TestRenderer())
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        mRendererSet = true
    }

    override fun onResume() {
        super.onResume()
        if (mRendererSet)
            binding.glSurfaceView.onResume()

        binding.mapView.onResume()
        binding.envView.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (mRendererSet)
            binding.glSurfaceView.onPause()
        binding.mapView.onPause()
        binding.envView.onPause()
    }

    /**
     * A native method that is implemented by the 'opengles' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'opengles' library on application startup.
        init {
            System.loadLibrary("opengles")
        }
    }
}