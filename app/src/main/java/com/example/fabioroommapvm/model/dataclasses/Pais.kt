package com.example.fabioroommapvm.model.dataclasses

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Paises",
    foreignKeys = [
        ForeignKey(
            entity = Continente::class,
            parentColumns = ["idContinente"],
            childColumns = ["idContinente"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Pais(
    @PrimaryKey(autoGenerate = true) val idPais: Int = 0,
    val nombrePais: String,
    val idContinente: Int // Relación con Continente
)
