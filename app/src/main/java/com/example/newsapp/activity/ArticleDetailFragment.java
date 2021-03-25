package com.example.newsapp.activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newsapp.R;
import com.example.newsapp.model.Article;

public class ArticleDetailFragment extends Fragment {

    private Article mArticle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.article_fragment, container, false);
        WebView myWebView = rootView.findViewById(R.id.webview);
        myWebView.clearCache(true);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        webSettings.setJavaScriptEnabled(true);

        mArticle = (Article) getArguments().getSerializable("article");
        myWebView.loadUrl(mArticle.getUrl());

        return rootView;
    }
}
