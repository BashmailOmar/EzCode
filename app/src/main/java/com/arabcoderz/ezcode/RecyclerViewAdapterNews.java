package com.arabcoderz.ezcode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapterNews extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int previousPosition = 0;
    private List<ListNews> ListNews;
    private Context context;

    public RecyclerViewAdapterNews(List<ListNews> list_News, Context context) {
        ListNews = list_News;
        this.context = context;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View menu1 = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.row_item_news, viewGroup, false);
        return new MenuItemViewHolder(menu1);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        menuItemHolder.textViewTitel.setText(ListNews.get(position).getTitle());
        Picasso.get().load(ListNews.get(position).getImg_link()).into(menuItemHolder.imageView);
        menuItemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ListNews.get(position).getLink()));
                context.startActivity(intent);
            }
        });

        if (position > previousPosition) { //scrolling DOWN
            AnimationUtil.animate(menuItemHolder, true);

        } else { // scrolling UP

            AnimationUtil.animate(menuItemHolder, false);
        }
        previousPosition = position;
    }

    @Override
    public int getItemCount() {
        return (null != ListNews ? ListNews.size() : 0);
    }

    protected class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView textViewTitel;

        public MenuItemViewHolder(View view) {
            super(view);
            cardView =  view.findViewById(R.id.card_View_news);
            imageView = view.findViewById(R.id.img_news);
            textViewTitel =  view.findViewById(R.id.title_news);
        }
    }

}
