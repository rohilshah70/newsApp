package com.example.newsapp.room;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.model.Article;
import com.example.newsapp.model.ResponseModel;
import com.example.newsapp.retrofit.APIList;

import java.util.List;
import java.util.function.Consumer;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.newsapp.Constants.API_KEY;
import static com.example.newsapp.Constants.NEWS_BASE_URL;

public class ArticleRepository {

    private APIList mAPIList;
    private MutableLiveData<List<Article>> mResponseModelLiveData;
    private Boolean mHasInternet;
    private ArticleDAO mArticleDAO;

    public ArticleRepository(Application application, Boolean hasInternet){
        ArticleDatabase db = ArticleDatabase.getDatabase(application);
        mArticleDAO = db.articleDAO();
//        mAllArticles = mArticleDAO.getAllArticles();
        mHasInternet = hasInternet;
        mResponseModelLiveData = new MutableLiveData<>();

        OkHttpClient client = new OkHttpClient.Builder().build();

        mAPIList = new Retrofit.Builder()
                .baseUrl(NEWS_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIList.class);

        //Fetch list as soon as repo is
        getArticleList();
    }

    public void getArticleList(){
        if(mHasInternet) {
            mAPIList.getTrendingNews("in", API_KEY)
                    .enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.body() != null) {
                                delete();
                                insert(response.body().getArticles());
//                            mResponseModelLiveData.postValue(response.body().getArticles());
                                //delete existing database and add new values
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            mResponseModelLiveData.postValue(null);
                        }
                    });
        } else {
            getAllArticles();
        }

    }

    //Live data will notify all observers on main thread
    public LiveData<List<Article>> getAllArticles() {
        return mArticleDAO.getAllArticles();
    }

    //Make sure insertion in on non UI thread
    public void insert(final List<Article> article) {
        ArticleDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                article.forEach(new Consumer<Article>() {
                    @Override
                    public void accept(Article it) {
                        mArticleDAO.insert(it);
                    }
                });
            }
        });
    }

    public void delete(){
        ArticleDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mArticleDAO.delete();
            }
        });
    }
}
