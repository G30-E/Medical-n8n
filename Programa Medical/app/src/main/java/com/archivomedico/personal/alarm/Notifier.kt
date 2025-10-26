package com.archivomedico.personal.alarm
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object Notifier {
    const val CH_ID = "meds_channel"
    fun ensureChannel(ctx: Context) {
        if (Build.VERSION.SDK_INT >= 26) {
            val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(
                NotificationChannel(CH_ID, "Recordatorios de medicación", NotificationManager.IMPORTANCE_HIGH)
            )
        }
    }
}