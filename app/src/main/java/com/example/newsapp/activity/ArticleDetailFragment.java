package com.example.newsapp.activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.newsapp.R;
import com.example.newsapp.databinding.ArticleFragmentBinding;
import com.example.newsapp.model.Article;

public class ArticleDetailFragment extends Fragment {

    private Article mArticle;
    private TextView mHeaderText;
    private ImageView mCrossImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ArticleFragmentBinding binding = ArticleFragmentBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
//        mHeaderText = rootView.findViewById(R.id.headerText1);
        mCrossImage = rootView.findViewById(R.id.crossImage1);
        WebView myWebView = rootView.findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Use navigation on cross button to go back to ArticleListFragment
        mCrossImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_articleDetailFragment_to_articleListFragment);
            }
        });

        //Get argument passed from ArticleListFragment
        mArticle = (Article) getArguments().getSerializable("article");
        binding.setHeader(mArticle);
        //Get article title to set header in fragment
//        mHeaderText.setText(mArticle.getTitle());
        //Load webview using URL from Article object
        myWebView.loadUrl(mArticle.getUrl());

        return rootView;
    }
}
