package com.example.Momento3AHA;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Lventas extends AppCompatActivity {

    ListView listVen;
    ArrayList<String> listado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lventas);

        listVen = findViewById(R.id.listaventas);
        CargarListado();
    }

    private void CargarListado() {
        listado= listaVendedor();
        ArrayAdapter<String> adapterUsu = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,listado);
        listVen.setAdapter(adapterUsu);
    }

    private ArrayList<String> listaVendedor() {
        ArrayList<String> datosUsu = new ArrayList<String>();
        BaseDatos helper = new BaseDatos (this, "BDVENTAS", null, 1);

        SQLiteDatabase obd = helper.getReadableDatabase();
        String consulta = "SELECT Nventa, idzona, idvendedor, fecha, valor FROM ventas";

        Cursor cUsuarios = obd.rawQuery(consulta, null);

        if(cUsuarios.moveToFirst())
        {
            do{

                datosUsu.add("— NÚMERO VENTA: " + cUsuarios.getString(0));
                datosUsu.add("— ID ZONA: " + cUsuarios.getString(1));
                datosUsu.add("— ID VENDEDOR: " + cUsuarios.getString(2));
                datosUsu.add("— FECHA: " + cUsuarios.getString(3));
                datosUsu.add("— VALOR: " + cUsuarios.getString(4));
                String espacio = "———————";
                String espacio1 = "    ";
                datosUsu.add(espacio + espacio1);

            }while(cUsuarios.moveToNext());
        }
        obd.close();  //CERRAR BASE DE DATOS
        return datosUsu;
    }
}
