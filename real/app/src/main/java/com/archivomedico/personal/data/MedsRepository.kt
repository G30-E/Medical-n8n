package com.archivomedico.personal.data
import android.content.Context
import com.archivomedico.personal.alarm.AlarmScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MedsRepository(private val appContext: Context) {
    private val dao = AppDatabase.get(appContext).medsDao()

    private fun rowsKey(s: String): String = s.trim().lowercase()

    fun getAllGrouped(): Flow<List<MedGroup>> = dao.getAll().map { rows ->
        rows.groupBy { Triple(rowsKey(it.name), rowsKey(it.dose), it.daysMask) }
            .values
            .map { groupRows ->
                val first = groupRows.first()
                val groupActive = groupRows.any { it.active }
                val times = groupRows.sortedWith(compareBy({ it.timeHour }, { it.timeMinute }))
                    .map { it.timeHour to it.timeMinute }
                MedGroup(first.name, first.dose, first.daysMask, times, groupActive)
            }
            .sortedWith(compareBy({ it.name }, { it.dose }))
    }

    suspend fun saveGroup(group: MedGroup) {
        val nameKey = rowsKey(group.name)
        val doseKey = rowsKey(group.dose)
        // delete existing rows for this group
        dao.deleteGroup(nameKey, doseKey, group.daysMask)
        // insert each time
        for ((h, m) in group.times.distinct()) {
            val id = dao.insert(
                MedicationSchedule(
                    name = group.name.trim(),
                    dose = group.dose.trim(),
                    timeHour = h,
                    timeMinute = m,
                    daysMask = group.daysMask,
                    active = group.active
                )
            )
            if (group.active) {
                // The entity isn't retrieved again, but we can schedule using a lightweight copy
                AlarmScheduler.scheduleFor(appContext, MedicationSchedule(id, group.name.trim(), group.dose.trim(), h, m, group.daysMask, active = true))
            }
        }
    }

    suspend fun deleteGroup(group: MedGroup) {
        // cancel alarms for existing rows
        val rows = dao.getGroup(rowsKey(group.name), rowsKey(group.dose), group.daysMask)
        rows.forEach { AlarmScheduler.cancel(appContext, it.id) }
        dao.deleteGroup(rowsKey(group.name), rowsKey(group.dose), group.daysMask)
    }

    suspend fun setActive(group: MedGroup, active: Boolean) {
        val rows = dao.getGroup(rowsKey(group.name), rowsKey(group.dose), group.daysMask)
        rows.forEach { row ->
            dao.updateActive(row.id, active)
            if (active) {
                AlarmScheduler.scheduleFor(appContext, row.copy(active = true))
            } else {
                AlarmScheduler.cancel(appContext, row.id)
            }
        }
    }

    
    suspend fun replaceGroup(originalName: String, originalDose: String, originalDaysMask: Int, updated: MedGroup) {
        // If renaming to a different name that already exists, block it
        val sameName = rowsKey(updated.name) == rowsKey(originalName)
        if (!sameName && nameExists(updated.name)) {
            throw IllegalStateException("Ya existe un medicamento con ese nombre.")
        }
        // Cancel alarms and delete the original group rows (by original keys)
        val nameKey = rowsKey(originalName)
        val doseKey = rowsKey(originalDose)
        val oldRows = dao.getGroup(nameKey, doseKey, originalDaysMask)
        oldRows.forEach { AlarmScheduler.cancel(appContext, it.id) }
        dao.deleteGroup(nameKey, doseKey, originalDaysMask)
        // Insert the updated group rows and (re)schedule if active
        for ((h, m) in updated.times.distinct()) {
            val newId = dao.insert(
                MedicationSchedule(
                    name = updated.name.trim(),
                    dose = updated.dose.trim(),
                    timeHour = h,
                    timeMinute = m,
                    daysMask = updated.daysMask,
                    active = updated.active
                )
            )
            if (updated.active) {
                AlarmScheduler.scheduleFor(appContext, MedicationSchedule(
                    id = newId,
                    name = updated.name.trim(),
                    dose = updated.dose.trim(),
                    timeHour = h,
                    timeMinute = m,
                    daysMask = updated.daysMask,
                    active = true
                ))
            }
        }
    }

    
    suspend fun nameExists(name: String): Boolean {
        // Use DAO exists; 1 == true
        return dao.existsByName(name) == 1
    }
}