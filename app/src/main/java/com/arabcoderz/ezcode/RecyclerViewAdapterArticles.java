package com.arabcoderz.ezcode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerViewAdapterArticles extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int previousPosition = 0;
    private List<ListArticle> ListArticle;
    private Context context;
    static public int articleId;

    public RecyclerViewAdapterArticles(List<ListArticle> list_article, Context context) {
        ListArticle = list_article;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View menu1 = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.row_item_article, viewGroup, false);
        return new MenuItemViewHolder(menu1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        menuItemHolder.textViewWriter.setText(ListArticle.get(position).getWriter());
        menuItemHolder.textViewDate.setText(ListArticle.get(position).getDate());
        menuItemHolder.textViewTitle.setText(ListArticle.get(position).getTitle());
        menuItemHolder.textViewContent.setText(ListArticle.get(position).getContent());
        menuItemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we will navigate to the article content page
                articleId = ListArticle.get(position).getId();
                context.startActivity(new Intent(context, ArticleContentActivity.class));
            }
        });//----------------------------------------------------------------------------------------
        if (position > previousPosition) { //scrolling DOWN
            AnimationUtil.animate(menuItemHolder, true);
        } else { // scrolling UP
            AnimationUtil.animate(menuItemHolder, false);
        }
        previousPosition = position;
    }

    @Override
    public int getItemCount() {
        return (null != ListArticle ? ListArticle.size() : 0);
    }

    protected class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView textViewWriter, textViewDate, textViewTitle, textViewContent;

        public MenuItemViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_View_article);
            textViewWriter = (TextView) view.findViewById(R.id.article_writer);
            textViewDate = (TextView) view.findViewById(R.id.article_date);
            textViewTitle = (TextView) view.findViewById(R.id.article_title);
            textViewContent = (TextView) view.findViewById(R.id.article_content);
        }
    }
}