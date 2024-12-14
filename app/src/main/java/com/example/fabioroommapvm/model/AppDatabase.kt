package com.example.fabioroommapvm.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fabioroommapvm.model.dao.ContinenteDAO
import com.example.fabioroommapvm.model.dao.PaisDAO
import com.example.fabioroommapvm.model.dao.RegionDAO
import com.example.fabioroommapvm.model.dao.ViajeDAO
import com.example.fabioroommapvm.model.dataclasses.Continente
import com.example.fabioroommapvm.model.dataclasses.Pais
import com.example.fabioroommapvm.model.dataclasses.Region
import com.example.fabioroommapvm.model.dataclasses.Viaje
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Database(entities = [Continente::class, Pais::class, Region::class, Viaje::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun continenteDao(): ContinenteDAO
    abstract fun paisDao(): PaisDAO
    abstract fun regionDao(): RegionDAO
    abstract fun viajeDao(): ViajeDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "marcadores_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                // Inicializa datos predeterminados en un hilo de IO seguro
                CoroutineScope(Dispatchers.IO).launch {
                    inicializarDatos(instance)
                }
                instance
            }
        }

        private suspend fun inicializarDatos(db: AppDatabase) {
            val continenteDao = db.continenteDao()
            val paisDao = db.paisDao()
            val regionDao = db.regionDao()

            // Verifica e inserta datos iniciales
            if (continenteDao.obtenerTodosContinentes().first().isEmpty()) {
                insertarDatosContinentes(continenteDao)
            }
            if (paisDao.obtenerTodosPaises().first().isEmpty()) {
                insertarDatosPaises(paisDao, continenteDao)
            }
            if (regionDao.obtenerTodasRegiones().first().isEmpty()) {
                insertarDatosRegiones(regionDao, paisDao)
            }
        }

        private suspend fun insertarDatosContinentes(continenteDao: ContinenteDAO) {
            val continentes = listOf(
                Continente(nombreContinente = "Asia"),
                Continente(nombreContinente = "Europa"),
                Continente(nombreContinente = "América")
            )
            continentes.forEach { continenteDao.insertarContinente(it) }
        }

        private suspend fun insertarDatosPaises(paisDao: PaisDAO, continenteDao: ContinenteDAO) {
            val continentes = continenteDao.obtenerTodosContinentes().first()
            val paises = listOf(
                Pais(
                    nombrePais = "China",
                    idContinente = continentes.first { it.nombreContinente == "Asia" }.idContinente
                ),
                Pais(
                    nombrePais = "España",
                    idContinente = continentes.first { it.nombreContinente == "Europa" }.idContinente
                ),
                Pais(
                    nombrePais = "Estados Unidos",
                    idContinente = continentes.first { it.nombreContinente == "América" }.idContinente
                )
            )
            paises.forEach { paisDao.insertarPais(it) }
        }

        private suspend fun insertarDatosRegiones(regionDao: RegionDAO, paisDao: PaisDAO) {
            val paises = paisDao.obtenerTodosPaises().first()
            val regiones = listOf(
                Region(
                    nombreRegion = "Beijing",
                    idPais = paises.first { it.nombrePais == "China" }.idPais
                ),
                Region(
                    nombreRegion = "Cataluña",
                    idPais = paises.first { it.nombrePais == "España" }.idPais
                ),
                Region(
                    nombreRegion = "California",
                    idPais = paises.first { it.nombrePais == "Estados Unidos" }.idPais
                )
            )
            regiones.forEach { regionDao.insertarRegion(it) }
        }
    }
}
