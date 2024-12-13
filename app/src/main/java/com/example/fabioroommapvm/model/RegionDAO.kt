package com.example.fabioroommapvm.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDAO {

    @Insert
    suspend fun insertarRegion(region: Region)

    @Query("SELECT * FROM REGIONES")
    fun obtenerTodasRegiones(): Flow<List<Region>>
}
