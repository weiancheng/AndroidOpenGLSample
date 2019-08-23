package com.example.androidopenglsample

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class FGLActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mButton: Button
    private lateinit var mSurfaceView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fgl)

        mButton = findViewById(R.id.btn_fgl_change)
        mSurfaceView = findViewById(R.id.glsurfaceview_fgl)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_fgl_change -> {

            }
        }
    }
}
