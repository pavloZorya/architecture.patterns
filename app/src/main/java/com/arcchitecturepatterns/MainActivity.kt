package com.arcchitecturepatterns

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arcchitecturepatterns.mvi.MviActivity
import com.arcchitecturepatterns.mvp.MvpActivity
import com.arcchitecturepatterns.mvvm.MvvmActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import timber.log.Timber.DebugTree


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("onCreate -> configure")

        setUpClickListener()

        Timber.plant(DebugTree())
    }

    private fun setUpClickListener() {
        Timber.i("setUpClickListener")
        mvvmButton.setOnClickListener {
            Timber.i("mvvmButton click")
            openActivity(MvvmActivity::class.java)
        }

        mviButton.setOnClickListener {
            Timber.i("mviButton click")
            openActivity(MviActivity::class.java)
        }

        mvpButton.setOnClickListener {
            Timber.i("mvpButton click")
            openActivity(MvpActivity::class.java)
        }
    }

    private fun openActivity(activityClass: Class<out Activity>) {
        Timber.d("openActivity -> class: ${activityClass.canonicalName}")
        startActivity(
            Intent(
                this,
                activityClass
            )
        )
    }
}
