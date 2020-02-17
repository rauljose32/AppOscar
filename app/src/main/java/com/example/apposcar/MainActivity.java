package com.example.apposcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apposcar.model.Filme;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextDiretor;
    private EditText editTextCategoria;
    private Button buttonSalvar;
    private Button buttonListar;
    SharedPreferences preferences;
    private List<Filme> filmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        editTextNome = findViewById(R.id.editTextNome);
        editTextDiretor = findViewById(R.id.editTextDiretor);
        editTextCategoria = findViewById(R.id.editTextCategoria);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonListar = findViewById(R.id.buttonIndicados);

        preferences = getSharedPreferences("jsonIndicados", Context.MODE_PRIVATE);
        filmes = new ArrayList<>();
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = editTextNome.getText().toString();
                editTextNome.setText("");
                String diretor = editTextDiretor.getText().toString();
                editTextDiretor.setText("");
                String categoria = editTextCategoria.getText().toString();
                editTextCategoria.setText("");
                cadastrarFilme(nome, diretor, categoria);
                editTextNome.requestFocus();

            }
        });

        buttonListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaCandidatos();
            }
        });

    }//method

    private void listaCandidatos() {
        if (!(filmes.isEmpty())) {
            Gson gson = new Gson();
            String stringJson = gson.toJson(filmes);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("json", stringJson);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), Indicados.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Favor Cadastrar ao menos um Filme",
                    Toast.LENGTH_LONG).show();
        }

    }//method

    private void cadastrarFilme(String nome, String diretor, String categoria) {

        if ((!nome.isEmpty()) & (!diretor.isEmpty()) & (!categoria.isEmpty())) {
            Filme f = new Filme(nome, diretor, categoria);
            filmes.add(f);
            Toast.makeText(
                    getApplicationContext(),
                    "Filme " + nome + " cadastrado",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Preencha todos os campos por favor",
                    Toast.LENGTH_SHORT).show();
        }
    }//method
}//class
