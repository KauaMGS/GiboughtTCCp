package com.kldv.gibought.Connex;

import android.content.Context;
import android.os.StrictMode;
import android.view.Gravity;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;

public class Connexion {

    /**
     * Método de Conexão com o banco de dados
     */
    public static Connection join(Context ctx){
        // Objeto de conexão
        Connection conn = null;
        try{
            // Adicionar política para criação de thread
            StrictMode.ThreadPolicy politica;
            politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            // Verificar se Driver de Conexão esta importado no projeto
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            // Realiza a conexão SQL Server
            conn = DriverManager.getConnection(
                    "jdbc:jtds:sqlserver://192.168.0.13;"+
                            "databaseName=GBAPP;user=sa;password=1234567;");

        }catch(SQLException e){
            //e.getMessage();
            Toast.makeText(ctx, "SERVIDOR " +
                    "INDISPONÍVEL", Toast.LENGTH_LONG).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
