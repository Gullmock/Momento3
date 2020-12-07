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

public class venta extends AppCompatActivity {

    EditText Nventa, Nzona, Nvendedor, fecha, valor;
    Button listar, buscar, regresar;

    //instanciamos la base de datos
    BaseDatos helperbd = new BaseDatos(this , "BDVENTAS", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fventa);

        Nventa = findViewById(R.id.etnventavendeventa);
        Nzona = findViewById(R.id.etidzonaventavende);
        Nvendedor = findViewById(R.id.etidvendedorventaven);
        fecha = findViewById(R.id.etfechaventavendedor);
        valor = findViewById(R.id.etvalorventavend);
        regresar = findViewById(R.id.btnregresar);
        listar = findViewById(R.id.btnlistarventavendedor);
        buscar = findViewById(R.id.btnbuscarventavendedor);

        //evento del boton buscar

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validarventa())
                {
                    buscarventa(Nventa.getText().toString());
                }
            }
        });

        //bonton de listar venta

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Lventas.class);
                startActivity(i);
            }
        });
    }

    //metodo para validar que los campos esten llenos
    public boolean validar(){

        boolean retorno = true;

        String c1 = Nzona.getText().toString();
        String c2 = Nventa.getText().toString();
        String c3 = Nvendedor.getText().toString();
        String c4 = fecha.getText().toString();
        String c5 = valor.getText().toString();

        if (c1.isEmpty())
        {
            Nzona.setError("Debe ingresar una zona");
            retorno = false;
        }
        if (c2.isEmpty())
        {
            Nventa.setError("Debe ingresar un valor");
            retorno = false;
        }
        if (c3.isEmpty())
        {
            Nvendedor.setError("Debe ingresar un vendedor");
            retorno = false;
        }
        if (c4.isEmpty())
        {
            fecha.setError("Debe ingresar una fecha");
            retorno = false;
        }
        if (c5.isEmpty())
        {
            valor.setError("Debe ingresar un valor");
            retorno = false;
        }

        return retorno;
    }

    //metodo para buscar usuario

    private boolean Buscarusu(String musuario)
    {

        SQLiteDatabase obd = helperbd.getReadableDatabase();

        String query = "SELECT Nventa FROM ventas WHERE Nventa = '"+musuario+"'";

        Cursor cbuscar = obd.rawQuery(query, null);

        if (cbuscar.moveToFirst())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //metodo para buscar usuarios


    private void buscarventa(String Musuario) {

        SQLiteDatabase obd = helperbd.getReadableDatabase();
        String query = "SELECT Nventa,idzona,idvendedor,fecha,valor FROM ventas WHERE Nventa = '"+Musuario+"'";
        Cursor cusuario = obd.rawQuery(query , null);

        if (cusuario.moveToFirst())
        {
            Nventa.setText(cusuario.getString(0));
            Nzona.setText(cusuario.getString(1));
            Nvendedor.setText(cusuario.getString(2));
            fecha.setText(cusuario.getString(3));
            valor.setText(cusuario.getString(4));
        }
        else
        {
            Toast.makeText(this, "La venta no existe", Toast.LENGTH_LONG).show();
        }

    }


    public boolean validarventa(){

        boolean retorno = true;

        String c1 = Nventa.getText().toString();

        if (c1.isEmpty())
        {
            Nventa.setError("Debe ingresar un numero de venta");
            retorno = false;
        }

        return retorno;
    }

    private boolean Buscarvendedor(String musuario)
    {

        SQLiteDatabase obd = helperbd.getReadableDatabase();

        String query = "SELECT codvendedor FROM vendedor WHERE codvendedor = '"+musuario+"'";

        Cursor cbuscar = obd.rawQuery(query, null);

        if (cbuscar.moveToFirst())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean Buscarzonas(String musuario)
    {

        SQLiteDatabase obd = helperbd.getReadableDatabase();

        String query = "SELECT Nzona FROM zona WHERE Nzona = '"+musuario+"'";

        Cursor cbuscar = obd.rawQuery(query, null);

        if (cbuscar.moveToFirst())
        {
            return true;
        }
        else
        {
            return false;
        }
    }



}
