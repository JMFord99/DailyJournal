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
import android.widget.CalendarView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import java.io.*
import java.text.ParseException
import java.time.LocalDateTime
import java.util.*


class MainActivity : ListActivity() {

    internal lateinit var mAdapter: JournalEntriesAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAlarm()


        mAdapter = JournalEntriesAdapter(applicationContext)

        listView.setFooterDividersEnabled(true)

        var footerView: TextView = layoutInflater.inflate(R.layout.footer_view, null) as TextView

        // TODO - Add footerView to ListView
        this.listView.addFooterView(footerView)

        // TODO - Attach Listener to FooterView
        footerView.setOnClickListener {
            val intent = Intent(applicationContext, com.example.dailyjournal.CompleteDailyPrompt::class.java)
            startActivityForResult(intent, ADD_TODO_ITEM_REQUEST)
        }

        // TODO - Attach the adapter to this ListActivity's ListView
        setListAdapter(mAdapter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        // TODO - Check result code and request code
        // if user submitted a new ToDoItem
        // Create a new ToDoItem from the data Intent
        // and then add it to the adapter

        if(resultCode == RESULT_OK && requestCode == ADD_TODO_ITEM_REQUEST) {
            val journalEntry: JournalEntry = JournalEntry(data as Intent)
            mAdapter.add(journalEntry)
        }

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all")
        menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_DELETE -> {
                //mAdapter.clear()
                return true
            }
            MENU_DUMP -> {
                dump()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun dump() {
        for (i in 0 until mAdapter.count) {
            val data = (mAdapter.getItem(i) as JournalEntry).toLog()
            Log.i(TAG,
                "Item " + i + ": " + data.replace(JournalEntry.ITEM_SEP, ","))
        }
    }

    // Load stored ToDoItems
    private fun loadItems() {
        var reader: BufferedReader? = null
        try {
            val fis = openFileInput(FILE_NAME)
            reader = BufferedReader(InputStreamReader(fis))

            var title: String? = null
            var mood = 50 as Integer
            var status: String? = null
            var date: LocalDateTime? = null

            do {
                title = reader.readLine();
                if (title == null)
                    break
                status = reader.readLine()
                //date = LocalDateTime.parse(reader.readLine(), JournalEntry.FORMAT)
                date = LocalDateTime.now()

                mAdapter.add(JournalEntry(title, mood,
                    JournalEntry.Status.valueOf(status), date, date))

            }
            while (true)

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

