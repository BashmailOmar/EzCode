package com.arabcoderz.ezcode;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterChallenges extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private int previousPosition = 0;

    private List<List_Item> List_Item;
    private Context context;

    public RecyclerViewAdapterChallenges(List<com.arabcoderz.ezcode.List_Item> list_Item, Context context) {
        List_Item = list_Item;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
