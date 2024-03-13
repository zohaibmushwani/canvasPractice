package com.mashwani.canvaspractice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.math.max

class RoadLinesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var lines: List<Line> = emptyList()
    private var lineWidth = 25f
    private var lineHeight = 120f
    private var lineSpeed = 2f
    private var lineSpacing = 45f

    private var isMoving = true

    init {
        attrs?.let {
            val styledAttrs = context.obtainStyledAttributes(it, R.styleable.RoadLinesView)
            lineWidth = styledAttrs.getDimension(R.styleable.RoadLinesView_lineWidth, lineWidth)
            lineHeight = styledAttrs.getDimension(R.styleable.RoadLinesView_lineHeight, lineHeight)
            lineSpeed = styledAttrs.getFloat(R.styleable.RoadLinesView_lineSpeed, lineSpeed)
            lineSpacing =
                styledAttrs.getDimension(R.styleable.RoadLinesView_lineSpacing, lineSpacing)
            styledAttrs.recycle()
        }
    }

    fun startMovement() {
        isMoving = true
        invalidate()
    }

    fun stopMovement() {
        isMoving = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (lines.isEmpty()) {
            lines = createLines()
        }

        for (line in lines) {
            drawTrapezoidalLine(canvas, line)
            if (isMoving) {
                updateLinePosition(line)
            }
        }

        if (isMoving) {
            invalidate()
        }
    }

//    private fun createLines(): List<Line> {
//        val lines = mutableListOf<Line>()
//        var y = 0f
//
//        while (y < height) {
//            lines.add(Line(y))
//            y += lineHeight + lineSpacing
//        }
//
//        return lines
//    }

    private fun createLines(): List<Line> {
        val lines = mutableListOf<Line>()
        val totalLineHeight = lineHeight + lineSpacing
        val totalLines = (height / totalLineHeight).toInt() + 1 // Adjust totalLines calculation

        // Calculate the initial y position to distribute lines evenly
        var initialY = (height - totalLines * lineHeight - (totalLines - 1) * lineSpacing) / 2f

        // Ensure initialY is not negative
        initialY = max(0f, initialY)

        for (i in 0 until totalLines) {
            lines.add(Line(initialY))
            initialY += lineHeight + lineSpacing
        }

        return lines
    }

    private fun drawTrapezoidalLine(canvas: Canvas, line: Line) {
        val perspectiveFactor = calculatePerspectiveFactor(line.y)
        val topX1 = (width / 2f) - (lineWidth / 2f) * perspectiveFactor
        val topX2 = (width / 2f) + (lineWidth / 2f) * perspectiveFactor
        val paint = Paint().apply {
            strokeWidth = lineWidth
            color = Color.WHITE
        }
        val path = Path()
        path.moveTo(topX1, line.y)
        path.lineTo(topX2, line.y)
        path.lineTo(topX2, line.y + lineHeight)
        path.lineTo(topX1, line.y + lineHeight)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun updateLinePosition(line: Line) {
        line.y += lineSpeed
        if (line.y > height) {
            line.y = -lineHeight
        }
    }

    private fun calculatePerspectiveFactor(y: Float): Float {
        val focalLength = height / 2f
        val initialFactor = 1.5f
        return max(1f, initialFactor + (y - height / 2f) / focalLength)
    }

    fun setLineWidth(width: Float) {
        lineWidth = width
    }

    fun setLineHeight(height: Float) {
        lineHeight = height
    }

    fun setLineSpeed(speed: Float) {
        lineSpeed = speed
    }

    fun setLineSpacing(spacing: Float) {
        lineSpacing = spacing
    }
}


data class Line(var y: Float)
