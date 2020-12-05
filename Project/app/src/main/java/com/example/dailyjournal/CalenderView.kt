package com.example.dailyjournal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class CalenderView : AppCompatActivity(){
    var mCal: CalendarView? = null
    private lateinit var date: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calander_view)
        mCal = findViewById(R.id.simpleCalendarView)
        date = findViewById(R.id.date)

        mCal?.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            //Should open a JournalEntry here of specfic date
            date.text = "$month/$dayOfMonth/$year"
            Toast.makeText(applicationContext, "$month/$dayOfMonth/$year", Toast.LENGTH_LONG).show()
            val intent = Intent(this, com.example.dailyjournal.FavoritesView::class.java);
            startActivity(intent);
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_navigation_menu, menu)

        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Delete all")
        menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "Dump to log")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.nav_past -> {
                val intent = Intent(this, CalenderView::class.java);
                startActivity(intent);
                true;
            }
            R.id.nav_faves -> {
                val intent = Intent(this, FavoritesView::class.java);
                startActivity(intent);
                true;
            }
            else -> false
        }
    }
}