package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class Feedback extends AppCompatActivity {

    Button submit;
    TextView mess, name;
    FirebaseFirestore db;
    String n , m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        db = FirebaseFirestore.getInstance();
        submit = findViewById(R.id.submit);
        name = findViewById(R.id.name);
        mess = findViewById(R.id.message);

        n = "";
        m = "";

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{

                    n = name.getText().toString();
                    m = mess.getText().toString();

                    if(n.equals("") || m.equals("") || n.equals(" ") || m.equals(" ") || n.equals(null) || m.equals(null) || m.equals("\n")){
                        Toast.makeText(Feedback.this, "All fields compulsory.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Feedback_file file = new Feedback_file(n, m);
                        db.collection("Feedback").document().set(file);
                        Toast.makeText(Feedback.this, "Successful.", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        mess.setText("");

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }catch (Exception e){
                    Toast.makeText(Feedback.this, "All fields compulsory.", Toast.LENGTH_SHORT).show();


                }


            }
        });
    }
}
