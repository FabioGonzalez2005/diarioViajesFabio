package com.example.fabioroommapvm.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.fabioroommapvm.model.dataclasses.Pais
import kotlinx.coroutines.flow.Flow

@Dao
interface PaisDAO {

    @Insert
    suspend fun insertarPais(pais: Pais)

    @Query("SELECT * FROM Paises WHERE idContinente = :idContinente")
    fun obtenerPaisesPorContinente(idContinente: Int): Flow<List<Pais>> // Obtener países por continente

    @Query("SELECT * FROM Paises")
    fun obtenerTodosPaises(): Flow<List<Pais>>

    @Transaction
    @Query("SELECT * FROM Paises WHERE idPais = :idPais")
    fun obtenerPaisConRegiones(idPais: Int): Flow<PaisConRegiones> // Obtener país con regiones asociadas
}
