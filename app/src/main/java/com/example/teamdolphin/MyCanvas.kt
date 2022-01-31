package com.example.teamdolphin

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import android.graphics.Bitmap


//constant brush sizes
private const val thin_stroke_width = 1f
private const val medium_stroke_width = 6f
private const val thick_stroke_width = 12f


class MyCanvas(context: Context): View(context) {

    //background color of canvas
    private val backcolor = ResourcesCompat.getColor(resources, R.color.color_green, null)
    private lateinit var canvas1: Canvas
    private lateinit var bitmap1: Bitmap

    //brush color, intended to add helper method, current constant, black
    private val brushcolor = ResourcesCompat.getColor(resources, R.color.black, null)

    private val touchTolerance = ViewConfiguration.get(context).scaledEdgeSlop

    private val paint = Paint().apply{
        color=brushcolor
        isAntiAlias=true
        isDither=true
        style = Paint.Style.STROKE
        strokeWidth=medium_stroke_width
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

    //Method: on user unclicking reset the path
    private fun touchUP() {
        path.reset()
    }

    //Method: on user moving while clicking put draw path on canvas
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

    //Method: on user clicking canvas, reset path and set x,y coordinates for canvas drawing
    private fun touchStart() {
        path.reset()
        path.moveTo(motionX,motionY)
        currentX=motionX
        currentY=motionY
    }
}