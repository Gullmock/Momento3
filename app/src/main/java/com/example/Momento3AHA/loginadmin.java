package com.example.Momento3AHA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginadmin extends AppCompatActivity {

    EditText usuarioad, clavead;
    Button ingresar, regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginadmin);

        usuarioad = findViewById(R.id.etusuarioad);
        clavead = findViewById(R.id.etclavead);
        ingresar = findViewById(R.id.btningresarad);
        regresar = findViewById(R.id.btnregresar);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act = new Intent(getApplicationContext(), inicio.class);
                startActivity(act);
                finish();
            }
        });



        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usuarioad.getText().toString().isEmpty() && !clavead.getText().toString().isEmpty()) {
                    if ((usuarioad.getText().toString().equals("admin@appventa.com")) && (clavead.getText().toString().equals("123"))) {
                        Intent act = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(act);

                    } else {
                        Toast.makeText(getApplicationContext(), "Los datos ingresados, son incorrectos", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Es necesario ingresar todos los datos", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
