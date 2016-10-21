package com.example.vtrai.votaapp.Adapters;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.example.vtrai.votaapp.DownloadImagem.DownloadTask;
import com.example.vtrai.votaapp.Entidades.Prefeito;
import com.example.vtrai.votaapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vtrai on 08/10/2016.
 */

public class ListaPrefeitos extends ArrayAdapter<Prefeito>{
    private final Activity context;
    private final ArrayList<Prefeito> prefeitos;

    public ListaPrefeitos(Activity context, ArrayList<Prefeito> prefeitos){
        super(context, R.layout.list_cell, prefeitos);
        this.context = context;
        this.prefeitos = prefeitos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PrefeitoViewHolder prefeitoViewHolder;

        if (null == convertView) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_cell, parent, false);

            prefeitoViewHolder = new PrefeitoViewHolder();
            prefeitoViewHolder.nome = (TextView) convertView.findViewById(R.id.prefeito_name);
            prefeitoViewHolder.partido = (TextView) convertView.findViewById(R.id.prefeito_partido);
            prefeitoViewHolder.imagem = (ImageView) convertView.findViewById(R.id.prefeito_image);

            convertView.setTag(prefeitoViewHolder);
        } else {
            prefeitoViewHolder = (PrefeitoViewHolder) convertView.getTag();
        }

        Prefeito prefeito = prefeitos.get(position);
        if (null != prefeito) {
            prefeitoViewHolder.nome.setText(prefeito.getNome());
            prefeitoViewHolder.partido.setText(prefeito.getPartido());
            DownloadTask runner = new DownloadTask(getContext(), prefeitoViewHolder.imagem);
            runner.execute(prefeito.getImagem());
            //prefeitoViewHolder.imagem = ;
            //prefeitoViewHolder.imagem.setImageResource(prefeito.getImagem());

        }

        return convertView;
    }

    private static class PrefeitoViewHolder {
        TextView nome;
        TextView partido;
        ImageView imagem;
    }
}
