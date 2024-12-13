package com.example.fabioroommapvm.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDAO {

    @Insert
    suspend fun insertarRegion(region: Region)

    @Query("SELECT * FROM Regiones")
    fun obtenerTodasRegiones(): Flow<List<Region>>

    // Metodo para obtener regiones por país
    @Query("SELECT * FROM Regiones WHERE idPais = :idPais")
    fun obtenerRegionesPorPais(idPais: Int): Flow<List<Region>>
}
