package com.example.app_movilidad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class PagPrincipalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pag_principal);

        ImageButton btnVanCentro = findViewById(R.id.btnLinea1);
        btnVanCentro.setOnClickListener(v -> {
            Intent intent = new Intent(PagPrincipalActivity.this, LineaCentroActivity.class);
            startActivity(intent);
        });
    }
}