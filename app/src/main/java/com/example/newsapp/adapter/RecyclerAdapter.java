package com.example.newsapp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.databinding.MainCardViewBinding;
import com.example.newsapp.model.Article;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ArticleViewHolder> {

    private List<Article> mArticleList = new ArrayList<>();
    private MainCardViewBinding mBinding;

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate card view
        mBinding = MainCardViewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);

        View view = mBinding.getRoot();
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        final Article article = mArticleList.get(position);
        mBinding.setRecyclerCard(article);

        //Use navigation to open full article in new fragment. Pass Article object between fragments
        holder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("article", article);
                Navigation.findNavController(view).navigate(R.id.action_articleListFragment_to_articleDetailFragment, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    public void setArticleFromActivity(List<Article> list){
        this.mArticleList = list;
        //tell adapter to redraw layout
        notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{
        private Button detailButton;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            detailButton = itemView.findViewById(R.id.detail_button);

        }
    }
}
