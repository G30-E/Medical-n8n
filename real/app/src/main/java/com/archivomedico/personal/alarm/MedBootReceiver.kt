package com.archivomedico.personal.alarm
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.archivomedico.personal.data.AppDatabase
import kotlinx.coroutines.*

class MedBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            CoroutineScope(Dispatchers.IO).launch {
                val dao = AppDatabase.get(context).medsDao()
                dao.getAll().collect { list ->
                    list.filter { it.active }.forEach { AlarmScheduler.scheduleFor(context, it) }
                }
            }
        }
    }
}