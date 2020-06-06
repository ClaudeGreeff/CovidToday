package com.app.covid

import LifecycleLogger
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class SplashScreen: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(LifecycleLogger(this))

        if (!isTaskRoot
            && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                && intent.action != null
                    && intent.action == Intent.ACTION_MAIN
        ) {
            finish()
            return
        }

        // Hide ActionBar
        window.requestFeature(Window.FEATURE_ACTION_BAR)

        var label: String
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            label = "Version " + pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            label = "Version " + e.localizedMessage
            e.printStackTrace()
        }

        Timber.i(label)
        Timber.i("This is the splashscreen check")

        startMainActivity()
    }

    private fun startMainActivity() {
        val i = Intent(this@SplashScreen, MainActivity::class.java)
        startIntent(i)
    }

    private fun startIntent(i: Intent) {
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(i)
        finish()
    }
}