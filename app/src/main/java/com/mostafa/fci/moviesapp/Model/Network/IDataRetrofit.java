package com.mostafa.fci.moviesapp.Model.Network;

import com.mostafa.fci.moviesapp.Model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IDataRetrofit {

    /// GET Method Request without Parameters
    @GET("moviesData.txt")
    Call<Movies> getMoviesList();

}
