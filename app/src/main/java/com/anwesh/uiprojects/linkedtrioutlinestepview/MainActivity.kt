package com.anwesh.uiprojects.linkedtrioutlinestepview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.anwesh.uiprojects.trioutlinestepview.TriOutLineStepView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view : TriOutLineStepView = TriOutLineStepView.create(this)
        fullScreen()
        view.addOnAnimationListener({createToast("${it} is complete")}, {createToast("${it} is reset")})
    }

    fun createToast(txt : String) {
        Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
    }
}

fun MainActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}