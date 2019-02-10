package com.example.tokenlabchallange.Class;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.tokenlabchallange.R;

import java.lang.reflect.Constructor;

public class DetailedMovieView {

    Context mContext;
    View parent_view;
    DetailedMovie movie;
    DetailedMovieViewHolder holder;

    public DetailedMovieView(Context mContext, View parent_view , DetailedMovie movie) {
        this.mContext = mContext;
        this.movie = movie;
        this.parent_view = parent_view;
        this.holder = new DetailedMovieViewHolder(parent_view);
        SetViews();
    }

    public void SetViews(){
        holder.movieTitle.setText(movie.title + "  (" + movie.release_date.split("-")[0] + ")");
        holder.movieRating.setText(movie.vote_average + "/10");
        holder.movieDescription.setText(movie.overview);
        holder.movieDescription.setMovementMethod(new ScrollingMovementMethod());

        String genres = "";
        int len = movie.genres.length-1;
        for (int j=0; j < len ; j++){
            genres += movie.genres[j] + " | ";
        }
        genres += movie.genres[len];

        holder.movieGenres.setText(genres);

        Glide.with(mContext).load(movie.poster_url).into(holder.movieImage);


    }

    public class DetailedMovieViewHolder {

        ConstraintLayout layout;
        TextView movieTitle,movieGenres,movieRating,movieDescription;
        ImageView movieImage;

        public DetailedMovieViewHolder(@NonNull View itemView) {
            movieImage = itemView.findViewById(R.id.movieImageD);
            movieTitle = itemView.findViewById(R.id.movieTitleD);
            movieGenres = itemView.findViewById(R.id.movieGenresD);
            movieRating = itemView.findViewById(R.id.movieRatingD);
            movieDescription = itemView.findViewById(R.id.movieDescription);
            layout = itemView.findViewById(R.id.movieItemViewD);
            

        }
    }

}
