package com.archivomedico.personal.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {
    @Query("SELECT * FROM medication_schedule ORDER BY name, dose, daysMask, timeHour, timeMinute")
    fun getAll(): Flow<List<MedicationSchedule>>

    @Query("SELECT * FROM medication_schedule WHERE lower(trim(name)) = lower(trim(:name)) AND lower(trim(dose)) = lower(trim(:dose)) AND daysMask = :daysMask ORDER BY timeHour, timeMinute")
    suspend fun getGroup(name: String, dose: String, daysMask: Int): List<MedicationSchedule>

    @Insert
    suspend fun insert(item: MedicationSchedule): Long

    @Update
    suspend fun update(item: MedicationSchedule)

    @Query("UPDATE medication_schedule SET active = :active WHERE id = :id")
    suspend fun updateActive(id: Long, active: Boolean)

    @Query("DELETE FROM medication_schedule WHERE lower(trim(name)) = lower(trim(:name)) AND lower(trim(dose)) = lower(trim(:dose)) AND daysMask = :daysMask")
    suspend fun deleteGroup(name: String, dose: String, daysMask: Int)


    @Query("SELECT * FROM medication_schedule WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): MedicationSchedule?

    
    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM medication_schedule WHERE lower(trim(name)) = lower(trim(:name))) THEN 1 ELSE 0 END")
    suspend fun existsByName(name: String): Int
}
