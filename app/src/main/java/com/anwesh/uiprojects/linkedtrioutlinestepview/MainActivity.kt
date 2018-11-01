package com.anwesh.uiprojects.linkedtrioutlinestepview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.trioutlinestepview.TriOutLineStepView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TriOutLineStepView.create(this)
    }
}
