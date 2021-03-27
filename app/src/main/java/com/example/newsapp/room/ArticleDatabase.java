package com.example.newsapp.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.newsapp.model.Article;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {Article.class},
        version = 1,
        exportSchema = false
)
public abstract class ArticleDatabase extends RoomDatabase {

    public abstract ArticleDAO articleDAO();

    //
    private static volatile ArticleDatabase INSTANCE;

    //To execute database operations on seperate thread
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ArticleDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ArticleDatabase.class, "article_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
