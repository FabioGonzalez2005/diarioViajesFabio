package com.example.fabioroommapvm.model.dataclasses

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Continentes",
    foreignKeys = [
        ForeignKey(
            entity = Viaje::class,
            parentColumns = ["idViaje"],
            childColumns = ["idViaje"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Continente(
    @PrimaryKey(autoGenerate = true) val idContinente: Int = 0,
    val nombreContinente: String,
    val idViaje: Int
)
