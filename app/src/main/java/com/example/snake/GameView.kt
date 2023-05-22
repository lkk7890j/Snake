package com.example.snake

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.Color

class GameView(mContext: Context, attributes: AttributeSet) : View(mContext, attributes) {
    var snakeBody: List<Position>? = null
    var  apple : Position? = null
    var size: Int = 0
    val gap = 3
    private val mPaint = Paint().apply { color = Color.BLACK }
    private val mPaintApple = Paint().apply { color = Color.RED }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            //apple產生
            apple?.run {
                drawRect(
                    (x * size).toFloat() + gap,(y * size).toFloat() + gap,
                    ((x + 1) * size).toFloat() - gap, ((y + 1) * size).toFloat() - gap, mPaintApple)
            }

            snakeBody?.forEach {
                drawRect(
                    (it.x * size).toFloat() + gap,(it.y * size).toFloat() + gap,
                    ((it.x + 1) * size).toFloat() - gap, ((it.y + 1) * size).toFloat() - gap, mPaint)
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        size = width / 20
    }
}