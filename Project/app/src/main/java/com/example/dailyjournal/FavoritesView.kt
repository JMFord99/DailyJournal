package com.example.dailyjournal

import android.app.ListActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.text.ParseException
import java.time.LocalDateTime


class FavoritesView : AppCompatActivity() {

    private lateinit var favorites: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites)

        favorites = findViewById<ListView>(R.id.fav_list_view)

        // Gets arraylist of filled journal entries
        val arrayList = intent.getStringArrayListExtra("arrayList")
        var found = false;
        if (arrayList != null) {
            val listOne = arrayListOf<String>()

            // Searches the arraylist for the user selected date.
            var i = 0
            for (i in 0 until (arrayList.size - 1)) {
                var data = arrayList.get(i).split("~")
                if (data[3].contains("1")) {
                    listOne.add(data[1])
                }
            }

            val listItems = arrayOfNulls<String>(listOne.size)
            i = 0;
            for (i in 0 until listOne.size) {
                listItems[i] = listOne.get(i)
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
            favorites.adapter = adapter
        }
    }
}
/*
class FavoritesView : ListActivity() {
    var mCal: CalendarView? = null
    private lateinit var date: TextView
    internal lateinit var mAdapter: JournalEntriesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.footer_view) crashes program if left in

        val filter = intent.getStringExtra("date")
        mAdapter = JournalEntriesAdapter(applicationContext)


        listView.setFooterDividersEnabled(true)

        var footerView: TextView = layoutInflater.inflate(R.layout.footer_view, null) as TextView

        // TODO - Add footerView to ListView
        this.listView.addFooterView(footerView)

        // TODO - Attach Listener to FooterView
        footerView.setOnClickListener {
            val intent = Intent(applicationContext, CompleteDailyPrompt::class.java)
            startActivityForResult(intent, ADD_TODO_ITEM_REQUEST)
        }

        // TODO - Attach the adapter to this ListActivity's ListView
        setListAdapter(mAdapter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        // TODO - Check result code and request code
        // if user submitted a new ToDoItem
        // Create a new ToDoItem from the data Intent
        // and then add it to the adapter

        if(resultCode == AppCompatActivity.RESULT_OK && requestCode == ADD_TODO_ITEM_REQUEST) {
            val journalEntry: JournalEntry = JournalEntry(data as Intent)
            mAdapter.add(journalEntry)
        }

    }

    fun dump() {
        for (i in 0 until mAdapter.count) {
            val data = (mAdapter.getItem(i) as JournalEntry).toLog()
            Log.i(
                TAG,
                "Item " + i + ": " + data.replace(JournalEntry.ITEM_SEP, ","))
        }
    }

    // Load stored ToDoItems
    private fun loadItems() {
        var reader: BufferedReader? = null
        try {
            val fis = openFileInput(FILE_NAME)
            reader = BufferedReader(InputStreamReader(fis))

            var lines = reader.readLines()

            var title: String? = null
            var entry: String? = null
            var mood: Integer?
            var status: String? = null
            var favorite: String?
            var date: LocalDateTime? = null

            var count = 1

            do {
                title = reader.readLine();
                entry = reader.readLine();
                if (title == null)
                    break
                if (entry == null)
                    break
                status = reader.readLine()
                favorite = reader.readLine()
                //date = LocalDateTime.parse(reader.readLine(), JournalEntry.FORMAT)
                date = LocalDateTime.now()
                mood = Integer.valueOf(reader.readLine()) as Integer

                if(favorite == "YES"){
                    mAdapter.add(JournalEntry(title, entry, mood,
                        JournalEntry.Status.valueOf(status), date, JournalEntry.Favorite.valueOf(favorite)))
                }
                count++
            }
            while (count < 5)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        } finally {
            if (null != reader) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }


    // Save ToDoItems to file
    private fun saveItems() {
        var writer: PrintWriter? = null
        try {
            val fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            writer = PrintWriter(
                BufferedWriter(
                    OutputStreamWriter(
                        fos)
                )
            )

            for (idx in 0 until mAdapter.count) {

                writer.println(mAdapter.getItem(idx))

            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            writer?.close()
        }
    }


    companion object {

        private val ADD_TODO_ITEM_REQUEST = 0
        private val FILE_NAME = "pastPrompts.txt"
        private val TAG = "Lab-UserInterface"

        // IDs for menu items
        private val MENU_DELETE = Menu.FIRST
        private val MENU_DUMP = Menu.FIRST + 1
    }

    public override fun onResume() {
        super.onResume()

        // Load saved ToDoItems, if necessary

        if (mAdapter.count == 0)
            loadItems()
    }




    override fun onPause() {
        super.onPause()

        // Save ToDoItems

        saveItems()

    }


}*/