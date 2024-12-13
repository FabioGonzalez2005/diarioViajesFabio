package com.example.fabioroommapvm.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PaisDAO {
    @Insert
    suspend fun insertarPais(pais: Pais)

    @Query("SELECT * FROM Paises")
    fun obtenerTodosPaises(): Flow<List<Pais>>
}
