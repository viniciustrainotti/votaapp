package com.example.vtrai.votaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vtrai.votaapp.Adapters.ListaPrefeitos;
import com.example.vtrai.votaapp.Entidades.Prefeito;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Op_prefeito extends AppCompatActivity implements Response.Listener, Response.ErrorListener{

    protected final String url = "https://dl.dropboxusercontent.com/u/40990541/prefeito.json";
    private ArrayList<Prefeito> prefeitos;
    private RequestQueue mQueue;
    private View inflating_view;
    private int vota;
    private String titulo;
    private int voto_prefeito;
    private int voto_vereador;
    private String nome_prefeito = "";
    private String nome_vereador = "";
    private Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_prefeito);

        b = new Bundle();

        b = getIntent().getExtras();

        titulo = b.getString("titulo_eleitor");
        voto_vereador = b.getInt("voto_vereador");
        nome_vereador = b.getString("nome_vereador");


        inflating_view = findViewById(R.id.activity_op_prefeito);
        ViewGroup parent =(ViewGroup) inflating_view.getParent();
        int index = parent.indexOfChild(inflating_view);
        parent.removeView(inflating_view);

        inflating_view = getLayoutInflater().inflate(R.layout.activity_op_prefeito, parent, false);
        parent.addView(inflating_view, index);
        inflating_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Toast t = Toast.makeText(getApplicationContext(), "ListView clicked", Toast.LENGTH_SHORT);
                t.show();
            }
        });


    }

    public void onStart()
    {
        super.onStart();

        mQueue = CustomVolleyRequestQueue.getmInstance(this).getRequestQueue();

        CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url,
                new JSONObject(), this, this);

        mQueue.add(jsonRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {


        CharSequence text = "erro";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    @Override
    public void onResponse(Object response) {

        ListView lv = (ListView)findViewById(R.id.lista_prefeitos);


        prefeitos = new ArrayList<Prefeito>();

        try {
            JSONArray cast = ((JSONObject) response).getJSONArray("prefeito");
            for (int i=0; i<cast.length(); i++) {
                JSONObject actor = cast.getJSONObject(i);
                String id = actor.getString("id");
                String name = actor.getString("nome");
                String partido = actor.getString("partido");
                String imagem = actor.getString("foto");
                initPrefeitos(prefeitos, id, name, partido, imagem);
            }

            lv.setAdapter(new ListaPrefeitos(this, prefeitos));
        }catch (JSONException e){
            e.printStackTrace();
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {

                Prefeito prefeito = prefeitos.get(position);
                Bundle b = new Bundle();
                b.putSerializable("prefeito", prefeito);

                Intent it = new Intent(getApplicationContext(), DetalhesPrefeito.class);
                it.putExtra("titulo_eleitor", titulo);
                it.putExtra("voto_vereador", voto_vereador);
                it.putExtra("nome_vereador", nome_vereador);
                it.putExtras(b);
                startActivity(it);
                finish();
            }
        });

    }

    private void initPrefeitos(ArrayList<Prefeito> prefeitos, String id, String nome, String partido, String imagem) {

        prefeitos.add(new Prefeito(id, nome, partido, imagem));

    }
}
