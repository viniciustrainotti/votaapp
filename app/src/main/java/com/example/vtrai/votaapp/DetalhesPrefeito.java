package com.example.vtrai.votaapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vtrai.votaapp.DownloadImagem.DownloadTask;
import com.example.vtrai.votaapp.Entidades.Prefeito;


public class DetalhesPrefeito extends AppCompatActivity {

    private TextView nome_prefeito;
    private TextView partido_prefeito;
    private ImageView imagem_prefeito;

    private Prefeito prefeito;

    private String titulo;
    private int voto_vereador;
    private String nome_vereador = "";

    private Bundle b;
    private MediaPlayer test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prefeito);

        prefeito = (Prefeito) this.getIntent().getSerializableExtra("prefeito");

        b = new Bundle();

        b = getIntent().getExtras();
        titulo = b.getString("titulo_eleitor");
        voto_vereador = b.getInt("voto_vereador");
        nome_vereador = b.getString("nome_vereador");

        nome_prefeito = (TextView)findViewById(R.id.nome_prefeito_detalhe);
        nome_prefeito.setText(prefeito.getNome());

        partido_prefeito = (TextView)findViewById(R.id.partido_prefeito_detalhe);
        partido_prefeito.setText(prefeito.getPartido());

        imagem_prefeito = (ImageView)findViewById(R.id.imagem_prefeito_detalhe);
        DownloadTask runner = new DownloadTask(this, imagem_prefeito);
        runner.execute(prefeito.getImagem());
    }
    public void cfn_prefeito(View view)
    {

        test = new MediaPlayer().create(this, R.raw.somurna);
        test.start();
        test.seekTo(1000);

        b = new Bundle();
        b.putSerializable("prefeito", prefeito);

        Intent it = new Intent(getApplicationContext(), Menu.class);

        int voto = Integer.parseInt(prefeito.getId());

        it.putExtra("titulo_eleitor", titulo);
        it.putExtra("voto_prefeito", voto);
        it.putExtra("voto_vereador", voto_vereador);
        it.putExtra("nome_vereador", nome_vereador);
        it.putExtra("nome_prefeito", prefeito.getNome());
        it.putExtras(b);
        startActivity(it);
        finish();

    }
}
