package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void opnArticles(View view)
    {
        Intent intent = new Intent(getApplicationContext(),ListOfArt.class);
        startActivity(intent);
        finish();
    }

    public void opnNews(View view)
    {
        Intent intent = new Intent(getApplicationContext(),ListOfNews.class);
        startActivity(intent);
        finish();
    }

    public void opnBooks(View view)
    {
        Intent intent = new Intent(getApplicationContext(),ListOfBooks.class);
        startActivity(intent);
        finish();
    }

    public void opnFeedback(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Feedback.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
