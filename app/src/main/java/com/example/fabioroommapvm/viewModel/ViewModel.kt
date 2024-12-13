package com.example.fabioroommapvm.viewModel

import androidx.lifecycle.ViewModel
import com.example.fabioroommapvm.model.dao.RegionDAO
import com.example.fabioroommapvm.model.dao.PaisDAO
import com.example.fabioroommapvm.model.dao.ContinenteDAO
import com.example.fabioroommapvm.model.dao.ViajeDAO
import com.example.fabioroommapvm.model.dataclasses.Continente
import com.example.fabioroommapvm.model.dataclasses.Pais
import com.example.fabioroommapvm.model.dataclasses.Region
import com.example.fabioroommapvm.model.dataclasses.Viaje
import kotlinx.coroutines.flow.Flow

// Clase ViewModel que conecta la capa de datos con la interfaz de usuario.
class UbicacionVistaModelo(
    private val continenteDao: ContinenteDAO,
    private val paisDao: PaisDAO,
    private val regionDao: RegionDAO,
    private val viajeDao: ViajeDAO
) : ViewModel() {

    // Flujo que proporciona una lista de continentes desde la base de datos.
    val continentes: Flow<List<Continente>> = continenteDao.obtenerTodosContinentes()

    // Flujo que proporciona una lista de países desde la base de datos.
    val paises: Flow<List<Pais>> = paisDao.obtenerTodosPaises()

    // Flujo que proporciona una lista de regiones desde la base de datos.
    val regiones: Flow<List<Region>> = regionDao.obtenerTodasRegiones()

    // Flujo que proporciona una lista de viajes desde la base de datos.
    val viajes: Flow<List<Viaje>> = viajeDao.obtenerTodosViajes()
}
