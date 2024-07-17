package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterShowHistory extends RecyclerView.Adapter<AdapterShowHistory.MyViewHolder> {
    private List<ResponseField> responseList;
    Activity activity;

    public AdapterShowHistory(List<ResponseField> responseList, Activity activity) {
        if (responseList == null) {
            this.responseList = new ArrayList<>();
        } else {
            this.responseList = responseList;

        }
        this.activity = activity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rcycler_show, parent, false);

        return new MyViewHolder(itemView, this,activity);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(responseList.get(position));

    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ResponseField currentQuestion;
        private AdapterShowHistory adapter;
        AppCompatTextView itemHistoryNameExam,itemHistoryFQ,itemHistoryTQ,
                itemHistoryTimeExam,itemHistoryAllExam;
        AppCompatButton ReExam,vecHistoryDetailExam;

        public void bind(ResponseField responseField) {
            currentQuestion = responseField;
            Log.i("700", "in adapter=>>"+String.valueOf(responseField.getNumQ()));
            itemHistoryNameExam.setText(currentQuestion.getName());
            itemHistoryAllExam.setText(String.valueOf(currentQuestion.getNumQ()));
            itemHistoryTQ.setText(String.valueOf(currentQuestion.getNumTR()));
            itemHistoryFQ.setText(String.valueOf(currentQuestion.getNumFR()));
            itemHistoryTimeExam.setText(String.valueOf(currentQuestion.getTime()));


        }
        MyViewHolder(View itemView, AdapterShowHistory adapter,Activity activity) {
            super(itemView);
            this.adapter = adapter;
            itemHistoryNameExam = itemView.findViewById(R.id.itemHistoryNameExam);
            itemHistoryAllExam = itemView.findViewById(R.id.itemHistoryAllExam);
            itemHistoryTQ = itemView.findViewById(R.id.itemHistoryTQ);
            itemHistoryFQ = itemView.findViewById(R.id.itemHistoryFQ);
            itemHistoryTimeExam = itemView.findViewById(R.id.itemHistoryTimeExam);
            vecHistoryDetailExam=itemView.findViewById(R.id.vecHistoryDetailExam);
            ReExam=itemView.findViewById(R.id.ReExam);
            vecHistoryDetailExam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(activity,DetailExamHistoryActivity.class);
                    intent.putExtra("numQ",currentQuestion.getNumQ());
                    intent.putExtra("Time",currentQuestion.getExamTime());
                    intent.putExtra("author",currentQuestion.getAname());
                    intent.putExtra("TR",currentQuestion.getNumTR());
                    intent.putExtra("FR",currentQuestion.getNumFR());
                    intent.putExtra("BR",currentQuestion.getNumBlankR());
                    intent.putExtra("TimeEnd",currentQuestion.getTime());
//                    intent.putExtra("validationQ",currentQuestion.getValidationQ());
                    Log.i("9log00","adapter");
//                    Log.i("9log00", String.valueOf(currentQuestion.getNumQ()));
//                    Log.i("9log00", String.valueOf(currentQuestion.getExamTime()));
//                    Log.i("9log00", String.valueOf(currentQuestion.getAname()));
//                    Log.i("9log00", String.valueOf(currentQuestion.getNumTR()));
//                    Log.i("9log00", String.valueOf(currentQuestion.getNumFR()));
//                    Log.i("9log00", String.valueOf(currentQuestion.getNumBlankR()));
//                    Log.i("9log00", String.valueOf(currentQuestion.getTime()));

                    activity.startActivity(intent);
                    activity.finish();
                }
            });
            ReExam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reexam(v,String.valueOf(currentQuestion.getId()));
                }
            });
        }
        private void reexam(View view, String EId ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("نام آزمون:" + currentQuestion.getName() + "\n" + "تعداد سوالات:"
                    + currentQuestion.getNumQ() + "\n" + "زمان شروع آزمون:"
                    + currentQuestion.getStartDate() + "\n" + "زمان پایان آزمون:"
                    + currentQuestion.getExpireDate() + "\n" + "تایم آزمون:"
                    + currentQuestion.getExamTime());
            builder.setNegativeButton("برگشت", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            builder.setPositiveButton("شروع آزمون", (dialogInterface, i) -> {
                Intent intent = new Intent(view.getContext(), QuestionsActivityExam.class);
                intent.putExtra("examName", currentQuestion.getName());
                intent.putExtra("examId", currentQuestion.getId());
                intent.putExtra("examTime", currentQuestion.getExamTime());
                intent.putExtra("AId",currentQuestion.getAId());
                intent.putExtra("aName",currentQuestion.getAname());
                Log.i("Aid", String.valueOf(currentQuestion.getAId()));

                view.getContext().startActivity(intent);
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }


    }
}
