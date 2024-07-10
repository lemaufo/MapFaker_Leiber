package com.example.mapfaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mapfaker.ui.theme.MapFakerTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.github.javafaker.Faker


class MainActivity : ComponentActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private val faker = Faker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mapView = MapView(this)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        setContent {
            MapFakerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        MapScreen(modifier = Modifier.weight(1f))
                        LibraryInfoScreen()
                        PersonInfoScreen()
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Coordenadas preestablecidas
        val locations = listOf(
            LatLng(16.8958594, -92.0672737), // UTS
            LatLng(16.8777072,  -92.1068149), // UDS
            LatLng(16.8978695, -92.0922434), // UDM
            LatLng(16.9004096, -92.1022763), // UPN
            LatLng(16.9077055, -92.0679112),// UBB
            //Biblioteca
            LatLng(16.9052604, -92.1159875),//EfrainBartalome
            LatLng(16.9052604, -92.1159875),//FrayLaurencio
        )

        // Generar y añadir marcadores con coordenadas preestablecidas
        for (location in locations) {
            val title = "Location: ${location.latitude}, ${location.longitude}"
            val snippet = "This is a fixed location."

            mMap.addMarker(MarkerOptions().position(location).title(title).snippet(snippet))
        }

        // Mover la cámara al primer marcador
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations[0], 10f))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    @Composable
    fun MapScreen(modifier: Modifier = Modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
                .background(Color.Gray)
        ) {
            AndroidView(
                factory = { mapView },
                modifier = modifier
                    .size(300.dp) // Tamaño del cuadro pequeño
                    .align(Alignment.Center)
            )
        }
    }

    @Composable
    fun LibraryInfoScreen() {
        val libraryName = faker.company().name()
        val librarianName = faker.name().fullName()
        val address = faker.address().fullAddress()
        val openingHours = "Abierto de ${faker.number().numberBetween(6, 10)} AM a ${faker.number().numberBetween(5, 9)} PM"

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Nombre de la Biblioteca: $libraryName", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
            Text(text = "Nombre del Bibliotecario: $librarianName", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            Text(text = "Dirección: $address", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            Text(text = "Horario de Apertura: $openingHours", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        }
    }
    @Composable
    fun PersonInfoScreen() {
        val name = faker.name().fullName()
        val address = faker.address().fullAddress()
        val phoneNumber = faker.phoneNumber().phoneNumber()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Nombre: $name", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
            Text(text = "Dirección: $address", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            Text(text = "Teléfono: $phoneNumber", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        MapFakerTheme {
            Greeting("Android")
        }
    }
}

