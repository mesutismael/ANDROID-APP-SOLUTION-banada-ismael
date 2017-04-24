package me.esmael.newsnow.api;

import android.content.Context;

import java.io.IOException;

import me.esmael.newsnow.BuildConfig;
import me.esmael.newsnow.models.Article;
import me.esmael.newsnow.models.Response;
import me.esmael.newsnow.models.SourceResponse;
import me.esmael.newsnow.utils.PreferenceHelper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by esmael256 on 4/24/2017.
 */

public class ApiHelper {
    private static NewsApi service;

    public static NewsApi getService() {
        if (service == null) {
            Interceptor logInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();

            service = retrofit.create(NewsApi.class);
        }
        return service;
    }

    public static Observable<Article> logIn(Context context, String email, String password) {
        return ApiHelper.getService().authenticate(email, password)
                .flatMap(article ->
                {
                    if (article != null) {
                        return Observable.just(article);
                    } else {
                        String error = "something went wrong";
                        return Observable.error(new IOException(error));
                    }
                })
                .doOnNext(article ->
                {


                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<Response> getArticles(Context context, String source,String category) {
        return ApiHelper.getService().getNewsArticles(source,category, BuildConfig.API_KEY)
                .flatMap(response ->
                {
                    if (response != null) {
                        return Observable.just(response);
                    } else {
                        String error = "something went wrong";
                        return Observable.error(new IOException(error));
                    }
                })
                .doOnNext(article ->
                {

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<Response> getAllArticles() {
        return ApiHelper.getService().getAllNewsArticles(BuildConfig.API_KEY)
                .flatMap(response ->
                {
                    if (response != null) {
                        return Observable.just(response);
                    } else {
                        String error = "something went wrong";
                        return Observable.error(new IOException(error));
                    }
                })
                .doOnNext(article ->
                {

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<SourceResponse> getSources(Context context, String category) {
        return ApiHelper.getService().getNewsSources(category, BuildConfig.API_KEY)
                .flatMap(sourceResponse ->
                {
                    if (sourceResponse != null) {
                        return Observable.just(sourceResponse);
                    } else {
                        String error = "something went wrong";
                        return Observable.error(new IOException(error));
                    }
                })
                .doOnNext(sourceResponse ->
                {

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
