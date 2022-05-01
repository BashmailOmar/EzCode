package com.arabcoderz.ezcode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class PlacesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int previousPosition = 0;
    private List<ListPlaces> List_place;
    private Context context;
    static public int placeNum = 0;

    public PlacesRecyclerViewAdapter(List<ListPlaces> list_places, Context context) {
        List_place = list_places;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View menu1 = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.row_users_places, viewGroup, false);
//        View menu2 = LayoutInflater.from(viewGroup.getContext()).inflate(
//                R.layout.row_first_place, viewGroup, false);
//        View menu3 = LayoutInflater.from(viewGroup.getContext()).inflate(
//                R.layout.row_second_place, viewGroup, false);
//        View menu4 = LayoutInflater.from(viewGroup.getContext()).inflate(
//                R.layout.row_third_place, viewGroup, false);
        return new MenuItemViewHolder(menu1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//يبالي هنا بالبوزيشن اغير الonCreateViewHolder
        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        menuItemHolder.placeInRowPlaces.setText(List_place.get(position).getPlace());
        menuItemHolder.usernameInRowPlaces.setText(List_place.get(position).getUsername());
        menuItemHolder.pointsInRowPlaces.setText(List_place.get(position).getPoint());
//        Picasso.get().load(List_place.get(position).getImg()).into(menuItemHolder.ImageViewInPRVA);
        Picasso.get().load(MainActivity.MainLink + "avatar/" + List_place.get(position).getImg()).into(menuItemHolder.ImageViewInPRVA);
//        menuItemHolder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //here we will navigate to the user page in the future ان شاء الله
//            }
//        });//----------------------------------------------------------------------------------------


        if (position > previousPosition) { //scrolling DOWN
            AnimationUtil.animate(menuItemHolder, true);

        } else { // scrolling UP

            AnimationUtil.animate(menuItemHolder, false);
        }
        previousPosition = position;
    }

    @Override
    public int getItemCount() {
        return (null != List_place ? List_place.size() : 0);
    }

    protected class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private TextView placeInRowPlaces, usernameInRowPlaces, pointsInRowPlaces;
        ImageView ImageViewInPRVA;

        public MenuItemViewHolder(View view) {
            super(view);
            placeInRowPlaces = (TextView) view.findViewById(R.id.placeInRowPlaces);
            usernameInRowPlaces = (TextView) view.findViewById(R.id.usernameInRowPlaces);
            pointsInRowPlaces = (TextView) view.findViewById(R.id.pointsInRowPlaces);
            ImageViewInPRVA = (ImageView) view.findViewById(R.id.user_img);
        }
    }

}
