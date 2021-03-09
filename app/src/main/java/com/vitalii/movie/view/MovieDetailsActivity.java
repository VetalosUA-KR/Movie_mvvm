package com.vitalii.movie.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vitalii.movie.R;
import com.vitalii.movie.databinding.ActivityMovieDetailsBinding;
import com.vitalii.movie.model.Result;

public class MovieDetailsActivity extends AppCompatActivity {

    private Result result;

    private ActivityMovieDetailsBinding activityMovieDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("movieData")) {

            result = intent.getParcelableExtra("movieData");

            //this line of code replaced all code below
            activityMovieDetailsBinding.setResult(result);

//            Toast.makeText(this, result.getTitle(), Toast.LENGTH_SHORT).show();
//            posterPath = "https://image.tmdb.org/t/p/w500/" + result.getPosterPath();
//            Glide.with(this)
//                    .load(posterPath)
//                    .placeholder(R.drawable.progress_circle)
//                    .into(posterImageView);
//
//            titleTextView.setText(result.getOriginalTitle());
//            voteCountTextView.setText(Integer.toString(result.getVoteCount()));
//            overviewTextView.setText(result.getOverview());
        }
    }
}











