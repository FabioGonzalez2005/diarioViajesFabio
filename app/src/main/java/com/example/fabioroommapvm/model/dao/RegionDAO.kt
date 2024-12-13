package com.example.fabioroommapvm.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.fabioroommapvm.model.dataclasses.Region
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDAO {
    @Insert
    suspend fun insertarRegion(region: Region)

    @Query("SELECT * FROM Regiones WHERE idPais = :idPais")
    fun obtenerRegionesPorPais(idPais: Int): Flow<List<Region>> // Obtener regiones asociadas a un país

    @Query("SELECT * FROM Regiones")
    fun obtenerTodasRegiones(): Flow<List<Region>>
}