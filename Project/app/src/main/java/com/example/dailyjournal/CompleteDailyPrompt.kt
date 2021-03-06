package com.example.dailyjournal

import android.Manifest
import android.app.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import java.time.LocalDateTime
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import java.time.LocalDate
import java.util.*

class CompleteDailyPrompt: Activity() {
    private var mDate: LocalDateTime? = null
    private var mStatusRadioGroup: RadioGroup? = null
    private var mFavoriteRadioGroup: RadioGroup? = null
    private var mTitle: TextView? = null
    private var mEntryText: EditText? = null
    private var mDefaultStatusButton: RadioButton? = null
    private var mDefaultPriorityButton: RadioButton? = null
    private var mood: TextView? = null

    private val status: JournalEntry.Status
        get() {
            when (mStatusRadioGroup!!.checkedRadioButtonId) {
                R.id.statusDone -> {
                    return JournalEntry.Status.COMPLETE
                }
                else -> {
                    return JournalEntry.Status.INCOMPLETE
                }
            }
        }

    private val favorite: JournalEntry.Favorite
        get() {
            when (mFavoriteRadioGroup!!.checkedRadioButtonId) {
                R.id.status_yes -> {
                    return JournalEntry.Favorite.YES
                }
                else -> {
                    return JournalEntry.Favorite.NO
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complete_prompt)

        //val p = Prompts()
        var d = LocalDate.now() as LocalDate

        //val prompt = p.getPrompt(d)
        //val prompt = p.get(d, this)

        mEntryText = findViewById<View>(R.id.entry) as EditText
        mTitle = findViewById<View>(R.id.prompt) as EditText
        mDefaultStatusButton = findViewById<View>(R.id.statusNotDone) as RadioButton
        mDefaultPriorityButton = findViewById<View>(R.id.status_yes) as RadioButton
        mStatusRadioGroup = findViewById<View>(R.id.statusGroup) as RadioGroup
        mFavoriteRadioGroup = findViewById<View>(R.id.status_group) as RadioGroup
        //dateView = findViewById<View>(R.id.date) as TextView
        //timeView = findViewById<View>(R.id.time) as TextView
        mood = findViewById<View>(R.id.mood) as TextView
        val seeker = findViewById<SeekBar>(R.id.seekBar)


        // TODO - Set up OnClickListener for the Reset Button
        val resetButton = findViewById<View>(R.id.resetButton) as Button
        resetButton.setOnClickListener {
            Log.i(TAG, "Entered resetButton.OnClickListener.onClick()")
            //setDefaultDateTime()
            mEntryText!!.setText("")
            mStatusRadioGroup!!.check(R.id.statusNotDone)
            mFavoriteRadioGroup!!.check(R.id.status_yes)
        }

        seeker?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                mood!!.setText(progress.toString())

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        // Set up OnClickListener for the Submit Button

        val submitButton = findViewById<View>(R.id.submitButton) as Button
        submitButton.setOnClickListener {
            // TODO - gather ToDoItem data

            var entry = mEntryText!!.getText().toString()
            var title = mTitle!!.text.toString()
           // var date = dateString + " " + timeString
            var date = LocalDateTime.now()
            //var priority = priority
            var status = status
            var favorite = favorite

            var mood = 10 as Integer
            //var mood = Integer.valueOf(mood!!.getText().toString()) as Integer

            //val entry = JournalEntry(entry, mood, status, date as LocalDateTime, lastUpdated = date, favorite = favorite)
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_PERMISSION
            )
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_READ_PERMISSION
            )

            // TODO - return data Intent and finish
            val data = Intent()
            JournalEntry.packageIntent(data, title, entry, mood, status, date.toString(), favorite)

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }



    class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            // Use the current date as the default date in the picker

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // Create a new instance of DatePickerDialog and return it
            return DatePickerDialog(activity, this, year, month, day)
        }

        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            setDateString(year, monthOfYear, dayOfMonth)
            val dateView: TextView = activity.findViewById(R.id.date)
            dateView.text = dateString
        }

    }

    // DialogFragment used to pick a ToDoItem deadline time

    class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            // Use the current time as the default values for the picker
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            // Create a new instance of TimePickerDialog and return
            return TimePickerDialog(activity, this, hour, minute, true)
        }

        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            setTimeString(hourOfDay, minute, 0)
            val timeView: TextView = activity.findViewById(R.id.time)
            timeView.text = timeString
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment()
        newFragment.show(fragmentManager, "datePicker")
    }

    private fun showTimePickerDialog() {
        val newFragment = TimePickerFragment()
        newFragment.show(fragmentManager, "timePicker")
    }

    companion object {
        val REQUEST_WRITE_PERMISSION = 1
        val REQUEST_READ_PERMISSION = 2

        // 7 days in milliseconds - 7 * 24 * 60 * 60 * 1000

        private val TAG = "Lab-UserInterface"

        private var timeString: String? = null
        private var dateString: String? = null

        private fun setDateString(year: Int, monthOfYear: Int, dayOfMonth: Int) {
            var monthOfYear = monthOfYear

            // Increment monthOfYear for Calendar/Date -> Time Format setting
            monthOfYear++
            var mon = "" + monthOfYear
            var day = "" + dayOfMonth

            if (monthOfYear < 10)
                mon = "0$monthOfYear"
            if (dayOfMonth < 10)
                day = "0$dayOfMonth"

            dateString = "$year-$mon-$day"
        }

        private fun setTimeString(hourOfDay: Int, minute: Int, mili: Int) {
            var hour = "" + hourOfDay
            var min = "" + minute

            if (hourOfDay < 10)
                hour = "0$hourOfDay"
            if (minute < 10)
                min = "0$minute"

            timeString = "$hour:$min:00"
        }
    }
}