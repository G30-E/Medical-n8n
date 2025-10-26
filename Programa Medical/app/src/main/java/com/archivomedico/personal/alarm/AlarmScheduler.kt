package com.archivomedico.personal.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.archivomedico.personal.data.MedicationSchedule
import java.time.ZoneId
import java.time.ZonedDateTime

object AlarmScheduler {
    private const val REQ_BASE = 71000

    fun scheduleFor(ctx: Context, item: MedicationSchedule) {
        cancel(ctx, item.id)
        val next = nextTriggerMillis(item) ?: return
        val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pi = pending(ctx, item.id, item)
        // Use setExactAndAllowWhileIdle if available; otherwise setExact
        try { am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next, pi) } catch (e: SecurityException) { /* exact alarm not allowed */ }
    }

    fun cancel(ctx: Context, id: Long) {
        if (id <= 0) return
        val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pi = pending(ctx, id, null)
        try { am.cancel(pi) } catch (_: Exception) {}
    }

    private fun pending(ctx: Context, id: Long, item: MedicationSchedule?): PendingIntent {
        val intent = Intent(ctx, MedAlarmReceiver::class.java).apply {
            putExtra("id", id)
            if (item != null) {
                putExtra("name", item.name)
                putExtra("dose", item.dose)
            }
        }
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getBroadcast(ctx, (REQ_BASE + id).toInt(), intent, flags)
    }

    /** Compute next trigger time in millis for the given item, within the next 2 weeks. */
    fun nextTriggerMillis(item: MedicationSchedule): Long? {
        val tz = ZoneId.systemDefault()
        val now = ZonedDateTime.now(tz)
        for (k in 0..14) {
            val cand = now.toLocalDate().plusDays(k.toLong())
            val dow = (cand.dayOfWeek.value % 7) // 0=Dom..6=Sab
            val maskConst = intArrayOf(1, 2, 4, 8, 16, 32, 64)[dow]
            if (item.daysMask and maskConst == 0) continue
            val zdt = cand.atTime(item.timeHour, item.timeMinute).atZone(tz)
            if (zdt.toInstant().toEpochMilli() > now.toInstant().toEpochMilli()) {
                return zdt.toInstant().toEpochMilli()
            }
        }
        return null
    }
}