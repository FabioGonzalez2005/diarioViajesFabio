package com.example.fabioroommapvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Regiones")
data class Region(
    // Asignamos la primary key
    @PrimaryKey(autoGenerate = true) val idRegion: Int = 0,
    // Asignamos los otros elementos de la tabla
    @ColumnInfo(name = "Nombre") val nombreRegion: String,
    @ColumnInfo(name = "CoordenadaX") val coordenadaX: Double,
    @ColumnInfo(name = "CoordenadaY") val coordenadaY: Double,
    val idPais: Int
)
