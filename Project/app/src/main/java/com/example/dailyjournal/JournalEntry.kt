package com.example.dailyjournal

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat.requestPermissions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.io.*


/*

This class contains the data for one journal entry. Each entry is stores in the mAdapter
 */
class JournalEntry {

    var prompt = String()
    var title = String()
    var date = LocalDateTime.now()
    var mood = 0 as Integer
    var status = Status.INCOMPLETE
    var favorite = Favorite.NO
    lateinit var dataIntent : Intent
    private lateinit var file: File
    private val filename = "pastPrompts"


    enum class Status {
        INCOMPLETE, COMPLETE
    }

    enum class Favorite {
        YES, NO
    }

    // Constructor for a journal entry object
    // Contians a title, which is a header, tue prompt, which is their jorunal entry,
    // a mood between 1 and 100, a complete/incomplete status, a date, and whether or not the post
    // is a favorite.
    internal constructor(title: String, prompt: String, mood: Integer, status: Status, date: LocalDateTime, favorite: Favorite) {
        this.title = title
        this.prompt = prompt
        this.mood = mood
        this.status = status
        this.date = date
        this.favorite = favorite
    }

    // Converts the current object to a single string for logging purposes
    fun toLog(): String {
        return ("Title" + title + "Prompt:" + prompt + ITEM_SEP + "Mood:" + mood
                + ITEM_SEP + "Status:" + status + ITEM_SEP + "Date:"
                + FORMAT.format(date) + "\n")
    }

    // Create a new ToDoItem from data packaged in an Intent
    internal constructor(intent: Intent) {

        prompt = intent.getStringExtra(JournalEntry.PROMPT).toString()
        title = intent.getStringExtra(JournalEntry.TITLE).toString()
        status = Status.valueOf(intent.getStringExtra(JournalEntry.STATUS).toString())
        mood = Integer.valueOf(intent.getStringExtra(JournalEntry.MOOD).toString()) as Integer
        //mood = intent.getStringExtra(intent.getStringExtra(JournalEntry.MOOD)) as Integer
        favorite = Favorite.valueOf(intent.getStringExtra(JournalEntry.FAVORITE).toString())
        /*try {
            date = LocalDateTime.parse(intent.getStringExtra(JournalEntry.DATE), FORMAT)
            lastUpdated = LocalDateTime.parse(intent.getStringExtra(JournalEntry.LASTUPDATED), FORMAT)
        } catch (e: ParseException) {
            date = LocalDateTime.now()
            lastUpdated = LocalDateTime.now()
        }*/

    }

    companion object {



        val ITEM_SEP = System.getProperty("line.separator")

        val FILE_NAME = "Data.txt"
        val PROMPT = "prompt"
        val TITLE = "title"
        val STATUS = "status"
        val DATE = "date"
        val MOOD = "mood"
        val FAVORITE = "favorite"

        val FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")


        // Take a set of String data values and
        // package them for transport in an Intent

        fun packageIntent(intent: Intent, title: String, prompt: String, mood: Integer, status: Status, date: String, favorite: Favorite) {

            intent.putExtra(JournalEntry.TITLE, title)
            intent.putExtra(JournalEntry.PROMPT, prompt)
            intent.putExtra(JournalEntry.MOOD, mood.toString())
            intent.putExtra(JournalEntry.STATUS, status.toString())
            intent.putExtra(JournalEntry.DATE, date)
            intent.putExtra(JournalEntry.FAVORITE, favorite.toString())

        }
    }
}
