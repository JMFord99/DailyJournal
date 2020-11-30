package com.example.dailyjournal

import android.os.Bundle
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class CalenderView : AppCompatActivity(){
    var mCal: CalendarView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calander_view)
        mCal = findViewById(R.id.simpleCalendarView)
        mCal?.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            //Should open a ToDoManager here?
            Toast.makeText(applicationContext, "$month/$dayOfMonth/$year", Toast.LENGTH_LONG).show()
        })

    }
}