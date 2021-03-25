package com.example.newsapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsapp.model.Article;

import java.util.List;

@Dao
public interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Article article);

    @Query("SELECT * from articles ORDER BY author")
    LiveData<List<Article>> getAllArticles();



}
