package com.example.vtrai.votaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vtrai.votaapp.DownloadImagem.DownloadTask;
import com.example.vtrai.votaapp.Entidades.Vereador;

public class DetalhesVereador extends AppCompatActivity {

    private TextView nome_vereador;
    private TextView partido_vereador;
    private ImageView imagem_vereador;

    private Vereador vereador;

    private String titulo;
    private int voto_prefeito;
    private String nome_prefeito = "";

    private Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_vereador);

        vereador = (Vereador) this.getIntent().getSerializableExtra("vereador");

        b = new Bundle();

        b = getIntent().getExtras();
        titulo = b.getString("titulo_eleitor");
        voto_prefeito = b.getInt("voto_prefeito");
        nome_prefeito = b.getString("nome_prefeito");

        nome_vereador = (TextView)findViewById(R.id.nome_vereador_detalhe);
        nome_vereador.setText(vereador.getNome());

        partido_vereador = (TextView)findViewById(R.id.partido_vereador_detalhe);
        partido_vereador.setText(vereador.getPartido());

        imagem_vereador = (ImageView)findViewById(R.id.imagem_vereador_detalhe);
        DownloadTask runner = new DownloadTask(this, imagem_vereador);
        runner.execute(vereador.getImagem());
    }

    public void cfn_vereador(View view)
    {

        b = new Bundle();
        b.putSerializable("vereador", vereador);

        Intent it = new Intent(getApplicationContext(), Menu.class);
        int voto = Integer.parseInt(vereador.getId());
        it.putExtra("titulo_eleitor", titulo);
        it.putExtra("voto_prefeito", voto_prefeito);
        it.putExtra("voto_vereador", voto);
        it.putExtra("nome_vereador", vereador.getNome());
        it.putExtra("nome_prefeito", nome_prefeito);
        it.putExtras(b);
        startActivity(it);
        finish();

    }
}
