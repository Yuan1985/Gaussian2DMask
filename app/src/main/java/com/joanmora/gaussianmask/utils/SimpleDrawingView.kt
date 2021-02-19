package com.joanmora.gaussianmask.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SimpleDrawingView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    // setup initial color
    private val paintColor: Int = Color.BLACK

    // defines paint and canvas
    private var drawPaint: Paint = Paint()

    // Setup paint with color and stroke styles
    private fun setupPaint() {
        drawPaint.setColor(paintColor)
        drawPaint.setAntiAlias(true)
        drawPaint.setStrokeWidth(5f)
        drawPaint.setStyle(Paint.Style.STROKE)
        drawPaint.setStrokeJoin(Paint.Join.ROUND)
        drawPaint.setStrokeCap(Paint.Cap.ROUND)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(50f, 50f, 20f, drawPaint)
        drawPaint.color = Color.GREEN
        canvas.drawCircle(50f, 150f, 20f, drawPaint)
        drawPaint.color = Color.BLUE
        canvas.drawCircle(50f, 250f, 20f, drawPaint)
    }

    init {
        isFocusable = true
        isFocusableInTouchMode = true
        setupPaint()
    }
}