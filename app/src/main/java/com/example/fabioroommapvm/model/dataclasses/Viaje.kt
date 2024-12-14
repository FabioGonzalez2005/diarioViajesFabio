package com.example.fabioroommapvm.model.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Viajes")
data class Viaje(
    @PrimaryKey(autoGenerate = true) val idViaje: Int = 0,
    @ColumnInfo(name = "nombreViaje") val nombreViaje: String,
    @ColumnInfo(name = "fechaViaje") val fechaViaje: String
)
