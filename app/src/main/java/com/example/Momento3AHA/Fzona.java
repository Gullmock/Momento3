package com.example.Momento3AHA;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Fzona extends AppCompatActivity {

    EditText Nzona, nombre, comision;
    Button Ingresarzona, listar, editar, eliminar, buscar, regresar;

    //instanciamos la base de datos
    BaseDatos helperbd = new BaseDatos(this , "BDVENTAS", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fzona);

        Nzona = findViewById(R.id.etnumerozona);
        nombre = findViewById(R.id.etnombrezona);
        comision = findViewById(R.id.etporzona);
        Ingresarzona = findViewById(R.id.btningresarzona);
        listar = findViewById(R.id.btnlistarz);
        buscar = findViewById(R.id.btnbuscarz);
        eliminar = findViewById(R.id.btneliminarz);
        editar = findViewById(R.id.btneditarz);
        regresar = findViewById(R.id.btnregresar);

        //evento del boton editar

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validarzona())
                {
                    SQLiteDatabase obde = helperbd.getWritableDatabase();

                    if (Buscarusu(Nzona.getText().toString()))//lo hallo
                    {
                        obde.execSQL("UPDATE zona SET " +
                                "Nzona = '"+Nzona.getText().toString()+"'," +
                                " nombre = '"+nombre.getText().toString()+"'," +
                                " comision = '"+comision.getText().toString()+"'" +
                                "WHERE Nzona = '"+Nzona.getText().toString()+"'");
                        Toast.makeText(Fzona.this, "La zona ha sido actualizada", Toast.LENGTH_LONG).show();


                    }
                    else
                    {
                        Toast.makeText(Fzona.this, "La zona no existe", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //evento del boton eliminar

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validarzona())
                {
                    //Alerta par averificar que el numero del gps sea correcto

                    AlertDialog.Builder Mensajealerta = new AlertDialog.Builder(Fzona.this);
                    Mensajealerta.setMessage("¿Esta seguro de eliminar la zona con el ID: "
                            + Nzona.getText().toString() + " ?")
                            .setTitle("Advertencia")
                            .setCancelable(false)
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //ejecutamos mando
                                    SQLiteDatabase obde = helperbd.getWritableDatabase();

                                    if (Buscarusu(Nzona.getText().toString()))//lo hallo
                                    {
                                        obde.execSQL("DELETE FROM zona WHERE Nzona = '"+Nzona.getText().toString()+"'");
                                        Toast.makeText(Fzona.this, "La zona ha sido eliminada satisfactoriamente", Toast.LENGTH_LONG).show();

                                        Nzona.setText("");
                                        nombre.setText("");
                                        comision.setText("");
                                        Nzona.requestFocus();
                                    }
                                    else
                                    {
                                        Toast.makeText(Fzona.this, "La zona no existe", Toast.LENGTH_LONG).show();
                                    }


                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //si el usuario no esta seguro cerramos el alert y pasamos a editar el numero
                                    dialogInterface.dismiss();

                                }
                            }).show();
                }

            }
        });

        //evento del boton buscar

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validarzona())
                {
                    buscarzona(Nzona.getText().toString());
                }
            }
        });

        //evento del boton listar

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Lzonas.class);
                startActivity(i);
            }
        });



        Ingresarzona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ingresamos los datos a la base de datos

                if (validar())
                {
                    Guardarzona(Nzona.getText().toString(),
                            nombre.getText().toString(),
                            comision.getText().toString());
                }
            }
        });

    }

    //metodo para validar que los campos esten llenos
    public boolean validar(){

        boolean retorno = true;

        String c1 = Nzona.getText().toString();
        String c2 = comision.getText().toString();
        String c3 = nombre.getText().toString();

        if (c1.isEmpty())
        {
            Nzona.setError("Debe ingresar una zona");
            retorno = false;
        }
        if (c2.isEmpty())
        {
            comision.setError("Debe ingresar un valor");
            retorno = false;
        }
        if (c3.isEmpty())
        {
            nombre.setError("Debe ingresar un nombre");
            retorno = false;
        }

        return retorno;
    }

    public boolean validarzona(){

        boolean retorno = true;

        String c1 = Nzona.getText().toString();

        if (c1.isEmpty())
        {
            Nzona.setError("Es necesario ingresar un numero de zona");
            retorno = false;
        }

        return retorno;
    }

    //metodo para buscar usuario

    private boolean Buscarusu(String musuario)
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
    private void Guardarzona(String Xnzona, String Xnombre, String Xcomision) {

        SQLiteDatabase odb = helperbd.getWritableDatabase();

        //verificar si el usuario existe
        if (!Buscarusu(Nzona.getText().toString()))
        {
            try
            {
                //crear contenedor (tabla temporal) de datos del contacto
                ContentValues c = new ContentValues();
                c.put("Nzona", Xnzona);
                c.put("nombre",Xnombre);
                c.put("comision",Xcomision);
                //INSERTAMOS LOS DATOS
                odb.insert("zona",null,c);
                odb.close();
                Toast.makeText(this,"La zona ha sido agregada",Toast.LENGTH_SHORT).show();

                Nzona.setText("");
                nombre.setText("");
                comision.setText("");
                Nzona.requestFocus();
            }
            catch (Exception e)
            {
                Toast.makeText(this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "La zona ya existe", Toast.LENGTH_LONG).show();
        }


    }

    private void buscarzona(String Musuario) {

        SQLiteDatabase obd = helperbd.getReadableDatabase();
        String query = "SELECT Nzona,nombre,comision FROM zona WHERE Nzona = '"+Musuario+"'";
        Cursor cusuario = obd.rawQuery(query , null);

        if (cusuario.moveToFirst())
        {
            Nzona.setText(cusuario.getString(0));
            nombre.setText(cusuario.getString(1));
            comision.setText(cusuario.getString(2));
        }
        else
        {
            Toast.makeText(this, "La zona no existe", Toast.LENGTH_LONG).show();
        }

    }

}
