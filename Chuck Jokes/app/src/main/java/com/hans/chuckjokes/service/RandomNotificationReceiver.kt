package com.hans.chuckjokes.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RandomNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Di sini, Anda dapat memulai service untuk menampilkan notifikasi acak
        val serviceIntent = Intent(context, RandomNotificationService::class.java)
        context.startService(serviceIntent)
    }
}