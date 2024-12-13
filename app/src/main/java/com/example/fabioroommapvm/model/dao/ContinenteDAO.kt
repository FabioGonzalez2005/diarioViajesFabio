package com.example.fabioroommapvm.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fabioroommapvm.model.dataclasses.Continente
import com.example.fabioroommapvm.model.dataclasses.Pais
import kotlinx.coroutines.flow.Flow

@Dao
interface ContinenteDAO {

    @Insert
    suspend fun insertarContinente(continente: Continente)

    @Query("SELECT * FROM Continentes")
    fun obtenerTodosContinentes(): Flow<List<Continente>>

    @Query("SELECT * FROM Paises WHERE idContinente = :idContinente")
    fun obtenerPaisesPorContinente(idContinente: Int): Flow<List<Pais>> // Obtener países asociados a un continente
}
