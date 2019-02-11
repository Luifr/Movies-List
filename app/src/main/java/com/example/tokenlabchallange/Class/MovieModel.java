package com.example.tokenlabchallange.Class;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.tokenlabchallange.Class.MoviePresenter.MovieReady;
import com.example.tokenlabchallange.Inteface.VoidListener;
import com.example.tokenlabchallange.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MovieModel {


    VoidListener movieReady;
    private Movie[] movies;
    Context mContext;

    public MovieModel(Context mContext, VoidListener mr){
        movieReady = mr;
        this.mContext = mContext;
        SetMovies();
    }

    void SetMovies(){
        if(!GetMoviesInCache()){
            GetMoviesFromWeb();
        }
    }

    public Movie[] GetMovies(){
        while(movies==null);
        return movies;
    }

    public void SortMoviesAlphabetically(){
        Arrays.sort(GetMovies(), new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.title.compareTo(rhs.title);
            }
        });
    }

    public void SortMoviesAlphabeticallyReverse(){
        Arrays.sort(GetMovies(), new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return rhs.title.compareTo(lhs.title);
            }
        });
    }

    public void SortMoviesByYear(){
        Arrays.sort(GetMovies(), new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.release_date.split("-")[0].compareTo(rhs.release_date.split("-")[0]);
            }
        });
    }

    public void SortMoviesByYearReverse(){
        Arrays.sort(GetMovies(), new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return rhs.release_date.split("-")[0].compareTo(lhs.release_date.split("-")[0]);
            }
        });
    }




    void GetMoviesFromWeb(){
        String moviesURL = "https://desafio-mobile.nyc3.digitaloceanspaces.com/movies";

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);


        JsonArrayRequest objectRequest = new JsonArrayRequest(
                JsonObjectRequest.Method.GET,
                moviesURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type collectionType = new TypeToken<Movie[]>() {}.getType();
                        movies = gson.fromJson(response.toString(), collectionType);
                        SetMoviesInCache();
                        movieReady.CallBack();
                    }
                },
                null
        );

        requestQueue.add(objectRequest);


    }

    void SetMoviesInCache(){
        // TODO
    }

    boolean GetMoviesInCache(){
        // TODO
        return false;
    }


}
