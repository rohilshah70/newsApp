package com.example.newsapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsapp.model.Article;

import java.util.List;

@Dao
public interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Article article);

    @Query("SELECT * from articles ORDER BY author COLLATE NOCASE")
    LiveData<List<Article>> getAllArticles();

    @Query("DELETE FROM articles")
    void delete();
}
