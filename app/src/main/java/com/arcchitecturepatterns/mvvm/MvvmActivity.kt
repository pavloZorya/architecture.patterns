package com.arcchitecturepatterns.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arcchitecturepatterns.R

class MvvmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)
        title = "MVVM"
    }
}