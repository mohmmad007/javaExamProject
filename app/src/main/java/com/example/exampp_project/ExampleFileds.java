package com.example.exampp_project;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.Random;

public class ExampleFileds{
    public static final String KEY_IDq="QId";
    public static final String KEY_QUSET="Question";
    public static final String KEY_TRESPONSE="TResponse";
    public static final String KEY_FRESPONSE1="FResponse1";
    public static final String KEY_FRESPONSE2="FResponse2";
    public static final String KEY_FRESPONSE3="FResponse3";
    public static final String KEY_ID="Id";

    public static final String KEY_IDe="EId";
    public static final String KEY_NAME="name";
    public static final String KEY_STARTDATE="startDate";
    public static final String KEY_EXPIREDATE="expireDate";
    public static final String KEY_EXAMTIME="examTime";
    public static final String KEY_NUMQ="numQ";

    public static final String KEY_ANAME = "Aname";
    public static final String KEY_APASS = "Apassword";
    public static final String KEY_AID = "AID";

    static Random random = new Random();
    @SuppressLint("DefaultLocale")
    public static final String KEY_IDeR=  String.format("%08d", random.nextInt(100000000));


    private long Qid;
    private String question;
    private String Tresponse;
    private String Fresponse1;
    private String Fresponse2;
    private String Fresponse3;

    public String getUserAnswer() {
        return UserAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        UserAnswer = userAnswer;
    }

    private String UserAnswer;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    private int Id;

    public long getQid() {
        return Qid;
    }

    public void setQid(long qid) {
        Qid = qid;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTresponse() {
        return Tresponse;
    }

    public void setTresponse(String tresponse) {
        Tresponse = tresponse;
    }

    public String getFresponse1() {
        return Fresponse1;
    }

    public void setFresponse1(String fresponse1) {
        Fresponse1 = fresponse1;
    }

    public String getFresponse2() {
        return Fresponse2;
    }

    public void setFresponse2(String fresponse2) {
        Fresponse2 = fresponse2;
    }

    public String getFresponse3() {
        return Fresponse3;
    }

    public void setFresponse3(String fresponse3) {
        Fresponse3 = fresponse3;
    }
    @NonNull
    @Override
    public String toString() {
        return question;
    }
    private String Aname;
    private int Apassword;
    private int AId;


    public int getApassword() {
        return Apassword;
    }

    public void setApassword(int apassword) {
        Apassword = apassword;
    }

    public String getAname() {
        return Aname;
    }

    public void setAname(String aname) {
        Aname = aname;
    }

    public int getAId() {
        return AId;
    }

    public void setAId(int AId) {
        this.AId = AId;
    }
    private Integer AID;
    private String name;
    private String startDate;
    private String expireDate;
    private int examTime;
    private int numQ;

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getAID() {
        return AID;
    }

    public void setAID(Integer AID) {
        this.AID = AID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getExamTime() {
        return examTime;
    }

    public void setExamTime(int examTime) {
        this.examTime = examTime;
    }

    public int getNumQ() {
        return numQ;
    }

    public void setNumQ(int numQ) {
        this.numQ = numQ;
    }

    public static Random getRandom() {
        return random;
    }

    public static void setRandom(Random random) {
        ExampleFileds.random = random;
    }


}

