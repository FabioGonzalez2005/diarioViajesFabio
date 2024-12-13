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
import com.example.fabioroommapvm.viewModel.MarcadorVistaModelo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val baseDeDatos = AppDatabase.getDatabase(this)
        val daoMarcador = baseDeDatos.marcadorDao()

        enableEdgeToEdge()
        setContent {
            ViewModelRMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // Usamos el ViewModel
                    val vistaModelo: MarcadorVistaModelo = viewModel {
                        MarcadorVistaModelo(daoMarcador)
                    }

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
