package com.example.tokenlabchallange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tokenlabchallange.Class.DetailedMovie;
import com.example.tokenlabchallange.Class.DetailedMovieModel;
import com.google.gson.Gson;

import org.json.JSONObject;

public class DetailedMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);

        Intent intent = getIntent();
        String id="";

        if(intent.hasExtra("ID")){
            id = intent.getExtras().getString("ID");
        }

        String moviesURL = "https://desafio-mobile.nyc3.digitaloceanspaces.com/movies/" + id;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                JsonObjectRequest.Method.GET,
                moviesURL,
                null, // parameters
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InitMovieView(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Rest", "Deu ruim: " + error.toString());
                    }
                }
        );

        requestQueue.add(objectRequest);


    }

    public void InitMovieView(String jsonMovie){

        Gson gson = new Gson();
        DetailedMovie movie = gson.fromJson(jsonMovie, DetailedMovie.class);

        DetailedMovieModel dmm = new DetailedMovieModel(getApplicationContext(),findViewById(R.id.movieItemViewD),movie);
    }

}
