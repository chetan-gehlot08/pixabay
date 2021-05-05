package com.pixabay.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.pixabay.R
import com.pixabay.util.AppUtil.Companion.hasBiometricEnrolled
import com.pixabay.util.AppUtil.Companion.isHardwareAvailable
import java.util.concurrent.Executor

class FingerprintActivity : AppCompatActivity() {
    private val TAG = "FingerprintActivity"
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var executor: Executor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fingerprint)
        supportActionBar?.hide()
        biometricLoginThroughFingerprint()
    }

    private fun launchSearchActivity() {
        startActivity(Intent(this, SearchActivity::class.java))
        finish()
    }

    private fun biometricLoginThroughFingerprint() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "${getString(R.string.auth_error)} $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                    finish()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.auth_success), Toast.LENGTH_SHORT
                    )
                        .show()
                    launchSearchActivity()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, getString(R.string.auth_failed),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    finish()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_login_title))
            .setSubtitle(getString(R.string.biometric_login_subtitle))
            .setNegativeButtonText(getString(R.string.cancel))
            .build()

        if (!isHardwareAvailable(this)) {
            Toast.makeText(
                this,
                getString(R.string.fingerprint_sensor_not_supported),
                Toast.LENGTH_LONG
            ).show()
            launchSearchActivity()
        } else if (!hasBiometricEnrolled(this)) {
            Toast.makeText(this, getString(R.string.lock_settings_not_enabled), Toast.LENGTH_LONG)
                .show()
            launchSearchActivity()
        } else {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}