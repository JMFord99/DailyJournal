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
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.*
import java.text.ParseException
import java.time.LocalDateTime
import java.util.*


class MainActivity: AppCompatActivity() {

    internal lateinit var mAdapter: JournalEntriesAdapter

    val arrayList = ArrayList<String>() //Creating an empty arraylist.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAlarm()

        var fav_button = findViewById<Button>(R.id.favorites_button)
        var prompt_button = findViewById<Button>(R.id.create_prompt_button)
        var past_button = findViewById<Button>(R.id.past_entries_button)
        mAdapter = JournalEntriesAdapter(applicationContext)


        fav_button.setOnClickListener {
            val intent = Intent(this, com.example.dailyjournal.FavoritesView::class.java)
            startActivity(intent)
        }

        prompt_button.setOnClickListener {
            val intent = Intent(applicationContext, CompleteDailyPrompt::class.java)
            startActivityForResult(intent, 0)
        }

        past_button.setOnClickListener {
            val intent = Intent(this, com.example.dailyjournal.CalenderView::class.java)
            intent.putExtra("arrayList", arrayList)

            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i(TAG, "Entered onActivityResult()")

        // TODO - Check result code and request code
        // if user submitted a new ToDoItem
        // Create a new ToDoItem from the data Intent
        // and then add it to the adapter

        if(resultCode == ListActivity.RESULT_OK && requestCode == ADD_TODO_ITEM_REQUEST) {
            val journalEntryItem: JournalEntry = JournalEntry(data as Intent)
            mAdapter.add(journalEntryItem)
            writeFile(journalEntryItem.date.toString() +
            "~" + journalEntryItem.title + "~" + journalEntryItem.prompt)
        }
    }



    @Throws(FileNotFoundException::class)
    private fun writeFile(str: String) {

        val fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)

        val pw = PrintWriter(BufferedWriter(OutputStreamWriter(fos)))

        pw.println(str)

        pw.close()

    }

    @Throws(IOException::class)
    private fun readFile() {

        val sep = System.getProperty("line.separator")
        val fis = openFileInput(FILE_NAME)
        val br = BufferedReader(InputStreamReader(fis))

        br.forEachLine {
            arrayList.add(it + sep)
        }

        br.close()
        /*
        // For Testing
        var data = "2020-12-07T13:35:52.140~bbbbbbbbb~entry127\n2020-12-05" +
        "T13:35:52.140~cccccccccc~entry125\n2020-12-04T13:35:52.140~ddddddddd~entry124\n2020-12-03T13:35:52.140~eeeeeeeee~entry123" +
                "\n2020-11-01T13:35:52.140~fffffffff~entry111\n2020-11-30T13:35:52.140~ggggggggg~entry11130\n" +
                "2020-11-25T13:35:52.140~hhhhhhhhh~entry1125\n2020-11-20T13:35:52.140~iiiiiiiii~entry112-\n" +
                "2020-11-15T13:35:52.140~jjjjjjjjj~entry1115\n"

        data.split("\n").forEach {
            arrayList.add(it + sep)
        }*/
    }






    public override fun onResume() {
        super.onResume()

        // Load saved ToDoItems, if necessary
        readFile()


    }

    override fun onPause() {
        super.onPause()

        // Save ToDoItems


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
       super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.nav_past -> {
                val intent = Intent(this, com.example.dailyjournal.CalenderView::class.java);
                startActivity(intent);
                true;
            }
            R.id.nav_faves -> {
                val intent = Intent(this, com.example.dailyjournal.FavoritesView::class.java);
                startActivity(intent);
                true;
            }
            else -> false
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
        val FILE_NAME = "Data.txt"
        private val TAG = "Lab-UserInterface"

        // IDs for menu items
        private val MENU_DELETE = Menu.FIRST
        private val MENU_DUMP = Menu.FIRST + 1
    }


    }

