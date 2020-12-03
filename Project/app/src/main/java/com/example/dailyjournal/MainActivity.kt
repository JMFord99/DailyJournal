package com.example.dailyjournal

import android.app.Activity
import android.app.AlarmManager
import android.app.ListActivity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.*
import java.text.ParseException
import java.time.LocalDateTime
import java.util.*


class MainActivity: AppCompatActivity() {

    internal lateinit var faves: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAlarm()

    }



    public override fun onResume() {
        super.onResume()

        // Load saved ToDoItems, if necessary


    }

    override fun onPause() {
        super.onPause()

        // Save ToDoItems

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
       super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_navigation_menu, menu)

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all")
        menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.nav_past -> {
                val intent = Intent(this, com.example.dailyjournal.CalenderView::class.java);
                startActivity(intent);
                true;
            }
            R.id.nav_faves -> {
                val intent = Intent(this, com.example.dailyjournal.FavoritesView::class.java);
                startActivity(intent);
                true;
            }
            else -> false
        }
    }







    fun setAlarm() {
        var mAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmIntent = Intent(this@MainActivity, DailyReminder::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this@MainActivity, 0, alarmIntent, 0)
        val calendar: Calendar = Calendar.getInstance()
        //mAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000L, pendingIntent)
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 20)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 1)
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    companion object {

        private val ADD_TODO_ITEM_REQUEST = 0
        private val FILE_NAME = "TodoManagerActivityData.txt"
        private val TAG = "Lab-UserInterface"

        // IDs for menu items
        private val MENU_DELETE = Menu.FIRST
        private val MENU_DUMP = Menu.FIRST + 1
    }


    }

