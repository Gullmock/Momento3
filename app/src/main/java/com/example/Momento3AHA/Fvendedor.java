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

public class Fvendedor extends AppCompatActivity {

    EditText vendedor, nombre, email, clave, fecha, totalv;
    Button Ingresarvendedor, listar, editar, eliminar, buscar, regresar;

    //instanciamos la base de datos
    BaseDatos helperbd = new BaseDatos(this , "BDVENTAS", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fvendedor);

        vendedor = findViewById(R.id.etvendedor);
        nombre = findViewById(R.id.etnombre);
        email = findViewById(R.id.etemail);
        clave = findViewById(R.id.etclave);
        fecha = findViewById(R.id.etfechavendedor);
        totalv = findViewById(R.id.etventa);
        Ingresarvendedor = findViewById(R.id.btningresarV);
        listar = findViewById(R.id.btnlistarv);
        editar = findViewById(R.id.btneditar);
        buscar = findViewById(R.id.btnbuscar);
        eliminar = findViewById(R.id.btneliminar);
        regresar = findViewById(R.id.btnregresar);

        //evento del boton editar

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarusu())
                {
                    SQLiteDatabase obde = helperbd.getWritableDatabase();

                    if (Buscarusu(vendedor.getText().toString()))//lo hallo
                    {
                        obde.execSQL("UPDATE vendedor SET " +
                                "nombre = '"+nombre.getText().toString()+"'," +
                                " email = '"+email.getText().toString()+"'," +
                                " clave = '"+clave.getText().toString()+"'," +
                                " fechai = '"+fecha.getText().toString()+"'," +
                                " tventas = '"+totalv.getText().toString()+"'" +
                                "WHERE codvendedor = '"+vendedor.getText().toString()+"'");
                        Toast.makeText(Fvendedor.this, "El vendedor fue actualizado", Toast.LENGTH_LONG).show();


                    }
                    else
                    {
                        Toast.makeText(Fvendedor.this, "El usuario no existe", Toast.LENGTH_LONG).show();
                    }
                }
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

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validarusu())
                {
                    //Alerta par averificar que el numero del gps sea correcto

                    AlertDialog.Builder Mensajealerta = new AlertDialog.Builder(Fvendedor.this);
                    Mensajealerta.setMessage("¿Esta seguro de eliminar el vendedor con la ID: "
                            + vendedor.getText().toString() + " ?")
                            .setTitle("Advertencia")
                            .setCancelable(false)
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //ejecutamos mando


                                    SQLiteDatabase obde = helperbd.getWritableDatabase();

                                    if (Buscarusu(vendedor.getText().toString()))//lo hallo
                                    {
                                        obde.execSQL("DELETE FROM vendedor WHERE codvendedor = '" + vendedor.getText().toString() + "'");
                                        Toast.makeText(Fvendedor.this, "El usuario se ha eliminado satisfactoriamente", Toast.LENGTH_LONG).show();

                                        vendedor.setText("");
                                        nombre.setText("");
                                        email.setText("");
                                        clave.setText("");
                                        fecha.setText("");
                                        totalv.setText("");
                                        vendedor.requestFocus();
                                    } else {
                                        Toast.makeText(Fvendedor.this, "El vendedor no existe", Toast.LENGTH_LONG).show();
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

        //evneto del boton listar

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Lvendedor.class);
                startActivity(i);
            }
        });


        //evento del boton guardar

        Ingresarvendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validar())
                {
                    //ingresamos los datos a la base de datos


                        Guardarvendedor(
                                vendedor.getText().toString(),
                                nombre.getText().toString(),
                                email.getText().toString(),
                                clave.getText().toString(),
                                fecha.getText().toString(),
                                totalv.getText().toString());


                }
            }
        });


    }

    //metodo para validar que los campos esten llenos
    public boolean validar(){

        boolean retorno = true;

        String c1 = vendedor.getText().toString();
        String c2 = clave.getText().toString();
        String c3 = nombre.getText().toString();
        String c4 = email.getText().toString();
        String c5 = fecha.getText().toString();
        String c6 = totalv.getText().toString();

        if (c1.isEmpty())
        {
            vendedor.setError("Debe ingresar un vendedor");
            retorno = false;
        }
        if (c2.isEmpty())
        {
            clave.setError("Debe ingresar una contraseña");
            retorno = false;
        }
        if (c3.isEmpty())
        {
            nombre.setError("Debe ingresar un nombre");
            retorno = false;
        }
        if (c5.isEmpty())
        {
            fecha.setError("Debe ingresar una fecha");
            retorno = false;
        }
        if (c4.isEmpty())
        {
            email.setError("Debe ingresar un email");
            retorno = false;
        }
        if (c6.isEmpty())
        {
            totalv.setError("Debe ingresar un valor");
            retorno = false;
        }


        return retorno;
    }



    private void Guardarvendedor(String Xvendedor, String Xnombre, String Xemail, String Xclave, String Xfechai, String Xtventas) {

        SQLiteDatabase odb = helperbd.getWritableDatabase();

        //verificar si el usuario existe
        if (!Buscarusu(vendedor.getText().toString()))
        {
            try
            {
                //crear contenedor (tabla temporal) de datos del contacto
                ContentValues c = new ContentValues();
                c.put("codvendedor", Xvendedor);
                c.put("nombre",Xnombre);
                c.put("email",Xemail);
                c.put("clave", Xclave);
                c.put("fechai", Xfechai);
                c.put("tventas", Xtventas);
                //INSERTAMOS LOS DATOS
                odb.insert("vendedor",null,c);
                odb.close();
                Toast.makeText(this,"El vendedor ha sido agregado",Toast.LENGTH_SHORT).show();

                vendedor.setText("");
                nombre.setText("");
                email.setText("");
                clave.setText("");
                fecha.setText("");
                totalv.setText("");
                vendedor.requestFocus();
            }
            catch (Exception e)
            {
                Toast.makeText(this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "El vendedor ya existe", Toast.LENGTH_LONG).show();
        }


    }

    //metodo para buscar usuario

    private boolean Buscarusu(String musuario)
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

    //metodo para buscar usuarios


    private void buscarvendedor(String Musuario) {

        SQLiteDatabase obd = helperbd.getReadableDatabase();
        String query = "SELECT codvendedor,nombre,email,clave,fechai,tventas FROM vendedor WHERE codvendedor = '"+Musuario+"'";
        Cursor cusuario = obd.rawQuery(query , null);

        if (cusuario.moveToFirst())
        {
            vendedor.setText(cusuario.getString(0));
            nombre.setText(cusuario.getString(1));
            email.setText(cusuario.getString(2));
            clave.setText(cusuario.getString(3));
            fecha.setText(cusuario.getString(4));
            totalv.setText(cusuario.getString(5));
        }
        else
        {
            Toast.makeText(this, "El vendedor no existe", Toast.LENGTH_LONG).show();
        }

    }


    public boolean validarusu(){

        boolean retorno = true;

        String c1 = vendedor.getText().toString();

        if (c1.isEmpty())
        {
            vendedor.setError("Debe ingresar un vendedor");
            retorno = false;
        }

        return retorno;
    }





}
