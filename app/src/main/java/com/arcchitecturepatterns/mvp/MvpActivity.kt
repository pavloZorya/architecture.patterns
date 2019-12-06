package com.arcchitecturepatterns.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arcchitecturepatterns.R

class MvpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp)
        title = "MVP"
    }
}