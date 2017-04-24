package me.esmael.newsnow.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import me.esmael.newsnow.R;
import me.esmael.newsnow.adapters.NewsAdapter;
import me.esmael.newsnow.adapters.SourcesAdapter;
import me.esmael.newsnow.api.ApiHelper;
import me.esmael.newsnow.models.Source;
import me.esmael.newsnow.models.SourceResponse;

import static java.security.AccessController.getContext;

/**
 * Created by esmael256 on 4/24/2017.
 */

public class SourcesFragment  extends Fragment implements SourcesAdapter.OnSourceClickListener{
    private static final String ARG_CATEGORY = "category";

    // TODO: Rename and change types of parameters
    private String category;
    private MaterialDialog dialogProgress;

    private OnFragmentInteractionListener mListener;
    private SourcesAdapter sourcesAdapter;

    public SourcesFragment() {
        // Required empty public constructor
    }


    public static SourcesFragment newInstance(String category) {
        SourcesFragment fragment = new SourcesFragment();
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

        RecyclerView artclesRecyclerView = (RecyclerView) rootView.findViewById(R.id.articles_recyclerView);
        sourcesAdapter = new SourcesAdapter();
        sourcesAdapter.setContext(getContext());
        sourcesAdapter.setSourceClickListener(this);
        this.showProgressDialog(getContext());


        ApiHelper.getSources(getContext(),category).subscribe(new rx.Observer<SourceResponse>() {
            @Override
            public void onCompleted() {
                SourcesFragment.this.dialogProgress.dismiss();

            }

            @Override
            public void onError(Throwable e) {
                SourcesFragment.this.dialogProgress.dismiss();
                SourcesFragment.this.showErrorDialog(getContext());

                e.printStackTrace();

            }

            @Override
            public void onNext(SourceResponse response) {
                SourcesFragment.this.dialogProgress.dismiss();
                sourcesAdapter.setSourceList(response.getSourceList());
                artclesRecyclerView.setAdapter(sourcesAdapter);
            }
        });


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
    public void onSourceClick(Source source) {

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
                .content("Loading sources")
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

}

