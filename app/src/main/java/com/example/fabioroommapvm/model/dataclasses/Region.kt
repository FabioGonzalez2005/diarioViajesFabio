package com.example.fabioroommapvm.model.dataclasses

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Regiones",
    foreignKeys = [
        ForeignKey(
            entity = Pais::class,
            parentColumns = ["idPais"],
            childColumns = ["idPais"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Region(
    @PrimaryKey(autoGenerate = true) val idRegion: Int = 0,
    val nombreRegion: String,
    val idPais: Int, // Relación con País
    val coordenadaX: Double,
    val coordenadaY: Double
)
