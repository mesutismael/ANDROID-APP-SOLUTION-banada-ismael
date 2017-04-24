package me.esmael.newsnow.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.esmael.newsnow.R;
import me.esmael.newsnow.models.Source;
import me.esmael.newsnow.utils.ImageUtils;

/**
 * Created by esmael256 on 4/24/2017.
 */

public class SourcesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_SOURCE = 0;
    private static final int TYPE_EMPTY = 1;
    private List<Source> sourceList;
    private OnSourceClickListener clickListener;
    private Context context;

    public void setSourceClickListener(OnSourceClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setContext(Context context)
    {
        this.context=context;
    }

    public void setSourceList(List<Source> sourceList) {
        this.sourceList = sourceList;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_SOURCE:
                View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_source_item, parent, false);
                return new SourceViewHolder(todoView);
            case TYPE_EMPTY:
                View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list_item, parent, false);
                return new EmptyViewHolder(emptyView);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (this.getEmptyCount() == 0)
            return TYPE_SOURCE;
        else
            return TYPE_EMPTY;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SourceViewHolder) {
            Source source = this.sourceList.get(position);
            ((SourceViewHolder) holder).bind(source);
        }
    }

    @Override
    public int getItemCount() {
        return this.getTodoCount() + this.getEmptyCount();
    }

    private int getTodoCount() {
        return this.sourceList != null ? this.sourceList.size() : 0;
    }

    private int getEmptyCount() {
        return this.getTodoCount() == 0 ? 1 : 0;
    }

    public interface OnSourceClickListener {
        void onSourceClick(Source source);
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View view) {
            super(view);
        }
    }

    public class SourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewUrl;
        private TextView textViewCategory;
        private ImageView imageViewThumbnail;
        private ImageButton imageButtonFavourite;
        private CardView cardView;
        private Source source;

        public SourceViewHolder(View itemView) {
            super(itemView);

            this.textViewTitle = (TextView) itemView.findViewById(R.id.title_textView);
            this.textViewDescription = (TextView) itemView.findViewById(R.id.description_textView);
            this.textViewUrl = (TextView) itemView.findViewById(R.id.url_textView);
            this.textViewCategory = (TextView) itemView.findViewById(R.id.category_textView);
            this.imageViewThumbnail=(ImageView) itemView.findViewById(R.id.thumbnail_imageView);
            this.imageButtonFavourite= (ImageButton) itemView.findViewById(R.id.favourite_imageButton);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
            this.imageButtonFavourite.setOnClickListener(v -> imageButtonFavourite.setImageResource(R.drawable.ic_star_black_24dp));

            cardView.setOnClickListener(this);
        }

        public void bind(Source source) {
            this.source = source;

            this.textViewTitle.setText(this.source.getName());
            this.textViewDescription.setText(this.source.getDescription());
            this.textViewUrl.setText("URL: "+this.source.getUrl());
            this.textViewCategory.setText("Category: "+this.source.getCategory());

            if(!TextUtils.isEmpty(this.source.getUrlsToLogos().getMedium()))
            ImageUtils.loadImage(context,imageViewThumbnail,this.source.getUrlsToLogos().getMedium());


        }

        @Override
        public void onClick(View view) {

            SourcesAdapter.this.clickListener.onSourceClick(this.source);
        }
    }
}


