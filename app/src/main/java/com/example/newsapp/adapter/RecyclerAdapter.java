package com.example.newsapp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.Article;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ArticleViewHolder> {

    private List<Article> mArticleList = new ArrayList<>();

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.main_card_view,
                parent,
                false
        );
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        final Article article = mArticleList.get(position);
        holder.authorText.setText(article.getAuthor());
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        Glide.with(holder.itemView).load(article.getUrlToImage()).into(holder.newsImage);
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
        //replace with notfy item changed
        notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{
        private TextView authorText;
        private TextView title;
        private TextView description;
        private ImageView newsImage;
        private Button detailButton;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            authorText = itemView.findViewById(R.id.author_text);
            title = itemView.findViewById(R.id.title_text);
            description = itemView.findViewById(R.id.description_text);
            newsImage = itemView.findViewById(R.id.imageViewPhoto);
            detailButton = itemView.findViewById(R.id.detail_button);

        }
    }
}
