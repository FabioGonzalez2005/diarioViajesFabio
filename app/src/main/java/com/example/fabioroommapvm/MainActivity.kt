package com.example.fabioroommapvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fabioroommapvm.model.AppDatabase
import com.example.fabioroommapvm.ui.theme.ViewModelRMTheme
import com.example.fabioroommapvm.view.MapaVista
import com.example.fabioroommapvm.viewModel.UbicacionVistaModelo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializamos la base de datos
        val baseDeDatos = AppDatabase.getDatabase(this)
        val continenteDao = baseDeDatos.continenteDao()
        val paisDao = baseDeDatos.paisDao()
        val regionDao = baseDeDatos.regionDao()
        val viajeDao = baseDeDatos.viajeDao()

        enableEdgeToEdge()
        setContent {
            ViewModelRMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // Usamos el ViewModel para gestionar la lógica de regiones, viajes, continentes y países
                    val vistaModelo: UbicacionVistaModelo = viewModel {
                        UbicacionVistaModelo(continenteDao, paisDao, regionDao, viajeDao)
                    }

                    // Renderizamos la vista del mapa
                    MapaVista(
                        modifier = Modifier.padding(innerPadding),
                        vistaModelo = vistaModelo,
                        context = this // Este contexto es necesario para que funcione la aplicación
                    )
                }
            }
        }
    }
}
