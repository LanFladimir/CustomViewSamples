package cn.view.cvs.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import cn.view.cvs.R

/**
 * 渐变ProgressBar
 */

class LinearGradientProgressBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val progressPaint = Paint()
    private val backgroundPaint = Paint()
    private var progress = 50f
    private var startColor = Color.parseColor("#4C87B7")
    private var endColor = Color.parseColor("#A3D5FE")
    private var x = 0f
    private var progressCallback: ProgressChange? = null

    init {
        // 初始化进度条画笔
        progressPaint.isAntiAlias = true
        progressPaint.style = Paint.Style.FILL

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.progress)
            startColor = typedArray.getColor(R.styleable.progress_startColor, startColor)
            endColor = typedArray.getColor(R.styleable.progress_endColor, endColor)
            progress = typedArray.getFloat(R.styleable.progress_progress, progress)
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        //绘制Path，限定Canvas边框
        val path = Path()
        path.addRoundRect(0f, 0f, width, height, height / 2, height / 2, Path.Direction.CW)
        canvas.clipPath(path)

        //绘制进度条
        val progressRect = RectF(0f, 0f, width * progress / 100f, height)
        val colors = intArrayOf(startColor, endColor)
        val shader = LinearGradient(
            0f,
            0f,
            width * progress / 100f,
            height,
            colors,
            null,
            Shader.TileMode.CLAMP
        )
        progressPaint.shader = shader
        canvas.drawRect(progressRect, progressPaint)
    }

    //滑动手势
    override fun onTouchEvent(event: android.view.MotionEvent): Boolean {
        when (event.action) {
            android.view.MotionEvent.ACTION_DOWN -> {
                x = event.rawX
                progress = (event.rawX - left) / width * 100
                progressCallback?.onProgressChange(progress)
                invalidate()
            }

            android.view.MotionEvent.ACTION_MOVE -> {
                progress = (event.rawX - left) / width * 100
                progress = if (progress < 0) 0f else if (progress > 100) 100f else progress
                progressCallback?.onProgressChange(progress)
                invalidate()
            }

            else -> {}
        }
        return true
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    fun setOnProgressChangeListener(callback: ProgressChange) {
        progressCallback = callback
    }

    interface ProgressChange {
        fun onProgressChange(progress: Float)
    }
}