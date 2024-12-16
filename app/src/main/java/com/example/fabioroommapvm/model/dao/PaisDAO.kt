package com.example.fabioroommapvm.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fabioroommapvm.model.dataclasses.Pais
import kotlinx.coroutines.flow.Flow

@Dao
interface PaisDAO {

    @Insert
    suspend fun insertarPais(pais: Pais)

    @Query("SELECT * FROM Paises WHERE idContinente = :idContinente")
    fun obtenerPaisesPorContinente(idContinente: Int): Flow<List<Pais>>

    @Query("SELECT * FROM Paises")
    fun obtenerTodosPaises(): Flow<List<Pais>>

    @Delete
    suspend fun eliminarPais(pais: Pais)
}
