package org.team5401.froscoutingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MCAnswer extends AppCompatActivity {

    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcanswer);
    }

    public void readData() {
        //answer = viewAnswer.getText().toString();
    }

    public void submit (View view) {
        readData();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("answer", answer);
        finish();
    }
}
