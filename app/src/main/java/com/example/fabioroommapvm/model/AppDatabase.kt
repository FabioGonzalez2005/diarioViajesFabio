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

@Database(entities = [Continente::class, Pais::class, Region::class, Viaje::class], version = 27)
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
            val viajeDao = db.viajeDao()

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
            if (viajeDao.obtenerTodosViajes().first().isEmpty()) {
                insertarDatosViajes(viajeDao, regionDao)
            }
        }

        private suspend fun insertarDatosContinentes(continenteDao: ContinenteDAO) {
            val continentes = listOf(
                Continente(nombreContinente = "Asia"),
                Continente(nombreContinente = "Europa"),
                Continente(nombreContinente = "América"),
                Continente(nombreContinente = "África"),
                Continente(nombreContinente = "Oceanía"),
                Continente(nombreContinente = "Antártida")
            )
            continentes.forEach { continenteDao.insertarContinente(it) }
        }

        private suspend fun insertarDatosPaises(paisDao: PaisDAO, continenteDao: ContinenteDAO) {
            val continentes = continenteDao.obtenerTodosContinentes().first()
            val paises = listOf(
                // Asia
                Pais(nombrePais = "China", idContinente = continentes.first { it.nombreContinente == "Asia" }.idContinente),
                Pais(nombrePais = "Japón", idContinente = continentes.first { it.nombreContinente == "Asia" }.idContinente),
                // Europa
                Pais(nombrePais = "España", idContinente = continentes.first { it.nombreContinente == "Europa" }.idContinente),
                Pais(nombrePais = "Francia", idContinente = continentes.first { it.nombreContinente == "Europa" }.idContinente),
                // América
                Pais(nombrePais = "Estados Unidos", idContinente = continentes.first { it.nombreContinente == "América" }.idContinente),
                Pais(nombrePais = "Brasil", idContinente = continentes.first { it.nombreContinente == "América" }.idContinente),
                // África
                Pais(nombrePais = "Sudáfrica", idContinente = continentes.first { it.nombreContinente == "África" }.idContinente),
                Pais(nombrePais = "Egipto", idContinente = continentes.first { it.nombreContinente == "África" }.idContinente),
                // Oceanía
                Pais(nombrePais = "Australia", idContinente = continentes.first { it.nombreContinente == "Oceanía" }.idContinente),
                Pais(nombrePais = "Nueva Zelanda", idContinente = continentes.first { it.nombreContinente == "Oceanía" }.idContinente)
            )
            paises.forEach { paisDao.insertarPais(it) }
        }

        private suspend fun insertarDatosRegiones(regionDao: RegionDAO, paisDao: PaisDAO) {
            val paises = paisDao.obtenerTodosPaises().first()
            val regiones = listOf(
                // Regiones de China
                Region(nombreRegion = "Beijing", idPais = paises.first { it.nombrePais == "China" }.idPais, coordenadaX = 39.9042, coordenadaY = 116.4074),
                Region(nombreRegion = "Shanghái", idPais = paises.first { it.nombrePais == "China" }.idPais, coordenadaX = 31.2304, coordenadaY = 121.4737),
                Region(nombreRegion = "Guangdong", idPais = paises.first { it.nombrePais == "China" }.idPais, coordenadaX = 23.1291, coordenadaY = 113.2644),
                Region(nombreRegion = "Tíbet", idPais = paises.first { it.nombrePais == "China" }.idPais, coordenadaX = 29.6530, coordenadaY = 91.1172),

                // Regiones de Japón
                Region(nombreRegion = "Tokio", idPais = paises.first { it.nombrePais == "Japón" }.idPais, coordenadaX = 35.6895, coordenadaY = 139.6917),
                Region(nombreRegion = "Osaka", idPais = paises.first { it.nombrePais == "Japón" }.idPais, coordenadaX = 34.6937, coordenadaY = 135.5023),
                Region(nombreRegion = "Hokkaidō", idPais = paises.first { it.nombrePais == "Japón" }.idPais, coordenadaX = 43.0642, coordenadaY = 141.3469),
                Region(nombreRegion = "Kyoto", idPais = paises.first { it.nombrePais == "Japón" }.idPais, coordenadaX = 35.0116, coordenadaY = 135.7681),

                // Regiones de España
                Region(nombreRegion = "Cataluña", idPais = paises.first { it.nombrePais == "España" }.idPais, coordenadaX = 41.3851, coordenadaY = 2.1734),
                Region(nombreRegion = "Andalucía", idPais = paises.first { it.nombrePais == "España" }.idPais, coordenadaX = 37.3891, coordenadaY = -5.9845),
                Region(nombreRegion = "Galicia", idPais = paises.first { it.nombrePais == "España" }.idPais, coordenadaX = 42.5751, coordenadaY = -8.1339),
                Region(nombreRegion = "Madrid", idPais = paises.first { it.nombrePais == "España" }.idPais, coordenadaX = 40.4168, coordenadaY = -3.7038),

                // Regiones de Francia
                Region(nombreRegion = "Île-de-France", idPais = paises.first { it.nombrePais == "Francia" }.idPais, coordenadaX = 48.8566, coordenadaY = 2.3522),
                Region(nombreRegion = "Provenza-Alpes-Costa Azul", idPais = paises.first { it.nombrePais == "Francia" }.idPais, coordenadaX = 43.8357, coordenadaY = 6.2088),
                Region(nombreRegion = "Bretaña", idPais = paises.first { it.nombrePais == "Francia" }.idPais, coordenadaX = 48.2020, coordenadaY = -2.9326),
                Region(nombreRegion = "Normandía", idPais = paises.first { it.nombrePais == "Francia" }.idPais, coordenadaX = 49.1829, coordenadaY = -0.3707),

                // Regiones de Estados Unidos
                Region(nombreRegion = "California", idPais = paises.first { it.nombrePais == "Estados Unidos" }.idPais, coordenadaX = 36.7783, coordenadaY = -119.4179),
                Region(nombreRegion = "Nueva York", idPais = paises.first { it.nombrePais == "Estados Unidos" }.idPais, coordenadaX = 40.7128, coordenadaY = -74.0060),
                Region(nombreRegion = "Texas", idPais = paises.first { it.nombrePais == "Estados Unidos" }.idPais, coordenadaX = 31.9686, coordenadaY = -99.9018),
                Region(nombreRegion = "Florida", idPais = paises.first { it.nombrePais == "Estados Unidos" }.idPais, coordenadaX = 27.9944, coordenadaY = -81.7603),

                // Regiones de Brasil
                Region(nombreRegion = "Río de Janeiro", idPais = paises.first { it.nombrePais == "Brasil" }.idPais, coordenadaX = -22.9068, coordenadaY = -43.1729),
                Region(nombreRegion = "Sao Paulo", idPais = paises.first { it.nombrePais == "Brasil" }.idPais, coordenadaX = -23.5505, coordenadaY = -46.6333),
                Region(nombreRegion = "Bahía", idPais = paises.first { it.nombrePais == "Brasil" }.idPais, coordenadaX = -12.9714, coordenadaY = -38.5014),
                Region(nombreRegion = "Amazonas", idPais = paises.first { it.nombrePais == "Brasil" }.idPais, coordenadaX = -3.1190, coordenadaY = -60.0217),

                // Regiones de Sudáfrica
                Region(nombreRegion = "Ciudad del Cabo", idPais = paises.first { it.nombrePais == "Sudáfrica" }.idPais, coordenadaX = -33.9249, coordenadaY = 18.4241),
                Region(nombreRegion = "Johannesburgo", idPais = paises.first { it.nombrePais == "Sudáfrica" }.idPais, coordenadaX = -26.2041, coordenadaY = 28.0473),
                Region(nombreRegion = "Durban", idPais = paises.first { it.nombrePais == "Sudáfrica" }.idPais, coordenadaX = -29.8587, coordenadaY = 31.0218),
                Region(nombreRegion = "Pretoria", idPais = paises.first { it.nombrePais == "Sudáfrica" }.idPais, coordenadaX = -25.7479, coordenadaY = 28.2293),

                // Regiones de Australia
                Region(nombreRegion = "Sídney", idPais = paises.first { it.nombrePais == "Australia" }.idPais, coordenadaX = -33.8688, coordenadaY = 151.2093),
                Region(nombreRegion = "Melbourne", idPais = paises.first { it.nombrePais == "Australia" }.idPais, coordenadaX = -37.8136, coordenadaY = 144.9631),
                Region(nombreRegion = "Queensland", idPais = paises.first { it.nombrePais == "Australia" }.idPais, coordenadaX = -20.9176, coordenadaY = 142.7028),
                Region(nombreRegion = "Perth", idPais = paises.first { it.nombrePais == "Australia" }.idPais, coordenadaX = -31.9505, coordenadaY = 115.8605)
            )
            regiones.forEach { regionDao.insertarRegion(it) }
        }

        private suspend fun insertarDatosViajes(viajeDao: ViajeDAO, regionDao: RegionDAO) {
            val regiones = regionDao.obtenerTodasRegiones().first()
            val viajes = listOf(
                Viaje(nombreViaje = "Gran Muralla", idRegion = regiones.first { it.nombreRegion == "Beijing" }.idRegion),
                Viaje(nombreViaje = "Ruta del Modernismo", idRegion = regiones.first { it.nombreRegion == "Cataluña" }.idRegion),
                Viaje(nombreViaje = "Costa Oeste", idRegion = regiones.first { it.nombreRegion == "California" }.idRegion)
            )
            viajes.forEach { viajeDao.insertarViaje(it) }
        }
    }
}
