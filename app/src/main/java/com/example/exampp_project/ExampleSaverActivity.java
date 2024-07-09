package com.example.exampp_project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExampleSaverActivity extends SQLiteOpenHelper {
    private static final String LOGTAG = "database";

    private static final String DB_NAME = "examples_db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_EXAMPLE = "tb_examples";

    private static final String CMD = "CREATE TABLE IF NOT EXISTS '" + TABLE_EXAMPLE + "' ('" +
            ExampleFileds.KEY_IDq + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '" +
            ExampleFileds.KEY_QUSET + "' TEXT, '" +
            ExampleFileds.KEY_TRESPONSE + "' TEXT, '" +
            ExampleFileds.KEY_FRESPONSE1 + "' TEXT, '" +
            ExampleFileds.KEY_FRESPONSE2 + "' TEXT, '" +
            ExampleFileds.KEY_FRESPONSE3 + "' TEXT,'" +
            ExampleFileds.KEY_IDe + "'TEXT" +
            ")";


    private static final String TABLE_EXAMPLES = "tb_examplesbase";

    private static final String CMDEXAM = "CREATE TABLE IF NOT EXISTS '" + TABLE_EXAMPLES + "' ('" +
            ExampleFileds.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '" +
            ExampleFileds.KEY_NAME + "' TEXT, '" +
            ExampleFileds.KEY_STARTDATE + "' DATE, '" +
            ExampleFileds.KEY_EXPIREDATE + "' DATE, '" +
            ExampleFileds.KEY_EXAMTIME + "' INTEGER, '" +
            ExampleFileds.KEY_NUMQ + "' INTEGER, '" +
            ExampleFileds.KEY_AID + "' TEXT " +
            ")";
    private static final String TABLE_AUTHOR = "tb_authors";
    private static final String CMDAUTHOR = "CREATE TABLE IF NOT EXISTS '" + TABLE_AUTHOR + "' ('" +
            ExampleFileds.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '" +
            ExampleFileds.KEY_ANAME + "' TEXT, '" +
            ExampleFileds.KEY_APASS + "' INTEGER" +

            ")";
    private static final String TABLE_RESPONSES = "tb_response";
    private static final String CMDRES = "CREATE TABLE IF NOT EXISTS '" + TABLE_RESPONSES + "' ('" +
            ResponseField.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '" +
            ResponseField.KEY_A_NAME + "' TEXT, '" +
            ResponseField.KEY_E_NAME + "' TEXT, '" +
            ResponseField.KEY_NUM_T_R + "' INTEGER, '" +
            ResponseField.KEY_NUM_F_R + "' INTEGER, '" +
            ResponseField.KEY_NUM_BLANK_R + "' INTEGER, '" +
            ResponseField.KEY_TIME + "' INTEGER, '" +
            ResponseField.KEY_PERCENTAGE_R + "' DOUBLE" +

            ")";

    public ExampleSaverActivity(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.i(LOGTAG, String.valueOf(ExampleFileds.KEY_IDq));

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOGTAG, "table created.");
        db.execSQL(CMDEXAM);
        db.execSQL(CMD);
        db.execSQL(CMDAUTHOR);
        db.execSQL(CMDRES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAMPLE);
        Log.i(LOGTAG, "table dropped.");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAMPLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESPONSES);

    }

    @SuppressLint("Range")
    public void insertExampleFileds(Editable question, Editable TR, Editable FR1, Editable FR2, Editable FR3) {
        Log.i("database", String.valueOf(question + ";" + TR + ";" + FR1 + ";" + FR2 + ";" + FR3 + ";"));
        //
        Log.i(LOGTAG, "start inserting");
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorID = db.rawQuery("SELECT MAX(Id) AS maxId FROM tb_examplesbase", null);


        //        Cursor cursorID = db.rawQuery("SELECT * FROM tb_examplesbase WHERE Id = (SELECT MAX(Id) FROM tb_examplesbase)", null);
        Log.i("database", "khkhkh :");
        Log.i("database", "khkhkh2 :" + cursorID.toString());
//        values.put(ExampleFileds.KEY_IDq,numQ);
        values.put(ExampleFileds.KEY_QUSET, String.valueOf(question));
        values.put(ExampleFileds.KEY_TRESPONSE, String.valueOf(TR));
        values.put(ExampleFileds.KEY_FRESPONSE1, String.valueOf(FR1));
        values.put(ExampleFileds.KEY_FRESPONSE2, String.valueOf(FR2));
        values.put(ExampleFileds.KEY_FRESPONSE3, String.valueOf(FR3));

        long maxId = 0;
        if (cursorID.moveToFirst()) {
            maxId = cursorID.getLong(cursorID.getColumnIndex("maxId"));
        }
        cursorID.close();
        values.put(ExampleFileds.KEY_IDe, maxId);


        long insertID = db.insert(TABLE_EXAMPLE, null, values);
        Log.i("database", "data inserted by id" + insertID);
        if (db.isOpen()) db.close();

    }

    @SuppressLint("Range")

    public List<ExampleFileds> getAllQuestions(int AID, String ename) {

        Log.i("azera007", String.valueOf(AID));
        SQLiteDatabase db = getReadableDatabase();
        List<ExampleFileds> exampleList = new ArrayList<>();
//        Cursor cursor =db.rawQuery("SELECT * FROM '"+TABLE_EXAMPLE+"'",null);
        Cursor cursor;
        if (AID != -1) {
            cursor = db.rawQuery("SELECT * FROM tb_examples WHERE EId = (SELECT Id FROM tb_examplesbase WHERE name = ? AND AID =? )", new String[]{ename, String.valueOf(AID)});

        } else {

            cursor = db.rawQuery("SELECT * FROM tb_examples WHERE EId = (SELECT MAX(Id) FROM tb_examplesbase)", null);
        }
        if (cursor.moveToFirst()) {
            do {
                ExampleFileds example = new ExampleFileds();
                example.setQuestion(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_QUSET)));
                Log.i(LOGTAG, "set query1");
                example.setQid(Long.parseLong(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_IDq))));
                example.setTresponse(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_TRESPONSE)));
                Log.i(LOGTAG, "set query2");
                example.setFresponse1(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_FRESPONSE1)));
                example.setFresponse2(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_FRESPONSE2)));
                Log.i(LOGTAG, "set query3");
                example.setFresponse3(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_FRESPONSE3)));
                Log.i(LOGTAG, "set query4");
