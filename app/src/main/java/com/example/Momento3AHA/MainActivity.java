package com.example.Momento3AHA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button vendedor, zona, venta, cerrarsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referenciamos botones

        vendedor = findViewById(R.id.btnvendedora);
        zona = findViewById(R.id.btnzona);
        venta = findViewById(R.id.btnventa);
        cerrarsesion = findViewById(R.id.btncerrarsesion);


        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), loginadmin.class);
            startActivity(i);
            finish();
            }
        });

        //generamos los eventos de los botones para pasar a los diferentes formularios



        vendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Fvendedor.class);
                startActivity(i);
            }
        });

        zona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Fzona.class);
                startActivity(i);
            }
        });

        venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Fventa.class);
                startActivity(i);
            }
        });

    }
}
