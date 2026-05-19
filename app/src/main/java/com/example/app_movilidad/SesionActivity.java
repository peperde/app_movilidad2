package com.example.app_movilidad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SesionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Aquí enlazas tu layout sesion.xml
        setContentView(R.layout.sesion);

        // 🔹 Conectar el botón registro_cuenta
        Button btnRegistro = findViewById(R.id.registro_cuenta);
        btnRegistro.setOnClickListener(v -> {
            // Al hacer clic, abre la Activity que carga registro_datos.xml
            Intent intent = new Intent(SesionActivity.this, RegistroDatosActivity.class);
            startActivity(intent);
        });
    }
}