//                Log.i(LOGTAG, String.valueOf(cursor.getLong(cursor.getColumnIndex("productID"))));
//                flower.setProductId(cursor.getLong(cursor.getColumnIndex("productId")));
//                Log.i(LOGTAG, "set query2");
//                flower.setCategory(cursor.getString(cursor.getColumnIndex(Flower.KEY_CAT)));
//                flower.setPrice(cursor.getDouble(cursor.getColumnIndex(Flower.KEY_PRICE)));
//                flower.setPhoto(cursor.getString(cursor.getColumnIndex(Flower.KEY_PHOTO)));
//                flower.setInstructions(cursor.getString(cursor.getColumnIndex(Flower.KEY_INSTR)));
                exampleList.add(example);
                Log.i(LOGTAG, "ablah");

            } while (cursor.moveToNext());
        }
        cursor.close();
        if (db.isOpen()) db.close();
        return exampleList;
    }

    @SuppressLint("Range")
    public void insertExampleBaseFields(String numQ, String Sname, String SstartTime, String SexpireTime, String SexamTime, Integer AID) {
//        Log.i("database",String.valueOf(question+";"+TR+";"+FR1+";"+FR2+";"+FR3+";"));
        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursorID = db.rawQuery("SELECT Id AS AId FROM tb_authors WHERE Id = ? ", new String[]{String.valueOf(AID)} );

        Log.i(LOGTAG, "start inserting");
        ContentValues values = new ContentValues();
        int InumQ= Integer.parseInt(numQ);
        values.put(ExampleFileds.KEY_NUMQ, InumQ);
        Log.i(LOGTAG, "start inserting1");
        values.put(ExampleFileds.KEY_NAME, String.valueOf(Sname));
        values.put(ExampleFileds.KEY_STARTDATE, String.valueOf(SstartTime));
        Log.i(LOGTAG, "start inserting2");
        Log.i(LOGTAG, "start inserting3");
        values.put(ExampleFileds.KEY_EXAMTIME, Integer.valueOf(SexamTime));
        values.put(ExampleFileds.KEY_EXPIREDATE, String.valueOf(SexpireTime));
//        values.put(ExampleFileds.KEY_IDe,String.valueOf(ideR));
        Log.i("yakhoda", "avall: " + AID);
        Log.i("yakhoda", "avall: ");

//        long AId = 1;
//        if (cursorID.moveToFirst()) {
////            AId = cursorID.getLong(cursorID.getColumnIndex("AId"));
//            Log.i("yakhoda","avall");
//            Log.i("yakhoda", String.valueOf(cursorID.getInt(cursorID.getColumnIndex("Id"))));
//        }
//        cursorID.close();
        values.put(ExampleFileds.KEY_AID, AID);
        long insertID = db.insert(TABLE_EXAMPLES, null, values);
        Log.i("database", "data inserted by id" + insertID);
        if (db.isOpen()) db.close();

    }

    public void updateExampleFields(long IdQ, String question, String TR, String FR1, String FR2, String FR3) {
        Log.i("database", "c.c.:3" + String.valueOf(question));

        ContentValues values = new ContentValues();
        values.put(ExampleFileds.KEY_QUSET, question);
        values.put(ExampleFileds.KEY_TRESPONSE, TR);
        values.put(ExampleFileds.KEY_FRESPONSE1, FR1);
        values.put(ExampleFileds.KEY_FRESPONSE2, FR2);
        values.put(ExampleFileds.KEY_FRESPONSE3, FR3);
        SQLiteDatabase db = getWritableDatabase();
        long update = db.update(TABLE_EXAMPLE, values, ExampleFileds.KEY_IDq + "=?", new String[]{String.valueOf(IdQ)});
        if (db.isOpen()) db.close();
        Log.i("database", "dada" + IdQ);


    }

    public void deleteExampleFields(long IdQ, String database, String column, String column2, Integer IdE, String Ename) {
        SQLiteDatabase db = getWritableDatabase();
        if (IdE == -1) {
            int count = db.delete(database,
                    column + " = ?", new String[]{String.valueOf(IdQ)});
            Log.i(LOGTAG, count + " rows deleted.");
        } else if (IdQ == -1) {
            int count = db.delete(database,
                    column + " = ? AND " + column2 + " = ?", new String[]{String.valueOf(IdE), Ename});
        }
        if (db.isOpen()) db.close();

    }

    @SuppressLint("Range")
    public String getExamName() {
        SQLiteDatabase db = getReadableDatabase();
        String ExamName;
        Cursor cursor = db.rawQuery("SELECT * FROM tb_examplesbase WHERE Id = (SELECT MAX(Id) FROM tb_examplesbase)", null);

        String examName = "";

        if (cursor.moveToFirst()) {
            examName = cursor.getString(cursor.getColumnIndex("name"));

        }
        Log.i("namee: ", examName);
        cursor.close();
        if (db.isOpen()) db.close();
        return examName;
    }

    @SuppressLint("Range")
    public boolean ISAuthor(String aname, String apass) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_authors WHERE Aname = ? ", new String[]{aname});
        boolean recordExists = cursor.moveToFirst();
        cursor.close();
        if (db.isOpen()) db.close();
        return recordExists;
    }

    public void insertAuthor(String aname, String apass) {
        ContentValues values = new ContentValues();


        values.put(ExampleFileds.KEY_ANAME, String.valueOf(aname));
        values.put(ExampleFileds.KEY_APASS, String.valueOf(apass));
        SQLiteDatabase db = getWritableDatabase();
        long insertID = db.insert(TABLE_AUTHOR, null, values);
        Log.i("database", "inserted user" + aname);
        if (db.isOpen()) db.close();
    }

    @SuppressLint("Range")
    public List<ExampleFileds> getuser(String aname, String apass) {
        Log.i("xcy", "loger2");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_authors WHERE Aname = ? AND Apassword = ? ", new String[]{aname, apass});
        List<ExampleFileds> authorList = new ArrayList<>();
        Log.i("xcy", "loger3");
        if (cursor.moveToFirst()) {
            Log.i("xcy", "loger4");
            Log.i("xcy", apass);

            Log.i("xcy", "loger4");

            do {
                ExampleFileds author = new ExampleFileds();
                author.setAname(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_ANAME)));
                author.setAId((int) Long.parseLong(cursor.getString(cursor.getColumnIndex("Id"))));
                author.setApassword((int) Long.parseLong(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_APASS))));
                authorList.add(author);
            } while (cursor.moveToNext());
        } else {
            authorList = null;
        }
        Log.i("xcy", "loger5");

        cursor.close();
        if (db.isOpen()) db.close();
        return authorList;
    }

    @SuppressLint("Range")
    public List<ExampleFileds> getAuthorListExams(Integer aid) {
        Log.i("xcy", "selecter1");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_examplesbase WHERE AID = ? ", new String[]{String.valueOf(aid)});
        List<ExampleFileds> authorList = new ArrayList<>();
        Log.i("xcy", "selecter2");
        if (cursor.moveToFirst()) {
            Log.i("xcy", "selecter3");

            Log.i("xcy", "selecter4");

            do {
                ExampleFileds author = new ExampleFileds();

                author.setAId((int) Long.parseLong(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_AID))));
                author.setName(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_NAME)));
                author.setExpireDate(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_EXPIREDATE)));
                author.setStartDate(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_STARTDATE)));
                author.setExamTime((int) Long.parseLong(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_EXAMTIME))));
                author.setNumQ((int) Long.parseLong(cursor.getString(cursor.getColumnIndex(ExampleFileds.KEY_NUMQ))));
                authorList.add(author);
            } while (cursor.moveToNext());
        } else {
            authorList = null;
            Log.i("xcy", "selecter5");

        }
        Log.i("xcy", "selecter6");

        cursor.close();
        if (db.isOpen()) db.close();
        return authorList;

    }

    public boolean hasADD(String sname, Integer aId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_examplesbase WHERE AID = ? AND name = ? ", new String[]{String.valueOf(aId), String.valueOf(sname)});

        boolean ExistRec = cursor.moveToFirst();

        cursor.close();
        if (db.isOpen()) db.close();
        return ExistRec;

    }

    public boolean editac(String Aname, String Apass, int pAid) {
        boolean isEdit = false;
        SQLiteDatabase db = getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put("Aname", Aname);
        values.put("Apassword", Apass);

        int rowsAffected = db.update("tb_authors", values, "Id = ? ", new String[]{String.valueOf(pAid)});

        if (rowsAffected > 0) {
            isEdit = true;
        }

        db.close();
        return isEdit;
    }


    public boolean acDel(String aname, int apass, String enterPass) {
        SQLiteDatabase db = getWritableDatabase();

        int count = db.delete("tb_authors",
                "Aname" + " = ? AND Apassword" + " = ?", new String[]{aname, enterPass});
        if (db.isOpen()) db.close();
        if (count != 0) {
            Log.i(LOGTAG, count + " rows deleted.");
            return true;
        } else return false;
    }

    @SuppressLint("Range")
    public HashMap<String, Double> selectNumResult(Integer pAId) {
        SQLiteDatabase db = getReadableDatabase();
        List<ResponseField> detailList = new ArrayList<>();
//    ContentValues values = new ContentValues();
//    values.put(ResponseField.KEY_E_NAME, "1");
//    values.put(ResponseField.KEY_A_NAME, "24");
//    values.put(ResponseField.KEY_NUM_T_R, 41);
//    values.put(ResponseField.KEY_NUM_F_R,12 );
//    values.put(ResponseField.KEY_NUM_BLANK_R,9 );
//    values.put(ResponseField.KEY_PERCENTAGE_R,91 );
//    values.put(ResponseField.KEY_NUM_F_R,12 );
//    values.put(ResponseField.KEY_TIME,10 );
//    db.insert(TABLE_RESPONSES,null,values);
//        ContentValues values1 = new ContentValues();
//        values1.put(ResponseField.KEY_E_NAME, "1");
//        values1.put(ResponseField.KEY_A_NAME, "24");
//        values1.put(ResponseField.KEY_NUM_T_R, 41);
//        values1.put(ResponseField.KEY_NUM_F_R,12 );
//        values1.put(ResponseField.KEY_NUM_BLANK_R,9 );
//        values1.put(ResponseField.KEY_PERCENTAGE_R,32 );
//        values1.put(ResponseField.KEY_NUM_F_R,12 );
//        values1.put(ResponseField.KEY_TIME,10 );
//        db.insert(TABLE_RESPONSES,null,values1);
        HashMap<String, Double> resultList = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RESPONSES + " WHERE " + ResponseField.KEY_A_NAME + " = ? ", new String[]{String.valueOf(pAId)});
