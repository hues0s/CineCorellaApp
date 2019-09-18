package com.huesosco.cinecorellaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.decorView.systemUiVisibility = (
                SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or SYSTEM_UI_FLAG_FULLSCREEN
                )


        val image = findViewById<ImageView>(R.id.splash_screen_image)
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_animation)
        image.startAnimation(animation)


        val homeIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

}
