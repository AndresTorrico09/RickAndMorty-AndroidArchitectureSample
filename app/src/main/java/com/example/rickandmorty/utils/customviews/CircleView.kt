package com.example.rickandmorty.utils.customviews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.rickandmorty.R

class CircleView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {
    var paint: Paint = Paint()
    var centerOfX = 340F //center of circle on X axis
    var centerOfY = 340F //center of circle on Y axis
    var radiusOfCircleView = 140F
    var isCenter = false

    init{
        val attributeArray: TypedArray? = context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.circleview, 0, 0
        )

        paint.color = attributeArray?.getColor(
                R.styleable.circleview_circle_background,
                ContextCompat.getColor(context, R.color.colorPrimary)
        ) ?: android.R.color.black

        isCenter = attributeArray?.getBoolean(
                R.styleable.circleview_circle_background,
                false
        ) ?: false

        paint.strokeWidth = 40f
        paint.style = Paint.Style.STROKE //contructor call
    }

    override fun onDraw(canvas: Canvas?) {
        isCenter?.let {
            centerOfX = (width / 2).toFloat()
            centerOfY = (height / 2).toFloat()
        }
        canvas?.drawCircle(centerOfX, centerOfY, radiusOfCircleView, paint)
        super.onDraw(canvas)

    }
}