package com.example.Momento3AHA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bienvenido extends AppCompatActivity {

    Button cerrar, vendedor, venta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        cerrar = findViewById(R.id.btncerrarsesion);
        vendedor = findViewById(R.id.btnvendedorvendedor);
        venta = findViewById(R.id.btnventavendedor);

        //evento del boton para cerrar sesion

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });

        //generamos los eventos de los botones para pasar a los diferentes formularios



        vendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Lvendedor.class);
                startActivity(i);
            }
        });

        venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Lventas.class);
                startActivity(i);
            }
        });


    }
}
