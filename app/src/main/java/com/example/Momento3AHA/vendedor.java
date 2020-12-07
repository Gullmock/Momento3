package com.example.Momento3AHA;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class vendedor extends AppCompatActivity {

    EditText vendedor, nombre, email, clave, fecha, totalv;
    Button listar, buscar, regresar;

    //instanciamos la base de datos
    BaseDatos helperbd = new BaseDatos(this, "BDVENTAS", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fvendedor);

        vendedor = findViewById(R.id.etvendedorvende);
        nombre = findViewById(R.id.etnombrevende);
        email = findViewById(R.id.etemailvende);
        clave = findViewById(R.id.etclavevende);
        fecha = findViewById(R.id.etfechavendedorvende);
        totalv = findViewById(R.id.etventavendevende);
        listar = findViewById(R.id.btnlistarvendevende);
        buscar = findViewById(R.id.btnbuscarvendevende);
        regresar = findViewById(R.id.btnregresar);

        //evento del boton editar

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //evento del boton buscar

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buscarvendedor(vendedor.getText().toString());
            }
        });

        //evento del boton eliminar


        //evneto del boton listar

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Lvendedor.class);
                startActivity(i);
            }
        });


    }


    private void buscarvendedor(String Musuario) {

        SQLiteDatabase obd = helperbd.getReadableDatabase();
        String query = "SELECT codvendedor,nombre,email,clave,fechai,tventas FROM vendedor WHERE codvendedor = '" + Musuario + "'";
        Cursor cusuario = obd.rawQuery(query, null);

        if (cusuario.moveToFirst()) {
            vendedor.setText(cusuario.getString(0));
            nombre.setText(cusuario.getString(1));
            email.setText(cusuario.getString(2));
            clave.setText(cusuario.getString(3));
            fecha.setText(cusuario.getString(4));
            totalv.setText(cusuario.getString(5));
        } else {
            Toast.makeText(this, "El vendedor no existe", Toast.LENGTH_LONG).show();
        }

    }
}
