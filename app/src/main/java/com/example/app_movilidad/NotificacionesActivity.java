package com.example.app_movilidad;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class NotificacionesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificaciones);

        Button btnRegresar = findViewById(R.id.regresar);
        btnRegresar.setOnClickListener(v -> finish());
    }
}