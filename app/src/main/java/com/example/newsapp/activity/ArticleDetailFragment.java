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
import com.example.newsapp.model.Article;

public class ArticleDetailFragment extends Fragment {

    private Article mArticle;
    private TextView mHeaderText;
    private ImageView mCrossImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.article_fragment, container, false);
        mHeaderText = rootView.findViewById(R.id.headerText1);
        mCrossImage = rootView.findViewById(R.id.crossImage1);
        WebView myWebView = rootView.findViewById(R.id.webview);
        myWebView.clearCache(true);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        webSettings.setJavaScriptEnabled(true);

        mCrossImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_articleDetailFragment_to_articleListFragment);
            }
        });

        mArticle = (Article) getArguments().getSerializable("article");
        mHeaderText.setText(mArticle.getTitle());
        myWebView.loadUrl(mArticle.getUrl());

        return rootView;
    }
}
