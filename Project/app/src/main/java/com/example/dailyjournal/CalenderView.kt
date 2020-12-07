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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class CalenderView : AppCompatActivity(){
    var mCal: CalendarView? = null
    private lateinit var date: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calander_view)
        mCal = findViewById(R.id.simpleCalendarView)
        date = findViewById(R.id.date)

        // When someone clicks on date opens that days prompt
        mCal?.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            //Should open a JournalEntry here of specific date
            var monthplus = month+1
            var day_str = dayOfMonth.toString()
            var month_str = monthplus.toString()

            if (dayOfMonth < 10){
                day_str = "0" + dayOfMonth
            }
            if(month < 10){
                month_str = "0" + monthplus
            }
            var date_formatted = "$year-" + month_str + "-" + day_str



            val arrayList = intent.getStringArrayListExtra("arrayList")
            var found = false;

            if (arrayList != null) {
                arrayList.forEach {
                    if (it.contains(date_formatted.toString())){
                        date.text = date_formatted
                        Toast.makeText(applicationContext, date_formatted, Toast.LENGTH_LONG).show()
                        val intent = Intent(this, com.example.dailyjournal.ReadPast::class.java)
                        intent.putExtra("date", date_formatted)
                        intent.putExtra("arrayList", arrayList)
                        startActivity(intent)
                        found = true;
                    }

                }
            }

            var cur = LocalDateTime.now().toString()
            if (cur.contains(date_formatted) && !found) {
                val intent = Intent(applicationContext, CompleteDailyPrompt::class.java)
                startActivityForResult(intent, 0)
            } else if (!found){
                date.text = date_formatted
                Toast.makeText(applicationContext, "No entry written for $date_formatted", Toast.LENGTH_LONG).show()
            }

        })

    }

    // Creates option menu to allow you to select a menu or day.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }

    // Startes intents based off options menu selection.
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