package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListOfNews extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<newsModel> newslist;
    NwsAdapter nwsAdapter;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_news);

        mProgress = new ProgressDialog(ListOfNews.this);
        mProgress.setTitle("Loading...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        mProgress.show();

        newslist = new ArrayList<>();
        setUpFB();
        setUpRV();
        dataFromFB();
    }


    private void dataFromFB()
    {
        if(newslist.size()>0)
        {
            newslist.clear();
        }
        db.collection("newsColl").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot documentSnapshot: task.getResult())
                        {
                            newsModel nwsModel = new newsModel(documentSnapshot.getString("date"),
                                    documentSnapshot.getString("link"));
                            newslist.add(nwsModel);
                        }

                        nwsAdapter = new NwsAdapter(ListOfNews.this,newslist, ListOfNews.this);
                        recyclerView.setAdapter(nwsAdapter);
                        mProgress.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ListOfNews.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    private void setUpFB()
    {
        db = FirebaseFirestore.getInstance();
    }

    private void setUpRV()
    {
        recyclerView = findViewById(R.id.recyclerViewN);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListOfNews.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
