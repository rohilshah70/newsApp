package com.example.newsapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newsapp.model.Article;
import com.example.newsapp.model.ResponseModel;
import com.example.newsapp.room.ArticleRepository;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private ArticleRepository mArticleRepo;
    private final LiveData<ResponseModel> mAllArticles;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        mArticleRepo = new ArticleRepository(application);
        mAllArticles = mArticleRepo.getAllArticles();
    }

    public LiveData<ResponseModel> getAllArticles() { return mAllArticles; }

    public void insert(Article article) { mArticleRepo.insert(article); }
}
