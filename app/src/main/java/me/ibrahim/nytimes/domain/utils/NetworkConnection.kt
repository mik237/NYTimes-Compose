package me.ibrahim.nytimes.domain.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnection @Inject constructor(private val connectivityManager: ConnectivityManager) {

    fun isConnected(): Boolean {
        return isWifiEnabled() || isCellularDataEnabled()
    }

    private fun isWifiEnabled(): Boolean {
        return isEnabled(NetworkCapabilities.TRANSPORT_WIFI)
    }

    private fun isCellularDataEnabled(): Boolean {
        return isEnabled(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    private fun isEnabled(transportType: Int): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasTransport(transportType)
    }

}