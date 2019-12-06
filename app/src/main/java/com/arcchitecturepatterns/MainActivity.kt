package com.arcchitecturepatterns

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arcchitecturepatterns.mvi.MviActivity
import com.arcchitecturepatterns.mvp.MvpActivity
import com.arcchitecturepatterns.mvvm.MvvmActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpClickListener()
    }

    private fun setUpClickListener() {
        mvvmButton.setOnClickListener {
            openActivity(MvvmActivity::class.java)
        }

        mviButton.setOnClickListener {
            openActivity(MviActivity::class.java)
        }

        mvpButton.setOnClickListener {
            openActivity(MvpActivity::class.java)
        }
    }

    private fun openActivity(activityClass: Class<out Activity>) {
        startActivity(
            Intent(
                this,
                activityClass
            )
        )
    }
}
