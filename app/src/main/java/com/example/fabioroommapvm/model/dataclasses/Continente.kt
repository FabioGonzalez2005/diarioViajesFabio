package com.example.fabioroommapvm.model.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Continentes")
data class Continente(
    @PrimaryKey(autoGenerate = true) val idContinente: Int = 0,
    val nombreContinente: String
)
