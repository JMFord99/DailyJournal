package com.example.dailyjournal



import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.text.DateFormat
import java.util.*


class DailyReminder : BroadcastReceiver() {
    private lateinit var mNotificationManager: NotificationManager
    private lateinit var mAlarmManager: AlarmManager
    private lateinit var mContext: Context
    private lateinit var mChannelID: String
    private val mVibratePattern = longArrayOf(0, 200, 200, 300)

    private val channelID = "my_channel_01"
    override fun onReceive(context: Context, intent: Intent?) {
        Log.i("DailyJournal", "Logging Alarm at: " +
                DateFormat.getDateTimeInstance().format(Date()))
        Toast.makeText(
                context, "Send Alarm Plz",
                Toast.LENGTH_LONG
        ).show()
        mContext = context
        mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()

        val mNotificationIntent = Intent(context, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val mContentIntent = PendingIntent.getActivity(
                context, 0,
                mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder = Notification.Builder(
                mContext, mChannelID
        ).setTicker("Ticker Test Attempt")
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true).setContentTitle("Do Journal")
                .setContentText("You Should fill out your journal entry, or else.")
                .setContentIntent(mContentIntent)

        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build())
        Log.i("DailyJournal","Send notification as:" +
                DateFormat.getDateTimeInstance().format(Date()))
    }

    private fun createNotificationChannel() {

        mChannelID = mContext.packageName + ".channel_01"

        // The user-visible name of the channel.
        val name = mContext.getString(R.string.channel_name)

        // The user-visible description of the channel
        val description = mContext.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(mChannelID, name, importance)

        // Configure the notification channel.
        mChannel.description = description
        mChannel.enableLights(true)

        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
        mChannel.lightColor = Color.RED
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = mVibratePattern

        mNotificationManager.createNotificationChannel(mChannel)
    }

    companion object {


        private const val TAG = "Lab-Notifications"
        private const val MY_NOTIFICATION_ID = 11151990
    }

}
