package com.example.tokenlabchallange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tokenlabchallange.Class.DetailedMoviePresenter;

public class DetailedMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);

        Intent intent = getIntent();
        String id="";

        if(intent.hasExtra("ID")){
            id = intent.getExtras().getString("ID");
            new DetailedMoviePresenter(getApplicationContext(),findViewById(R.id.movieItemViewD),id);
        }
        else{
            // TODO ERRORRRR
        }


    }






}
