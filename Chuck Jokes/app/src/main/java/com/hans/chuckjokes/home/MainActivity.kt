package com.hans.chuckjokes.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.hans.chuckjokes.R
import com.hans.chuckjokes.about.AboutActivity
import com.hans.chuckjokes.button.ButtonRandom
import com.hans.chuckjokes.favorite.FavoriteFragment
import com.hans.chuckjokes.random.RandomActivity
import com.hans.chuckjokes.search.ResultFragment
import com.hans.chuckjokes.search.SearchActivity
import com.hans.chuckjokes.service.RandomNotificationReceiver
import com.hans.chuckjokes.service.RandomNotificationService
import java.util.Calendar

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)


        topAppBar.setNavigationOnClickListener {

        }

        val buttonRandomJokes: ButtonRandom = findViewById(R.id.button_random)
        buttonRandomJokes.setOnClickListener(this)

        setupRandomNotificationAlarm()
    }

    override fun onClick(v: View){
        when(v.id){
            R.id.button_random ->{
                val navActivity = Intent(this, RandomActivity::class.java)
                startActivity(navActivity)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                val navSearchActivity = Intent(this, SearchActivity::class.java)
                startActivity(navSearchActivity)
                return true
            }
            R.id.action_favorite -> {
                val favoriteFragment = FavoriteFragment.newInstance()

                // Replace the existing fragment in the container
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, favoriteFragment)
                    .addToBackStack(null)  // Optional: Add the transaction to the back stack
                    .commit()
                return true
            }
            R.id.action_about -> {
                val navAbout = Intent(this, AboutActivity::class.java)
                startActivity(navAbout)
                return true
            }
            R.id.action_Language_Settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                return true
            }
            // Add more cases as needed
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupRandomNotificationAlarm() {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, RandomNotificationService::class.java)
        val pendingIntent = PendingIntent.getService(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Tentukan waktu kapan layanan akan dijalankan (misalnya, setiap 24 jam)
        val triggerAtMillis = System.currentTimeMillis() + 2000

        // Jadwalkan layanan menggunakan AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            2000,
            pendingIntent
        )
        Toast.makeText(this, "Random notification alarm set up!", Toast.LENGTH_SHORT).show()
    }
}