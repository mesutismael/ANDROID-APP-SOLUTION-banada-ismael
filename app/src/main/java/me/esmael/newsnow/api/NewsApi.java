package me.esmael.newsnow.api;

import java.util.List;

import me.esmael.newsnow.models.Article;
import me.esmael.newsnow.models.Response;
import me.esmael.newsnow.models.SourceResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by esmael256 on 4/24/2017.
 */

public interface NewsApi {

    @FormUrlEncoded
    @POST("authenticate")
    Observable<Article> authenticate(@Field("phone") String phone,
                                     @Field("android_key") String password);


    @GET("articles")
    Observable<Response> getNewsArticles(@Query("source") String source,@Query("category") String category,
                                      @Header("x-api-key") String api_key);

    @GET("articles")
    Observable<Response> getAllNewsArticles(@Header("x-api-key") String api_key);

    @GET("sources")
    Observable<SourceResponse> getNewsSources(@Query("category") String category,
                                              @Header("x-api-key") String api_key);


}