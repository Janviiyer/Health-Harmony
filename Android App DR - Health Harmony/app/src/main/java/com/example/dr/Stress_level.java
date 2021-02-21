package com.example.dr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Stress_level extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_level);
    }

    public void open_yoga(View view) {

        String url = "https://www.yogajournal.com/practice/yoga-sequences/yoga-for-inner-peace-stress-relief-daily-practice-challenge/";

        Intent i1 = new Intent(Intent.ACTION_VIEW);
        i1.setData(Uri.parse(url));
        startActivity(i1);

    }

    public void open_games(View view) {

        String url = "https://www.edenwald.org/10-online-games-to-reduce-stress/";

        Intent i2 = new Intent(Intent.ACTION_VIEW);
        i2.setData(Uri.parse(url));
        startActivity(i2);
    }

    public void open_songs(View view) {

        String url = "https://www.wesmoss.com/news/these-10-songs-have-been-proven-to-relieve-stress-and-anxiety/";

        Intent i3 = new Intent(Intent.ACTION_VIEW);
        i3.setData(Uri.parse(url));
        startActivity(i3);
    }

    public void back_aero(View view) {
        Intent i = new Intent(Stress_level.this,Dashboard.class);
        startActivity(i);
    }
}