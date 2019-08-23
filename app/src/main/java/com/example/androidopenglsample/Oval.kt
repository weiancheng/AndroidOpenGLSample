package com.example.androidopenglsample

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Oval(): GLSurfaceView.Renderer {

    private val COORDS_PER_VERTEX = 3

    private var height = 0.0f
    private var shapePos: FloatArray
    private var radius = 1.0f
    private var viewMatrix = FloatArray(16)
    private var projectMatrix = FloatArray(16)
    private var mvpMatrix = FloatArray(16)
    private var program: Int = -1
    private var positionHandle: Int = 0
    private var colorHandle: Int = 0
    private var matrixHandle = 0
    private var vertexBuffer: FloatBuffer
    private val color = floatArrayOf(1f, 1f, 1f, 1f)
    private val vertexShaderCode = "attribute vec4 vPosition;" +
            "uniform mat4 vMatrix;" +
            "void main() {" +
            "   gl_Position = vMatrix * vPosition;" +
            "}"
    private val fragmentShaderCode = "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "   gl_FragColor = vColor;" +
            "}"

    init {
        shapePos = createPosition()
        vertexBuffer = (ByteBuffer.allocateDirect(shapePos.size.minus(4))).asFloatBuffer()
        vertexBuffer.put(shapePos)
        vertexBuffer.position(0)
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        program = GLES20.glCreateProgram()

        GLES20.glAttachShader(program, vertexShader)
        GLES20.glAttachShader(program, fragmentShader)
        GLES20.glLinkProgram(program)
    }

    private fun loadShader(shaderType: Int, shaderCode: String): Int {
        val shader = GLES20.glCreateShader(shaderType)
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }

    private fun createPosition(): FloatArray {
        val data = ArrayList<Float>()

        data.add(0.0f)
        data.add(0.0f)
        data.add(height)
        val angDegSpan = 360.0f.div(360)
        for (i in 0 .. (360+angDegSpan).toInt() step angDegSpan.toInt()) {
            data.add((radius * sin(i * PI / 180.0f)).toFloat())
            data.add((radius * cos(i * PI / 180.0f)).toFloat())
            data.add(height)
        }

        val f = FloatArray(data.size)
        for (i in 0..f.size step 1) {
            f[i] = data[i]
        }

        return f
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        val ratio = (width/height).toFloat()
        Matrix.frustumM(projectMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)

        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 7f, 0f, 0f, 0f, 0f, 1f, 0f)

        Matrix.multiplyMM(mvpMatrix, 0, projectMatrix, 0, viewMatrix, 0)
    }

    override fun onSurfaceCreated(gl: GL10?, eglConfig: EGLConfig?) {
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glUseProgram(program)

        matrixHandle = GLES20.glGetUniformLocation(program, "vMatrix")
        GLES20.glUniformMatrix4fv(matrixHandle, 1, false, mvpMatrix, 0)

        positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(positionHandle,
            COORDS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            0,
            vertexBuffer)
        colorHandle = GLES20.glGetUniformLocation(program, "vColor")
        GLES20.glUniform4fv(colorHandle, 1, color, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, shapePos.size.div(3))
        GLES20.glDisableVertexAttribArray(positionHandle)
    }
}
