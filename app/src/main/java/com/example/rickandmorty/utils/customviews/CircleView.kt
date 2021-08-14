package com.example.rickandmorty.utils.customviews

import android.content.Context
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

    init{
        paint.color = ContextCompat.getColor(context, R.color.colorPrimary)
        paint.strokeWidth = 40f
        paint.style = Paint.Style.STROKE //contructor call
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(centerOfX, centerOfY, radiusOfCircleView, paint)
        super.onDraw(canvas)

    }
}