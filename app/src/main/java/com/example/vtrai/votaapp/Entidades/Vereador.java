package com.example.vtrai.votaapp.Entidades;

/**
 * Created by vtrai on 08/10/2016.
 */

public class Vereador {

    private final String id;
    private final String nome;
    private final String partido;
    private final String imagem;

    public Vereador(String id, String nome, String partido, String imagem){
        this.id = id;
        this.nome = nome;
        this.partido = partido;
        this.imagem = imagem;
    }

    public String getId(){
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getPartido() {
        return partido;
    }

    public String getImagem() {
        return imagem;
    }

    public boolean equals(Object outro) {
        return outro instanceof Vereador && (
                (
                        (Vereador) outro).getNome().equals(nome)
                        && ((Vereador) outro).getPartido().equals(partido)
                        && ((Vereador) outro).getImagem().equals(imagem)
        );
    }


}
