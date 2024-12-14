package com.example.fabioroommapvm.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fabioroommapvm.model.dataclasses.Continente
import kotlinx.coroutines.flow.Flow

@Dao
interface ContinenteDAO {

    @Insert
    suspend fun insertarContinente(continente: Continente)

    @Query("SELECT * FROM Continentes")
    fun obtenerTodosContinentes(): Flow<List<Continente>>
}
