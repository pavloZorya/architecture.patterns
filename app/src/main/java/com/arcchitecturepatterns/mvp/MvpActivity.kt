package com.arcchitecturepatterns.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arcchitecturepatterns.R
import timber.log.Timber

class MvpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate -> configure")
        setContentView(R.layout.activity_mvp)
        title = "MVP"
    }
}