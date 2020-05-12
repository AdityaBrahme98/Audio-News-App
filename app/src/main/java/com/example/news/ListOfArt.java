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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ListOfArt extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog mProgress;
    ArrayList<articleModel> list;
    private StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference aColl = db.collection("articleColl");
    ArtAdapter artAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_art);

        mProgress = new ProgressDialog(ListOfArt.this);
        mProgress.setTitle("Loading...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        mProgress.show();

        storageReference = FirebaseStorage.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataFromFb();


    }

    private void dataFromFb()
    {
        if(list.size()>0)
        {
            list.clear();
        }

        aColl.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot documentSnapshot: task.getResult())
                        {
                            articleModel am = new articleModel(documentSnapshot.getString("name")
                                    ,documentSnapshot.getString("link"));
                            list.add(am);
                        }

                        artAdapter = new ArtAdapter(ListOfArt.this,list, ListOfArt.this);
                        recyclerView.setAdapter(artAdapter);

                        mProgress.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListOfArt.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListOfArt.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
