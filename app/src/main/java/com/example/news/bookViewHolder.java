package com.example.news;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class bookViewHolder extends RecyclerView.ViewHolder {

    TextView nameb;

    public bookViewHolder(@NonNull View itemView) {
        super(itemView);

        nameb = itemView.findViewById(R.id.nameb);
    }
}
