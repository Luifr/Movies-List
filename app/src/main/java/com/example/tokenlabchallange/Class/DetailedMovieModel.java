package com.example.tokenlabchallange.Class;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tokenlabchallange.Inteface.VoidListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class DetailedMovieModel {

    DetailedMovie movie;
    Context mContext;
    String id;
    VoidListener moviePresenter;

    public DetailedMovieModel(Context mContext, String id, VoidListener moviePresenter) {
        this.mContext = mContext;
        this.id = id;
        this.moviePresenter = moviePresenter;
        SetMovie();
    }

    void SetMovie(){
        if(!GetMovieInCache()){
            GetMovieFromWeb();
        }
    }

    public DetailedMovie GetMovie(){
        return movie;
    }

    void GetMovieFromWeb(){

        String moviesURL = "https://desafio-mobile.nyc3.digitaloceanspaces.com/movies/" + id;

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                JsonObjectRequest.Method.GET,
                moviesURL,
                null, // parameters
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        movie = gson.fromJson(response.toString(), DetailedMovie.class);
                        moviePresenter.CallBack();
                        SetMovieInCache();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        moviePresenter.OnError();
                    }
                }
        );

        objectRequest.setRetryPolicy(new DefaultRetryPolicy(8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(objectRequest);

    }

    void SetMovieInCache(){

        Gson jsonObj = new Gson();
        String json = jsonObj.toJson(movie);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("movie", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(id,json);
        editor.apply();
    }

    boolean GetMovieInCache() {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("movie", MODE_PRIVATE);
        String obj = sharedPreferences.getString(id,"");

        if(obj.equals(""))
            return false;

        Gson jsonObj = new Gson();
        movie = jsonObj.fromJson(obj,DetailedMovie.class);

        return true;
    }

}
