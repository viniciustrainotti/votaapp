package com.example.vtrai.votaapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmarVoto extends AppCompatActivity implements Response.Listener, Response.ErrorListener{

    public static final String REQUEST_TAG = "UserAutentication";
    private TextView teste;
    private Bundle b = null;
    private String titulo;
    private RequestQueue mQueue;
    private int voto_prefeito = 0;
    private int voto_vereador = 0;
    private String nome_prefeito = null;
    private String nome_vereador = null;
    protected final String url = "http://vota.mybluemix.net/api/votar";
    private AlertDialog alerta;
    private MediaPlayer test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_voto);

        test = new MediaPlayer().create(this, R.raw.somurna);

        teste = (TextView) findViewById(R.id.view_todos_votos);

        b = new Bundle();

        b = getIntent().getExtras();
        titulo = b.getString("titulo_eleitor");
        voto_prefeito = b.getInt("voto_prefeito");
        voto_vereador = b.getInt("voto_vereador");
        nome_prefeito = b.getString("nome_prefeito");
        nome_vereador = b.getString("nome_vereador");

        teste.setText("Vereador escolhido \n\n"
                    + nome_vereador + "\n\n" +
                    "\n\n Prefeito escolhido \n\n" + nome_prefeito);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        b.putString("titulo_eleitor", titulo);

        // Votou de novo
        if (error.networkResponse.statusCode == 429) {
            CharSequence text = "Você já votou, não é permitido votar novamente!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Intent intent = new Intent(this, Menu.class);
            intent.putExtras(b);
            startActivity(intent);
            finish();

        }

        // Erro ao processar o voto
        if (error.networkResponse.statusCode == 422) {
            CharSequence text = "Tente novamente";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Intent intent = new Intent(this, Menu.class);
            intent.putExtras(b);
            startActivity(intent);
            finish();

        }

    }

    @Override
    public void onResponse(Object response) {

        test.start();
        test.seekTo(100);
        Intent it = new Intent(this, Menu.class);
        b.putString("titulo_eleitor", titulo);

        it.putExtras(b);
        startActivity(it);
        finish();

        Context context = getApplicationContext();
        CharSequence text = "Votos computados com sucesso! Por gentileza, utilize a opção de Sair do menu";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void zera_votos() {

        voto_prefeito = 0;
        voto_vereador = 0;
        nome_prefeito = null;
        nome_vereador = null;

        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("titulo_eleitor", titulo);
        intent.putExtra("voto_prefeito", voto_prefeito);
        intent.putExtra("voto_vereador", voto_vereador);
        intent.putExtra("nome_vereador", nome_vereador);
        intent.putExtra("nome_prefeito", nome_prefeito);
        startActivity(intent);
        finish();

    }

    public void cfn_todos() {


        mQueue = CustomVolleyRequestQueue.getmInstance(this.getApplicationContext()).getRequestQueue();

        JSONObject params = new JSONObject();

        try {
            params.put("titulo_eleitor", titulo);
            params.put("vereador", voto_vereador);
            params.put("prefeito", voto_prefeito);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.POST, url,
                params, this, this);
        jsonRequest.setTag(REQUEST_TAG);

        mQueue.add(jsonRequest);

    }

    public void exemplo_simples() {

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Confirma");
        //define a mensagem
        builder.setMessage("Você confirma os votos?");
        //define um botão como positivo
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //.makeText(Menu.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
                cfn_todos();

            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Modificar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(Menu.this, "negativo=" + arg1, Toast.LENGTH_SHORT).show();
                zera_votos();
                Toast.makeText(ConfirmarVoto.this, "Limpado votos!Por gentileza, votar novamente", Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    public void cfn_todos_votos(View v)
    {
        exemplo_simples();
    }
}
