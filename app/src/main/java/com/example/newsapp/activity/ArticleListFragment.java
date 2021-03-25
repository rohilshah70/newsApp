package com.example.newsapp.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.adapter.RecyclerAdapter;
import com.example.newsapp.model.ResponseModel;
import com.example.newsapp.viewModel.NewsViewModel;

public class ArticleListFragment extends Fragment {

    private NewsViewModel mNewsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.article_list_fragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);

        mNewsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        mNewsViewModel.getAllArticles().observe(getViewLifecycleOwner(), new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel articleList) {
                //update recycler view
                recyclerAdapter.setArticleFromActivity(articleList.getArticles());
            }
        });
        return rootView;
    }
}
