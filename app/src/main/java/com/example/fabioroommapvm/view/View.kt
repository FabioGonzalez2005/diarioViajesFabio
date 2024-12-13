package com.example.fabioroommapvm.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fabioroommapvm.R
import com.example.fabioroommapvm.viewModel.MarcadorVistaModelo
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex

val GoogleSat: OnlineTileSourceBase = object : XYTileSource(
    "Google-Sat",
    0, 19, 256, ".png", arrayOf(
        "https://mt0.google.com",
        "https://mt1.google.com",
        "https://mt2.google.com",
        "https://mt3.google.com"
    )
) {
    override fun getTileURLString(aTile: Long): String {
        return baseUrl + "/vt/lyrs=s&x=" + MapTileIndex.getX(aTile) + "&y=" + MapTileIndex.getY(
            aTile
        ) + "&z=" + MapTileIndex.getZoom(aTile)
    }
}

@Composable
fun MapaVista(
    modifier: Modifier = Modifier,
    vistaModelo: MarcadorVistaModelo,
    context: Context // Contexto necesario para cargar los drawables
) {
    val marcadoresConTipo by vistaModelo.marcadoresConTipo.collectAsState(initial = emptyList())

    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(28.957375205489004, -13.554245657440829)
        zoom = 17.0
    }

    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    SideEffect {
        mapProperties = mapProperties
            .copy(tileSources = GoogleSat)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.SHOW_AND_FADEOUT)
    }

    OpenStreetMap(
        modifier = modifier.fillMaxSize(),
        cameraState = cameraState,
        properties = mapProperties
    ) {
        marcadoresConTipo.forEach { marcadorConTipo ->
            val marcador = marcadorConTipo.marcador
            val tipo = marcadorConTipo.tiposMarcadores[0].tituloTipoMarcador

            val markerState = rememberMarkerState(
                geoPoint = GeoPoint(marcador.coordenadaX, marcador.coordenadaY)
            )

            // Selección del ícono según el tipo
            val markerIcon = when (tipo) {
                "Restaurante" -> context.getDrawable(R.drawable.restaurant)
                "Supermercado" -> context.getDrawable(R.drawable.supermercado)
                "Playa" -> context.getDrawable(R.drawable.playa)
                "Biblioteca" -> context.getDrawable(R.drawable.biblioteca)
                else -> context.getDrawable(org.osmdroid.library.R.drawable.marker_default)
            }

            // Crear el marcador con el ícono
            Marker(
                state = markerState,
                title = marcador.tituloMarcador,
                snippet = tipo,
                icon = markerIcon
            ) {
                Column(
                    modifier = Modifier
                        .size(180.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = it.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Text(
                        text = it.snippet,
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}

