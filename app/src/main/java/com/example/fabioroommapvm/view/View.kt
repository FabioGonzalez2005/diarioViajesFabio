package com.example.fabioroommapvm.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
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
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import org.osmdroid.util.GeoPoint

@Composable
fun MenuVista(
    modifier: Modifier = Modifier,
    vistaModelo: UbicacionVistaModelo,
    context: Context
) {
    // Estado para las selecciones
    var nombreViaje by remember { mutableStateOf("") }
    var continenteSeleccionado by remember { mutableStateOf<Continente?>(null) }
    var paisSeleccionado by remember { mutableStateOf<Pais?>(null) }
    var regionSeleccionada by remember { mutableStateOf<Region?>(null) }
    var viajesConfirmados by remember { mutableStateOf<List<Pair<String, Int>>>(emptyList()) }

    // Estado para el mapa y el marcador
    var coordenadaX by remember { mutableStateOf(28.957375205489004) }
    var coordenadaY by remember { mutableStateOf(-13.554245657440829) }
    var marcadorVisible by remember { mutableStateOf(false) }
    var mapaVisible by remember { mutableStateOf(false) }  // Estado para mostrar/ocultar el mapa
    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(coordenadaX, coordenadaY)
        zoom = 17.0
    }

    // Estado para los menús desplegables
    var continenteMenuExpanded by remember { mutableStateOf(false) }
    var paisMenuExpanded by remember { mutableStateOf(false) }
    var regionMenuExpanded by remember { mutableStateOf(false) }

    // Obtener los datos
    val continentes by vistaModelo.continentes.collectAsState(initial = emptyList())
    val paises by vistaModelo.paises.collectAsState(initial = emptyList())
    val regiones by vistaModelo.regiones.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de nombre del viaje
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
                if (regionSeleccionada != null && paisSeleccionado != null && continenteSeleccionado != null && nombreViaje.isNotBlank()) {
                    val nuevoViaje = "Viaje: $nombreViaje\n" +
                            "Continente: ${continenteSeleccionado!!.nombreContinente}\n" +
                            "País: ${paisSeleccionado!!.nombrePais}\n" +
                            "Región: ${regionSeleccionada!!.nombreRegion}"

                    // Asignamos un ID único al viaje
                    val nuevoId = viajesConfirmados.size

                    // Agregar el nuevo viaje a la lista
                    viajesConfirmados = viajesConfirmados + (nuevoViaje to nuevoId)

                    // Limpiar los campos para agregar otro viaje
                    nombreViaje = ""
                    continenteSeleccionado = null
                    paisSeleccionado = null
                    regionSeleccionada = null
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar viaje")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la lista de viajes confirmados con botón para eliminar
        viajesConfirmados.forEach { (viaje, id) ->
            Row(
                modifier = Modifier
                    .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = viaje,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )




                }
            }
