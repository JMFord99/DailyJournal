package com.example.dailyjournal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/*
This class is used to read past data that is stored. It reads an arrayList which is
packaged in the intent. the list is created from reading the file line by line.
Each line in the array list is one journal entry separated by "~". We separate the entry
to get all the data out of it.
 */
class ReadPast : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read_past)

        var prompt = findViewById<TextView>(R.id.prompt)
        var entry = findViewById<TextView>(R.id.entry)
        var mood = findViewById<TextView>(R.id.mood)
        var date = findViewById<TextView>(R.id.date)

        val arrayList = intent.getStringArrayListExtra("arrayList")
        val chosen_date = intent.getStringExtra("date")

        if (arrayList != null) {
            arrayList.forEach {
                if (it.contains(chosen_date.toString())){
                    var data = it.split("~")
                    date.text = chosen_date
                    prompt.text = data[1]
                    entry.text = data[2]
                    if (data[3].contains("1"))
                        mood.text = "Favorited"
                }

            }
        }

    }
}