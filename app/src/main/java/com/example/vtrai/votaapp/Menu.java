package com.example.vtrai.votaapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;



public class Menu extends AppCompatActivity implements Response.Listener, Response.ErrorListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        teste = (TextView) findViewById(R.id.textView3);

        b = getIntent().getExtras();
        titulo = b.getString("titulo_eleitor");
        voto_prefeito = b.getInt("voto_prefeito");
        voto_vereador = b.getInt("voto_vereador");
        nome_prefeito = b.getString("nome_prefeito");
        nome_vereador = b.getString("nome_vereador");

        teste.setText(" Bem vindo eleitor \n\n Número do título: " + titulo);

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.op_vereador) {

            if (voto_vereador == 0) {

                Intent intent = new Intent(this, Op_vereador.class);
                intent.putExtra("titulo_eleitor", titulo);
                intent.putExtra("voto_prefeito", voto_prefeito);
                intent.putExtra("nome_prefeito", nome_prefeito);
                startActivity(intent);
                finish();
            }
            else {
                Context context = getApplicationContext();
                CharSequence text = "Você já votou para vereador!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }


        }
        if (id == R.id.confimar_voto) {

           // exemplo_simples();
            if (voto_vereador != 0 && voto_prefeito != 0) {
                Intent intent = new Intent(this, ConfirmarVoto.class);
                intent.putExtra("titulo_eleitor", titulo);
                intent.putExtra("voto_vereador", voto_vereador);
                intent.putExtra("nome_vereador", nome_vereador);
                intent.putExtra("voto_prefeito", voto_prefeito);
                intent.putExtra("nome_prefeito", nome_prefeito);
                startActivity(intent);
                finish();
            }
            else {

                Context context = getApplicationContext();
                CharSequence text = "Você não pode deixar nenhum voto em branco!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }


        }
        if (id == R.id.sair) {

            finish();

        } else if (id == R.id.op_prefeito) {

            if (voto_prefeito == 0) {

                Intent intent = new Intent(this, Op_prefeito.class);
                intent.putExtra("titulo_eleitor", titulo);
                intent.putExtra("voto_vereador", voto_vereador);
                intent.putExtra("nome_vereador", nome_vereador);
                startActivity(intent);
                finish();

            }
            else{
                Context context = getApplicationContext();
                CharSequence text = "Você já votou para prefeito!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public void onResponse(Object response) {


    }
}
