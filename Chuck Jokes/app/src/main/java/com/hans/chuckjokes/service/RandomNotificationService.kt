package com.hans.chuckjokes.service

import android.Manifest
import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hans.chuckjokes.R
import java.util.Random

class RandomNotificationService : IntentService("RandomNotificationService") {

    private var channelInitialized = false

    override fun onCreate() {
        super.onCreate()
        initializeNotificationChannel()
    }

    private fun initializeNotificationChannel() {
        if (!channelInitialized) {
            val channel = NotificationChannel(
                "channel_id",
                "Random Joke Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            channelInitialized = true
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            showRandomNotification()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "Random Joke Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for displaying random joke notifications"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showRandomNotification() {
        // Logika untuk memilih pesan acak
        val randomMessage = getRandomMessage()

        // Pengecekan izin notifikasi
        if (areNotificationsEnabled()) {
            // Konfigurasi notifikasi
            val builder = NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Random Joke Notification")
                .setContentText(randomMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // Tampilkan notifikasi
            val notificationManager = NotificationManagerCompat.from(this)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v("BUBUBABABA", "BABAYOOOO")
                notificationManager.notify(getRandomNotificationId(), builder.build())
                return
            }
        } else {
            // Notifications are not enabled, you can redirect the user to the notification settings
            redirectToNotificationSettings()
        }
    }

    private fun areNotificationsEnabled(): Boolean {
        val notificationManager = NotificationManagerCompat.from(this)
        return notificationManager.areNotificationsEnabled()
    }

    private fun redirectToNotificationSettings() {
        // Redirect the user to the app notification settings
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        startActivity(intent)
    }

    private fun getRandomMessage(): String {
        // Implementasikan logika untuk memilih pesan acak dari koleksi pesan
        // Misalnya, Anda dapat menggunakan daftar pesan yang telah Anda siapkan.
        val messages = arrayOf(getString(R.string.notif1), getString(R.string.notif2), "Read and laugh today!")
        val random = Random()
        val index = random.nextInt(messages.size)
        return messages[index]
    }

    private fun getRandomNotificationId(): Int {
        // Generate ID notifikasi secara acak untuk memastikan notifikasi yang berbeda setiap kali
        return Random().nextInt(1000)
    }
}