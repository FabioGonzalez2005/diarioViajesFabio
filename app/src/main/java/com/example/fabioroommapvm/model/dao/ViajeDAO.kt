package com.example.fabioroommapvm.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.fabioroommapvm.model.dataclasses.Viaje
import kotlinx.coroutines.flow.Flow

@Dao
interface ViajeDAO {

    @Insert
    suspend fun insertarViaje(viaje: Viaje)

    @Query("SELECT * FROM Viajes")
    fun obtenerTodosViajes(): Flow<List<Viaje>>

    @Update
    suspend fun actualizarViaje(viaje: Viaje)

    @Delete
    suspend fun eliminarViaje(viaje: Viaje)
}
