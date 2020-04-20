package com.example.infoexample.extra

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class InternetCheckManager {

    interface OnInternetStateChange{
        fun onInternetStateChange(isConnected : Boolean)
    }

    companion object {
        fun bindInternetListenerIn(lifecycleOwner: LifecycleOwner, onInternetStateChange : OnInternetStateChange?, context: Context) {
            BoundInternetListener(lifecycleOwner, onInternetStateChange, context)
        }
    }

    class BoundInternetListener(
        lifecycleOwner: LifecycleOwner,
        val onInternetStateChange : OnInternetStateChange?,
        private val mContext: Context
    ) : LifecycleObserver {
        var broadcastReceiver: BroadcastReceiver? = null

        init {
            lifecycleOwner.lifecycle.addObserver(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun addInternetCheckListener() {
            if (broadcastReceiver == null) {
                val filter = IntentFilter()
                filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)

                broadcastReceiver = object : BroadcastReceiver() {
                    override fun onReceive(_context: Context, intent: Intent) {
                        var connMgr: ConnectivityManager =
                            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                        var isConnected = false
                        if (connMgr != null) {
                            var networkCapabilities = connMgr.activeNetworkInfo
                            if (networkCapabilities != null) {
                                if (networkCapabilities.type == ConnectivityManager.TYPE_WIFI) {
                                    isConnected = true
                                } else if (networkCapabilities.type == ConnectivityManager.TYPE_MOBILE) {
                                    isConnected = true
                                }
                            } else {
                                isConnected = false
                            }
                        }

                        if(onInternetStateChange != null){
                            onInternetStateChange.onInternetStateChange(isConnected)
                        }else{
                            if(!isConnected){
                                Toast.makeText(mContext, "Please Connect Internet", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }

                mContext.registerReceiver(broadcastReceiver, filter)
            }
        }


        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun removeInternetListener() {
            if (broadcastReceiver != null) {
                mContext.unregisterReceiver(broadcastReceiver)
                broadcastReceiver = null
            }
        }
    }
}