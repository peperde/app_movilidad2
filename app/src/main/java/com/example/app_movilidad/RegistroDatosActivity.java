package com.example.app_movilidad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroDatosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_datos);

        // 🔹 Conectar el ImageButton regresar
        ImageButton btnRegresar = findViewById(R.id.regresar);
        btnRegresar.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroDatosActivity.this, SesionActivity.class);
            startActivity(intent);
            finish(); // opcional: cierra RegistroDatosActivity para no volver con "atrás"
        });
    }
}