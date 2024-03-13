package com.mashwani.canvaspractice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat


data class BottomBarItem(val text: String, val unselectedIconResId: Int, val selectedIconResId: Int)

class BottomBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    companion object {
        private const val DENSITY_FACTOR = 75
        private const val CORNER_RADIUS = 30f
        private const val ICON_SCALING_FACTOR = 0.7f
        private const val TEXT_SIZE = 35f
    }

    private val verticalLineHeightFactor: Float = DENSITY_FACTOR * resources.displayMetrics.density
    private val paint: Paint = Paint().apply {
        isAntiAlias = true
    }

    private val textPaint: TextPaint = TextPaint().apply {
        color = Color.WHITE
        textSize = TEXT_SIZE
        textAlign = Paint.Align.CENTER
//        typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
    }

    private var _position = 0
    private val boxRects = mutableListOf<Rect>()
    private val boxItems = mutableListOf<BottomBarItem>()

    init {
        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                for (i in boxRects.indices) {
                    if (boxRects[i].contains(event.x.toInt(), event.y.toInt())) {
                        listener?.onBoxClicked(i)
                        performClick()
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val canvasWidth = width.toFloat()
        val canvasHeight = height.toFloat()

        drawImaginaryBoxes(canvas)
    }

    private fun drawImaginaryBoxes(canvas: Canvas) {
        for (i in boxRects.indices) {
            val boxRect = boxRects[i]
            paint.color = Color.BLACK
            paint.style = Paint.Style.FILL
            canvas.drawRect(boxRect, paint)

            val centerX = (boxRect.left + boxRect.right) / 2f
            val centerY = (boxRect.top + boxRect.bottom) / 2f

            // Check if the box is unselected
//            if (i != position) {
            // Draw the icon and text for the box
            ContextCompat.getDrawable(context, boxItems[i].unselectedIconResId)?.apply {
                val iconWidth = intrinsicWidth * ICON_SCALING_FACTOR
                val iconHeight = intrinsicHeight * ICON_SCALING_FACTOR
                colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
                setBounds(
                    (centerX - iconWidth / 2).toInt(),
                    (centerY - iconHeight / 2).toInt(),
                    (centerX + iconWidth / 2).toInt(),
                    (centerY + iconHeight / 2).toInt()
                )
                draw(canvas)
            }

            boxItems[i].text.let {
                canvas.drawText(it, centerX, centerY + 60f, textPaint)
            }
//            }
        }
    }


    private var position: Int
        get() = _position
        set(value) {
            _position = value
            invalidate()
        }

    interface OnBoxClickListener {
        fun onBoxClicked(position: Int)
    }

    private var listener: OnBoxClickListener? = null

    fun setOnBoxClickListener(listener: OnBoxClickListener) {
        this.listener = listener
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val boxWidth = width / 3
        val boxHeight = verticalLineHeightFactor.toInt()

        boxRects.clear()
        for (i in 0 until 3) {
            val left = (i * (width / 3))
            val right = left + boxWidth
            val top = (height - boxHeight)
            val bottom = top + boxHeight
            boxRects.add(Rect(left, top, right, bottom))
        }
    }

    fun setBoxItems(items: List<BottomBarItem>) {
        boxItems.clear()
        boxItems.addAll(items)
        invalidate()
    }
}

