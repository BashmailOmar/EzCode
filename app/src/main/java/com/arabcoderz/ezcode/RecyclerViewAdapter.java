package com.arabcoderz.ezcode;

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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int previousPosition = 0;

    private List<List_Item> List_Item;
    private Context context;

    public RecyclerViewAdapter(List<List_Item> list_Item, Context context) {
        List_Item = list_Item;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View menu1 = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.row_item_news, viewGroup, false);
        return new MenuItemViewHolder(menu1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;

        menuItemHolder.textView.setText(List_Item.get(position).getTitle());

        Picasso.get().load(List_Item.get(position).getImg_link()).into(menuItemHolder.imageView);


        menuItemHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(List_Item.get(position).getLink()));
                context.startActivity(intent);
            }
        });
        menuItemHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(List_Item.get(position).getLink()));
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
        return (null != List_Item ? List_Item.size() : 0);
    }

    protected class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView textView;

        public MenuItemViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_View_news);
            imageView = (ImageView) view.findViewById(R.id.img_news);
            textView = (TextView) view.findViewById(R.id.title_news);
        }
    }

}
