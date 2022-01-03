package com.jskaleel.sorkuduvai.ui.splash

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jskaleel.sorkuduvai.databinding.ActivityLauncherBinding
import com.jskaleel.sorkuduvai.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {
    private val binding: ActivityLauncherBinding by lazy {
        ActivityLauncherBinding.inflate(layoutInflater)
    }

    private val launcherViewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (launcherViewModel.isReady.value == true) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        launchMainActivity()
                        true
                    } else {
                        false
                    }
                }
            }
        )
        launcherViewModel.delaySplashScreen()
    }

    private fun launchMainActivity() {
        startActivity(MainActivity.newIntent(baseContext))
        this@LauncherActivity.finish()
    }
}