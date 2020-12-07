package com.example.Momento3AHA;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Lzonas extends AppCompatActivity {

    ListView listVen;
    ArrayList<String> listado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lzonas);

        listVen = findViewById(R.id.listazonas);
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
        String consulta = "SELECT Nzona, nombre, comision FROM zona";

        Cursor cUsuarios = obd.rawQuery(consulta, null);

        if(cUsuarios.moveToFirst())
        {
            do{

                datosUsu.add("— NÚMERO DE ZONA: " + cUsuarios.getString(0));
                datosUsu.add("— NOMBRE: " + cUsuarios.getString(1));
                datosUsu.add("— COMISIÓN: " + cUsuarios.getString(2));
                String espacio = "———————";
                String espacio1 = "    ";
                datosUsu.add(espacio + espacio1);

            }while(cUsuarios.moveToNext());
        }
        obd.close();  //CERRAR BASE DE DATOS
        return datosUsu;
    }
}
