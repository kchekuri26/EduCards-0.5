package com.example.educards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.exitNewCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.saveQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = ((EditText) findViewById(R.id.questionField)).getText().toString();
                String answer = ((EditText) findViewById(R.id.answerField)).getText().toString();
                Intent data = new Intent(); // create a new Intent, this is where we will put our data
                data.putExtra("string1", question); // puts one string into the Intent, with the key as 'question'
                data.putExtra("string2", answer); // puts another string into the Intent, with the key as 'answer'
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes this activity and pass data to the original activity that launched this activity
            }
        });

    }
}
