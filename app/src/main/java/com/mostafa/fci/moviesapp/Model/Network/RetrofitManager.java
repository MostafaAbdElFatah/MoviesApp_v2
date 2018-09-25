package com.mostafa.fci.moviesapp.Model.Network;

import android.util.Log;

import com.mostafa.fci.moviesapp.Model.Movies;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    public void getMovies(final IMoviesList moviesList){

        final IDataRetrofit mDataRetrofit;
        final Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(URLs.MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mDataRetrofit = retrofit.create(IDataRetrofit.class);
        Call<Movies> call = mDataRetrofit.getMoviesList();
        call.enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (moviesList != null) {

                    Log.v("onResponse::","GetData");
                    moviesList.moviesList(response.body().getMovies());

                    Log.v("onResponse::","onResponse:"+ response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                if (moviesList != null) {
                    moviesList.onFailure();
                }
                Log.v("onFailure","onFailure:" + t.getMessage() );
                Log.v("onFailure","onFailure:" + t.getLocalizedMessage() );
                Log.v("onFailure","onFailure:" + t.toString() );

                t.printStackTrace();

            }
        });
    }
}
