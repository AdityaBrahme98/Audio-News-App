package com.example.news;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class newsViewHolder extends RecyclerView.ViewHolder {

    TextView Ndate;

    public newsViewHolder(@NonNull View itemView) {
        super(itemView);
        Ndate = itemView.findViewById(R.id.date);
    }
}
