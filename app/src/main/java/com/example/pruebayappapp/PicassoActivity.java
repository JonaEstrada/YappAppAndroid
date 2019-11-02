package com.example.pruebayappapp;

import android.media.JetPlayer;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class PicassoActivity extends AppCompatActivity {

    private RequestQueue queue;
    private Button btnConsultar;

    //Se le asignará mayor tiempo de respuesta al servidor
    public static final int MY_DEFAULT_TIMEOUT = 35000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Cargar imagen mediante picasso
        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into((ImageView) findViewById(R.id.img_View));

        btnConsultar = (Button) findViewById(R.id.btn_Consultar);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peticiones();
            }
        });
    }

    //Método para realizar las peticiones GET volley
    private void peticiones() {
        queue = Volley.newRequestQueue(this);
        String URL = "http://beta.yappapp.mx/test/json/apps";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject objetoJSON = response.getJSONObject("data");
                    String dato1 = (String) objetoJSON.getString("dato1");
                    String dato2 = (String) objetoJSON.getString("dato2");
                    String dato3 = (String) objetoJSON.getString("dato3");

                    String msg= (String) response.get("msg").toString();
                    String code= (String) response.get("code").toString();
                    Toast.makeText(PicassoActivity.this, "Dato1: " + dato1+ " Dato2: "+dato2+
                            " Dato 3: "+dato3+ " MSG: "+msg+ " Code: "+code, Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(PicassoActivity.this, "" + e, Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PicassoActivity.this, "" + error, Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}
