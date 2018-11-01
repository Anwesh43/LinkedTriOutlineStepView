package com.anwesh.uiprojects.trioutlinestepview

/**
 * Created by anweshmishra on 01/11/18.
 */

import android.view.View
import android.view.MotionEvent
import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color

val nodes : Int = 5
val sides : Int = 3

fun Float.divideScale(j : Int, n : Int) : Float = Math.min(1f/ n, Math.max(0f, this - (1f / n) * j))  * n

fun Canvas.drawTOLSNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = w / (nodes + 1)
    val size : Float = gap / 3
    val deg : Float = 360f / sides
    paint.strokeWidth = Math.min(w, h) / 60
    paint.strokeCap = Paint.Cap.ROUND
    paint.color = Color.parseColor("#673AB7")
    save()
    translate(i * gap + gap, h/2)
    for (j in 0..(sides - 1)) {
        val sc : Float = scale.divideScale(j, sides)
        save()
        rotate((deg * j + deg / 2) * sc)
        drawLine(0f, 0f, -size, 0f, paint)
        restore()
    }
    restore()
}

class TriOutLineStepView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}