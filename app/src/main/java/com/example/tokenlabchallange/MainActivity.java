package com.example.tokenlabchallange;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tokenlabchallange.Class.Movie;
import com.example.tokenlabchallange.Class.MovieAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    MovieAdapter mAdapter;
    Movie[] movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String moviesURL = "https://desafio-mobile.nyc3.digitaloceanspaces.com/movies";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                JsonObjectRequest.Method.GET,
                moviesURL,
                null, // parameters
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        InitRecyclerView(response.toString());
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

    public void InitRecyclerView(String jsonMovies) {

        Gson gson = new Gson();
        Type collectionType = new TypeToken<Movie[]>() {
        }.getType();
        movies = gson.fromJson(jsonMovies, collectionType);
        RecyclerView listViewer = findViewById(R.id.movieListContainer);
        listViewer.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        listViewer.setLayoutManager(lm);

        Arrays.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.title.compareTo(rhs.title);
            }
        });

        mAdapter = new MovieAdapter(this, movies);
        listViewer.setAdapter(mAdapter);

        listViewer.addItemDecoration(new DividerItemDecoration(listViewer.getContext(),((LinearLayoutManager) lm).getOrientation()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.alphabetic){
            Arrays.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie lhs, Movie rhs) {
                    return lhs.title.compareTo(rhs.title);
                }
            });
        }
        else if(id == R.id.alphabeticR){
            Arrays.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie lhs, Movie rhs) {
                    return rhs.title.compareTo(lhs.title);
                }
            });
        }
        else if(id == R.id.year){
            Arrays.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie lhs, Movie rhs) {
                    return lhs.release_date.split("-")[0].compareTo(rhs.release_date.split("-")[0]);
                }
            });
        }
        else if(id == R.id.yearR){
            Arrays.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie lhs, Movie rhs) {
                    return rhs.release_date.split("-")[0].compareTo(lhs.release_date.split("-")[0]);
                }
            });
        }

        mAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }
}
