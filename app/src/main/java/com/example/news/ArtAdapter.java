package com.example.news;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ArtViewHolder> {

    ListOfArt listOfArt;
    ArrayList<articleModel> list;
    Context context;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlistener = listener;
    }

    public ArtAdapter(ListOfArt listOfArt,ArrayList<articleModel> list, Context context) {
        this.listOfArt = listOfArt;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ArtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater1 = LayoutInflater.from(listOfArt.getBaseContext());
        View view = layoutInflater1.inflate(R.layout.elements,null,false);

        return new ArtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArtViewHolder holder, final int position) {

        holder.name.setText(list.get(position).getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = new Bundle();

                extras.putString("name",list.get(position).getName());
                extras.putString("link",list.get(position).getLink());
                extras.putInt("key",2);

                Intent intent = new Intent(context, AudioPlayer.class);
                intent.putExtras(extras);
                v.getContext().startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ArtViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ArtViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mlistener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mlistener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
