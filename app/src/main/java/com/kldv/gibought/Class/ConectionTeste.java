package com.kldv.gibought.Class;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kldv.gibought.Connex.Connexion;
import com.kldv.gibought.R;

import java.sql.Connection;
import java.sql.SQLException;
@SuppressWarnings({"SpellCheckingInspection"})
public class ConectionTeste extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conection_teste);
        TextView texto = findViewById(R.id.textOnee);

        Button btnTest = findViewById(R.id.testButton);
        btnTest.setOnClickListener(view -> {
            Connection conn = Connexion.join(getApplicationContext());
            try {
                if (conn != null) {
                    if (!conn.isClosed())
                        texto.setText("CONEXÃO REALIZADA COM SUCESSO!!!");
                    else
                        texto.setText("CONEXÃO FECHADA!!!");
                }else{
                    texto.setText("CONEXÃO NULA, NÃO REALIZADA!!!");
                }
            } catch (SQLException e) {
                //e.printStackTrace();
                texto.setText("CONEXÃO FALHOU!!! " +
                        e.getMessage());
            }
        });
    }

}