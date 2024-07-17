package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuestionsActivityExam extends AppCompatActivity  {
    AppCompatButton btnNext,btnBack,btnSabt;
    AppCompatTextView question,ShowTimer;
    RadioGroup radioGroup;
    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    RadioButton answer4;
    ArrayList<ExampleFileds> questions;
    ExampleSaverActivity dbHelper;
    String UserAnswer;
    int counter = 0;
    int timer,aId;
    int examId;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_questions);
        question=findViewById(R.id.questionE);
        radioGroup = findViewById(R.id.answer);
        answer1 = findViewById(R.id.TRE);
        answer2 =findViewById(R.id.FR1E);
        answer3 =findViewById(R.id.FR2E);
        answer4 = findViewById(R.id.FR3E);
        dbHelper = new ExampleSaverActivity(this);
        btnBack=findViewById(R.id.btnback);
        btnSabt=findViewById(R.id.btnsabt);
        btnNext = findViewById(R.id.btnnext);
        ShowTimer = findViewById(R.id.timer);
        if (counter <= 0 ){
            btnBack.setVisibility(View.GONE);
        }

        btnSabt.setVisibility(View.GONE);

        Intent intent=getIntent();
        if(intent.hasExtra("examName")) {
            examId = intent.getIntExtra("examId",-1);
            aId = intent.getIntExtra("AId",-1);
            questions = (ArrayList<ExampleFileds>) dbHelper.getAllQuestions(intent.getIntExtra("AId", -1), intent.getStringExtra("examName"));
            Collections.shuffle(questions);
            Toast.makeText(getApplicationContext(), counter + " of " + questions.size(),Toast.LENGTH_SHORT).show();
        }
        timer = intent.getIntExtra("examTime", 0);
        long totalTimeInMillis = timer * 60 * 1000; // Convert minutes to milliseconds
        CountDownTimer countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / (60 * 1000);
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                ShowTimer.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                setAnswer();
                dbHelper.insertAnswers(questions,timer,examId, aId);
                showLastExamResult();
//                QuestionsActivityExam.this.finish();
                Log.i("true_response_db", "true: "+ String.valueOf(dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumTR()) + "\nfalse: " + String.valueOf(dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumFR()) + "\nblanks: " + String.valueOf(dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumBlankR()));
            }
        };
        countDownTimer.start();
        try {
            btnNext.setOnClickListener(v -> {
                setAnswer();
                counter++;
                Toast.makeText(getApplicationContext(), counter + " of " + questions.size(),Toast.LENGTH_SHORT).show();
                if (counter == questions.size() - 1){
                    btnNext.setVisibility(View.GONE);
                }
                if (counter == questions.size() - 1){
                    btnNext.setVisibility(View.GONE);
                    btnSabt.setVisibility(View.VISIBLE);
                }
                else {
                    btnSabt.setVisibility(View.GONE);
                }
                if (counter > 0 ){
                    btnBack.setVisibility(View.VISIBLE);
                }
                setTexts();
                UserAnswer = questions.get(counter).getUserAnswer();
                if (UserAnswer != null) {
                    UserAnswer = UserAnswer.trim();
                }
                checkAnswer(UserAnswer);
            });
            btnBack.setOnClickListener(v -> {
                setAnswer();
                counter--;
                if (counter <= 0 ){
                    btnBack.setVisibility(View.GONE);
                }
                else {
                    btnBack.setVisibility(View.VISIBLE);
                }
                if (counter <= questions.size() - 1){
                    btnNext.setVisibility(View.VISIBLE);
                }
                setTexts();
                UserAnswer = questions.get(counter).getUserAnswer();
                if (UserAnswer != null) {
                    UserAnswer = UserAnswer.trim();
                }
                checkAnswer(UserAnswer);
            });
            btnSabt.setOnClickListener(v -> {
                setAnswer();
                dbHelper.insertAnswers(questions, timer, examId, aId);
                showLastExamResult();
//                this.finish();
                countDownTimer.cancel();
                Log.i("true_response_db", "true: "+ String.valueOf(dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumTR()) + "\nfalse: " + String.valueOf(dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumFR()) + "\nblanks: " + String.valueOf(dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumBlankR()));
            });
        } catch (Exception e) {
            Log.i("Exception", String.valueOf(e.getCause()));
        }
        setTexts();

    }

    public void setTexts(){
        String TRE = questions.get(counter).getTresponse();
        String FR1E = questions.get(counter).getFresponse1();
        String FR2E = questions.get(counter).getFresponse2();
        String FR3E = questions.get(counter).getFresponse3();

        // Create a list to hold the response options and shuffle them
        List<String> responseOptions = new ArrayList<>();
        responseOptions.add(TRE);
        responseOptions.add(FR1E);
        responseOptions.add(FR2E);
        responseOptions.add(FR3E);
        Collections.shuffle(responseOptions);

        // Set the shuffled responses to the TextViews
        question.setText(questions.get(counter).getQuestion());
        answer1.setText(responseOptions.get(0));  // Shuffled response
        answer2.setText(responseOptions.get(1));  // Shuffled response
        answer3.setText(responseOptions.get(2));  // Shuffled response
        answer4.setText(responseOptions.get(3));  // Shuffled response
    }

    public void checkAnswer(String UserAnswer){
        if (UserAnswer == null){
            radioGroup.clearCheck();  // Clear the checked state of all radio buttons in the RadioGroup
        } else {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                View view = radioGroup.getChildAt(i);
                if (view instanceof RadioButton) {
                    RadioButton radioButton = (RadioButton) view;
                    if (radioButton.getText().toString().equals(UserAnswer)) {
                        radioButton.setChecked(true);  // Set the checked state to true for the matching radio button
                        break;
                    }
                }
            }
        }
    }
    public void setAnswer() {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            String selectedAnswer = selectedRadioButton.getText().toString();
            questions.get(counter).setUserAnswer(selectedAnswer);
        }else {
            questions.get(counter).setUserAnswer(null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setAnswer();
        dbHelper.insertAnswers(questions,timer,examId, aId);
        showLastExamResult();
//        QuestionsActivityExam.this.finish();
        Toast.makeText(getApplicationContext(),"آزمون به پایان رسید",Toast.LENGTH_SHORT).show();
        Log.i("true_response_db", "true: "+ String.valueOf(dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumTR()) + "\nfalse: " + String.valueOf(dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumFR()) + "\nblanks: " + String.valueOf(dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumBlankR()));
    }

    public void showLastExamResult(){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivityExam.this);
        builder.setTitle("Last Exam Result");

        // Retrieve the last exam history object

        // Build the message for the dialog
        String message = "True: " + dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumTR() + "\n" +
                "False: " + dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumFR() + "\n" +
                "Blanks: " + dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getNumBlankR() + "\n" +
                "Percentage" + dbHelper.getExamHistory(aId).get(dbHelper.getExamHistory(aId).size() - 1).getPercentageR()+'%';
        builder.setMessage(message);

        // Add a button to close the dialog
        builder.setPositiveButton("OK", (dialog, which) -> {dialog.dismiss();
        this.finish();
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

