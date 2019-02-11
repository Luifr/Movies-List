package com.example.tokenlabchallange.Class;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tokenlabchallange.Inteface.VoidListener;
import com.example.tokenlabchallange.R;

import static android.support.v7.widget.RecyclerView.*;

public class MoviePresenter implements VoidListener {



    MovieModel model;
    VoidListener ui;
    RecyclerView movieRecycler;
    MovieAdapter mAdapter;
    Context mContext;

    public MoviePresenter(Context mContext, RecyclerView movieRecycler, VoidListener listener) {
        ui = listener;
        this.movieRecycler = movieRecycler;
        this.mContext = mContext;
        model = new MovieModel(mContext,this);

    }


    void InitRecyclerView() {

        movieRecycler.setHasFixedSize(true);
        LayoutManager lm = new LinearLayoutManager(mContext);
        movieRecycler.setLayoutManager(lm);

        model.SortMoviesAlphabetically();

        mAdapter = new MovieAdapter(mContext, model.GetMovies());
        movieRecycler.addItemDecoration(new DividerItemDecoration(movieRecycler.getContext(),((LinearLayoutManager) lm).getOrientation()));
        movieRecycler.setAdapter(mAdapter);

        ui.CallBack();

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
