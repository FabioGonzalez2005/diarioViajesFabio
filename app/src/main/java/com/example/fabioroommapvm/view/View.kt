package com.example.fabioroommapvm.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fabioroommapvm.model.dataclasses.Continente
import com.example.fabioroommapvm.model.dataclasses.Pais
import com.example.fabioroommapvm.model.dataclasses.Region
import com.example.fabioroommapvm.viewModel.UbicacionVistaModelo

@Composable
fun MenuVista(
    modifier: Modifier = Modifier,
    vistaModelo: UbicacionVistaModelo,
    context: Context
) {
    var nombreViaje by remember { mutableStateOf("") }
    var continenteSeleccionado by remember { mutableStateOf<Continente?>(null) }
    var paisSeleccionado by remember { mutableStateOf<Pais?>(null) }
    var regionSeleccionada by remember { mutableStateOf<Region?>(null) }

    var continenteMenuExpanded by remember { mutableStateOf(false) }
    var paisMenuExpanded by remember { mutableStateOf(false) }
    var regionMenuExpanded by remember { mutableStateOf(false) }

    val continentes by vistaModelo.continentes.collectAsState(initial = emptyList())
    val paises by vistaModelo.paises.collectAsState(initial = emptyList())
    val regiones by vistaModelo.regiones.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nombreViaje,
            onValueChange = { nombreViaje = it },
            label = { Text("Nombre del viaje") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown para seleccionar continente
        Box {
            OutlinedTextField(
                value = continenteSeleccionado?.nombreContinente ?: "Seleccione continente",
                onValueChange = {},
                label = { Text("Continente") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { continenteMenuExpanded = !continenteMenuExpanded }) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = continenteMenuExpanded,
                onDismissRequest = { continenteMenuExpanded = false }
            ) {
                continentes.forEach { continente ->
                    DropdownMenuItem(
                        onClick = {
                            continenteSeleccionado = continente
                            paisSeleccionado = null
                            regionSeleccionada = null
                            continenteMenuExpanded = false
                        },
                        text = { Text(continente.nombreContinente) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown para seleccionar país
        if (continenteSeleccionado != null) {
            Box {
                OutlinedTextField(
                    value = paisSeleccionado?.nombrePais ?: "Seleccione país",
                    onValueChange = {},
                    label = { Text("País") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { paisMenuExpanded = !paisMenuExpanded }) {
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownMenu(
                    expanded = paisMenuExpanded,
                    onDismissRequest = { paisMenuExpanded = false }
                ) {
                    paises.filter { it.idContinente == continenteSeleccionado?.idContinente }.forEach { pais ->
                        DropdownMenuItem(
                            onClick = {
                                paisSeleccionado = pais
                                regionSeleccionada = null
                                paisMenuExpanded = false
                            },
                            text = { Text(pais.nombrePais) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown para seleccionar región
        if (paisSeleccionado != null) {
            Box {
                OutlinedTextField(
                    value = regionSeleccionada?.nombreRegion ?: "Seleccione región",
                    onValueChange = {},
                    label = { Text("Región") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { regionMenuExpanded = !regionMenuExpanded }) {
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownMenu(
                    expanded = regionMenuExpanded,
                    onDismissRequest = { regionMenuExpanded = false }
                ) {
                    regiones.filter { it.idPais == paisSeleccionado?.idPais }.forEach { region ->
                        DropdownMenuItem(
                            onClick = {
                                regionSeleccionada = region
                                regionMenuExpanded = false
                            },
                            text = { Text(region.nombreRegion) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Acción para confirmar
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar viaje")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (regionSeleccionada != null) {
            Text(
                text = "Viaje a ${regionSeleccionada.nombreRegion}, ${paisSeleccionado?.nombrePais}, ${continenteSeleccionado?.nombreContinente}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )
        }
    }
}
