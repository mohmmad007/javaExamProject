package com.example.exampp_project;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterlistActivity extends RecyclerView.Adapter<AdapterlistActivity.MyViewHolder> {

    boolean exam;
    TextView isempty;
    private List<ExampleFileds> questions;
    Activity activity;

    public AdapterlistActivity(List<ExampleFileds> questions, boolean exam, Activity activity) {
        if (questions == null) {
            this.questions = new ArrayList<>();
        } else {
            this.questions = questions;

        }
        this.exam = exam;
        this.activity = activity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);

        return new MyViewHolder(itemView, this, exam);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(questions.get(position), exam);


    }

    @Override
    public int getItemCount() {
        isempty = activity.findViewById(R.id.isEmptyText);
        Log.i("azera0008", String.valueOf(questions.size()));
        int numE = questions.size();
        if (numE == 0) {
////        اینجا چون قرار نیست با لیست سوالا کاری داشته باشیم،سفارشی نکردم و فقط برای لیسترآزمونا درستش کردم
            isempty.setVisibility(View.VISIBLE);
            isempty.setText("هنوز آزمونی طراحی نکرده اید!");
        }
        return questions.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btnedit, btndel;
        AppCompatTextView tvName;
        ExampleFileds currentQuestion;

        private AdapterlistActivity adapter;

        public void bind(ExampleFileds questionsFieldViewActivity, boolean exam) {

            currentQuestion = questionsFieldViewActivity;
            if (exam) {
                tvName.setText(questionsFieldViewActivity.getName());

            } else {
                tvName.setText(questionsFieldViewActivity.getQuestion());
            }
        }

        MyViewHolder(@NonNull View itemView, AdapterlistActivity adapter, boolean exam) {
            super(itemView);
            this.adapter = adapter;
            itemView.setOnClickListener(this); // اضافه کردن OnClickListener به itemView
            tvName = itemView.findViewById(R.id.tvName);
            btndel = itemView.findViewById(R.id.delete);
            btnedit = itemView.findViewById(R.id.edit);


            btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (exam) {
                        Intent i = new Intent(v.getContext(), ViewDataActivity.class);
                        i.putExtra(ExampleFileds.KEY_AID, currentQuestion.getAId());
                        i.putExtra(ExampleFileds.KEY_NAME, currentQuestion.getName());
                        v.getContext().startActivity(i);


                    } else {
                        Intent i = new Intent(v.getContext(), QuestionsActivity.class);
                        i.putExtra(ExampleFileds.KEY_QUSET, currentQuestion.getQuestion());
                        i.putExtra(ExampleFileds.KEY_TRESPONSE, currentQuestion.getTresponse());
                        i.putExtra(ExampleFileds.KEY_FRESPONSE1, currentQuestion.getFresponse1());
                        i.putExtra(ExampleFileds.KEY_FRESPONSE2, currentQuestion.getFresponse2());
                        i.putExtra(ExampleFileds.KEY_FRESPONSE3, currentQuestion.getFresponse3());
                        i.putExtra(ExampleFileds.KEY_IDq, currentQuestion.getQid());
                        v.getContext().startActivity(i);
                    }
                }
            });
            btndel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (exam) {
                        delete(-1, v, adapter, ExampleFileds.KEY_AID, ExampleFileds.KEY_NAME, true, "tb_examplesbase", currentQuestion.getAId(), currentQuestion.getName());
                        Log.i("azeradel", String.valueOf(currentQuestion.getAId()));
                    } else {

                        delete(currentQuestion.getQid(), v, adapter, ExampleFileds.KEY_IDq, "NOT_FOUND", false, "tb_examples", -1, "NOT_FOUND");
                    }

                }
            });

        }


        @Override
        public void onClick(View v) {

            Log.i("clicker", "Clicked Item: " + String.valueOf(itemView.findViewById(R.id.edit).getId()));
            Log.i("clicker", "Clicked Item2: " + String.valueOf(v.getId()));
            Log.i("clicker", "Clicked Item3: " + String.valueOf(itemView.getId()));

        }

        private void delete(long IDQ, View v, AdapterlistActivity adapter, String column, String column2, boolean exam, String table, Integer IDE, String Ename) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("آیا میخواهید این را حذف نمایید؟");
            builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ExampleSaverActivity Edel;
                    List<ExampleFileds> exampleList;
                    Log.i("lists", "aaa1");
                    Edel = new ExampleSaverActivity(builder.getContext());
                    if (exam) {
                        Log.i("azera", "aaa2");
                        Log.i("azera", String.valueOf(IDQ));
                        Log.i("azera", table);
                        Log.i("azera", column);
                        Edel.deleteExampleFields(IDQ, table, column, column2, IDE, Ename);
                        exampleList = Edel.getAuthorListExams(IDE);

                    } else {

                        exampleList = Edel.getAllQuestions(-1, "NOT_FOUND");
                        Edel.deleteExampleFields(IDQ, table, column, column2, IDE, Ename);
                    }
//                    Log.i("lists","saa"+ String.valueOf(exampleList.get(0).getQuestion()));
//                    question= (AppCompatTextView) exampleList;
                    Log.i("lists", "as" + exampleList.getClass().getSimpleName());
//                    ExampleSaverActivity Edel=new ExampleSaverActivity(v.getContext());
//                    SQLiteDatabase db=Edel.getWritableDatabase();
                    adapter.updateQuestions(exampleList);
                    adapter.notifyDataSetChanged();
//                    Intent intent = new Intent(v.getContext(),ViewDataActivity.class);
//                    v.getContext().startActivity(intent);

                    Log.i("dialog", "showed");

                }
            });
            builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateQuestions(List<ExampleFileds> newQuestions) {
        Log.i("lists", "aaa4");
        questions = newQuestions;
        Log.i("lists", "aaa5");
        notifyDataSetChanged();
    }
}
