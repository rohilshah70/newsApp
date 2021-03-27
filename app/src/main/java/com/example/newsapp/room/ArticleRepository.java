package com.example.newsapp.room;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.model.Article;
import com.example.newsapp.model.ResponseModel;
import com.example.newsapp.retrofit.APIList;
import com.example.newsapp.retrofit.RetrofitClient;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.newsapp.Constants.API_KEY;

public class ArticleRepository {

    private APIList mAPIList;
    private MutableLiveData<List<Article>> mResponseModelLiveData;
    private Boolean mHasInternet;
    private ArticleDAO mArticleDAO;
    private static Retrofit mRetrofit = null;

    public ArticleRepository(Application application, Boolean hasInternet){
        //Get instance of room database
        ArticleDatabase db = ArticleDatabase.getDatabase(application);
        mArticleDAO = db.articleDAO();
        mHasInternet = hasInternet;
        mResponseModelLiveData = new MutableLiveData<>();

        mRetrofit = RetrofitClient.getClient();
        mAPIList = mRetrofit.create(APIList.class);

        //Fetch list
        getArticleList();
    }

    public void getArticleList(){
        if(mHasInternet) {
            //make request to fetch news list and handle response
            mAPIList.getTrendingNews("in", API_KEY)
                    .enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.body() != null) {
                                //Delete existing entries from the table first
                                delete();
                                //insert the new fetched list in the database
                                insert(response.body().getArticles());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            mResponseModelLiveData.postValue(null);
                        }
                    });
        } else {
            //If no internet, then call getAllArticle() which returns list from db
            getAllArticles();
        }

    }

    //Live data will notify all observers
    public LiveData<List<Article>> getAllArticles() {
        return mArticleDAO.getAllArticles();
    }

    //Make sure insertion in on non UI thread
    //NewsViewModel get notified since we have LiveData object in database
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

    //Perform table entries deletion on separate thread
    public void delete(){
        ArticleDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mArticleDAO.delete();
            }
        });
    }
}
