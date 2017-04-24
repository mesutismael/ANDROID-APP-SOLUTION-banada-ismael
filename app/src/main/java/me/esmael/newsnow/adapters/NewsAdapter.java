package me.esmael.newsnow.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.esmael.newsnow.R;
import me.esmael.newsnow.models.Article;
import me.esmael.newsnow.utils.ImageUtils;

/**
 * Created by esmael256 on 4/24/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ARTICLE = 0;
    private static final int TYPE_EMPTY = 1;
    private List<Article> articleList;
    private OnArticleClickListener clickListener;
    private Context context;

    public void setArticleClickListener(OnArticleClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setContext(Context context)
    {
        this.context=context;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ARTICLE:
                View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
                return new ArticleViewHolder(todoView);
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
            return TYPE_ARTICLE;
        else
            return TYPE_EMPTY;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ArticleViewHolder) {
            Article article = this.articleList.get(position);
            ((ArticleViewHolder) holder).bind(article);
        }
    }

    @Override
    public int getItemCount() {
        return this.getTodoCount() + this.getEmptyCount();
    }

    private int getTodoCount() {
        return this.articleList != null ? this.articleList.size() : 0;
    }

    private int getEmptyCount() {
        return this.getTodoCount() == 0 ? 1 : 0;
    }

    public interface OnArticleClickListener {
        void onArticleClick(Article article);
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View view) {
            super(view);
        }
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewAuthor;
        private TextView textViewpublishedAt;
        private ImageView imageViewThumbnail;
        private ImageButton imageButtonFavourite;
        private CardView cardView;
        private Article article;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            this.textViewTitle = (TextView) itemView.findViewById(R.id.title_textView);
            this.textViewDescription = (TextView) itemView.findViewById(R.id.description_textView);
            this.textViewAuthor = (TextView) itemView.findViewById(R.id.author_textView);
            this.textViewpublishedAt = (TextView) itemView.findViewById(R.id.published_at_textView);
            this.imageViewThumbnail=(ImageView) itemView.findViewById(R.id.thumbnail_imageView);
            this.imageButtonFavourite= (ImageButton) itemView.findViewById(R.id.favourite_imageButton);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
            this.imageButtonFavourite.setOnClickListener(v -> imageButtonFavourite.setImageResource(R.drawable.ic_star_black_24dp));

            cardView.setOnClickListener(this);
        }

        public void bind(Article article) {
            this.article = article;

            this.textViewTitle.setText(this.article.getTitle());
            this.textViewDescription.setText(this.article.getDescription());
            this.textViewAuthor.setText("Author: "+this.article.getAuthor());
            this.textViewpublishedAt.setText("Date: "+this.article.getPublishedAt());
            ImageUtils.loadImage(context,imageViewThumbnail,this.article.getUrlToImage());


        }

        @Override
        public void onClick(View view) {

            NewsAdapter.this.clickListener.onArticleClick(this.article);
        }
    }
}

