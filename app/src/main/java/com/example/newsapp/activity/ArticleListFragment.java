package com.example.newsapp.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.adapter.RecyclerAdapter;
import com.example.newsapp.model.Article;
import com.example.newsapp.viewModel.NewsViewModel;

import java.util.List;

public class ArticleListFragment extends Fragment {

    private NewsViewModel mNewsViewModel;
    private ImageView mCrossImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.article_list_fragment, container, false);
        mCrossImage = rootView.findViewById(R.id.crossImage);
        RecyclerView recyclerView = rootView.findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);

        //Get view model instance
        mNewsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        //Observe getAllArticles() in view model. As soon as articleList gets updated, onChanged() method gets called
        mNewsViewModel.getAllArticles().observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articleList) {
                if(!articleList.isEmpty())
                    //update recycler view
                    recyclerAdapter.setArticleFromActivity(articleList);
                else {
                    //the list is empty if newtork connection is not there the first time app is launched
                    //In that case, since no data is fetched, no data is cached
                    makeToast("No internet connection or no cached data");
                }
            }
        });

        //Use navigation on cross button to go back to MotionFragment
        mCrossImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_articleListFragment_to_motionFragment);
            }
        });
        return rootView;
    }

    private void makeToast(String msg) {
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                msg,
                Toast.LENGTH_LONG);

        toast.show();
    }
}
