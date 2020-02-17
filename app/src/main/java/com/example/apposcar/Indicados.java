package com.example.apposcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.apposcar.model.Filme;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Indicados extends AppCompatActivity {

    SharedPreferences preferences;
    ListView listView;
    private List<HashMap<String, String>> indicados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicados);
        getSupportActionBar().hide();

        indicados = new ArrayList<>();
        preferences = getSharedPreferences("jsonIndicados", Context.MODE_PRIVATE);
        listView = findViewById(R.id.listViewResultado);
        String json = preferences.getString("json", null);
        gerarJson(json);

    }//method

    private void gerarJson(String json) {
        Gson gson = new Gson();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                Filme f = gson.fromJson(String.valueOf(array.getJSONObject(i)), Filme.class);

                HashMap<String, String> list = new HashMap<>();
                list.put("nome", f.getNome());
                list.put("diretor", f.getDiretor());
                list.put("categoria", f.getCategoria());

                indicados.add(list);

            }
            criarLista();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//method

    private void criarLista() {
        ListAdapter adapter = new SimpleAdapter(
                Indicados.this,
                indicados,
                R.layout.item_list,
                new String[]{"nome", "diretor", "categoria"},
                new int[]{R.id.textViewNome, R.id.textViewDiretor, R.id.textViewCategoria});
        listView.setAdapter(adapter);
    }//method

}//class
