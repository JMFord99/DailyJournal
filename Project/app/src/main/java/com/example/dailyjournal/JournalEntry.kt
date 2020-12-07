package com.example.dailyjournal

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat.requestPermissions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.io.*

class JournalEntry {

    var prompt = String()
    var date = LocalDateTime.now()
    var mood = 0 as Integer
    var status = Status.INCOMPLETE
    var favorite = Favorite.NO
    var lastUpdated = LocalDateTime.now()
    lateinit var dataIntent : Intent
    private lateinit var file: File
    private val filename = "pastPrompts"


    enum class Status {
        INCOMPLETE, COMPLETE
    }

    enum class Favorite {
        YES, NO
    }

    internal constructor(prompt: String, mood: Integer, status: Status, date: LocalDateTime, lastUpdated: LocalDateTime, favorite: Favorite) {
        this.prompt = prompt
        this.mood = mood
        this.status = status
        this.date = date
        this.lastUpdated = lastUpdated
        this.favorite = favorite
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
        favorite = Favorite.valueOf(intent.getStringExtra(JournalEntry.FAVORITE).toString())
        /*try {
            date = LocalDateTime.parse(intent.getStringExtra(JournalEntry.DATE), FORMAT)
            lastUpdated = LocalDateTime.parse(intent.getStringExtra(JournalEntry.LASTUPDATED), FORMAT)
        } catch (e: ParseException) {
            date = LocalDateTime.now()
            lastUpdated = LocalDateTime.now()
        }*/

    }


    // saver for file
    fun save(context: Context) {

        //val filePath = context.filesDir.absolutePath
        //file = File("$filePath/pastPrompts")

        val fos: FileOutputStream =
            context.openFileOutput("pastPrompts", Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(this)
        os.close()
        fos.close()
    }

    //helper
    fun get(context: Context): JournalEntry {
        //val filePath = context.filesDir.absolutePath
        //file = File("$filePath/pastPrompts")

        val fis: FileInputStream = context.openFileInput("pastPrompts")
        val ist = ObjectInputStream(fis)
        val entry = ist.readObject() as JournalEntry
        ist.close()
        fis.close()
        return entry
    }
    
    //sqlite 
    /*fun createDatabase(db: SQLiteDatabase){
        db.execSQL("CREATE TABLE entries(" +
                "entry_date DATE," +
                "entry_text STRING," +
                "mood INTEGER);")
    }*/

    /*fun addEntry(db: SQLiteDatabase){
        db.execSQL("INSERT INTO entries VALUES (" + dateFormat(date) + ", " + prompt + ", " + mood + ");")
    }

    fun geEntry(db: SQLiteDatabase){
        db.execSQL("SELECT entry_text, mood FROM entries WHERE entry_date = " + dateFormat(date) + ";")
    }

    fun dateFormat(date: LocalDateTime): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = date.format(formatter)
        return formatted
    }*/


    companion object {



        val ITEM_SEP = System.getProperty("line.separator")

        val PROMPT = "prompt"
        val STATUS = "status"
        val DATE = "date"
        val MOOD = "mood"
        val LASTUPDATED = "lastUpdated"
        val FAVORITE = "favorite"

        val FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")


        // Take a set of String data values and
        // package them for transport in an Intent

        fun packageIntent(intent: Intent, prompt: String,
                          mood: Integer, status: Status, date: String, lastUpdated: String, favorite: Favorite) {

            intent.putExtra(JournalEntry.PROMPT, prompt)
            intent.putExtra(JournalEntry.MOOD, mood.toString())
            intent.putExtra(JournalEntry.STATUS, status.toString())
            intent.putExtra(JournalEntry.DATE, date)
            intent.putExtra(JournalEntry.LASTUPDATED, lastUpdated)
            intent.putExtra(JournalEntry.FAVORITE, favorite.toString())

        }
    }
}
