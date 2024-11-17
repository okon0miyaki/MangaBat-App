package com.okon0miyaki.mywebview

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        Handler().postDelayed({
            val intent = Intent(
                this@SplashScreenActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }, 1200)
    }
}