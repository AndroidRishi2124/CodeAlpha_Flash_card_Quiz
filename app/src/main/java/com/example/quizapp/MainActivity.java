package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView questionText, scoreText;
    private RadioButton option1, option2, option3, option4;
    private RadioGroup optionGroup;
    private Button submitButton;

    private ArrayList<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
            // Initialize UI components
            questionText = findViewById(R.id.questionText);
            scoreText = findViewById(R.id.scoreText);
            option1 = findViewById(R.id.option1);
            option2 = findViewById(R.id.option2);
            option3 = findViewById(R.id.option3);
            option4 = findViewById(R.id.option4);
            optionGroup = findViewById(R.id.optionGroup);
            submitButton = findViewById(R.id.submitButton);

            // Initialize question list
            questionList = new ArrayList<>();
            loadQuestions();

            // Display the first question
            displayQuestion();

            // Set submit button onClick listener
            submitButton.setOnClickListener(view -> {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    displayQuestion();
                } else {
                    displayScore();
                }
            });
        }

        private void loadQuestions() {
            questionList.add(new Question("What is the capital of France?",
                    new String[]{"Berlin", "London", "Paris", "Madrid"}, 2));
            questionList.add(new Question("Who wrote 'Romeo and Juliet'?",
                    new String[]{"Mark Twain", "William Shakespeare", "Charles Dickens", "J.K. Rowling"}, 1));
            questionList.add(new Question("What is 2 + 2?",
                    new String[]{"3", "4", "5", "6"}, 1));
        }

        private void displayQuestion() {
            Question currentQuestion = questionList.get(currentQuestionIndex);
            questionText.setText(currentQuestion.getQuestion());
            option1.setText(currentQuestion.getOptions()[0]);
            option2.setText(currentQuestion.getOptions()[1]);
            option3.setText(currentQuestion.getOptions()[2]);
            option4.setText(currentQuestion.getOptions()[3]);
            optionGroup.clearCheck();
        }

        private void checkAnswer() {
            int selectedOptionIndex = optionGroup.indexOfChild(findViewById(optionGroup.getCheckedRadioButtonId()));
            if (selectedOptionIndex == questionList.get(currentQuestionIndex).getAnswerIndex()) {
                score++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            }
        }

        private void displayScore() {
            questionText.setVisibility(View.GONE);
            optionGroup.setVisibility(View.GONE);
            submitButton.setVisibility(View.GONE);

            scoreText.setVisibility(View.VISIBLE);
            scoreText.setText("Your Score: " + score + "/" + questionList.size());
        }
    }