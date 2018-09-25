package com.mostafa.fci.moviesapp.Presenter;

import android.content.Context;
import com.mostafa.fci.moviesapp.Model.Database.RoomDB.RoomManager;
import com.mostafa.fci.moviesapp.Model.Movies.Movie;
import com.mostafa.fci.moviesapp.Model.Network.IMoviesList;
import com.mostafa.fci.moviesapp.Model.Network.NetworkState;
import com.mostafa.fci.moviesapp.Model.Network.RetrofitManager;
import com.mostafa.fci.moviesapp.View.IView;

import java.util.List;

public class Presenter implements IMoviesList {

    IView view;
    Context context;
    RoomManager mRoomManager;
    RetrofitManager mRetrofitManager;

    public Presenter(Context context) {
        this.context = context;
        this.view = (IView) context;
        mRoomManager = new RoomManager(context);
        mRetrofitManager = new RetrofitManager();

        if (mRoomManager.isHasRows()) {
            //check database
            List<Movie> movies = mRoomManager.getMovies();
            this.moviesList(movies);
        }else if (NetworkState.isOnLine(context)){
            //check internet
            mRetrofitManager.getMovies(this);
        } else {
            //show Toast Error
            view.showInternetError();
        }
    }

    public void refreshData(){
        if (NetworkState.isOnLine(context)){
            //check internet
            mRetrofitManager.getMovies(this);
        } else {
            //show Toast Error
            view.showInternetError();
        }
    }

    @Override
    public void moviesList(List<Movie> movies) {
        view.updateListView(movies);
        mRoomManager.deleteAllMovies();
        mRoomManager.saveMovies(movies);
    }

    @Override
    public void onFailure() {
        view.showInternetError();
    }
}
