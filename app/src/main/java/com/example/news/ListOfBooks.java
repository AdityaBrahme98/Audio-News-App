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

public class ListOfBooks extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<bookModel> booklist;
    BksAdapter bksAdapter;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_books);

        mProgress = new ProgressDialog(ListOfBooks.this);
        mProgress.setTitle("Loading...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        mProgress.show();

        booklist = new ArrayList<>();
        setUpFB();
        setUpRV();
        dataFromFB();
    }


    private void dataFromFB()
    {
        if(booklist.size()>0)
        {
            booklist.clear();
        }
        db.collection("bookColl").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot documentSnapshot: task.getResult())
                        {
                            bookModel bksModel = new bookModel(documentSnapshot.getString("name"),
                                    documentSnapshot.getString("link"));
                            booklist.add(bksModel);
                        }

                        bksAdapter = new BksAdapter(ListOfBooks.this,booklist, ListOfBooks.this);
                        recyclerView.setAdapter(bksAdapter);
                        mProgress.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ListOfBooks.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void setUpFB()
    {
        db = FirebaseFirestore.getInstance();
    }

    private void setUpRV()
    {
        recyclerView = findViewById(R.id.recyclerViewB);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListOfBooks.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
