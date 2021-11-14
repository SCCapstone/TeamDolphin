package com.example.teamdolphin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.content.res.ResourcesCompat


class MyCanvas(context: Context): View(context) {

    private val backcolor = ResourcesCompat.getColor(resources, R.color.color_green, null)
    private lateinit var canvas1: Canvas
    private lateinit var bitmap1: Bitmap

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
}