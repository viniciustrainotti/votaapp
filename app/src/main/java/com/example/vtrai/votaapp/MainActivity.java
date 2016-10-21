package com.example.vtrai.votaapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener{

    public static final String REQUEST_TAG = "UserAutentication";
    private Button mButton;
    private EditText mTitulo;
    private EditText mSenha;
    private TextView mTexto;
    private RequestQueue mQueue;
    protected final String url = "http://vota.mybluemix.net/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitulo = (EditText)findViewById(R.id.editText);
        mSenha = (EditText)findViewById(R.id.editText2);
        mButton = (Button)findViewById(R.id.button);
        mTexto = (TextView)findViewById(R.id.textView);

    }

    protected void onStop()
    {
        super.onStop();
        if(mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Context context = getApplicationContext();
        CharSequence text = "Login Incorreto";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    @Override
    public void onResponse(Object response) {

        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("titulo_eleitor", mTitulo.getText().toString());
        startActivity(intent);
        finish();
    }

    public void entrar(View view) {

        /*Intent intent = new Intent(this, Menu.class);
        intent.putExtra("titulo_eleitor", mTitulo.getText().toString());
        startActivity(intent);
        finish();*/

        mQueue = CustomVolleyRequestQueue.getmInstance(this.getApplicationContext()).getRequestQueue();
        JSONObject params = new JSONObject();
        try {
            params.put("titulo", mTitulo.getText().toString());
            params.put("senha", mSenha.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.POST, url,
                params, this, this);
        jsonRequest.setTag(REQUEST_TAG);

        mQueue.add(jsonRequest);

    }
}
