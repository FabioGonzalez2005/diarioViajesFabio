package com.example.fabioroommapvm.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "Paises")
data class Pais(
    // Asignamos la primary key.
    @PrimaryKey(autoGenerate = true) val idPais: Int = 0,
    val nombrePais: String
)

data class RegionConPais(
    @Embedded val region: Region,
    @Relation(
        parentColumn = "idPais",
        entityColumn = "idPais"
    )
    val tiposRegiones: List<Pais> // Lista de tipos relacionados con el marcador.
)
