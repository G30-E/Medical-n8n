package com.archivomedico.personal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: SavedLocation)

    @Query("SELECT * FROM saved_locations WHERE userEmail = :userEmail ORDER BY name ASC")
    fun getLocationsForUser(userEmail: String): Flow<List<SavedLocation>>

    @Delete
    suspend fun delete(location: SavedLocation)
}
