package com.example.newsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.example.newsapp.R;
import com.example.newsapp.adapter.RecyclerAdapter;
import com.example.newsapp.model.Article;
import com.example.newsapp.model.ResponseModel;
import com.example.newsapp.viewModel.NewsViewModel;

import java.util.List;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}