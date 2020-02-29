package com.example.educards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("string2");

            ((TextView) findViewById(R.id.flashcard_question)).setText(question);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(answer);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Shows the answer on the back of the card when clicked on the question.
        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
            }
        });

        //Shows the question on the card when clicked on the answer.
        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.showOptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.showOptions).setVisibility(View.INVISIBLE);
                findViewById(R.id.hideOptions).setVisibility(View.VISIBLE);
                findViewById(R.id.option_one).setVisibility(View.VISIBLE);
                findViewById(R.id.option_two).setVisibility(View.VISIBLE);
                findViewById(R.id.option_three).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.option_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.option_one).setBackgroundColor(getResources().getColor(R.color.colorAccent, null));
                findViewById(R.id.option_three).setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
            }
        });

        findViewById(R.id.option_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.option_two).setBackgroundColor(getResources().getColor(R.color.colorAccent, null));
                findViewById(R.id.option_three).setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
            }
        });

        findViewById(R.id.option_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.option_three).setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
            }
        });

        findViewById(R.id.hideOptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.showOptions).setVisibility(View.VISIBLE);
                findViewById(R.id.hideOptions).setVisibility(View.INVISIBLE);
                findViewById(R.id.option_one).setVisibility(View.INVISIBLE);
                findViewById(R.id.option_two).setVisibility(View.INVISIBLE);
                findViewById(R.id.option_three).setVisibility(View.INVISIBLE);
            }
        });


        findViewById(R.id.background).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.option_one).setBackground(getDrawable(R.drawable.option_background));
                findViewById(R.id.option_two).setBackground(getDrawable(R.drawable.option_background));
                findViewById(R.id.option_three).setBackground(getDrawable(R.drawable.option_background));

            }
        });


        findViewById(R.id.addNewCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });












    }
}
