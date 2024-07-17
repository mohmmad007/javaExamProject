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

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.MyViewHolder> {
    private List<ResponseField> resRankList;
    Activity activity;

    public AdapterRanking(List<ResponseField> resRankList, Activity activity) {
        Log.i("RankSQL","startAD");

        if (resRankList == null) {
            this.resRankList = new ArrayList<>();
        } else {
            this.resRankList = resRankList;

        }
        this.activity = activity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_rank, parent, false);

        return new MyViewHolder(itemView, this,activity);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(resRankList.get(position));

    }

    @Override
    public int getItemCount() {
        return resRankList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ResponseField currentQuestion;
        private AdapterRanking adapter;
        AppCompatTextView uNum,uName,uNumTR,
                uNumFR,uPercent;


        public void bind(ResponseField responseField) {
            currentQuestion = responseField;
            uNum.setText(String.valueOf(currentQuestion.getSetVirtualColumn()));
            uName.setText(String.valueOf(currentQuestion.getAname()));
            uNumTR.setText(String.valueOf(currentQuestion.getNumTR()));
            uNumFR.setText(String.valueOf(currentQuestion.getNumFR()));
            uPercent.setText(String.valueOf(currentQuestion.getPercentageR()));


        }
        MyViewHolder(View itemView, AdapterRanking adapter,Activity activity) {
            super(itemView);
            this.adapter = adapter;
            uNum = itemView.findViewById(R.id.uNum);
            uName = itemView.findViewById(R.id.uName);
            uNumTR = itemView.findViewById(R.id.uNumTR);
            uNumFR = itemView.findViewById(R.id.uNumFR);
            uPercent = itemView.findViewById(R.id.uPercent);

        }



    }
}
