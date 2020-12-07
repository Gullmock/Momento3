package com.example.Momento3AHA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText  usuario, clave;
    Button ingresar, regresar;

    //instanciamos la base de datos
    BaseDatos helperbd = new BaseDatos(this , "BDVENTAS", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.etusuario);
        clave = findViewById(R.id.etclave);
        ingresar = findViewById(R.id.btningresarusu);
        regresar = findViewById(R.id.btnregresar);

        //inizialoizamos base de datos

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
            public void onClick(View view) {

                if (validar())
                {
                    String Xusuario = usuario.getText().toString();
                    String Xclave = clave.getText().toString();

                    try{
                        Cursor cursor = helperbd.Logearusuarios(Xusuario, Xclave);

                        if (cursor.getCount() > 0)
                        {
                            //pasamos a la otra actividad
                            Intent i = new Intent(getApplicationContext(), Bienvenido.class);
                            startActivity(i);
                            finish();

                            usuario.setText("");
                            clave.setText("");
                            usuario.requestFocus();
                        }
                        else
                        {
                            Toast.makeText(Login.this, "El usuario y/o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //velidar edit text

    public boolean validar(){

        boolean retorno = true;

        String c1 = usuario.getText().toString();
        String c2 = clave.getText().toString();

        if (c1.isEmpty())
        {
            usuario.setError("Es necesario ingresar un usuario");
            retorno = false;
        }
        if (c2.isEmpty())
        {
            clave.setError("Es necesario ingresar una contraseña");
            retorno = false;
        }

        return retorno;
    }

    private boolean login(String Musuario, String Mclave) {

        SQLiteDatabase obd = helperbd.getReadableDatabase();
        String query = "SELECT codvendedor,clave FROM vendedor WHERE codvendedor = '"+Musuario+"'";
        Cursor cusuario = obd.rawQuery(query , null);

        if (cusuario.getString(0) == usuario.getText().toString() && cusuario.getString(1) == clave.getText().toString())
        {
            return true;
        }
        else
        {
            return false;
        }

    }
}