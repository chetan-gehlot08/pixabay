package com.pixabay.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

class AppUtil {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }

        fun createLoadingFeature(context: Context): CircularProgressDrawable {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            return circularProgressDrawable
        }

        private fun isNetworkAvailableNew(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                return when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    //for other device how are able to connect with Ethernet
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    //for check internet over Bluetooth
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                    else -> false
                }
            } else {
                return connectivityManager.activeNetworkInfo?.isConnected ?: false
            }
        }

        fun isHardwareAvailable(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val bm = BiometricManager.from(context)
                val canAuthenticate =
                    bm.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
                !(canAuthenticate == BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE || canAuthenticate == BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE)
            } else {
                false
            }
        }

        fun hasBiometricEnrolled(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val bm = BiometricManager.from(context)
                val canAuthenticate =
                    bm.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
                (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS)
            } else {
                false
            }
        }

        fun hasDeviceCredentials(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val bm = BiometricManager.from(context)
                val canAuthenticate =
                    bm.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS)
            } else {
                false
            }
        }
    }
}