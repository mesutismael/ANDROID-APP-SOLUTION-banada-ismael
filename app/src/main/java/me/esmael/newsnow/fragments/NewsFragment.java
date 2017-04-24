package me.esmael.newsnow.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import me.esmael.newsnow.R;
import me.esmael.newsnow.activities.WebViewActivity;
import me.esmael.newsnow.adapters.NewsAdapter;
import me.esmael.newsnow.api.ApiHelper;
import me.esmael.newsnow.models.Article;
import me.esmael.newsnow.models.Response;
import me.esmael.newsnow.utils.Constants;
import me.esmael.newsnow.utils.NavigationUtils;
import me.esmael.newsnow.utils.Observer;
import me.esmael.newsnow.utils.PreferenceHelper;

/**
 * Created by esmael256 on 4/24/2017.
 */

public class NewsFragment extends Fragment implements NewsAdapter.OnArticleClickListener{
        private static final String ARG_CATEGORY = "category";

        // TODO: Rename and change types of parameters
        private String category;
        private MaterialDialog dialogProgress;

        private OnFragmentInteractionListener mListener;
        private NewsAdapter newsAdapter;
        private AppCompatSpinner categorySpinner;
        private AppCompatSpinner sourceSpinner;
           String articleCategory;
         String articalSource;
        ArrayAdapter sourceArrayAdapter;
        ArrayAdapter categoryArrayAdapter;
        List <String> categorylist;
        List <String> sourceList;
        RecyclerView artclesRecyclerView;

        public NewsFragment() {
                // Required empty public constructor
        }


        public static NewsFragment newInstance(String category) {
                NewsFragment fragment = new NewsFragment();
                Bundle args = new Bundle();
                args.putString(ARG_CATEGORY, category);
                fragment.setArguments(args);
                return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                if (getArguments() != null) {
                        category = getArguments().getString(ARG_CATEGORY);
                }


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                View rootView = inflater.inflate(R.layout.fragment_news, container, false);
                categorylist= new ArrayList();
                categorylist.add("Select Category");
                categorylist.add("Tecnology");
                categorylist.add("business");
                categorylist.add("sport");
                categorylist.add("Entertainment");
                categorylist.add("Politics");
                categorylist.add("Gaming");
                categorylist.add("Music");

                sourceList= new ArrayList();
                sourceList.add("Select News Source");
                sourceList.add("ABC News AU");
                sourceList.add("Bild");
                sourceList.add("Bussiness insider");
                sourceList.add("BBC sport");
                sourceList.add("Al jazeera");
                sourceList.add("CNBC");
                sourceList.add("CNN");

                categoryArrayAdapter = new ArrayAdapter(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, categorylist);
                sourceArrayAdapter = new ArrayAdapter(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, sourceList);
                categorySpinner= (AppCompatSpinner)rootView.findViewById(R.id.category_spinner);
                sourceSpinner= (AppCompatSpinner)rootView.findViewById(R.id.source_spinner);
                categorySpinner.setAdapter(categoryArrayAdapter);
                sourceSpinner.setAdapter(sourceArrayAdapter);
                articalSource= Constants.SOURCE_TECH_CRUNCH;
                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                sourceSpinner.setVisibility(View.VISIBLE);
                              switch (position)
                              {
                                      case 0:
                                              break;
                                      case 1:
                                              articleCategory="technology";
                                              break;
                                      case 2:
                                              articleCategory="sport";
                                              break;
                                      case 3:
                                              articleCategory="Entertainment";
                                              break;
                                      case 4:
                                              articleCategory="politics";
                                              break;
                                      case 5:
                                              articleCategory="gaming";
                                              break;
                                      case 6:
                                              articleCategory="misic";
                                              break;
                              }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {


                        }
                });


                sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                switch (position)
                                {
                                        case 0:
                                                break;
                                        case 1:
                                                articalSource="abc-news-au";
                                                break;
                                        case 2:
                                                articalSource="bild";
                                                break;
                                        case 3:
                                                articalSource="business-insider";
                                                break;
                                        case 4:
                                                articalSource="bbc-sport";
                                                break;
                                        case 5:
                                                articalSource="aljazeera-english";
                                                break;
                                        case 6:
                                                articalSource="cnbc";
                                                break;
                                        case 7:
                                                articalSource="cnn";
                                                break;
                                }
                                getArticlesWithCategoryAndSource(articalSource,articleCategory);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                });


                artclesRecyclerView = (RecyclerView) rootView.findViewById(R.id.articles_recyclerView);
                newsAdapter = new NewsAdapter();
                newsAdapter.setContext(getContext());
                newsAdapter.setArticleClickListener(this);
                this.showProgressDialog(getContext());
                getAllArticles();

                return rootView;
        }

        // TODO: Rename method, update argument and hook method into UI event
        public void onButtonPressed(Uri uri) {
                if (mListener != null) {
                        mListener.onFragmentInteraction(uri);
                }
        }

        @Override
        public void onAttach(Context context) {
                super.onAttach(context);
                if (context instanceof OnFragmentInteractionListener) {
                        mListener = (OnFragmentInteractionListener) context;
                } else {
                        throw new RuntimeException(context.toString()
                                + " must implement OnFragmentInteractionListener");
                }
        }

        @Override
        public void onDetach() {
                super.onDetach();
                mListener = null;
        }

        @Override
        public void onArticleClick(Article article) {
                Intent i= new Intent(NewsFragment.this.getContext(), WebViewActivity.class);
                i.putExtra("url",article.getUrl());
                startActivity(i);

        }


        public interface OnFragmentInteractionListener {
                // TODO: Update argument type and name
                void onFragmentInteraction(Uri uri);
        }


        private void showErrorDialog(Context context) {
                new MaterialDialog.Builder(context)
                        .title(R.string.dialog_error)
                        .content(R.string.failed_data)
                        .positiveText(R.string.dialog_positive)
                        .show();
        }

        private void showProgressDialog(Context context) {
                this.dialogProgress = new MaterialDialog.Builder(context)
                        .content(R.string.loading_news)
                        .progress(true, 0)
                        .cancelable(false)
                        .show();
        }


        private void getArticlesWithCategoryAndSource(String source_article,String category_article)
        {
                ApiHelper.getArticles(getContext(),source_article,category_article).subscribe(new rx.Observer<Response>() {
                        @Override
                        public void onCompleted() {
                                NewsFragment.this.dialogProgress.dismiss();

                        }

                        @Override
                        public void onError(Throwable e) {
                                NewsFragment.this.dialogProgress.dismiss();
                                NewsFragment.this.showErrorDialog(getContext());

                                e.printStackTrace();

                        }

                        @Override
                        public void onNext(Response response) {
                                NewsFragment.this.dialogProgress.dismiss();
                                newsAdapter.setArticleList(response.getArticles());
                                artclesRecyclerView.setAdapter(newsAdapter);
                        }
                });
        }


        private void getAllArticles()
        {
                ApiHelper.getAllArticles().subscribe(new rx.Observer<Response>() {
                        @Override
                        public void onCompleted() {
                                NewsFragment.this.dialogProgress.dismiss();

                        }

                        @Override
                        public void onError(Throwable e) {
                                NewsFragment.this.dialogProgress.dismiss();
                                NewsFragment.this.showErrorDialog(getContext());

                                e.printStackTrace();

                        }

                        @Override
                        public void onNext(Response response) {
                                NewsFragment.this.dialogProgress.dismiss();
                                newsAdapter.setArticleList(response.getArticles());
                                artclesRecyclerView.setAdapter(newsAdapter);
                        }
                });
        }

}
