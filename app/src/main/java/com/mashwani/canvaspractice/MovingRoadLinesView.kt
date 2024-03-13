package com.mashwani.canvaspractice

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

//class VerticalMovingLinesView @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null
//) : View(context, attrs) {
//
//    private val lineColor = Color.WHITE
//    private val lineWidth = 40f
//    private val lineHeight = 200f
//    private val lineSpacing = 80f
//    private val linesCount = 6
//    private val linePaint = Paint().apply {
//        color = lineColor
//        style = Paint.Style.FILL
//        textSize = 40f
//    }
//
//
//    private var currentOffset = 0f
//
//    init {
//        startScrolling()
//    }
//
//    private fun startScrolling() {
//        val totalDistance =4*(lineHeight + lineSpacing )
//        val speed = 0.3f
//        val duration = (totalDistance / speed).toLong()
//        val animator = ValueAnimator.ofFloat(0f, totalDistance)
//        animator.duration = duration
//        animator.interpolator = LinearInterpolator()
//        animator.repeatMode = ValueAnimator.RESTART
//        animator.repeatCount = ValueAnimator.INFINITE
//        animator.addUpdateListener { animation ->
//            currentOffset = animation.animatedValue as Float
//            invalidate()
//        }
//        animator.start()
//    }
//
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        val startX = width / 2f - lineWidth / 2
//        val startY = currentOffset
//        var yPos = startY
//        for (i in 0 until linesCount) {
//            val top = yPos + i * (lineHeight + lineSpacing)
//            val bottom = top + lineHeight
//            canvas.drawRect(startX, top, startX + lineWidth, bottom, linePaint)
//
//            // Calculate the x-coordinate for the text based on whether it's left or right
//            val textX = if (i % 2 == 0) startX + lineWidth + 10f else startX - 50f
//
//            // Calculate the y-coordinate for the text
//            val textY = (top + bottom) / 2
//
//            // Draw the text (index i) next to the rectangle
//            canvas.drawText(i.toString(), textX, textY, linePaint)
//
//            if (bottom >= height && currentOffset >= 0) {
//                yPos -= (lineHeight + lineSpacing) * linesCount
//            }
//        }
//    }



//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//
//        val startX = width / 2f - lineWidth / 2
//        var yPos = currentOffset
//
//        for (i in 0 until linesCount) {
//            val top = yPos + i * (lineHeight + lineSpacing)
//            val bottom = top + lineHeight
//
//            // Calculate left and right offsets to create trapezoid shape
//            val topWidth = lineWidth * 0.6f
//            val bottomWidth = lineWidth
//            val leftOffset = (lineWidth - topWidth) / 2
//            val rightOffset = (lineWidth - bottomWidth) / 2
//
//            val leftStartX = startX + leftOffset
//            val rightStartX = startX + rightOffset
//
//            // Draw trapezoid shape
//            val path = Path()
//            path.moveTo(leftStartX, top)
//            path.lineTo(leftStartX + topWidth, top)
//            path.lineTo(rightStartX + bottomWidth, bottom)
//            path.lineTo(rightStartX, bottom)
//            path.close()
//
//            canvas.drawPath(path, linePaint)
//
//            if (bottom >= height && currentOffset >= 0) {
//                yPos -= (lineHeight + lineSpacing) * linesCount
//            }
//        }
//    }



//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//
//        val startX = width / 2f - lineWidth / 2
//        var yPos = currentOffset % (lineHeight + lineSpacing)
//
//        for (i in 0 until linesCount) {
//            val top = yPos + i * (lineHeight + lineSpacing)
//            val bottom = top + lineHeight
//
//            // Calculate the normalized position in the range [0, 1]
//            val progress = (top % (lineHeight + lineSpacing)) / (lineHeight + lineSpacing)
//
//            // Calculate top and bottom widths based on the normalized position
//            val initialTopWidth = lineWidth * 0.6f
//            val initialBottomWidth = lineWidth
//            val topWidth = initialTopWidth + (lineWidth - initialTopWidth) * progress
//            val bottomWidth = initialBottomWidth + (lineWidth - initialBottomWidth) * progress
//
//            // Adjust the sizes to make them increase smoothly and create perspective
//            val adjustedTopWidth = initialTopWidth + (lineWidth - initialTopWidth) * (1 - progress)
//            val adjustedBottomWidth = initialBottomWidth + (lineWidth - initialBottomWidth) * progress
//
//            val leftOffset = (lineWidth - adjustedTopWidth) / 2
//            val rightOffset = (lineWidth - adjustedBottomWidth) / 2
//
//            val leftStartX = startX + leftOffset
//            val rightStartX = startX + rightOffset
//
//            // Draw trapezoid shape
//            val path = Path()
//            path.moveTo(leftStartX, top)
//            path.lineTo(leftStartX + adjustedTopWidth, top)
//            path.lineTo(rightStartX + adjustedBottomWidth, bottom)
//            path.lineTo(rightStartX, bottom)
//            path.close()
//
//            canvas.drawPath(path, linePaint)
//
//            // Reset to initial size and move if the line is out of the screen
//            if (bottom >= height && currentOffset >= 0) {
//                yPos -= (lineHeight + lineSpacing) * linesCount
//            }
//        }
//    }
//}
