package com.example.exampp_project;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterShowExam extends RecyclerView.Adapter<AdapterShowExam.MyViewHolder> {

    private final List<ExampleFileds> questions;
    Activity activity;
    ExampleSaverActivity dbHelper;

    public AdapterShowExam(List<ExampleFileds> questions, Activity activity) {
        if (questions == null) {
            this.questions = new ArrayList<>();
        } else {
            this.questions = questions;

        }
        this.activity = activity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_exam_activity, parent, false);

        return new MyViewHolder(itemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(questions.get(position));
    }

    @Override
    public int getItemCount() {
        Log.i("size", String.valueOf(questions.size()));
        return questions.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView examName;
        Button btnDetail, btnToEXam;
        AdapterShowExam adapter;
        ExampleFileds currentExam;

        MyViewHolder(@NonNull View itemView, AdapterShowExam adapter) {
            super(itemView);
            this.adapter = adapter;
            examName = itemView.findViewById(R.id.ExamNameAdapter);
            btnDetail = itemView.findViewById(R.id.examDetail);
            btnToEXam = itemView.findViewById(R.id.toExam);
        }

        public void bind(ExampleFileds examFieldViewActivity) {
            currentExam = examFieldViewActivity;
            Log.i("bind", currentExam.getName());
            examName.setText(currentExam.getName());
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setNegativeButton("برگشت", (dialogInterface, i) -> dialogInterface.dismiss());
                    builder.setMessage("نام آزمون:" + currentExam.getName() + "\n" + "تعداد سوالات:"
                            + currentExam.getNumQ() + "\n" + "زمان شروع آزمون:"
                            + currentExam.getStartDate() + "\n" + "زمان پایان آزمون:"
                            + currentExam.getExpireDate() + "\n" + "تایم آزمون:"
                            + currentExam.getExamTime());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            });
            btnToEXam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("نام آزمون:" + currentExam.getName() + "\n" + "تعداد سوالات:"
                            + currentExam.getNumQ() + "\n" + "زمان شروع آزمون:"
                            + currentExam.getStartDate() + "\n" + "زمان پایان آزمون:"
                            + currentExam.getExpireDate() + "\n" + "تایم آزمون:"
                            + currentExam.getExamTime());
                    builder.setNegativeButton("برگشت", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    });
                    builder.setPositiveButton("شروع آزمون", (dialogInterface, i) -> {
                        Intent intent = new Intent(activity, QuestionsActivityExam.class);
                        intent.putExtra("examName", currentExam.getName());
                        intent.putExtra("examId", currentExam.getId());
                        intent.putExtra("examTime", currentExam.getExamTime());
                        intent.putExtra("AId",currentExam.getAId());
                        intent.putExtra("aName",currentExam.getAname());
                        Log.i("Aid", String.valueOf(currentExam.getAId()));

                        activity.startActivity(intent);
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }


}

