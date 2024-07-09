package com.example.exampp_project;

public class ResponseField extends ExampleFileds {
    public static final String KEY_ID="Id";
    public static final String KEY_E_NAME="EName";
    public static final String KEY_A_NAME="AName";
    public static final String KEY_NUM_T_R="numTR";
    public static final String KEY_NUM_F_R="numFR";
    public static final String KEY_TIME="time";
    public static final String KEY_NUM_BLANK_R="numBlankR";
    public static final String KEY_PERCENTAGE_R="percentageR";



    private String EName;

    public String getAName() {
        return AName;
    }

    public void setAName(String AName) {
        this.AName = AName;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    public int getNumTR() {
        return numTR;
    }

    public void setNumTR(int numTR) {
        this.numTR = numTR;
    }

    public int getNumFR() {
        return numFR;
    }

    public void setNumFR(int numFR) {
        this.numFR = numFR;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNumBlankR() {
        return numBlankR;
    }

    public void setNumBlankR(int numBlankR) {
        this.numBlankR = numBlankR;
    }

    public double getPercentageR() {
        return percentageR;
    }

    public void setPercentageR(double percentageR) {
        this.percentageR = percentageR;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    private String AName;
    private int numTR;
    private int numFR;
    private int time;
    private int numBlankR;
    private double percentageR;
    private int Id;

}
