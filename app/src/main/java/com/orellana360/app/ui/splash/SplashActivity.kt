package com.orellana360.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.orellana360.app.MainActivity
import com.orellana360.app.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLogo.alpha = 0f
        binding.tvSubtitle.alpha = 0f
        binding.progressBar.alpha = 0f

        binding.tvLogo.animate()
            .alpha(1f)
            .setDuration(800)
            .start()

        binding.tvSubtitle.animate()
            .alpha(1f)
            .setDuration(800)
            .setStartDelay(300)
            .start()

        binding.progressBar.animate()
            .alpha(1f)
            .setDuration(600)
            .setStartDelay(600)
            .start()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 2000)
    }
}
