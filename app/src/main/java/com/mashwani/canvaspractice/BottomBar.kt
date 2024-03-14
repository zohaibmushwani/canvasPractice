package com.mashwani.canvaspractice

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat


class BottomBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val boxRects = mutableListOf<Rect>()
    private val boxItems = mutableListOf<BottomBarItem>()

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private val textPaint = TextPaint().apply {
        color = Color.WHITE
        textSize = 35f
        textAlign = Paint.Align.CENTER
    }

    private val densityFactor = 56 * resources.displayMetrics.density

    private var selectedPosition = 0

    var onItemSelectedListener: ((position: Int) -> Unit)? = null

    private var animator: ValueAnimator? = null

    private var oldRect: Rect = Rect()

    private fun drawTrapezoid(canvas: Canvas, rect: Rect) {
        val gradient = LinearGradient(
            rect.centerX().toFloat(), // Start X position (center)
            rect.bottom.toFloat(),    // Start Y position (bottom)
            rect.centerX().toFloat(), // End X position (center)
            rect.top.toFloat(),       // End Y position (top)
            Color.argb(85, 255, 255, 255), // Start color (semi-transparent white)
            Color.argb(25, 255, 255, 255), // End color (fully transparent)
            Shader.TileMode.CLAMP
        )

        paint.shader = gradient
        paint.style = Paint.Style.FILL

        val path = Path()
        path.moveTo(rect.left.toFloat() + (rect.width() * 0.2f), rect.bottom.toFloat() - 10f)
        path.lineTo(rect.right.toFloat() - (rect.width() * 0.2f), rect.bottom.toFloat() - 10f)
        path.lineTo(rect.right.toFloat(), rect.top.toFloat())
        path.lineTo(rect.left.toFloat(), rect.top.toFloat())
        path.close()

        canvas.drawPath(path, paint)

        paint.shader = null
    }

    private fun animateIndicatorAndTrapezoid(fromRect: Rect, toRect: Rect) {
        animator?.cancel()

        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()

            addUpdateListener { animation ->
                val fraction = animation.animatedFraction
                val newX = fromRect.left + (toRect.left - fromRect.left) * fraction
                val newY = fromRect.top + (toRect.top - fromRect.top) * fraction
                val newWidth = fromRect.width() + (toRect.width() - fromRect.width()) * fraction
                val newHeight = fromRect.height() + (toRect.height() - fromRect.height()) * fraction

                val newRect = Rect(newX.toInt(), newY.toInt(), (newX + newWidth).toInt(), (newY + newHeight).toInt())
                oldRect.set(newRect)
                invalidate()
            }

            start()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            for (i in boxRects.indices) {
                if (boxRects[i].contains(event.x.toInt(), event.y.toInt())) {
                    selectedPosition = i
                    onItemSelectedListener?.invoke(i)
                    animateIndicatorAndTrapezoid(oldRect, boxRects[i])
                    performClick()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        boxRects.forEachIndexed { index, boxRect ->
            drawBox(canvas, boxRect,index ,index == selectedPosition)
        }
    }

    private fun drawBox(canvas: Canvas, rect: Rect, index: Int, isSelected: Boolean) {
        paint.color = ContextCompat.getColor(context, R.color.bbar_back)
        canvas.drawRect(rect, paint)

        val centerX = rect.centerX().toFloat()
        val centerY = rect.centerY().toFloat()

        val iconResId = if (isSelected) boxItems[index].selectedIconResId else boxItems[index].unselectedIconResId
        val textColor = ContextCompat.getColor(context, if (isSelected) R.color.bbar_ic_selected else R.color.bbar_ic_unselected)

        drawIcon(canvas, iconResId, centerX, centerY - 25f)
        drawText(canvas, boxItems[index].text, centerX, centerY + 55f, textColor)

        if (isSelected) {
            drawIndicator(canvas, oldRect)
            drawTrapezoid(canvas, oldRect)
        }
    }

    private fun drawIcon(canvas: Canvas, iconResId: Int, centerX: Float, centerY: Float) {
        val icon = ContextCompat.getDrawable(context, iconResId) ?: return
        icon.apply {
            val scaledWidth = intrinsicWidth.toFloat()
            val scaledHeight = intrinsicHeight.toFloat()
            setBounds(
                (centerX - scaledWidth / 2).toInt(),
                (centerY - scaledHeight / 2).toInt(),
                (centerX + scaledWidth / 2).toInt(),
                (centerY + scaledHeight / 2).toInt()
            )
            draw(canvas)
        }
    }

    private fun drawText(canvas: Canvas, text: String, x: Float, y: Float, color: Int) {
        textPaint.apply {
            this.color = color
        }
        canvas.drawText(text, x, y, textPaint)
    }

    private fun drawIndicator(canvas: Canvas, rect: Rect) {
        paint.color = Color.WHITE
        val indicatorHeight = 10f
        val indicatorRadius = 15f

        val startX = rect.left.toFloat() + (rect.width() * 0.19f)
        val endX = rect.right.toFloat() - (rect.width() * 0.19f)
        val startY = rect.bottom.toFloat() - indicatorHeight
        val endY = rect.bottom.toFloat()

        val indicatorRect = RectF(startX, startY, endX, endY)
        canvas.drawRoundRect(indicatorRect, indicatorRadius, indicatorRadius, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val boxWidth = w / 5
        val boxHeight = densityFactor.toInt()
        boxRects.clear()
        for (i in 0 until 5) {
            val left = i * boxWidth
            val right = left + boxWidth
            val top = h - boxHeight
            val bottom = top + boxHeight
            boxRects.add(Rect(left, top, right, bottom))
        }
        oldRect.set(boxRects[0])
    }

    fun setBoxItems(items: List<BottomBarItem>) {
        boxItems.clear()
        boxItems.addAll(items)
        invalidate()
    }

    data class BottomBarItem(
        val text: String,
        val selectedIconResId: Int,
        val unselectedIconResId: Int
    )

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
