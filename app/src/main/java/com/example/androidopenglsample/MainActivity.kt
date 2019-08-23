package com.example.androidopenglsample

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var glSurfaceView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fgl)

        glSurfaceView = findViewById(R.id.glsurfaceview_fgl)
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(Oval())
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }
}
