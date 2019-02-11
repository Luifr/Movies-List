package com.example.tokenlabchallange.Class;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tokenlabchallange.Inteface.VoidListener;
import com.example.tokenlabchallange.R;

public class DetailedMoviePresenter implements VoidListener {

    Context mContext;
    View parent_view;
    DetailedMovieViewHolder holder;
    DetailedMovieModel model;

    public DetailedMoviePresenter(Context mContext, View parent_view , String id) {
        this.mContext = mContext;
        this.parent_view = parent_view;
        model = new DetailedMovieModel(mContext,id,this);
        this.holder = new DetailedMovieViewHolder(parent_view);
        if(model.GetMovie() != null){
            SetViews();
        }
    }

    public void SetViews(){

        DetailedMovie movie = model.GetMovie();

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

        holder.v1.setVisibility(View.VISIBLE);
        holder.v2.setVisibility(View.VISIBLE);
        holder.movieInfo.setVisibility(View.VISIBLE);

        holder.loading.setVisibility(View.GONE);

    }

    @Override
    public void CallBack() {
        SetViews();
    }

    @Override
    public void OnError() {
        holder.loading.setVisibility(View.GONE);
        holder.error.setVisibility(View.VISIBLE);
    }

    public class DetailedMovieViewHolder {

        ConstraintLayout layout;
        TextView movieTitle,movieGenres,movieRating,movieDescription,movieTagline,movieStudio,movieRuntime;
        ImageView movieImage;
        ProgressBar loading;

        View v1,v2;

        TextView error,movieInfo;

        public DetailedMovieViewHolder(@NonNull View itemView) {
            movieImage = itemView.findViewById(R.id.movieImageD);
            movieTitle = itemView.findViewById(R.id.movieTitleD);
            movieGenres = itemView.findViewById(R.id.movieGenresD);
            movieRuntime = itemView.findViewById(R.id.movieRuntime);
            movieTagline = itemView.findViewById(R.id.movieTagLine);
            movieStudio = itemView.findViewById(R.id.movieStudio);
            movieRating = itemView.findViewById(R.id.movieRatingD);
            movieDescription = itemView.findViewById(R.id.movieDescription);

            error = itemView.findViewById(R.id.detailedViewError);

            v1 = itemView.findViewById(R.id.view);
            v2 = itemView.findViewById(R.id.view2);
            movieInfo = itemView.findViewById(R.id.movieInfo);

            loading = itemView.findViewById(R.id.movieLoading);
            layout = itemView.findViewById(R.id.movieItemViewD);

            loading.setIndeterminate(true);

        }
    }

}
