package com.example.fabioroommapvm.model.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "Regiones",
    foreignKeys = [
        ForeignKey(
            entity = Pais::class,
            parentColumns = ["idPais"],
            childColumns = ["idPais"],
            onDelete = ForeignKey.CASCADE // Si se elimina un país, las regiones asociadas también se eliminarán
        )
    ]
)
data class Region(
    @PrimaryKey(autoGenerate = true) val idRegion: Int = 0,
    val nombreRegion: String,
    val idPais: Int // Relación con el País
)
