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
    @Embedded val pais: Pais,  // Embedding el Pais
    @Relation(
        parentColumn = "idPais",
        entityColumn = "idPais"
    )
    val regiones: List<Region> // Lista de regiones relacionadas con el país
)
