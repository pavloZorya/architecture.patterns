package com.arcchitecturepatterns.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arcchitecturepatterns.R
import timber.log.Timber

class MvvmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate -> configure")
        setContentView(R.layout.activity_mvvm)
        title = "MVVM"
    }
}