package com.example.fabioroommapvm.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fabioroommapvm.model.dataclasses.Viaje
import kotlinx.coroutines.flow.Flow

@Dao
interface ViajeDAO {

    @Insert
    suspend fun insertarViaje(viaje: Viaje)

    @Query("SELECT * FROM Viajes WHERE idPais = :idPais")
    fun obtenerViajesPorPais(idPais: Int): Flow<List<Viaje>> // Obtener viajes asociados a un país

    @Query("SELECT * FROM Viajes")
    fun obtenerTodosViajes(): Flow<List<Viaje>>
}
