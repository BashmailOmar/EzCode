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

public class RecyclerViewAdapterChallenges extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int previousPosition = 0;
    private List<ListChallenges> List_Context;
    public static Context context;
    public static int index;

    public RecyclerViewAdapterChallenges(List<ListChallenges> list_Context, Context context) {
        List_Context = list_Context;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View menu1 = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.row_item_challenges, viewGroup, false);
        return new MenuItemViewChalleng(menu1);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MenuItemViewChalleng menuItemHolder = (MenuItemViewChalleng) holder;

        menuItemHolder.textViewTitel.setText(List_Context.get(position).getChallenge_title());
        menuItemHolder.textViewLanguage.setText(List_Context.get(position).getChallenge_programming_language());
        menuItemHolder.textViewLevel.setText(List_Context.get(position).getChallenge_level());
        menuItemHolder.textViewPoint.setText(List_Context.get(position).getChallenge_points());

        menuItemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, ChallengesContentActivity.class));
                index = List_Context.get(position).getId();
            }
        });


        if (position > previousPosition) { //scrolling DOWN
            AnimationUtil.animate(menuItemHolder, true);

        } else { // scrolling UP

            AnimationUtil.animate(menuItemHolder, false);
        }
        previousPosition = position;

    }

    public int getItemCount() {
        return (null != List_Context ? List_Context.size() : 0);
    }

    protected class MenuItemViewChalleng extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView textViewTitel;
        private TextView textViewPoint;
        private TextView textViewLanguage;
        private TextView textViewLevel;

        public MenuItemViewChalleng(View view) {
            super(view);
            cardView =  view.findViewById(R.id.card_View_challenges);
            textViewTitel =  view.findViewById(R.id.challengeTitle);
            textViewLanguage = view.findViewById(R.id.challengeLang);
            textViewPoint = view.findViewById(R.id.challengePoints);
            textViewLevel = view.findViewById(R.id.challengeLvl);
        }
    }

}
