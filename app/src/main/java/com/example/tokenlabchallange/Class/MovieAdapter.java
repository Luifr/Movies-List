package com.example.tokenlabchallange.Class;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tokenlabchallange.DetailedMovieActivity;
import com.example.tokenlabchallange.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    Context mContext;
    Movie[] movies;

    public MovieAdapter(Context mContext, Movie[] movies) {
        this.mContext = mContext;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_listitem, viewGroup, false);
        MovieViewHolder movieHolder = new MovieViewHolder(view);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.movieTitle.setText(movies[i].title + "  (" + movies[i].release_date.split("-")[0] + ")");
        movieViewHolder.movieRating.setText(movies[i].vote_average + "/10");

        String genres = "";
        int len = movies[i].genres.length-1;
        for (int j=0; j < len ; j++){
            genres += movies[i].genres[j] + " | ";
        }
        genres += movies[i].genres[len];

        movieViewHolder.movieGenres.setText(genres);

        Glide.with(mContext).load(movies[i].poster_url).into(movieViewHolder.movieImage);

        movieViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailedMovieActivity.class);
                intent.putExtra("ID",Integer.toString(movies[movieViewHolder.getAdapterPosition()].id));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.length;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parentLayout;
        TextView movieTitle,movieGenres,movieRating;
        ImageView  movieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.movieItemView);
            movieImage = itemView.findViewById(R.id.movieImage);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieGenres = itemView.findViewById(R.id.movieGenres);
            movieRating = itemView.findViewById(R.id.movieRating);
        }
    }

}
