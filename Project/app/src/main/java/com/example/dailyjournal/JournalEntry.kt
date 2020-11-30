package com.example.dailyjournal

import android.content.Intent
import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class JournalEntry {

    var prompt = String()
    var date = LocalDateTime.now()
    var mood = 50 as Integer
    var status = Status.INCOMPLETE
    var lastUpdated = LocalDateTime.now()

    enum class Status {
        INCOMPLETE, COMPLETE
    }

    internal constructor(prompt: String, mood: Integer, status: Status, date: LocalDateTime, lastUpdated: LocalDateTime) {
        this.prompt = prompt
        this.mood = mood
        this.status = status
        this.date = date
        this.lastUpdated = lastUpdated
    }

    // Create a new ToDoItem from data packaged in an Intent

    internal constructor(intent: Intent) {

        prompt = intent.getStringExtra(JournalEntry.PROMPT).toString()
        status = Status.valueOf(intent.getStringExtra(JournalEntry.STATUS).toString())
        mood = intent.getStringExtra(intent.getStringExtra(JournalEntry.MOOD)) as Integer

        try {
            date = LocalDateTime.parse(intent.getStringExtra(JournalEntry.DATE), FORMAT)
            lastUpdated = LocalDateTime.parse(intent.getStringExtra(JournalEntry.LASTUPDATED), FORMAT)
        } catch (e: ParseException) {
            date = LocalDateTime.now()
            lastUpdated = LocalDateTime.now()
        }

    }


    companion object {

        val ITEM_SEP = System.getProperty("line.separator")

        val PROMPT = "prompt"
        val STATUS = "status"
        val DATE = "date"
        val MOOD = "mood"
        val LASTUPDATED = "lastUpdated"

        val FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")


        // Take a set of String data values and
        // package them for transport in an Intent

        fun packageIntent(intent: Intent, prompt: String,
                          mood: Integer, status: Status, date: String, lastUpdated: String) {

            intent.putExtra(JournalEntry.PROMPT, prompt)
            intent.putExtra(JournalEntry.MOOD, mood.toString())
            intent.putExtra(JournalEntry.STATUS, status.toString())
            intent.putExtra(JournalEntry.DATE, date)
            intent.putExtra(JournalEntry.LASTUPDATED, lastUpdated)
        }
    }



}