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
import com.android.volley.toolbox.Volley;
import com.example.tokenlabchallange.Inteface.VoidListener;
import com.example.tokenlabchallange.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;

public class MoviePresenter implements VoidListener {



    MovieModel model;

    RecyclerView movieRecycler;
    MovieAdapter mAdapter;
    Context mContext;

    public MoviePresenter(Context mContext, RecyclerView movieRecycler) {


        this.movieRecycler = movieRecycler;
        this.mContext = mContext;
        model = new MovieModel(mContext,this);
    }


    void InitRecyclerView() {

        movieRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(mContext);
        movieRecycler.setLayoutManager(lm);

        model.SortMoviesAlphabetically();

        mAdapter = new MovieAdapter(mContext, model.GetMovies());
        movieRecycler.setAdapter(mAdapter);

        movieRecycler.addItemDecoration(new DividerItemDecoration(movieRecycler.getContext(),((LinearLayoutManager) lm).getOrientation()));

    }

    public void ItemSelected(int id){
        if(id == R.id.alphabetic){
            model.SortMoviesAlphabetically();
        }
        else if(id == R.id.alphabeticR){
            model.SortMoviesAlphabeticallyReverse();
        }
        else if(id == R.id.year){
            model.SortMoviesByYear();
        }
        else if(id == R.id.yearR){
            model.SortMoviesByYearReverse();
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void CallBack() {
        InitRecyclerView();
    }
}
