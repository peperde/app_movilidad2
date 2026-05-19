package com.example.app_movilidad;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.Arrays;
import java.util.List;

public class LineaCentroActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Marker vehiculoMarker;
    private Handler handler = new Handler();
    private int index = 0;
    private TextView txtTiempo, txtEstado;

    // Ruta simulada (evita edificios - son puntos sobre calles)
    private List<LatLng> rutaReal = Arrays.asList(
            new LatLng(4.7110, -74.0721),
            new LatLng(4.7115, -74.0725),
            new LatLng(4.7120, -74.0728),
            new LatLng(4.7125, -74.0730),
            new LatLng(4.7130, -74.0732),
            new LatLng(4.7135, -74.0735),
            new LatLng(4.7140, -74.0740),
            new LatLng(4.7145, -74.0745)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linea_centro);

        // Conectar los TextView de tu layout
        txtTiempo = findViewById(R.id.tiempoLlegada);
        txtEstado = findViewById(R.id.panel_estado);

        // Configurar el mapa (con el ID que ya tienes)
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragmentLinea1);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Botón regresar (con el ID que ya tienes)
        Button btnRegresar = findViewById(R.id.regresar_pagprincipal);
        btnRegresar.setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Dibujar la ruta
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(rutaReal)
                .width(10)
                .color(0xFF0000FF);
        mMap.addPolyline(polylineOptions);

        // Mover cámara al primer punto
        mMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory
                .newLatLngZoom(rutaReal.get(0), 15));

        // Agregar marcador del vehículo
        vehiculoMarker = mMap.addMarker(new MarkerOptions()
                .position(rutaReal.get(0))
                .title("Vehículo"));

        // Iniciar simulación
        iniciarSimulacion();
    }

    private void iniciarSimulacion() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < rutaReal.size()) {
                    LatLng nuevaPos = rutaReal.get(index);
                    if (vehiculoMarker != null && mMap != null) {
                        vehiculoMarker.setPosition(nuevaPos);
                        mMap.animateCamera(com.google.android.gms.maps.CameraUpdateFactory
                                .newLatLng(nuevaPos));
                    }

                    // Actualizar textos
                    int minutosRestantes = (rutaReal.size() - index) * 2;
                    txtTiempo.setText("Faltan " + minutosRestantes + " min");
                    txtEstado.setText("En ruta");

                    index++;
                    handler.postDelayed(this, 2000);
                } else {
                    txtEstado.setText("Llegó");
                    txtTiempo.setText("Destino alcanzado");
                }
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}