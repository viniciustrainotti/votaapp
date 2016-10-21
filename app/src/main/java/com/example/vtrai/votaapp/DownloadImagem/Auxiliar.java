package com.example.vtrai.votaapp.DownloadImagem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by vtrai on 18/10/2016.
 */

public class Auxiliar {

    public static Bitmap baixarImagem(String url) throws IOException{

        URL endereco;
        InputStream inputStream;
        Bitmap imagem;

        endereco = new URL(url);
        inputStream = endereco.openStream();
        imagem = BitmapFactory.decodeStream(inputStream);

        inputStream.close();

        return imagem;

    }
}
