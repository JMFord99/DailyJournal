package com.example.dailyjournal

import android.content.Intent
import java.text.ParseException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.database.sqlite.SQLiteDatabase

class JournalEntry {

    var prompt = String()
    var date = LocalDateTime.now()
    var mood = 0 as Integer
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

    fun toLog(): String {
        return ("Prompt:" + prompt + ITEM_SEP + "Mood:" + mood
                + ITEM_SEP + "Status:" + status + ITEM_SEP + "Date:"
                + FORMAT.format(date) + "\n")
    }

    // Create a new ToDoItem from data packaged in an Intent

    internal constructor(intent: Intent) {

        prompt = intent.getStringExtra(JournalEntry.PROMPT).toString()
        status = Status.valueOf(intent.getStringExtra(JournalEntry.STATUS).toString())
        mood = Integer.valueOf(intent.getStringExtra(JournalEntry.MOOD).toString()) as Integer
        //mood = intent.getStringExtra(intent.getStringExtra(JournalEntry.MOOD)) as Integer

        /*try {
            date = LocalDateTime.parse(intent.getStringExtra(JournalEntry.DATE), FORMAT)
            lastUpdated = LocalDateTime.parse(intent.getStringExtra(JournalEntry.LASTUPDATED), FORMAT)
        } catch (e: ParseException) {
            date = LocalDateTime.now()
            lastUpdated = LocalDateTime.now()
        }*/

    }
    
    //sqlite 
    fun createDatabase(db: SQLiteDatabase){
        db.execSQL("CREATE TABLE entries(" +
                "entry_date DATE," +
                "entry_text STRING," +
                "mood INTEGER);")
    }

    fun addEntry(db: SQLiteDatabase){
        db.execSQL("INSERT INTO entries VALUES (" + dateFormat(date) + ", " + prompt + ", " + mood + ");")
    }

    fun geEntry(db: SQLiteDatabase){
        db.execSQL("SELECT entry_text, mood FROM entries WHERE entry_date = " + dateFormat(date) + ";")
    }

    fun dateFormat(date: LocalDateTime): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = date.format(formatter)
        return formatted
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
