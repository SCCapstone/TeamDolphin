package com.example.teamdolphin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat

private const val stroke_width = 6f


class MyCanvas(context: Context): View(context) {

    //background color of canvas
    private val backcolor = ResourcesCompat.getColor(resources, R.color.color_green, null)
    private lateinit var canvas1: Canvas
    private lateinit var bitmap1: Bitmap

    //pen color
    private val pencolor = ResourcesCompat.getColor(resources, R.color.black, null)

    private val touchTolerance = ViewConfiguration.get(context).scaledEdgeSlop

    private val paint = Paint().apply{
        color=pencolor
        isAntiAlias=true
        isDither=true
        style = Paint.Style.STROKE
        strokeWidth=stroke_width
        strokeCap=Paint.Cap.ROUND
        strokeJoin=Paint.Join.ROUND
    }

    private var path = Path()
    private var motionX = 0f
    private var motionY = 0f
    private var currentX = 0f
    private var currentY = 0f



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if(::bitmap1.isInitialized)bitmap1.recycle()


        bitmap1 = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas1 = Canvas(bitmap1)

        canvas1.drawColor(backcolor)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap1, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        motionX = event!!.x
        motionY = event!!.y
        when(event.action)
        {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUP()
        }
        return true
    }

    private fun touchUP() {
        path.reset()
    }

    private fun touchMove() {
        val dx = Math.abs(motionX-currentX)
        val dy = Math.abs(motionY-currentY)
        if(dx>=touchTolerance || dy>=touchTolerance)
        {
            path.quadTo(currentX,currentY,(motionX+currentX)/2, (motionY+currentY)/2)
            currentX = motionX
            currentY = motionY
            canvas1.drawPath(path,paint)
        }
        invalidate()
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionX,motionY)
        currentX=motionX
        currentY=motionY
    }
}