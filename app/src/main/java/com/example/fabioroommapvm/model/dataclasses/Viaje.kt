package com.example.fabioroommapvm.model.dataclasses

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Viajes",
    foreignKeys = [
        ForeignKey(
            entity = Region::class, // Ahora vinculado con Region
            parentColumns = ["idRegion"],
            childColumns = ["idRegion"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Viaje(
    @PrimaryKey(autoGenerate = true) val idViaje: Int = 0,
    val nombreViaje: String,
    val idRegion: Int // Relación con Region
)
