package com.example.tokenlabchallange.Class;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

        SpannableStringBuilder str = new SpannableStringBuilder("Genres:");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, str.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        String genres = "";
        int len = movie.genres.length-1;
        for (int j=0; j < len ; j++){
            genres += movie.genres[j] + " | ";
        }
        genres += movie.genres[len];
        str = str.append("  " + genres);

        holder.movieGenres.setText(str);

        str = new SpannableStringBuilder("Runtime:");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, str.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str = str.append("  " + movie.runtime + " minutes");
        holder.movieRuntime.setText(str);

        if(movie.production_companies != null && movie.production_companies.length >= 1){
            str = new SpannableStringBuilder("Studio:");
            str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, str.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            str = str.append("  " + movie.production_companies[0].name);
            holder.movieStudio.setText(str);
        }

        holder.movieTagline.setText(movie.tagline);
        holder.movieRating.setText(movie.vote_average + "/10");
        holder.movieTitle.setText(movie.title + "  (" + movie.release_date.split("-")[0] + ")");
        holder.movieDescription.setText(movie.overview);
        holder.movieDescription.setMovementMethod(new ScrollingMovementMethod());


        Glide.with(mContext).load(movie.poster_url).into(holder.movieImage);

        holder.loading.setVisibility(View.GONE);

    }

    public class DetailedMovieViewHolder {

        ConstraintLayout layout;
        TextView movieTitle,movieGenres,movieRating,movieDescription,movieTagline,movieStudio,movieRuntime;
        ImageView movieImage;
        ProgressBar loading;

        public DetailedMovieViewHolder(@NonNull View itemView) {
            movieImage = itemView.findViewById(R.id.movieImageD);
            movieTitle = itemView.findViewById(R.id.movieTitleD);
            movieGenres = itemView.findViewById(R.id.movieGenresD);
            movieRuntime = itemView.findViewById(R.id.movieRuntime);
            movieTagline = itemView.findViewById(R.id.movieTagLine);
            movieStudio = itemView.findViewById(R.id.movieStudio);
            movieRating = itemView.findViewById(R.id.movieRatingD);
            movieDescription = itemView.findViewById(R.id.movieDescription);

            loading = itemView.findViewById(R.id.movieLoading);
            layout = itemView.findViewById(R.id.movieItemViewD);

            loading.setIndeterminate(true);

        }
    }

}
