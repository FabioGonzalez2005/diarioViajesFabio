package com.example.fabioroommapvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Continentes")
data class Continente(
    // Asignamos la primary key
    @PrimaryKey(autoGenerate = true) val idContinente: Int = 0,
    val nombreContinente: String
)
