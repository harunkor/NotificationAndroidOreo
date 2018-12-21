package com.harunkor.notificationandroidoreo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.media.RingtoneManager



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, MainActivity::class.java)


      //  showNotificationBeforeOreo(applicationContext,"TITLE NOTIF","DESCRIPTION NOTIF",intent,0)



       showNotificationForOreo(applicationContext,"TITLE NOTIF",
           "DESCRIPTION NOTIF",intent,0,"MY_CHANNELID_"+1)






    }





    private fun showNotificationBeforeOreo(context: Context, notificationTitle: String, notificationSubtitle: String, intent: Intent, requestcode:Int) {

        val pendingIntent = PendingIntent.getActivity(context, requestcode, intent, PendingIntent.FLAG_ONE_SHOT)

        val notificationBuilder = NotificationCompat.Builder(context)
            .setSmallIcon(android.R.drawable.stat_notify_chat)
            .setContentTitle(notificationTitle)
            .setContentText(notificationSubtitle)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(requestcode, notificationBuilder.build())
    }







    private fun showNotificationForOreo(context: Context, notificationTitle: String, notificationSubtitle: String, intent: Intent,requestcode:Int,notifchannelid:String) {

        val pendingIntent = PendingIntent.getActivity(context, requestcode, intent, PendingIntent.FLAG_ONE_SHOT)


       // val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, notifchannelid)
            .setSmallIcon(android.R.drawable.stat_notify_chat)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setNumber(2)
            .setContentTitle(notificationTitle)
            .setContentText(notificationSubtitle)
          //  .setSound(uri)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // OREO  ve üstü için gerekli
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(notifchannelid, "CHANNEL NAME", NotificationManager.IMPORTANCE_HIGH)


            notificationManager.createNotificationChannel(channel)


          //  SetNotifChannelScreen()  // notif channel ayar ekranı görüntülemek isterseniz
        }
        notificationManager.notify(requestcode, notificationBuilder.build())
    }







    @RequiresApi(Build.VERSION_CODES.O)
    private  fun SetNotifChannelScreen()
    {
        val channelId = "MY_CHANNELID_"+1
        val channel = NotificationChannel(channelId, "CHANNEL NAME", NotificationManager.IMPORTANCE_HIGH)
        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId())
        }
        startActivity(intent)
    }





}
