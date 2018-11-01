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
import android.util.Log

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

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += (0.1f / sides) * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                Log.d("ANIMATION_STARTED at", "${System.currentTimeMillis()}")
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                Log.d("ANIMATION_STOPPED at", "${System.currentTimeMillis()}")
                animated = false
            }
        }
    }

    data class TOLSNode(var i : Int, val state : State = State()) {

        private var next : TOLSNode? = null

        private var prev : TOLSNode? = null

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < nodes - 1) {
                next = TOLSNode(i + 1)
                next?.prev = this
            }
        }

        fun draw(canvas : Canvas, paint : Paint) {
            canvas.drawTOLSNode(i, state.scale, paint)
            next?.draw(canvas, paint)
        }

        fun update(cb : (Int, Float) -> Unit) {
            state.update {
                cb(i, it)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            state.startUpdating(cb)
        }

        fun getNext(dir : Int, cb : () -> Unit) : TOLSNode {
            var curr : TOLSNode? = this.prev
            if (dir == 1) {
                curr = next
            }
            if (curr != null) {
                return curr
            }
            cb()
            return this
        }
    }
}