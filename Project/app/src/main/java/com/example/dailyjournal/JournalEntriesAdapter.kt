package com.example.dailyjournal

import android.content.Context
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.*

class JournalEntriesAdapter(private val mContext: Context) : BaseAdapter() {

    private val mItems = ArrayList<JournalEntry>()

    fun add(item: JournalEntry) {

        mItems.add(item)
        notifyDataSetChanged()

    }


    // Returns item at position
    override fun getItem(pos: Int): Any {
        return mItems[pos]
    }

    // returns item position
    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    //returns number of items stored
    override fun getCount(): Int {

        return mItems.size

    }

    // creates the view
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val journalEntry = getItem(position) as JournalEntry
        val viewHolder: ViewHolder

        if (null == convertView) {
            viewHolder = ViewHolder()
            val inflater: LayoutInflater

            inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            viewHolder.mItemLayout = inflater.inflate(R.layout.journal_entry, parent, false) as RelativeLayout
        }
        else {
            viewHolder = convertView.tag as ViewHolder
            viewHolder.mStatusView!!.setOnCheckedChangeListener(null)
        }

        // Display Prompt in TextView

        viewHolder.mPromptView = viewHolder.mItemLayout!!.findViewById(R.id.promptView)
        viewHolder.mPromptView!!.text = journalEntry.prompt

        // Set up Status CheckBox

        viewHolder.mStatusView = viewHolder.mItemLayout!!.findViewById(R.id.statusCheckBox)
        //viewHolder.mStatusView!!.text = toDoItem.status.toString()
        viewHolder.mStatusView!!.setChecked(journalEntry.status == JournalEntry.Status.COMPLETE)

        // Display Mood in a TextView

        viewHolder.mMoodView = viewHolder.mItemLayout!!.findViewById(R.id.moodView)
        viewHolder.mMoodView!!.text = journalEntry.mood.toString()

        // Display Date

        // val pattern = "yyyy-MM-dd 'at' HH:mm:ss"
        // val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
        // val date = simpleDateFormat.format(journalEntry.date)

        viewHolder.mDateView = viewHolder.mItemLayout!!.findViewById(R.id.dateView)
        viewHolder.mDateView!!.text = journalEntry.date.toString().subSequence(0, 10)

        viewHolder.mItemLayout!!.setTag(viewHolder)

        return viewHolder.mItemLayout!!


    }
    internal class ViewHolder {
        var position: Int = 0
        var mItemLayout: RelativeLayout? = null
        var mPromptView: TextView? = null
        var mStatusView: CheckBox? = null
        var mMoodView: TextView? = null
        var mDateView: TextView? = null
    }
}