//    Cursor cursorNumE = db.rawQuery("SELECT COUNT(Id) FROM "+TABLE_RESPONSES +" WHERE "+ResponseField.KEY_A_NAME +" = ? ", new String[]{String.valueOf(pAId)});
//            int countNumE = cursorNumE.getCount();

        if (cursor.moveToFirst()) {
            do {
                ResponseField detail = new ResponseField();
                detail.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ResponseField.KEY_ID))));
                detail.setEName(cursor.getString(cursor.getColumnIndex(ResponseField.KEY_E_NAME)));
                detail.setAName(cursor.getString(cursor.getColumnIndex(ResponseField.KEY_A_NAME)));
                detail.setNumTR(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ResponseField.KEY_NUM_T_R))));
                detail.setNumFR(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ResponseField.KEY_NUM_F_R))));
                detail.setNumBlankR(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ResponseField.KEY_NUM_BLANK_R))));
                detail.setTime(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ResponseField.KEY_TIME))));
                detail.setPercentageR(Double.parseDouble(cursor.getString(cursor.getColumnIndex(ResponseField.KEY_PERCENTAGE_R))));
                detailList.add(detail);
            } while (cursor.moveToNext());


        }
        if (detailList != null) {
            int countNumE = 0;
            int countNumTR = 0;
            int countNumFR = 0;
            int countNumBR = 0;
            int countNumTime = 0;
            double countPercentageTR = 0;
            double countPercentageFR = 0;
            for (ResponseField response : detailList) {
                countNumE++;
                countNumTR += response.getNumTR();
                countNumFR += response.getNumFR();
                countNumBR += response.getNumBlankR();
                countNumTime += response.getTime();
            }

            countPercentageTR = (double) countNumTR / ((double) (countNumFR + countNumBR + countNumTR) / 100);
            countPercentageFR = (double) countNumFR / ((double) (countNumFR + countNumBR + countNumTR) / 100);

            resultList.put("countNumE", Double.valueOf(countNumE));
            resultList.put("countNumTR", Double.valueOf(countNumTR));
            resultList.put("countNumFR", Double.valueOf(countNumFR));
            resultList.put("countNumBR", Double.valueOf(countNumBR));
            resultList.put("countNumTime", Double.valueOf(countNumTime));
            resultList.put("countPercentageTR", countPercentageTR);
            resultList.put("countPercentageFR", countPercentageFR);
        }
        return resultList;
    }


