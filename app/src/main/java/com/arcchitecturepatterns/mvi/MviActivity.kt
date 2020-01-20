package com.arcchitecturepatterns.mvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arcchitecturepatterns.R
import timber.log.Timber

class MviActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate -> configure")
        setContentView(R.layout.activity_mvi)
        title = "MVI"
    }
}
