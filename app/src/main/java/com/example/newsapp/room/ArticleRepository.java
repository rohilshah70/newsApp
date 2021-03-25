package com.example.newsapp.room;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.model.Article;
import com.example.newsapp.model.ResponseModel;
import com.example.newsapp.retrofit.APIList;

import java.util.List;

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
    private MutableLiveData<ResponseModel> mResponseModelLiveData;


    private ArticleDAO mArticleDAO;
    private LiveData<List<Article>> mAllArticles;

    public ArticleRepository(Application application){
        ArticleDatabase db = ArticleDatabase.getDatabase(application);
        mArticleDAO = db.articleDAO();
//        mAllArticles = mArticleDAO.getAllArticles();

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
        mAPIList.getTrendingNews("in", API_KEY)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.body() != null) {
                            mResponseModelLiveData.postValue(response.body());
                            //delete existing database and add new values
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                            mResponseModelLiveData.postValue(null);
                    }
                });
    }

    //Live data will notify all observers on main thread
    public LiveData<ResponseModel> getAllArticles() {
        return mResponseModelLiveData;
    }

    //Make sure insertion in on non UI thread
    public void insert(final Article article) {
        ArticleDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mArticleDAO.insert(article);
            }
        });
    }
}
