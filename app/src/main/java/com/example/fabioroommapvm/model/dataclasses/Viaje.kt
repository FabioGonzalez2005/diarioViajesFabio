package com.example.fabioroommapvm.model.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "Viajes",
    foreignKeys = [
        ForeignKey(
            entity = Pais::class,
            parentColumns = ["idPais"],
            childColumns = ["idPais"],
            onDelete = ForeignKey.CASCADE // Si se elimina un país, los viajes relacionados también se eliminarán
        )
    ]
)
data class Viaje(
    @PrimaryKey(autoGenerate = true) val idViaje: Int = 0,
    @ColumnInfo(name = "nombreViaje") val nombreViaje: String,
    @ColumnInfo(name = "fechaViaje") val fechaViaje: String,
    @ColumnInfo(name = "idPais") val idPais: Int // Relación con el Pais
)
