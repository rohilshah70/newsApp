package com.example.newsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.newsapp.R;
import com.example.newsapp.adapter.RecyclerAdapter;
import com.example.newsapp.model.Article;
import com.example.newsapp.model.ResponseModel;
import com.example.newsapp.viewModel.NewsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
// 331630b2361f49cab367786d3c65fb8c

    private NewsViewModel mNewsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);

        mNewsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        mNewsViewModel.getAllArticles().observe(this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel articleList) {
                //update recycler view
                recyclerAdapter.setArticleFromActivity(articleList.getArticles());
            }
        });

    }
}