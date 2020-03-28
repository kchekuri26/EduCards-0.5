package com.example.educards;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.plattysoft.leonids.ParticleSystem;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    CountDownTimer countDownTimer;


    private void startTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
    }


    // returns a random number between minNumber and maxNumber, inclusive.
    // for example, if i called getRandomNumber(1, 3), there's an equal chance of it returning either 1, 2, or 3.
    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) { // this 100 needs to match the 100 we used when we called startActivityForResult!

            String question = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("string2");
            String wrongOption1 = data.getExtras().getString("string3");
            String wrongOption2 = data.getExtras().getString("string4");

            flashcardDatabase.insertCard(new Flashcard(question, answer, wrongOption1, wrongOption2));
            allFlashcards = flashcardDatabase.getAllCards();

            ((TextView) findViewById(R.id.flashcard_question)).setText(question);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(answer);
            ((TextView) findViewById(R.id.option_one)).setText(wrongOption1);
            ((TextView) findViewById(R.id.option_two)).setText(wrongOption2);
            ((TextView) findViewById(R.id.option_three)).setText(answer);

            Snackbar.make(findViewById(R.id.flashcard_question),
                    "Card successfully created!",
                    Snackbar.LENGTH_SHORT)
                    .show();

        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            // grab the data passed from AddCardActivity
            // set the TextViews to show the EDITED question and answer
            String question = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("string2");
            String wrongOption1 = data.getExtras().getString("string3");
            String wrongOption2 = data.getExtras().getString("string4");

            ((TextView) findViewById(R.id.flashcard_question)).setText(question);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(answer);
            ((TextView) findViewById(R.id.option_one)).setText(wrongOption1);
            ((TextView) findViewById(R.id.option_two)).setText(wrongOption2);
            ((TextView) findViewById(R.id.option_three)).setText(answer);

            Snackbar.make(findViewById(R.id.flashcard_question),
                    "Card successfully edited!",
                    Snackbar.LENGTH_SHORT)
                    .show();

            Flashcard flashcardToEdit = allFlashcards.get(currentCardDisplayedIndex);
            flashcardToEdit.setQuestion(question);
            flashcardToEdit.setAnswer(answer);
            flashcardToEdit.setWrongAnswer1(wrongOption1);
            flashcardToEdit.setWrongAnswer2(wrongOption2);

            flashcardDatabase.updateCard(flashcardToEdit);
            allFlashcards = flashcardDatabase.getAllCards();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        countDownTimer = new CountDownTimer(16000, 1000) {
            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.timer)).setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
            }
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
            ((TextView) findViewById(R.id.option_one)).setText(allFlashcards.get(0).getWrongAnswer1());
            ((TextView) findViewById(R.id.option_two)).setText(allFlashcards.get(0).getWrongAnswer2());
            ((TextView) findViewById(R.id.option_three)).setText(allFlashcards.get(0).getAnswer());
        }

        //Shows the answer on the back of the card when clicked on the question.
        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View answerSideView = findViewById(R.id.flashcard_answer);
                View questionSideView = findViewById(R.id.flashcard_question);

                findViewById(R.id.flashcard_question).setCameraDistance(25000);
                findViewById(R.id.flashcard_answer).setCameraDistance(25000);
                findViewById(R.id.flashcard_question).animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                                        findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        findViewById(R.id.flashcard_answer).setRotationY(-90);
                                        findViewById(R.id.flashcard_answer).animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();
            }
        });

        //Shows the question on the card when clicked on the answer.
        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setCameraDistance(25000);
                findViewById(R.id.flashcard_answer).setCameraDistance(25000);
                findViewById(R.id.flashcard_answer).animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                                        findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        findViewById(R.id.flashcard_question).setRotationY(-90);
                                        findViewById(R.id.flashcard_question).animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();
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
                new ParticleSystem(MainActivity.this, 100, R.drawable.confetti, 3000)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(findViewById(R.id.option_three), 100);
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
                findViewById(R.id.flashcard_question).setCameraDistance(25000);
                findViewById(R.id.flashcard_answer).setCameraDistance(25000);
                findViewById(R.id.flashcard_answer).animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                                        findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        findViewById(R.id.flashcard_question).setRotationY(-90);
                                        findViewById(R.id.flashcard_question).animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();
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
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        findViewById(R.id.editCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.size()!=0){
                    Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                    intent.putExtra("stringKey1", ((TextView) findViewById(R.id.flashcard_question)).getText());
                    intent.putExtra("stringKey2", ((TextView) findViewById(R.id.flashcard_answer)).getText());
                    intent.putExtra("stringKey3", ((TextView) findViewById(R.id.option_one)).getText());
                    intent.putExtra("stringKey4", ((TextView) findViewById(R.id.option_two)).getText());
                    MainActivity.this.startActivityForResult(intent, 101);
                }
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // advance our pointer index so we can show the next card
                if (allFlashcards.size()-1>=0){
                    currentCardDisplayedIndex = getRandomNumber(0, allFlashcards.size()-1);
                }

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }
                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);
                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        findViewById(R.id.flashcard_question).startAnimation(rightInAnim);
                        findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);

                        if (allFlashcards.size()!=0){
                            // set the question and answer TextViews with data from the database
                            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                            ((TextView) findViewById(R.id.option_one)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                            ((TextView) findViewById(R.id.option_two)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                            ((TextView) findViewById(R.id.option_three)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        } else {
                            ((TextView) findViewById(R.id.flashcard_question)).setText(R.string.question1);
                            ((TextView) findViewById(R.id.flashcard_answer)).setText(R.string.answer1);
                            ((TextView) findViewById(R.id.option_one)).setText(R.string.option1);
                            ((TextView) findViewById(R.id.option_two)).setText(R.string.option2);
                            ((TextView) findViewById(R.id.option_three)).setText(R.string.answer1);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });
                countDownTimer.start();




            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();

                if (allFlashcards.size()!=0){
                    currentCardDisplayedIndex--;

                    if (currentCardDisplayedIndex>=0){
                        ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                        ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        ((TextView) findViewById(R.id.option_one)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                        ((TextView) findViewById(R.id.option_two)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                        ((TextView) findViewById(R.id.option_three)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    }
                } else {
                    ((TextView) findViewById(R.id.flashcard_question)).setText(R.string.question1);
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(R.string.answer1);
                    ((TextView) findViewById(R.id.option_one)).setText(R.string.option1);
                    ((TextView) findViewById(R.id.option_two)).setText(R.string.option2);
                    ((TextView) findViewById(R.id.option_three)).setText(R.string.answer1);
                }
            }
        });




    }
}
