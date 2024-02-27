package com.example.aston_intensiv_2.ui.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.aston_intensiv_2.hardCodedData


class SpinningWheelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val colors = hardCodedData()
    private val oval = RectF()
    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 20 * resources.displayMetrics.density
        isAntiAlias = true
    }
    private val defaultPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    var rotationAngle: Float = 0f
    var isWheelSpinning: Boolean = false
    var text: String = ""


    override fun performClick(): Boolean {
        if (!isWheelSpinning)
            return super.performClick()
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpec = MeasureSpec.makeMeasureSpec(widthSize * 3 / 5, widthMode)
        super.onMeasure(widthMeasureSpec, heightSpec)
    }

    override fun onDraw(canvas: Canvas) {
        val viewHeight = (width * 3 / 5).toFloat()
        val circleWidth = viewHeight

        drawText(canvas, circleWidth, viewHeight * 1 / 5)
        drawWheel(
            canvas,
            circleWidth / 2f, circleWidth / 2f,
            rotationAngle
        )

        super.onDraw(canvas)
    }

    private fun drawText(canvas: Canvas, x: Float, y: Float) {
        canvas.drawText(
            text,
            x, y,
            textPaint
        )
    }

    private fun drawWheel(canvas: Canvas, centerX: Float, centerY: Float, rotation: Float) {
        oval.apply {
            top = 0f
            bottom = centerY * 2
            left = 0f
            right = centerX * 2
        }
        val sweepAngle = 360f / colors.size
        var startAngle = -90f - sweepAngle / 2

        canvas.rotate(rotation, centerX, centerY)

        colors.forEach { (_, color) ->
            canvas.drawArc(
                oval,
                startAngle,
                sweepAngle,
                true,
                defaultPaint.also {
                    it.color = color
                }
            )
            startAngle += sweepAngle
        }
    }
}