//    public List<ٍExampleFileds> getExamHistory(int AId) {
//
//
//        SQLiteDatabase db = getReadableDatabase();
//
////
////        String query = "SELECT e.* FROM " + TABLE_EXAMPLES + " e " +
////                "JOIN " + TABLE_RESPONSES + " r ON e.Id = r.Ename " +
////                "WHERE r.AName = ?";
//
//        @SuppressLint("Recycle") Cursor cursorExam = db.rawQuery("SELECT * FROM " +
//        TABLE_EXAMPLES + " WHERE Id = (SELECT Ename FROM " + TABLE_RESPONSES + " WHERE AName = ? ) "
//        , new String[]{String.valueOf(AId)});
////        @SuppressLint("Recycle") Cursor cursorExam = db.rawQuery(query,
////                new String[]{String.valueOf(AId)});
//        List<ResponseField> responseList = new ArrayList<>();
//
//        if (cursorExam.moveToFirst()) {
//            do {
//                ExampleFileds detail = new ExampleFileds();
////                Log.i("700", cursorExam.getString(
////                        cursorExam.getColumnIndex(ExampleFileds.KEY_NAME)));
////                detail.setName(cursorExam.getString(cursorExam.getColumnIndex(
////                        ExampleFileds.KEY_NAME)));
////                detail.setStartDate(cursorExam.getString(cursorExam.getColumnIndex(
////                        ExampleFileds.KEY_STARTDATE)));
////                Log.i("700", "b");
////                detail.setExpireDate(cursorExam.getString(cursorExam.getColumnIndex(
////                        ExampleFileds.KEY_EXPIREDATE)));
////                detail.setExamTime(Integer.parseInt(cursorExam.getString(cursorExam.getColumnIndex(
////                        ExampleFileds.KEY_EXAMTIME))));
////                detail.setNumQ(Integer.parseInt(cursorExam.getString(cursorExam.getColumnIndex(
////                        ExampleFileds.KEY_NUMQ))));
////                detail.setAId(Integer.parseInt(cursorExam.getString(cursorExam.getColumnIndex(
////                        ExampleFileds.KEY_AID))));
////                Log.i("700", "c");
////                responseList.add(detail);
//            } while (cursorExam.moveToNext());
//        }
//
//        cursorExam.close(); // بستن Cursor بعد از استفاده از آن
//
//
//        return responseList;
//    }

    @SuppressLint("Range")
    public List<ResponseField> getExamHistory(int AId) {

        SQLiteDatabase db = getReadableDatabase();


//        String query = "SELECT e.* FROM " + TABLE_EXAMPLES + " e " +
//                "JOIN " + TABLE_RESPONSES + " r ON e.Id = r.Ename " +
//                "WHERE r.AName = ?";
        @SuppressLint("Recycle") Cursor cursorres = db.rawQuery("SELECT * FROM " +
                        TABLE_RESPONSES + " WHERE AName = ?"
                , new String[]{String.valueOf(AId)});

//        @SuppressLint("Recycle") Cursor cursorExam = db.rawQuery(query,
//                new String[]{String.valueOf(AId)});
        List<ResponseField> responseList = new ArrayList<>();

        if (cursorres.moveToFirst()) {
            do {
                ResponseField detail2 = new ResponseField();
                detail2.setNumTR(Integer.parseInt(cursorres.getString(cursorres.getColumnIndex(
                        ResponseField.KEY_NUM_T_R))));
                detail2.setNumFR(Integer.parseInt(cursorres.getString(cursorres.getColumnIndex(
                        ResponseField.KEY_NUM_F_R))));
                detail2.setTime(Integer.parseInt(cursorres.getString(cursorres.getColumnIndex(
                        ResponseField.KEY_TIME))));
                detail2.setNumBlankR(Integer.parseInt(cursorres.getString(cursorres.getColumnIndex(
                        ResponseField.KEY_NUM_BLANK_R))));
                detail2.setPercentageR(Double.parseDouble(cursorres.getString(cursorres.getColumnIndex(
                        ResponseField.KEY_PERCENTAGE_R))));
                detail2.setEName(String.valueOf(cursorres.getString(cursorres.getColumnIndex(
                        ResponseField.KEY_E_NAME))));



                @SuppressLint("Recycle") Cursor cursorExam = db.rawQuery("SELECT * FROM " +
                                TABLE_EXAMPLES + " WHERE Id = ? "
                        , new String[]{String.valueOf(detail2.getEName())});
                if (cursorExam.moveToFirst()) {
                    do {
                        detail2.setId(Integer.parseInt(cursorExam.getString(cursorExam.getColumnIndex(
                                ExampleFileds.KEY_ID))));

                        detail2.setAId(Integer.parseInt(cursorExam.getString(cursorExam.getColumnIndex(
                                ExampleFileds.KEY_AID))));
                        detail2.setName(cursorExam.getString(cursorExam.getColumnIndex(
                                ExampleFileds.KEY_NAME)));
                        Log.i("700", "name: " + detail2.getName());
                        Log.i("700", "name: " + detail2.getId());
                        detail2.setStartDate(cursorExam.getString(cursorExam.getColumnIndex(
                                ExampleFileds.KEY_STARTDATE)));
                        detail2.setExpireDate(cursorExam.getString(cursorExam.getColumnIndex(
                                ExampleFileds.KEY_EXPIREDATE)));
                        detail2.setExamTime(Integer.parseInt(cursorExam.getString(cursorExam.getColumnIndex(
                                ExampleFileds.KEY_EXAMTIME))));
                        detail2.setNumQ(Integer.parseInt(cursorExam.getString(cursorExam.getColumnIndex(
                                ExampleFileds.KEY_NUMQ))));
                        detail2.setAId(Integer.parseInt(cursorExam.getString(cursorExam.getColumnIndex(
                                ExampleFileds.KEY_AID))));
                    } while (cursorExam.moveToNext());
                }
                cursorExam.close();
                responseList.add(detail2);

            } while (cursorres.moveToNext());
        }

        cursorres.close(); // بستن Cursor بعد از استفاده از آن


        return responseList;
    }
//    public void a(){
//        ContentValues values = new ContentValues();
//        SQLiteDatabase db=getWritableDatabase();
//        values.put("AName", 3);
//        values.put("EName", 1);
//        values.put("numTR", 2);
//        values.put("numFR", 3);
//        values.put("numBlankR", 0);
//        values.put("Time", 20);
//        values.put("PercentageR", 100);
//
//        db.insert(TABLE_RESPONSES, null, values);
//    }

}















































