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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Clase que define la base de datos de la aplicación utilizando Room.
@Database(entities = [Continente::class, Pais::class, Region::class, Viaje::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun continenteDao(): ContinenteDAO
    abstract fun paisDao(): PaisDAO
    abstract fun regionDao(): RegionDAO
    abstract fun viajeDao(): ViajeDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @OptIn(DelicateCoroutinesApi::class)
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
                // Inicializa datos predeterminados en la base de datos en un hilo global.
                GlobalScope.launch {
                    try {
                        datosIniciales(instance.continenteDao(), instance.paisDao(), instance.regionDao())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                instance
            }
        }

        // Función para insertar datos iniciales en la base de datos.
        private suspend fun datosIniciales(
            continenteDao: ContinenteDAO,
            paisDao: PaisDAO,
            regionDao: RegionDAO
        ) {
            try {
                // Insertar Continentes
                insertarDatosContinentes(continenteDao)

                // Obtener los continentes desde la base de datos
                val continentesFromDb = continenteDao.obtenerTodosContinentes()

                // Insertar Países
                insertarDatosPaises(paisDao, continentesFromDb)

                // Obtener los países desde la base de datos
                val paisesFromDb = paisDao.obtenerTodosPaises()

                // Insertar Regiones
                insertarDatosRegiones(regionDao, paisesFromDb)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Función para insertar datos de Continentes
        private suspend fun insertarDatosContinentes(continenteDao: ContinenteDAO) {
            val continentes = listOf(
                Continente(nombreContinente = "Asia"),
                Continente(nombreContinente = "Europa"),
                Continente(nombreContinente = "América")
            )
            val continentesFromDb = continenteDao.obtenerTodosContinentes()
            if (continentesFromDb.isEmpty()) {
                continentes.forEach { continenteDao.insertarContinente(it) }
            }
        }

        // Función para insertar datos de Países
        private suspend fun insertarDatosPaises(paisDao: PaisDAO, continenteDao: Flow<List<Continente>>) {
            // Obtener los continentes desde la base de datos (ahora como lista)
            val continentesFromDb = continenteDao.obtenerTodosContinentes().first()

            val paises = listOf(
                Pais(
                    nombrePais = "China",
                    idContinente = continentesFromDb.first { it.nombreContinente == "Asia" }.idContinente
                ),
                Pais(
                    nombrePais = "España",
                    idContinente = continentesFromDb.first { it.nombreContinente == "Europa" }.idContinente
                ),
                Pais(
                    nombrePais = "Estados Unidos",
                    idContinente = continentesFromDb.first { it.nombreContinente == "América" }.idContinente
                )
            )
            paises.forEach { paisDao.insertarPais(it) }
        }

        // Función para insertar datos de Regiones
        private suspend fun insertarDatosRegiones(regionDao: RegionDAO, paisDao: Flow<List<Pais>>) {
            // Obtener los países desde la base de datos (ahora como lista)
            val paisesFromDb = paisDao.obtenerTodosPaises().first()

            val regiones = listOf(
                Region(
                    nombreRegion = "Beijing",
                    idPais = paisesFromDb.first { it.nombrePais == "China" }.idPais
                ),
                Region(
                    nombreRegion = "Cataluña",
                    idPais = paisesFromDb.first { it.nombrePais == "España" }.idPais
                ),
                Region(
                    nombreRegion = "California",
                    idPais = paisesFromDb.first { it.nombrePais == "Estados Unidos" }.idPais
                )
            )
            regiones.forEach { regionDao.insertarRegion(it) }
        }
    }
}
