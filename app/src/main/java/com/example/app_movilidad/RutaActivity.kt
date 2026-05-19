package com.example.app_movilidad

import android.os.Bundle
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.*

class RutaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var markerVehiculo: Marker? = null

    // Lista de paradas simuladas
    private val paradasRutaA01 = listOf(
        LatLng(20.4740, -103.4430), // Tlajomulco Centro
        LatLng(20.4755, -103.4450), // Constitución e Independencia
        LatLng(20.4765, -103.4470), // Plaza Principal
        LatLng(20.4780, -103.4490), // Degollado Poniente
        LatLng(20.4790, -103.4510), // Higuera
        LatLng(20.4800, -103.4530)  // Vallarta Oriente
    )

    private val REQUEST_LOCATION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruta)

        // Verificar permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Activar ubicación si ya se concedió el permiso
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }

        // Dibujar la ruta
        map.addPolyline(
            PolylineOptions()
                .addAll(paradasRutaA01)
                .color(android.graphics.Color.BLUE)
                .width(8f)
        )

        // Colocar marcadores en cada parada
        paradasRutaA01.forEachIndexed { index, parada ->
            map.addMarker(
                MarkerOptions()
                    .position(parada)
                    .title("Parada ${index + 1}")
            )
        }

        // Centrar cámara en la primera parada
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(paradasRutaA01[0], 14f))

        // Iniciar simulación del vehículo
        simularVehiculo()
    }

    private fun simularVehiculo() {
        GlobalScope.launch(Dispatchers.Main) {
            for (punto in paradasRutaA01) {
                if (markerVehiculo == null) {
                    markerVehiculo = map.addMarker(
                        MarkerOptions()
                            .position(punto)
                            .title("Vehículo A-01")
                    )
                } else {
                    markerVehiculo?.position = punto
                }
                delay(5000) // espera 5 segundos en cada parada
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (::map.isInitialized) {
                    map.isMyLocationEnabled = true
                }
            } else {
                Toast.makeText(this, "Se necesita permiso de ubicación para mostrar el mapa", Toast.LENGTH_LONG).show()
            }
        }
    }
}