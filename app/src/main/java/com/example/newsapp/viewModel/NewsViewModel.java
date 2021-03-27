package com.example.newsapp.viewModel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newsapp.model.Article;
import com.example.newsapp.room.ArticleRepository;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private Application mApplication;
    private ArticleRepository mArticleRepo;
    private final LiveData<List<Article>> mAllArticles;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        mApplication = application;
        mArticleRepo = new ArticleRepository(application, hasInternetConnection());
        mAllArticles = mArticleRepo.getAllArticles();
    }

    //Returns LiveData object of type List<Article> from database. As soon as the list gets
    //updated, all observers observing this method get notified with new list
    public LiveData<List<Article>> getAllArticles() { return mAllArticles; }

    //Check to see if there is internet connection and passes the value to ArticleRepository
    private boolean hasInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
