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

public class Fventa extends AppCompatActivity {

    EditText Nventa, Nzona, Nvendedor, fecha, valor, ventaven;
    Button Ingresarventa, eliminar, editar, listar, buscar, regresar;

    //instanciamos la base de datos
    BaseDatos helperbd = new BaseDatos(this , "BDVENTAS", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fventa);

        Nventa = findViewById(R.id.etnventa);
        Nzona = findViewById(R.id.etidzona);
        Nvendedor = findViewById(R.id.etidvendedor);
        fecha = findViewById(R.id.etfechaventa);
        valor = findViewById(R.id.etvalorv);
        Ingresarventa = findViewById(R.id.btningresarventa);
        eliminar = findViewById(R.id.btneliminarven);
        editar = findViewById(R.id.btneditarven);
        listar = findViewById(R.id.btnlistarven);
        buscar = findViewById(R.id.btnbuscarven);
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

                if (validarventa())
                {
                    SQLiteDatabase obde = helperbd.getWritableDatabase();

                    if (Buscarusu(Nventa.getText().toString()))//lo hallo
                    {
                        obde.execSQL("UPDATE ventas SET " +
                                "Nventa = '"+Nventa.getText().toString()+"'," +
                                " idzona = '"+Nzona.getText().toString()+"'," +
                                " idvendedor = '"+Nvendedor.getText().toString()+"'," +
                                " fecha = '"+fecha.getText().toString()+"'," +
                                " valor = '"+valor.getText().toString()+"'" +
                                "WHERE Nventa = '"+Nventa.getText().toString()+"'");
                        Toast.makeText(Fventa.this, "La venta se ha actualizado", Toast.LENGTH_LONG).show();


                    }
                    else
                    {
                        Toast.makeText(Fventa.this, "La venta no existe", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //evento del boton eliminar

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validarventa())
                {
                    //Alerta par averificar que el numero del gps sea correcto

                    AlertDialog.Builder Mensajealerta = new AlertDialog.Builder(Fventa.this);
                    Mensajealerta.setMessage("¿Esta seguro de eliminar la venta con el ID: "
                            + Nventa.getText().toString() + " ?")
                            .setTitle("Advertencia")
                            .setCancelable(false)
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //ejecutamos mando
                                    SQLiteDatabase obde = helperbd.getWritableDatabase();

                                    if (Buscarusu(Nventa.getText().toString()))//lo hallo
                                    {
                                        obde.execSQL("DELETE FROM ventas WHERE Nventa = '"+Nventa.getText().toString()+"'");
                                        Toast.makeText(Fventa.this, "La venta ha sido eliminada", Toast.LENGTH_LONG).show();

                                        Nventa.setText("");
                                        Nzona.setText("");
                                        Nvendedor.setText("");
                                        valor.setText("");
                                        fecha.setText("");
                                        Nventa.requestFocus();
                                    }
                                    else
                                    {
                                        Toast.makeText(Fventa.this, "La venta no existe", Toast.LENGTH_LONG).show();
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



        //guardar venta boton

        Ingresarventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ingresamos los datos a la base de datos

                if (validar())
                {
                    Guardarventa(Nventa.getText().toString(),
                            Nzona.getText().toString(),
                            Nvendedor.getText().toString(),
                            fecha.getText().toString(),
                            valor.getText().toString());
                }
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
            Nventa.setError("Es necesario un número de venta");
            retorno = false;
        }

        return retorno;
    }

    private void Guardarventa(String Xventa, String Xidzona, String Xidvendedor, String Xfecha, String Xvalor) {

        SQLiteDatabase odb = helperbd.getWritableDatabase();

        //verificar si el usuario existe
        if (!Buscarusu(Nventa.getText().toString()))
        {
            if (Buscarvendedor(Nvendedor.getText().toString()))
            {
                if (Buscarzonas(Nzona.getText().toString()))
                {
                    try
                    {
                        //crear contenedor (tabla temporal) de datos del contacto
                        ContentValues c = new ContentValues();
                        c.put("Nventa", Xventa);
                        c.put("idzona",Xidzona);
                        c.put("idvendedor",Xidvendedor);
                        c.put("fecha", Xfecha);
                        c.put("valor", Xvalor);
                        //INSERTAMOS LOS DATOS
                        odb.insert("ventas",null,c);


                        //agregar esta venta al vendedor seleccionado

                        String query = "SELECT tventas FROM vendedor WHERE codvendedor = '"+Nvendedor.getText().toString()+"'";
                        Cursor cusuario = odb.rawQuery(query , null);

                        if (cusuario.moveToFirst())
                        {
                            cusuario.getInt(0);
                        }

                        int estasi = cusuario.getInt(0);



                        int VentaVendedor = Integer.parseInt(valor.getText().toString());

                        int total = estasi + VentaVendedor;

                        odb.execSQL("UPDATE vendedor SET " + "tventas = '"+total+"'" + "WHERE codvendedor = '"+Nvendedor.getText().toString()+"'");

                        odb.close();

                        Toast.makeText(this,"Venta agregada correctamente",Toast.LENGTH_SHORT).show();

                        Nventa.setText("");
                        Nzona.setText("");
                        Nvendedor.setText("");
                        fecha.setText("");
                        valor.setText("");
                        Nventa.requestFocus();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "La zona no existe", Toast.LENGTH_LONG).show();
                }

            }
            else
                {
                    Toast.makeText(this, "El vendedor no existe", Toast.LENGTH_LONG).show();
                }

        }
        else
        {
            Toast.makeText(this, "La venta ya existe", Toast.LENGTH_LONG).show();
        }


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
