package com.example.tokenlabchallange.Class;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tokenlabchallange.Inteface.VoidListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;

public class MovieModel {

    String path = "cache";
    VoidListener movieReady;
    private Movie[] movies;
    Context mContext;

    public MovieModel(Context mContext, VoidListener listener){
        movieReady = listener;
        this.mContext = mContext;
        SetMovies();
    }

    void SetMovies(){
        if(!GetMoviesInCache()){
            GetMoviesFromWeb();
        }
    }

    public Movie[] GetMovies(){
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
                        Type collectionType = new TypeToken<Movie[]>() {
                        }.getType();
                        movies = gson.fromJson(response.toString(), collectionType);
                        movieReady.CallBack();
                        SetMoviesInCache();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        movieReady.OnError();
                    }
                }
        );

        objectRequest.setRetryPolicy(new DefaultRetryPolicy(8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(objectRequest);


    }

    void SetMoviesInCache(){

        Gson jsonObj = new Gson();
        String json = jsonObj.toJson(movies);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("movie", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("movies",json);
        editor.apply();
    }

    boolean GetMoviesInCache() {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("movie", MODE_PRIVATE);
        String obj = sharedPreferences.getString("movies","");

        if(obj.equals(""))
            return false;

        Gson jsonObj = new Gson();
        movies = jsonObj.fromJson(obj,Movie[].class);

        return true;
    }

    public void FlushCache(){
        mContext.getSharedPreferences("movie", MODE_PRIVATE).edit().clear().apply();
    }


}
