package com.example.fabioroommapvm.model.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "Paises",
    foreignKeys = [
        ForeignKey(
            entity = Continente::class,
            parentColumns = ["idContinente"],
            childColumns = ["idContinente"],
            onDelete = ForeignKey.CASCADE // Si se elimina un continente, los países relacionados también se eliminarán
        )
    ]
)
data class Pais(
    @PrimaryKey(autoGenerate = true) val idPais: Int = 0,
    val nombrePais: String,
    val idContinente: Int // Relación con Continente
)
