package com.archivomedico.personal.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medication_schedule")
data class MedicationSchedule(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val dose: String,
    val timeHour: Int,
    val timeMinute: Int,
    val daysMask: Int,
    val startDate: Long? = null, // epochDay
    val endDate: Long? = null,   // epochDay
    val notes: String? = null,
    val active: Boolean = true
)