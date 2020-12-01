package com.example.dailyjournal

import android.app.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import java.time.LocalDateTime
import android.view.View
import android.widget.*
import java.util.*

class CompleteDailyPrompt: Activity() {
    private var mDate: LocalDateTime? = null
    private var mPriorityRadioGroup: RadioGroup? = null
    private var mStatusRadioGroup: RadioGroup? = null
    private var mTitleText: EditText? = null
    private var mDefaultStatusButton: RadioButton? = null
    private var mDefaultPriorityButton: RadioButton? = null
    private var dateView: TextView? = null
    private var timeView: TextView? = null

   /* private val mood: Mood
        get() {

            when (mPriorityRadioGroup!!.checkedRadioButtonId) {
                R.id.lowPriority -> {
                    return Priority.LOW
                }
                R.id.highPriority -> {
                    return Priority.HIGH
                }
                else -> {
                    return Priority.MED
                }
            }
        }


    */

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complete_prompt)

        mTitleText = findViewById<View>(R.id.title) as EditText
        mDefaultStatusButton = findViewById<View>(R.id.statusNotDone) as RadioButton
        mDefaultPriorityButton = findViewById<View>(R.id.medPriority) as RadioButton
        mPriorityRadioGroup = findViewById<View>(R.id.priorityGroup) as RadioGroup
        mStatusRadioGroup = findViewById<View>(R.id.statusGroup) as RadioGroup
        dateView = findViewById<View>(R.id.date) as TextView
        timeView = findViewById<View>(R.id.time) as TextView

        // Set the default date and time

        setDefaultDateTime()

        // OnClickListener for the Date button, calls showDatePickerDialog() to
        // show
        // the Date dialog

        val datePickerButton = findViewById<View>(R.id.date_picker_button) as Button
        datePickerButton.setOnClickListener { showDatePickerDialog() }

        // OnClickListener for the Time button, calls showTimePickerDialog() to
        // show
        // the Time Dialog

        val timePickerButton = findViewById<View>(R.id.time_picker_button) as Button
        timePickerButton.setOnClickListener { showTimePickerDialog() }

        // OnClickListener for the Cancel Button,

        val cancelButton = findViewById<View>(R.id.cancelButton) as Button
        cancelButton.setOnClickListener { Log.i(TAG, "Entered cancelButton.OnClickListener.onClick()")

            // TODO - Indicate result and finish
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        // TODO - Set up OnClickListener for the Reset Button
        val resetButton = findViewById<View>(R.id.resetButton) as Button
        resetButton.setOnClickListener {
            Log.i(TAG, "Entered resetButton.OnClickListener.onClick()")
            setDefaultDateTime()
            mTitleText!!.setText("")
            mPriorityRadioGroup!!.check(R.id.medPriority)
            mStatusRadioGroup!!.check(R.id.statusNotDone)

        }

        // Set up OnClickListener for the Submit Button

        val submitButton = findViewById<View>(R.id.submitButton) as Button
        submitButton.setOnClickListener {
            Log.i(TAG, "Entered submitButton.OnClickListener.onClick()")

            // TODO - gather ToDoItem data


            var title = mTitleText!!.getText().toString()
            var date = dateString + " " + timeString
            //var priority = priority
            var status = status

            var mood = 50 as Integer

            // TODO - return data Intent and finish
            val data = Intent()
            JournalEntry.packageIntent(data, title, mood, status, date, date)

            setResult(Activity.RESULT_OK, data)
            finish()

        }
    }

    private fun setDefaultDateTime() {

        // Default is current time + 7 days
        mDate = LocalDateTime.now()

        val c = Calendar.getInstance()
        //c.time = mDate

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH))

        dateView!!.text = dateString

        setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
            c.get(Calendar.MILLISECOND))

        timeView!!.text = timeString
    }

    // DialogFragment used to pick a ToDoItem deadline date

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