package com.example.educards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        String s1 = getIntent().getStringExtra("stringKey1");
        String s2 = getIntent().getStringExtra("stringKey2");
        String s3 = getIntent().getStringExtra("stringKey3");
        String s4 = getIntent().getStringExtra("stringKey4");
        ((EditText) findViewById(R.id.questionField)).setText(s1);
        ((EditText) findViewById(R.id.answerField)).setText(s2);
        ((EditText) findViewById(R.id.wrong1)).setText(s3);
        ((EditText) findViewById(R.id.wrong2)).setText(s4);

        findViewById(R.id.exitNewCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.saveQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((EditText) findViewById(R.id.questionField)).getText().length()==0 || ((EditText) findViewById(R.id.answerField)).getText().length()==0 || ((EditText) findViewById(R.id.wrong1)).getText().length()==0 || ((EditText) findViewById(R.id.wrong2)).getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Enter All Fields!!", Toast.LENGTH_SHORT).show();
                } else {
                    String question = ((EditText) findViewById(R.id.questionField)).getText().toString();
                    String answer = ((EditText) findViewById(R.id.answerField)).getText().toString();
                    String wrong1 = ((EditText) findViewById(R.id.wrong1)).getText().toString();
                    String wrong2 = ((EditText) findViewById(R.id.wrong2)).getText().toString();
                    Intent data = new Intent(); // create a new Intent, this is where we will put our data
                    data.putExtra("string1", question); // puts one string into the Intent, with the key as 'question'
                    data.putExtra("string2", answer); // puts another string into the Intent, with the key as 'answer'
                    data.putExtra("string3", wrong1);
                    data.putExtra("string4", wrong2);
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes this activity and pass data to the original activity that launched this activity
                }
            }
        });

    }
}
