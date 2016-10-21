package com.example.vtrai.votaapp.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vtrai.votaapp.DownloadImagem.DownloadTask;
import com.example.vtrai.votaapp.Entidades.Vereador;
import com.example.vtrai.votaapp.R;

import java.util.ArrayList;

/**
 * Created by vtrai on 18/10/2016.
 */

public class ListaVereador extends ArrayAdapter<Vereador> {
    private final Activity context;
    private final ArrayList<Vereador> vereadores;

    public ListaVereador(Activity context, ArrayList<Vereador> vereadores){
        super(context, R.layout.list_cell2, vereadores);
        this.context = context;
        this.vereadores = vereadores;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        VereadorViewHolder vereadorViewHolder;

        if (null == convertView) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_cell2, parent, false);

            vereadorViewHolder = new VereadorViewHolder();
            vereadorViewHolder.nome = (TextView) convertView.findViewById(R.id.vereador_name);
            vereadorViewHolder.partido = (TextView) convertView.findViewById(R.id.vereador_partido);
            vereadorViewHolder.imagem = (ImageView) convertView.findViewById(R.id.vereador_image);

            convertView.setTag(vereadorViewHolder);
        } else {
            vereadorViewHolder = (VereadorViewHolder) convertView.getTag();
        }

        Vereador vereador = vereadores.get(position);
        if (null != vereador) {
            vereadorViewHolder.nome.setText(vereador.getNome());
            vereadorViewHolder.partido.setText(vereador.getPartido());
            DownloadTask runner = new DownloadTask(getContext(), vereadorViewHolder.imagem);
            runner.execute(vereador.getImagem());
        }

        return convertView;
    }

    private static class VereadorViewHolder {
        TextView nome;
        TextView partido;
        ImageView imagem;
    }
}
