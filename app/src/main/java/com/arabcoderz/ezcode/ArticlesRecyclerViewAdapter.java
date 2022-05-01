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

public class ArticlesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int previousPosition = 0;
    private List<List_Article> List_Article;
    private Context context;
    static public int articleId;

    public ArticlesRecyclerViewAdapter(List<List_Article> list_article, Context context) {
        List_Article = list_article;
        this.context = context;
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
        menuItemHolder.textViewWriter.setText(List_Article.get(position).getWriter());
        menuItemHolder.textViewDate.setText(List_Article.get(position).getDate());
        menuItemHolder.textViewTitle.setText(List_Article.get(position).getTitle());
        menuItemHolder.textViewContent.setText(List_Article.get(position).getContent());
        menuItemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we will navigate to the article content page
                articleId = List_Article.get(position).getId();
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
        return (null != List_Article ? List_Article.size() : 0);
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