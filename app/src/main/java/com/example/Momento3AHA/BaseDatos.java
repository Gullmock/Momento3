package com.example.Momento3AHA;///EN ESTA CLASE CREAREMOS LA BASE DE DATOS CON TODAS SUS TABLAS///

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class BaseDatos extends SQLiteOpenHelper {

    //CREAMOS LA TABLA DEL VENDEDOR

    String tblvendedor = "CREATE TABLE vendedor (codvendedor INTEGER PRIMARY KEY, " +
            "nombre TEXT, email TEXT, clave INT, fechai INT, tventas INT)";

    //CREAMOS LA TABLA DE LA ZONA

    String tblzona = "CREATE TABLE zona (Nzona INTEGER PRIMARY KEY, nombre TEXT, comision INT)";

    //CREAMOS TABLA DE VENTAS

    String tblventas = "CREATE TABLE ventas (Nventa INTEGER PRIMARY KEY, idzona INT, idvendedor INT, fecha INT, valor INT)";

    public BaseDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tblvendedor);
        sqLiteDatabase.execSQL(tblventas);
        sqLiteDatabase.execSQL(tblzona);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE tblvendedor");
        sqLiteDatabase.execSQL("DROP TABLE tblventas");
        sqLiteDatabase.execSQL("DROP TABLE tblzona");
        sqLiteDatabase.execSQL(tblventas);
        sqLiteDatabase.execSQL(tblzona);
        sqLiteDatabase.execSQL(tblvendedor);

    }

    //metodo de login para validar los usuarios

    public Cursor Logearusuarios(String usu, String pas) throws SQLException
    {
        Cursor mcursor = null;

        mcursor=this.getReadableDatabase().query("vendedor", new String[]{
                "codvendedor", "nombre", "email", "clave", "fechai", "tventas"},
                "email like '"+usu+"'" + "and clave like '"+pas+"'",
                null, null, null, null);

        return mcursor;
    }

}

