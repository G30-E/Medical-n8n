package com.archivomedico.personal.alarm
import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.archivomedico.personal.R
import com.archivomedico.personal.data.AppDatabase
import kotlinx.coroutines.*

class MedAlarmReceiver : BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getLongExtra("id", -1L)
        val name = intent.getStringExtra("name") ?: "Medicamento"
        val dose = intent.getStringExtra("dose") ?: ""
        Notifier.ensureChannel(context)
        val notif = NotificationCompat.Builder(context, Notifier.CH_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(name)
            .setContentText("Toma programada: " + dose)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        NotificationManagerCompat.from(context).notify(id.toInt(), notif)

        // Reprogramar siguiente toma
        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.get(context).medsDao()
            val item = dao.getById(id) ?: return@launch
            AlarmScheduler.scheduleFor(context, item)
        }
    }
}