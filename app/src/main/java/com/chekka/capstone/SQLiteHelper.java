package com.chekka.capstone;

/**
 * Created by Juned on 3/13/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;

import java.sql.Blob;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class SQLiteHelper extends SQLiteOpenHelper {
    private com.chekka.capstone.SQLiteHelper dbhelper;
    private SQLiteDatabase db;
    String userIDmid = "";
    int[] array = {2, 5, 6,8,0,7,9,4};

    static String DATABASE_NAME = "UserDataBase";
    public static StringBuilder sbforid = new StringBuilder();
    public static StringBuilder sbforname = new StringBuilder();
    public static StringBuilder sbforscore = new StringBuilder();
    public static StringBuilder sbforlow = new StringBuilder();
    public static StringBuilder sbforhigh = new StringBuilder();
    StringBuilder sb_gethigh= new StringBuilder();
    StringBuilder sb_getlow = new StringBuilder();
    StringBuilder sb_gethighformid = new StringBuilder();
    StringBuilder sb_getlowformid = new StringBuilder();
    public static String sbfinal;
    List<Integer> answers, addallformid;
    String forlow_str, forhigh_str;
    ArrayList<HashMap<String, String>> midhigh, midlow,store;
    // TABLE FOR LOG IN
    public static int counter_class = 0;
    public static int counter_stud = 0;
    public static int counter_excel = 0;
    public static int counter_item = 0;
    public static int counter_mid = 0;
    public static int counter_highPLUS = 0;
    public static int counter_high = 0;
    public static int counter_low = 0;
    public static int counter_high_sb = 0;
    public static int counter_low_sb = 0;
    public static int counter_midhigh = 0;
    public static int counter_midlow = 0;
    String highdata, lowdata;
    String halftok, midone, halftokhigh, highdone, halftoklow, lowdone, highformid_tok, lowformid_tok;
    public static int countcor = 0, countincor = 0, lowcountcor = 0, lowcountincor = 0, highcountcor = 0, highcountincor = 0;
    public static String mifinal, lowfinal, highfinal;
    String low, high;
    public static final String tbl_users = "tbl_users";

    public static final String userID = "userID";

    public static final String username = "username";

    public static final String email = "email";

    public static final String password = "password";

    public static final String tbl_answerkey = "tbl_answerkey";

    public static final String answerkeyID = "answerkeyID";

    public static final String answerkey = "answerkey";

    public static final String examID = "examID";

    public static final String tbl_students_answer2 = "tbl_students_answer2";

    public static final String students_answerID2 = "students_answerID2";

    public static final String students_answer2 = "students_answer2";

    public static final String students_examID2 = "students_examID2";

    public static final String students_number2 = "students_number2";

    public static final String students_score2 = "students_score2";

    public static final String students_average2 = "students_average2";
    // TABLE FOR CLASS

    public static final String tbl_students_answer101 = "tbl_students_answer101";

    public static final String students_answerID101 = "students_answerID101";

    public static final String students_answer101 = "students_answer101";

    public static final String students_examID101 = "students_examID101";

    public static final String students_number101 = "students_number101";

    public static final String students_score101 = "students_score101";

    public static final String students_average101 = "students_average101";

    public static final String students_examname101 = "students_examname101";

    public static final String students_userID101 = "students_userID101";

    public static final String students_itemcount101 = "students_itemcount101";

    public static final String students_image101 = "students_image101";

    public static final String students_name101 = "students_name101";

    public static final String students_class101 = "students_class101";


    public static final String second_tbl_exam = "second_tbl_exam";

    public static final String second_exam_ID = "second_exam_ID";

    public static final String second_exam_Name = "second_exam_Name ";

    public static final String second_exam_Class = "second_exam_Class";

    public static final String second_exam_set = "second_exam_set";

    public static final String second_user_ID = "second_user_ID";

    public static final String second_itemscount = "second_itemscount";


    public static final String tbl_class = "tbl_class";

    public static final String classID = "classID";

    public static final String classname = "classname";

    public static final String UserID = "UserID";

    // TABLE FOR CLASS WITH NO OF STUDENTS

    public static final String tbl_class_with_no_of_students = "tbl_class_with_no_of_students";

    public static final String classID_with_no_of_students = "classID_with_no_of_students";

    public static final String classname_with_no_of_students = "classname_with_no_of_students";

    public static final String classstudents_with_no_of_students = "classstudents_with_no_of_students";

    public static final String UserID_with_no_of_students = "UserID_with_no_of_students";

    // TABLE FOR CLASS WITH NO OF STUDENTS

    public static final String tbl_students = "tbl_students";

    public static final String studentsID = "studentsID";

    public static final String studentsname = "studentsname";

    public static final String studentsemail = "studentsemail";

    public static final String studentsclass = "studentsclass";

    public static final String studentsuserid = "studentsuserid";
    // NEW STUDENT
    public static final String new_tbl_students = "new_tbl_students";

    public static final String new_studentsID = "new_studentsID";

    public static final String new_studentsname = "new_studentsname";

    public static final String new_studentsclass = "new_studentsclass";

    public static final String new_studentsnumber = "new_studentsnumber";

    public static final String new_studentsuserid = "new_studentsuserid";

    // CREATE EXAM SAMPLE
    public static final String sample_tbl_exam = "sample_tbl_exam";

    public static final String sample_exam_ID = "sample_exam_ID";

    public static final String sample_exam_Name = "sample_exam_Name ";

    public static final String sample_exam_Class = "sample_exam_Class";

    public static final String sample_exam_set = "sample_exam_set";

    public static final String sample_student_ID = "sample_student_ID";

    public static final String tbl_exam = "tbl_exam";

    public static final String exam_ID = "exam_ID";

    public static final String exam_Name = "exam_Name";

    public static final String exam_Class = "exam_Class";

    public static final String exam_set = "exam_set";

    public static final String student_ID = "student_ID";

    public static final String final_tbl_exam = "final_tbl_exam";

    public static final String final_exam_ID = "final_exam_ID";

    public static final String final_exam_name = "final_exam_name";

    public static final String final_exam_class = "final_exam_class";

    public static final String final_exam_set = "final_exam_set";

    public static final String final_exam_section = "final_exam_section";

    public static final String final_total_count = "final_total_count";

    public static final String final_item_per_count = "final_item_per_count";

    public static final String final_category = "final_category";

    public static final String final_user_ID = "final_user_ID";

    public static final String final_correct = "final_correct";

    public static final String final_incorrect = "final_incorrect";

    public static final String final_scoreitems = "final_scoreitems";

    public static final String final_firstnumberpersection = "final_firstnumberpersection";

    public static final String final_date = "final_date";




    public static final String final_scan = "final_scan";

    byte[] image;

    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        answers = new ArrayList<>();
        addallformid = new ArrayList<>();
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + tbl_users + " (" + userID + " INTEGER PRIMARY KEY, " + username + " VARCHAR, " + email + " VARCHAR, " + password + " VARCHAR)";

        database.execSQL(CREATE_TABLE);

        String CREATE_TABLE_CLASS = "CREATE TABLE IF NOT EXISTS " + tbl_class + " (" + classID + " INTEGER PRIMARY KEY, " + classname + " VARCHAR NOT NULL, " + UserID + " INTEGER NOT NULL, " + "FOREIGN KEY(UserID) REFERENCES tbl_users(userID))";
        database.execSQL(CREATE_TABLE_CLASS);

        String CREATE_TABLE_CLASS_WITH_NO_OF_STUDENTS = "CREATE TABLE IF NOT EXISTS " + tbl_class_with_no_of_students + " (" + classID_with_no_of_students + " INTEGER PRIMARY KEY, " + classname_with_no_of_students + " VARCHAR NOT NULL, " + classstudents_with_no_of_students + " VARCHAR NOT NULL, " + UserID_with_no_of_students + " INTEGER NOT NULL, " + "FOREIGN KEY(UserID_with_no_of_students) REFERENCES tbl_users(UserID))";
        database.execSQL(CREATE_TABLE_CLASS_WITH_NO_OF_STUDENTS);

        String CREATE_TABLE_SAMPLE_EXAM = "CREATE TABLE IF NOT EXISTS " + sample_tbl_exam + " (" + sample_exam_ID + " INTEGER PRIMARY KEY, " + sample_exam_Name + " VARCHAR NOT NULL, " + sample_exam_Class + " VARCHAR NOT NULL, " + sample_exam_set + " VARCHAR NOT NULL, " + sample_student_ID + " INTEGER NOT NULL, " + "FOREIGN KEY(sample_student_ID) REFERENCES tbl_users(UserID))";
        System.out.println("DB" + CREATE_TABLE_SAMPLE_EXAM);
        database.execSQL(CREATE_TABLE_SAMPLE_EXAM);

        String CREATE_TABLE_FINAL_EXAM = "CREATE TABLE IF NOT EXISTS " + second_tbl_exam + " (" + second_exam_ID + " INTEGER PRIMARY KEY, " + second_exam_Name + " VARCHAR NOT NULL, " + second_exam_Class + " VARCHAR NOT NULL, " + second_exam_set + " VARCHAR NOT NULL, " + second_itemscount + " VARCHAR NOT NULL, " + second_user_ID + " INTEGER NOT NULL, " + "FOREIGN KEY(second_user_ID) REFERENCES tbl_users(UserID))";
        System.out.println("DB" + CREATE_TABLE_FINAL_EXAM);
        database.execSQL(CREATE_TABLE_FINAL_EXAM);


        String CREATE_TABLE_FINAL_101 = "CREATE TABLE IF NOT EXISTS " + tbl_students_answer101 + " (" + students_answerID101 + " INTEGER PRIMARY KEY, " + students_answer101 + " VARCHAR NOT NULL, " + students_score101 + " VARCHAR NOT NULL, " + students_average101 + " VARCHAR NOT NULL, " + students_examID101 + " VARCHAR NOT NULL, " + students_itemcount101 + " VARCHAR NOT NULL, " + students_examname101 + " VARCHAR NOT NULL, " + students_userID101 + " VARCHAR NOT NULL, " + students_name101 + " VARCHAR NOT NULL," + students_image101 + " BLOB NOT NULL," + students_class101 + " VARCHAR NOT NULL," + students_number101 + " INTEGER NOT NULL, " + "FOREIGN KEY(students_number101) REFERENCES tbl_Students(studentsID))";

        database.execSQL(CREATE_TABLE_FINAL_101);

        String CREATE_TABLE_FINALEXAM = "CREATE TABLE IF NOT EXISTS " + final_tbl_exam + " (" + final_exam_ID + " INTEGER PRIMARY KEY, " + final_exam_name + " VARCHAR NOT NULL, " + final_exam_class + " VARCHAR NOT NULL, " + final_exam_set + " VARCHAR NOT NULL, " + final_exam_section + " VARCHAR NOT NULL, " + final_total_count + " VARCHAR NOT NULL, " + final_item_per_count + " VARCHAR NOT NULL, " + final_category + " VARCHAR NOT NULL, " + final_user_ID + " VARCHAR NOT NULL, " + final_correct + " VARCHAR NOT NULL, " + final_incorrect + " VARCHAR NOT NULL, " + final_scoreitems + " VARCHAR NOT NULL," + final_firstnumberpersection + " VARCHAR NOT NULL," + final_date + " VARCHAR NOT NULL)";
        database.execSQL(CREATE_TABLE_FINALEXAM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tbl_users);
        db.execSQL("DROP TABLE IF EXISTS " + tbl_class);
        db.execSQL("DROP TABLE IF EXISTS " + tbl_class_with_no_of_students);
        db.execSQL("DROP TABLE IF EXISTS " + tbl_students);
        db.execSQL("DROP TABLE IF EXISTS " + sample_tbl_exam);
        db.execSQL("DROP TABLE IF EXISTS " + second_tbl_exam);
        db.execSQL("DROP TABLE IF EXISTS " + tbl_students_answer2);
        db.execSQL("DROP TABLE IF EXISTS " + tbl_students_answer101);
        db.execSQL("DROP TABLE IF EXISTS " + final_tbl_exam);
        db.execSQL("DROP TABLE IF EXISTS " + tbl_answerkey);
        onCreate(db);

    }

    // UPDATE PASSWORD
    public boolean update(String s, String s1) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + tbl_users + " SET password = " + "'" + s + "' " + "WHERE email = " + "'" + s1 + "'");
        return true;
    }

    // GET USER ID
    public int getIdFromClassName(String Email) {
        String query = "SELECT userID FROM " + tbl_users + " WHERE " + email + " = '" + Email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        int userID = -1;
        if (res != null && res.moveToFirst()) {
            userID = res.getInt(res.getColumnIndex("userID"));
        }
        return userID;
    }

    public int getfromExamName(String exam_ID) {
        String query = "SELECT " + second_itemscount + "," + second_exam_Name + "," + second_exam_ID + " FROM " + second_tbl_exam + " WHERE " + second_exam_ID + " = '" + exam_ID + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        int userID = -1;
        if (res != null && res.moveToFirst()) {
            userID = res.getInt(res.getColumnIndex("second_exam_ID"));
            userID = res.getInt(res.getColumnIndex("second_itemscount"));
        }
        return userID;
    }

    public ArrayList<HashMap<String, String>> GetExamNameFromDatabase(String examd_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT " + second_itemscount + "," + second_exam_Name + "," + second_exam_ID + " FROM " + second_tbl_exam + " WHERE " + second_exam_ID + " = '" + exam_ID + "'";
        Cursor cursor = db.query(second_tbl_exam, new String[]{second_itemscount, second_exam_Name, second_exam_ID}, second_exam_ID + "=?", new String[]{String.valueOf(examd_ID)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> user = new HashMap<>();
                user.put("second_itemscount", cursor.getString(cursor.getColumnIndex(second_itemscount)));
                user.put("second_exam_Name", cursor.getString(cursor.getColumnIndex(second_exam_Name)));
                user.put("second_exam_ID", cursor.getString(cursor.getColumnIndex(second_exam_ID)));
                System.out.println("HELLO" + user);
                userList.add(user);
            }
            while (cursor.moveToNext());
            //Cursor cursor1 = db.query(tbl_class_with_no_of_students, new String[]{classname_with_no_of_students, classstudents_with_no_of_students, UserID_with_no_of_students}, UserID_with_no_of_students + "=?", new String[]{String.valueOf(user_id)},null, "AND NOT "+classname_with_no_of_students+"=Everyone", null );

        }
        System.out.println("BAKA NAMAN" + cursor);
        return userList;
    }

    // NOT WORKING
    public int getClassNamefFromTable(String user_id) {
        String query = "SELECT classname FROM " + tbl_class + " WHERE " + UserID + " = '" + user_id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        int userID = -1;
        if (res != null && res.moveToFirst()) {
            userID = res.getInt(res.getColumnIndex("userID"));
        }
        return userID;
    }

    // INSERT CLASS - NOT WORKING
    void insertUserDetails(String classname_for_insert, String classuserid_for_insert) {
        SQLiteDatabase db = this.getWritableDatabase();
        String inserusert = "INSERT INTO " + tbl_class + " (classname,UserID) VALUES('" + classname_for_insert + "', '" + classuserid_for_insert + "');";
        db.execSQL(inserusert);

        // Closing SQLite database object.
        db.close();
    }

    // SELECT CLASS - NOT WORKING
    void select_class(String user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String inserusert = "SELECT " + classname + "," + classID + " FROM " + tbl_class + " WHERE " + UserID + " = '" + user_id + "'";
        db.execSQL(inserusert);

        // Closing SQLite database object.
        db.close();
    }

    // RELOAD ALL DATA OF CLASS NAME - NOT WORKING
    public ArrayList<HashMap<String, String>> GetClassName(String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT " + classname + "," + classID + " FROM " + tbl_class + " WHERE " + UserID + " = '" + user_id + "'";
        Cursor cursor = db.query(tbl_class, new String[]{classname, classID}, UserID + "=?", new String[]{String.valueOf(user_id)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {


                HashMap<String, String> user = new HashMap<>();
                user.put("classname", cursor.getString(cursor.getColumnIndex(classname)));
                user.put("classID", cursor.getString(cursor.getColumnIndex(classID)));
                System.out.println("HELLO" + user);
                userList.add(user);
            }
            while (cursor.moveToNext());

            System.out.println("HEHE");
        }

        return userList;
    }


    // RELOAD ALL DATA OF CLASS NAME
    public ArrayList<HashMap<String, String>> GetClassName_with_no_of_students(String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT " + classname_with_no_of_students + "," + classstudents_with_no_of_students + ", " + UserID_with_no_of_students + " FROM " + tbl_class_with_no_of_students + " WHERE " + UserID_with_no_of_students + " = '" + user_id + "'";
        Cursor cursor = db.query(tbl_class_with_no_of_students, new String[]{classID_with_no_of_students, classname_with_no_of_students, classstudents_with_no_of_students, UserID_with_no_of_students}, UserID_with_no_of_students + "=?", new String[]{String.valueOf(user_id)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                String a = "Everyone";
                if (a.equals(cursor.getString(cursor.getColumnIndex(classname_with_no_of_students)))) {
                    System.out.println("Hello");
                } else {
                    HashMap<String, String> user = new HashMap<>();
                    user.put("classID_with_no_of_students", cursor.getString(cursor.getColumnIndex(classID_with_no_of_students)));
                    user.put("classname_with_no_of_students", cursor.getString(cursor.getColumnIndex(classname_with_no_of_students)));
                    user.put("classstudents_with_no_of_students", cursor.getString(cursor.getColumnIndex(classstudents_with_no_of_students)));
                    user.put("UserID_with_no_of_students", cursor.getString(cursor.getColumnIndex(UserID_with_no_of_students)));
                    System.out.println("HELLO" + user);
                    userList.add(user);
                    counter_class++;
                }
            }
            while (cursor.moveToNext());
            //Cursor cursor1 = db.query(tbl_class_with_no_of_students, new String[]{classname_with_no_of_students, classstudents_with_no_of_students, UserID_with_no_of_students}, UserID_with_no_of_students + "=?", new String[]{String.valueOf(user_id)},null, "AND NOT "+classname_with_no_of_students+"=Everyone", null );

        }
        System.out.println("BAKA NAMAN" + cursor);
        return userList;
    }

    // INSERT CLASS DETAILS
    void insert_class_details(String classname_with_no_of_students, String UserID_with_no_of_students, String classstudents_with_no_of_students) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert_class = "INSERT INTO " + tbl_class_with_no_of_students + " (classname_with_no_of_students, classstudents_with_no_of_students,UserID_with_no_of_students) VALUES('" + classname_with_no_of_students + "', '" + classstudents_with_no_of_students + "','" + UserID_with_no_of_students + "');";
        db.execSQL(insert_class);

        // Closing SQLite database object.
        db.close();
    }

    void insert_exam_paper(String examID2, String answer2, String number2, String score2, String average2) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert_class = "INSERT INTO " + tbl_students_answer2 + "(students_examID2 , students_answer2, students_number2, students_score2,students_average2   ) VALUES('" + examID2 + "', '" + answer2 + "','" + number2 + "','" + score2 + "','" + average2 + "');";
        db.execSQL(insert_class);
        // Closing SQLite database object.
        db.close();
    }

    public void insertData(String answer, String score, String average, String examid, String examname, String item, byte[] image, String userid, String studentname, String studentnumber, String studentclass) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO tbl_students_answer101 VALUES (NULL, ?, ?, ?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, answer);
        statement.bindString(2, score);
        statement.bindString(3, average);
        statement.bindString(4, examid);
        statement.bindString(5, item);
        statement.bindString(6, examname);
        statement.bindString(7, userid);
        statement.bindString(8, studentname);
        statement.bindBlob(9, image);
        statement.bindString(10, studentclass);
        statement.bindString(11, studentnumber);
        statement.executeInsert();
    }

    public List<String> getAllLClassNamesForSpinner(String user_id) {
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  classname_with_no_of_students FROM " + tbl_class_with_no_of_students + " WHERE " + UserID_with_no_of_students + " = '" + user_id + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String a = "Everyone";
                if (a.equals(cursor.getString(0))) {
                    System.out.println("Hello");
                } else {
                    list.add(cursor.getString(0));//adding 2nd column data
                }
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }

    public List<String> getAllLClassNamesForSpinnerExam(String user_id) {
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  classname_with_no_of_students FROM " + tbl_class_with_no_of_students + " WHERE " + UserID_with_no_of_students + " = '" + user_id + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }

    // INSERT STUDENT DETAILS
    void insert_student_details(String studentsname, String studentsuserid, String studentsclass, String studentsID, String studentsemail) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert_student = "INSERT INTO " + tbl_students + " (studentsname, studentsclass, studentsemail, studentsuserid, studentsID) VALUES('" + studentsname + "', '" + studentsclass + "', '" + studentsemail + "', '" + studentsuserid + "','" + studentsID + "');";
        db.execSQL(insert_student);

        // Closing SQLite database object.
        db.close();
    }

    public ArrayList<HashMap<String, String>> GetStudentName(String user_id, String studentsclasses) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        //String query = "SELECT "+studentsname+","+ studentsclass+", "+ studentsuserid+" , "+ studentsID+" FROM "+ tbl_students+" WHERE " + studentsclass + " = '" + studentsclasses + "'";
        //System.out.println("QUERY YARN"+query);
        Cursor cursor = db.query(tbl_students, new String[]{studentsname, studentsclass, studentsuserid, studentsID, studentsemail}, studentsuserid + "=?", new String[]{String.valueOf(user_id)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                try {
                    if (studentsclasses.equals(cursor.getString(cursor.getColumnIndex(studentsclass)))) {
                        student.put("studentsname", cursor.getString(cursor.getColumnIndex(studentsname)));
                        student.put("studentsclass", cursor.getString(cursor.getColumnIndex(studentsclass)));
                        student.put("studentsuserid", cursor.getString(cursor.getColumnIndex(studentsuserid)));
                        student.put("studentsID", cursor.getString(cursor.getColumnIndex(studentsID)));
                        student.put("studentsemail", cursor.getString(cursor.getColumnIndex(studentsemail)));
                        System.out.println("LETSGOO2" + student);
                        studentList.add(student);
                        counter_stud++;
                    }
                } catch (Exception e) {
                    System.out.println("ZUP");
                }
            }
            while (cursor.moveToNext());

            System.out.println("HEHE");
        }
        return studentList;

    }  public ArrayList<HashMap<String, String>> GetStudentNameOne(String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        //String query = "SELECT "+studentsname+","+ studentsclass+", "+ studentsuserid+" , "+ studentsID+" FROM "+ tbl_students+" WHERE " + studentsclass + " = '" + studentsclasses + "'";
        //System.out.println("QUERY YARN"+query);
        Cursor cursor = db.query(tbl_students, new String[]{studentsname}, studentsuserid + "=?", new String[]{String.valueOf(user_id)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                try {

                        student.put("studentsname", cursor.getString(cursor.getColumnIndex(studentsname)));
                        student.put("studentsclass", cursor.getString(cursor.getColumnIndex(studentsclass)));
                        student.put("studentsuserid", cursor.getString(cursor.getColumnIndex(studentsuserid)));
                        student.put("studentsID", cursor.getString(cursor.getColumnIndex(studentsID)));
                        student.put("studentsemail", cursor.getString(cursor.getColumnIndex(studentsemail)));
                        System.out.println("LETSGOO2" + student);
                        studentList.add(student);

                } catch (Exception e) {
                    System.out.println("ZUP");
                }
            }
            while (cursor.moveToNext());

            System.out.println("HEHE");
        }
        return studentList;
    }
    public ArrayList<HashMap<String, String>> GetExamNameClass(String studname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        //String query = "SELECT "+studentsname+","+ studentsclass+", "+ studentsuserid+" , "+ studentsID+" FROM "+ tbl_students+" WHERE " + studentsclass + " = '" + studentsclasses + "'";
        //System.out.println("QUERY YARN"+query);
        Cursor cursor = db.query(tbl_students_answer101, new String[]{students_score101, students_name101}, students_name101 + "=?", new String[]{studname}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                try {

                        student.put("students_score101", cursor.getString(cursor.getColumnIndex(students_score101)));
                        student.put("students_name101", cursor.getString(cursor.getColumnIndex(students_name101)));
                        System.out.println("LETSGOO200=" + student);
                        studentList.add(student);

                } catch (Exception e) {
                    System.out.println("ZUP");
                }
            }
            while (cursor.moveToNext());

            System.out.println("HEHE");
        }
        return studentList;
    }

    public boolean studentnumber(int studentsID, String studentsname) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + tbl_students + " SET studentsID = " + "'" + studentsID + "' " + "WHERE studentsname = " + "'" + studentsname + "'");
        return true;
    }

    void new_insert_student_details(String studentsname, String studentsuserid, String studentsclass, Integer studentsID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert_student = "INSERT INTO " + new_tbl_students + " (new_studentsname, new_studentsclass,new_studentsuserid, new_studentsnumber) VALUES('" + studentsname + "', '" + studentsclass + "','" + studentsuserid + "','" + studentsID + "');";
        db.execSQL(insert_student);

        // Closing SQLite database object.
        db.close();
    }

    public ArrayList<HashMap<String, String>> Getnew_StudentName(String user_id, String studentsclasses) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT " + new_studentsname + "," + new_studentsclass + ", " + new_studentsuserid + " , " + new_studentsID + " FROM " + new_tbl_students + " WHERE " + new_studentsclass + " = '" + studentsclasses + "'";
        System.out.println("QUERY YARN" + query);
        Cursor cursor = db.query(new_tbl_students, new String[]{new_studentsname, new_studentsclass, new_studentsuserid, new_studentsnumber, new_studentsID}, new_studentsuserid + "=?", new String[]{String.valueOf(user_id)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();

                if (studentsclasses.equals(cursor.getString(cursor.getColumnIndex(new_studentsclass)))) {
                    student.put("new_studentsname", cursor.getString(cursor.getColumnIndex(new_studentsname)));
                    student.put("new_studentsclass", cursor.getString(cursor.getColumnIndex(new_studentsclass)));
                    student.put("new_studentsuserid", cursor.getString(cursor.getColumnIndex(new_studentsuserid)));
                    student.put("new_studentsID", cursor.getString(cursor.getColumnIndex(new_studentsID)));
                    student.put("new_studentsnumber", cursor.getString(cursor.getColumnIndex(new_studentsnumber)));
                    System.out.println("LETSGOO2" + student);
                    studentList.add(student);
                }
            }
            while (cursor.moveToNext());

            System.out.println("HEHE");
        }

        return studentList;
    }

    public boolean update_classname(String classname, String classname_orig) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + tbl_class_with_no_of_students + " SET classname_with_no_of_students = " + "'" + classname + "' " + "WHERE classname_with_no_of_students = " + "'" + classname_orig + "'");
        return true;
    }

    public void delete_classname(String classname) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(tbl_class_with_no_of_students, "classID_with_no_of_students=?", new String[]{classname});
        db.close();
    }

    public void delete_students(String studentid) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(tbl_students, "studentsID=?", new String[]{studentid});
        db.close();
    }
    public void delete_students_under_answerkey(String studentid) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(tbl_students, "studentsID=?", new String[]{studentid});
        db.close();
    }
    public void deleteexam(String examnameid) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(final_tbl_exam, "final_exam_ID=?", new String[]{examnameid});
        db.close();
    }
    public void deleteexamanswers(String examnameid) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tbl_students_answer101, "students_examname101=?", new String[]{examnameid});
        db.close();
    }

    public void deleteclassunder(String classnamee) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tbl_students_answer101, "students_examname101=?", new String[]{classnamee});
        db.close();
    }
    void insert_examwith(String exam_Name, String exam_Class, String exam_set, String exam_itemscount, String exam_userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert_examwith = "INSERT INTO " + second_tbl_exam + " (second_exam_Name, second_exam_Class,second_exam_set, second_itemscount, second_user_ID) VALUES('" + exam_Name + "', '" + exam_Class + "','" + exam_set + "','" + exam_itemscount + "','" + exam_userid + "');";
        db.execSQL(insert_examwith);

        // Closing SQLite database object.
        db.close();
    }

    public ArrayList<HashMap<String, String>> GetExamName(String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT " + final_exam_ID + ", " + final_exam_name + ", " + final_exam_set + ", " + final_exam_section + "," + final_total_count + ", " + final_item_per_count + " , " + final_user_ID + " FROM " + final_tbl_exam + " WHERE " + final_user_ID + " = '" + user_id + "'";
        System.out.println("EXAM YARN" + query);
        Cursor cursor = db.query(final_tbl_exam, new String[]{final_exam_ID, final_exam_class, final_exam_name, final_exam_set, final_exam_section, final_total_count, final_item_per_count, final_user_ID, final_category, final_scoreitems, final_firstnumberpersection, final_date}, final_user_ID + "=?", new String[]{String.valueOf(user_id)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                student.put("final_exam_ID", cursor.getString(cursor.getColumnIndex(final_exam_ID)));
                student.put("final_exam_class", cursor.getString(cursor.getColumnIndex(final_exam_class)));
                student.put("final_exam_name", cursor.getString(cursor.getColumnIndex(final_exam_name)));
                student.put("final_exam_set", cursor.getString(cursor.getColumnIndex(final_exam_set)));
                student.put("final_exam_section", cursor.getString(cursor.getColumnIndex(final_exam_section)));
                student.put("final_total_count", cursor.getString(cursor.getColumnIndex(final_total_count)));
                student.put("final_item_per_count", cursor.getString(cursor.getColumnIndex("final_item_per_count")));
                student.put("final_user_ID", cursor.getString(cursor.getColumnIndex("final_user_ID")));
                student.put("final_category", cursor.getString(cursor.getColumnIndex("final_category")));
                student.put("final_scoreitems", cursor.getString(cursor.getColumnIndex("final_scoreitems")));
                student.put("final_firstnumberpersection", cursor.getString(cursor.getColumnIndex("final_firstnumberpersection")));
                student.put("final_date", cursor.getString(cursor.getColumnIndex("final_date")));
                System.out.println("LETSGOO2" + student);
                studentList.add(student);

            }
            while (cursor.moveToNext());

            System.out.println("HEHE" + studentList);
        }
        return studentList;
    }

    public ArrayList<HashMap<String, String>> GetExamNameToStudent(String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT " + exam_Name + "," + exam_Class + ", " + exam_set + " , " + student_ID + " FROM " + tbl_exam + " WHERE " + student_ID + " = '" + user_id + "'";
        System.out.println("EXAM YARN" + query);
        Cursor cursor = db.query(tbl_exam, new String[]{exam_Name, exam_Class, exam_set, student_ID}, student_ID + "=?", new String[]{String.valueOf(user_id)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();

                student.put("exam_Name", cursor.getString(cursor.getColumnIndex(exam_Name)));
                student.put("exam_Class", cursor.getString(cursor.getColumnIndex(exam_Class)));
                student.put("exam_set", cursor.getString(cursor.getColumnIndex(exam_set)));
                student.put("student_ID", cursor.getString(cursor.getColumnIndex(student_ID)));
                System.out.println("LETSGOO2" + student);
                studentList.add(student);

            }
            while (cursor.moveToNext());

            System.out.println("HEHE");
        }
        return studentList;
    }

    public ArrayList<HashMap<String, String>> GetStudentInfo(String studnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT " + students_number101 + "," + students_score101 + "," + students_itemcount101 + " ," + students_average101 + " ," + students_examname101 + "  FROM " + tbl_students_answer101 + " WHERE " + students_number101 + " ='" + studnum + "'";
        System.out.println("EXAM YARN" + query);
        Cursor cursor = db.query(tbl_students_answer101, new String[]{students_number101, students_score101, students_itemcount101, students_average101, students_examname101}, students_number101 + "=?", new String[]{String.valueOf(studnum)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                student.put("students_number101", cursor.getString(cursor.getColumnIndex(students_number101)));
                student.put("students_score101", cursor.getString(cursor.getColumnIndex(students_score101)));
                student.put("students_itemcount101", cursor.getString(cursor.getColumnIndex(students_itemcount101)));
                student.put("students_average101", cursor.getString(cursor.getColumnIndex(students_average101)));
                student.put("students_examname101", cursor.getString(cursor.getColumnIndex(students_examname101)));
                System.out.println("LETSGOO2" + student);
                studentList.add(student);

            }
            while (cursor.moveToNext());

            System.out.println("HEHE");
        }
        return studentList;
    }

    public String getIdFromAnswer(String answerkeyIDE, String answerexamID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT answerkey FROM " + tbl_answerkey + " WHERE " + answerkeyID + " = '" + answerkeyIDE + "' AND " + examID + " = '" + answerexamID + "' ";
        System.out.println("query" + query);

        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));
        }
        System.out.println("user" + userID);
        return userID;
    }

    public String getanswerkeyid(String answerexamID) {
        String query = "SELECT answerkeyID FROM " + tbl_answerkey + " WHERE " + examID + " = '" + answerexamID + "' ";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));
        }
        System.out.println("user" + userID);
        return userID;
    }

    public String getcorrectscoreexam(String answerexamID) {
        String query = "SELECT final_correct FROM " + final_tbl_exam + " WHERE " + final_exam_ID + " = '" + answerexamID + "' ";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));
        }
        System.out.println("user" + userID);
        return userID;
    }

    public String getincorrectscoreexam(String answerexamID) {
        String query = "SELECT final_incorrect FROM " + final_tbl_exam + " WHERE " + final_exam_ID + " = '" + answerexamID + "' ";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));
        }
        System.out.println("user" + userID);
        return userID;
    }

    public String getstudentnameforimage(String answerexamID, String userid) {
        String query = "SELECT studentsname FROM " + tbl_students + " WHERE " + studentsID + " = '" + answerexamID + "' AND " + studentsuserid + " = '" + userid + "'";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));
        }
        System.out.println("user" + userID);
        return userID;
    }

    public String getstudentsidforchecking(String studentid_checking) {
        String query = "SELECT studentsID FROM " + tbl_students + " WHERE " + studentsID + " = '" + studentid_checking + "'";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            //  userID = (res.getString(0));
            userID = (res.getString(0));
        }
        System.out.println("user" + userID);
        return userID;
    }

    public String getstudidforpaper(String studentnumber_paper, String examname) {
        String query = "SELECT students_number101 FROM " + tbl_students_answer101 + " WHERE " + students_number101 + " = '" + studentnumber_paper + "' AND " + students_examname101 + " = '" + examname + "' ";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));

        }
        System.out.println("HAHAHAHA" + userID);
        return userID;
    }
    public String getstudscoredisplayclass(String studentnumber_paper, String examname) {
        String query = "SELECT students_score101 FROM " + tbl_students_answer101 + " WHERE " + students_number101 + " = '" + studentnumber_paper + "' AND " + students_examname101 + " = '" + examname + "' ";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));

        }
        System.out.println("user" + userID);
        return userID;
    }

    public String getstuduserid(String studentnumber_paper) {
        String query = "SELECT students_userID101 FROM " + tbl_students_answer101 + " WHERE " + students_number101 + " = '" + studentnumber_paper + "'";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));

        }
        System.out.println("user" + userID);
        return userID;
    }

    public byte[] getstudanaswerimage(String studentnumber_paper) {
        String query = "SELECT students_image101 FROM " + tbl_students_answer101 + " WHERE " + students_number101 + " = '" + studentnumber_paper + "'";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            image = res.getBlob(0);
        }
        System.out.println("user" + image);
        return image;
    }

    public boolean update_answerkey(String anskey, String exid) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + tbl_answerkey + " SET answerkey = " + "'" + anskey + "' " + "WHERE examID = " + "'" + exid + "'");
        return true;
    }

    public boolean update_answersheet(String anssheet, String exid) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + tbl_students_answer101 + " SET students_number101 = " + "'" + anssheet + "' " + "WHERE examID = " + "'" + exid + "'");
        return true;
    }

    public boolean updateStudents(String answer, String score, String average, String examID, String items, String exam_Name,
                                  String userid, String studid, String examname2, String studentname, String eded) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(students_answer101, answer);
        contentValues.put(students_score101, score);
        contentValues.put(students_average101, average);
        contentValues.put(students_examID101, examID);
        contentValues.put(students_itemcount101, items);
        contentValues.put(students_examname101, exam_Name);
        contentValues.put(students_userID101, userid);
        contentValues.put(students_name101, studentname);
        contentValues.put(students_class101, eded);

        db.update(tbl_students_answer101, contentValues, students_number101 + " = ? AND " + students_examname101 + "= ?",
                new String[]{String.valueOf(studid), exam_Name});

        return true;
    }

    public String getstudclass(String studentid, String studuserid) {
        String query = "SELECT studentsID FROM " + tbl_students + " WHERE " + studentsID + " = '" + studentid + "' AND studentsuserid = '" + studuserid + "'";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));
        }
        System.out.println("user" + userID);
        return userID;
    }

    public String getuseridtblusers(String studentid, String studuserid) {
        String query = "SELECT studentsuserid FROM " + tbl_students + " WHERE " + studentsID + " = '" + studentid + "' AND studentsuserid  = '" + studuserid + "'";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));
        }
        System.out.println("user" + userID);
        return userID;
    }

    public String getemailtblusers(String studentid, String studuserid) {
        String query = "SELECT studentsemail FROM " + tbl_students + " WHERE " + studentsID + " = '" + studentid + "' AND studentsuserid  = '" + studuserid + "'";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));
        }
        System.out.println("user" + userID);
        return userID;
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public byte[] getBitmapbyStudentnumber(String studentnumber, String examname) {
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] select = {students_image101};
        qb.setTables(tbl_students_answer101);
        System.out.println("queryto" + qb);
        Cursor c = qb.query(database, select, students_number101 + " = ? AND " + students_examname101 + "= ?", new String[]{studentnumber, examname}, null, null, null);
        byte[] result = null;
        if (c.moveToFirst()) {
            do {
                result = c.getBlob(c.getColumnIndex(students_image101));
            } while (c.moveToNext());
        }
        System.out.println("resultbitmap" + result);
        return result;
    }

    public ArrayList<HashMap<String, String>> GetStudentExcel(String studnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT " + students_number101 + "," + students_score101 + "," + students_itemcount101 + " ," + students_average101 + " ," + students_examname101 + "," + students_name101 + "    FROM " + tbl_students_answer101 + " WHERE " + students_examname101 + " ='" + studnum + "'";
        System.out.println("EXAM YARN" + query);
        Cursor cursor = db.query(tbl_students_answer101, new String[]{students_number101, students_score101, students_itemcount101, students_average101, students_examname101, students_name101}, students_examname101 + "=?", new String[]{String.valueOf(studnum)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                student.put("students_number101", cursor.getString(cursor.getColumnIndex(students_number101)));
                sbforid.append(cursor.getString(cursor.getColumnIndex(students_number101)) + ",");

                student.put("students_name101", cursor.getString(cursor.getColumnIndex(students_name101)));
                sbforname.append(cursor.getString(cursor.getColumnIndex(students_name101)) + ",");


                student.put("students_score101", cursor.getString(cursor.getColumnIndex(students_score101)));
                sbforscore.append(cursor.getString(cursor.getColumnIndex(students_score101)) + ",");

                System.out.println("LETSGOO2" + student);
                studentList.add(student);

                counter_excel++;
            }
            while (cursor.moveToNext());

            System.out.println("HEHE");
        }
        return studentList;
    }

    public String getstudentname(String studentid, String studuserid) {
        String query = "SELECT studentsname FROM " + tbl_students + " WHERE " + studentsID + " = '" + studentid + "' AND studentsuserid  = '" + studuserid + "'";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));

        }
        System.out.println("user" + userID);
        return userID;
    }

    public ArrayList<HashMap<String, String>> GetStudent(String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        //String query = "SELECT "+studentsname+","+ studentsclass+", "+ studentsuserid+" , "+ studentsID+" FROM "+ tbl_students+" WHERE " + studentsclass + " = '" + studentsclasses + "'";
        //System.out.println("QUERY YARN"+query);
        Cursor cursor = db.query(tbl_students, new String[]{studentsname, studentsclass, studentsuserid, studentsID, studentsemail}, studentsuserid + "=?", new String[]{String.valueOf(user_id)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                try {
                    student.put("studentsname", cursor.getString(cursor.getColumnIndex(studentsname)));
                    student.put("studentsclass", cursor.getString(cursor.getColumnIndex(studentsclass)));
                    student.put("studentsuserid", cursor.getString(cursor.getColumnIndex(studentsuserid)));
                    student.put("studentsID", cursor.getString(cursor.getColumnIndex(studentsID)));
                    student.put("studentsemail", cursor.getString(cursor.getColumnIndex(studentsemail)));
                    System.out.println("LETSGOO2" + student);
                    studentList.add(student);


                } catch (Exception e) {
                    System.out.println("ZUP");
                }
            }
            while (cursor.moveToNext());

            System.out.println("HEHE");
        }
        return studentList;
    }

    public boolean update_studentedit(String studid, String studname, String studemail, String studclass, String studuserid, String studid2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(studentsID, studid);
        contentValues.put(studentsname, studname);
        contentValues.put(studentsemail, studemail);
        contentValues.put(studentsclass, studclass);
        contentValues.put(studentsuserid, studuserid);
        db.update(tbl_students, contentValues, studentsID + "= ?",
                new String[]{String.valueOf(studid2)});

        return true;
    }
    public boolean update_studentanswerkey(String studentnumberchu, String studentnumberchureal) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + tbl_students_answer101 + " SET students_number101 = " + "'" + studentnumberchu + "'" + " WHERE students_number101 = " + "'" + studentnumberchureal + "'");
        return true;

    }

    public boolean update_classedit(String classid, String classname, String classstudent, String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(classID_with_no_of_students, classid);
        contentValues.put(classname_with_no_of_students, classname);
        contentValues.put(classstudents_with_no_of_students, classstudent);
        contentValues.put(UserID_with_no_of_students, userid);

        db.update(tbl_class_with_no_of_students, contentValues, classID_with_no_of_students + "= ?",
                new String[]{String.valueOf(classid)});

        return true;
    }
    public boolean update_studit(String classnamestud, String classnamestudreal) {
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL("UPDATE " + tbl_students + " SET studentsclass = " + "'" + classnamestud + "' " + "WHERE studentsclass = " + "'" + classnamestudreal + "'");
            return true;

    }
    public boolean update_examanswers(String examnamestud, String examnamestudreal) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + tbl_students_answer101 + " SET students_examname101 = " + "'" + examnamestud + "' " + "WHERE students_examname101 = " + "'" + examnamestudreal + "'");
        return true;

    }
    public boolean update_examedit(String examname, String examid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(final_exam_name, examname);
        contentValues.put(final_exam_ID, examid);


        db.update(final_tbl_exam, contentValues, final_exam_ID + "= ?",
                new String[]{String.valueOf(examid)});

        return true;
    }

    public String getexamname(String examname) {
        String query = "SELECT final_exam_name FROM " + final_tbl_exam + " WHERE " + final_exam_name + " = '" + examname + "'";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));

        }
        System.out.println("user" + userID);
        return userID;
    }

    public String getpassword(String email1) {
        String query = "SELECT password FROM " + tbl_users + " WHERE " + email + " = '" + email1 + "'";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        String userID = "";
        if (res.moveToFirst()) {
            userID = (res.getString(0));

        }
        System.out.println("user" + userID);
        return userID;
    }

    public ArrayList<HashMap<String, String>> GetStudentResultItem(String studnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT " + students_answer101 + "," + students_score101 + " FROM " + tbl_students_answer101 + " WHERE " + students_examname101 + " ='" + studnum + "'";
        Cursor cursor = db.query(tbl_students_answer101, new String[]{students_answer101, students_score101}, students_examname101 + "=?", new String[]{String.valueOf(studnum)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                student.put("students_answer101", cursor.getString(cursor.getColumnIndex(students_answer101)));
                student.put("students_score101", cursor.getString(cursor.getColumnIndex(students_score101)));
                System.out.println("LETSGOO2" + student);
                studentList.add(student);
                counter_item++;
            }
            while (cursor.moveToNext());

        }
        return studentList;
    }

    public ArrayList<HashMap<String, String>> Getresulthigh(String studnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT " + students_answer101 + "," + students_score101 + " ," + students_answerID101 + " FROM " + tbl_students_answer101 + " WHERE " + students_examname101 + " ='" + studnum + "'";
        Cursor cursor = db.query(tbl_students_answer101, new String[]{students_answer101, students_score101, students_answerID101}, students_examname101 + "=?", new String[]{String.valueOf(studnum)}, null, null, students_answer101 + " DESC", null);

        highcountcor = 0;
        highcountincor = 0;
        if (cursor.moveToFirst()) {

            do {
                System.out.println("counter AAHH: "+counter_high_sb);
                counter_high_sb++;
                if (counter_high_sb <= test.result) {
                    HashMap<String, String> student = new HashMap<>();
                    student.put("students_answer101", cursor.getString(cursor.getColumnIndex(students_answer101)));
                    student.put("students_score101", cursor.getString(cursor.getColumnIndex(students_score101)));
                    student.put("students_answerID101", cursor.getString(cursor.getColumnIndex(students_answerID101)));
                    studentList.add(student);
                    System.out.println("Result High: " + studentList);
                    highdata = cursor.getString(cursor.getColumnIndex(students_answer101));

                    StringTokenizer tokens = new StringTokenizer(highdata, "[]");
                    for (int i = 0; i < 1; i++) {
                        halftokhigh = tokens.nextToken();

                    }

                    StringTokenizer tokens1 = new StringTokenizer(halftokhigh, ", ");
                    int numberofmid = 1;
                    int totalcount = Integer.parseInt(test.listview_total_count);
                    for (int i = 0; i < totalcount; i++) {
                        highdone = tokens1.nextToken();
                        if (i == test.counthigh) {
                            highfinal = highdone;
                            if (highfinal.equals("1")) {
                                highcountcor++;
                            } else if (highfinal.equals("0")) {
                                highcountincor++;
                            }
                        }
                    }
                }
            }
            while (cursor.moveToNext());

        }
        return studentList;
    }

    public ArrayList<HashMap<String, String>> Getresulthighcounter(String studnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT " + students_answer101 + "," + students_score101 + " ," + students_answerID101 + " FFROM " + tbl_students_answer101 + " WHERE " + students_examname101 + " ='" + studnum + "'";
        Cursor cursor = db.query(tbl_students_answer101, new String[]{students_answer101, students_score101, students_answerID101}, students_examname101 + "=?", new String[]{String.valueOf(studnum)}, null, null, students_answer101 + " DESC", null);


        if (cursor.moveToFirst()) {
            do {
                counter_high++;
                if (counter_high <= test.result) {
                    HashMap<String, String> student = new HashMap<>();
                    student.put("students_answer101", cursor.getString(cursor.getColumnIndex(students_answer101)));
                    student.put("students_score101", cursor.getString(cursor.getColumnIndex(students_score101)));
                    student.put("students_answerID101", cursor.getString(cursor.getColumnIndex(students_answerID101)));
                    studentList.add(student);
                    System.out.println("Result High: " + studentList);
                    highdata = cursor.getString(cursor.getColumnIndex(students_answer101));
                    StringTokenizer tokens = new StringTokenizer(highdata, "[]");
                    high = cursor.getString(cursor.getColumnIndex(students_answerID101));
                    sbforhigh.append(high + ",");
                    System.out.println("counterhigh " + sbforhigh);

                }

            }
            while (cursor.moveToNext());

        }
        return studentList;
    }

    public ArrayList<HashMap<String, String>> Getresultlow(String studnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT " + students_answer101 + "," + students_score101 + "," + students_answerID101 + " FROM " + tbl_students_answer101 + " WHERE " + students_examname101 + " ='" + studnum + "'";
        Cursor cursor = db.query(tbl_students_answer101, new String[]{students_answer101, students_score101, students_answerID101}, students_examname101 + "=?", new String[]{String.valueOf(studnum)}, null, null, students_answer101 + " ASC", null);

        lowcountcor = 0;
        lowcountincor = 0;
        if (cursor.moveToFirst()) {
            do {
                counter_low_sb++;
                if (counter_low_sb <= test.result) {
                    HashMap<String, String> student = new HashMap<>();
                    student.put("students_answer101", cursor.getString(cursor.getColumnIndex(students_answer101)));
                    student.put("students_score101", cursor.getString(cursor.getColumnIndex(students_score101)));
                    student.put("students_answerID101", cursor.getString(cursor.getColumnIndex(students_answerID101)));
                    studentList.add(student);
                    lowdata = cursor.getString(cursor.getColumnIndex(students_answer101));
                    System.out.println("Result low: " + lowdata);


                    StringTokenizer tokens = new StringTokenizer(lowdata, "[]");
                    for (int i = 0; i < 1; i++) {
                        halftoklow = tokens.nextToken();
                    }
                    System.out.println("halftoklow " + halftoklow);
                    StringTokenizer tokens1 = new StringTokenizer(halftoklow, ", ");
                    int numberofmid = 1;
                    int totalcount = Integer.parseInt(test.listview_total_count);
                    for (int i = 0; i < totalcount; i++) {

                        lowdone = tokens1.nextToken();
                        System.out.println("lowdone : " + lowdone);
                        if (i == test.countlow) {
                            lowfinal = lowdone;
                            if (lowfinal.equals("1")) {
                                System.out.println("correct");
                                lowcountcor++;
                            } else if (lowfinal.equals("0")) {
                                System.out.println("notcorrect");
                                lowcountincor++;
                            }
                        }
                    }

                }


            }
            while (cursor.moveToNext());


        }
        return studentList;
    }

    public ArrayList<HashMap<String, String>> Getresultlowcounter(String studnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT " + students_answer101 + "," + students_score101 + "," + students_answerID101 + " FROM " + tbl_students_answer101 + " WHERE " + students_examname101 + " ='" + studnum + "'";
        Cursor cursor = db.query(tbl_students_answer101, new String[]{students_answer101, students_score101, students_answerID101}, students_examname101 + "=?", new String[]{String.valueOf(studnum)}, null, null, students_answer101 + " ASC", null);

        if (cursor.moveToFirst()) {
            do {
                counter_low++;
                if (counter_low <= test.result) {
                    HashMap<String, String> student = new HashMap<>();
                    student.put("students_answer101", cursor.getString(cursor.getColumnIndex(students_answer101)));
                    student.put("students_score101", cursor.getString(cursor.getColumnIndex(students_score101)));
                    student.put("students_answerID101", cursor.getString(cursor.getColumnIndex(students_answerID101)));
                    studentList.add(student);
                    lowdata = cursor.getString(cursor.getColumnIndex(students_answer101));
                    System.out.println("Result low: " + lowdata);
                    StringTokenizer tokens = new StringTokenizer(lowdata, "[]");
                    low = cursor.getString(cursor.getColumnIndex(students_answerID101));
                    sbforlow.append(low + ",");
                    System.out.println("counterlow " + sbforlow);
                }

            }
            while (cursor.moveToNext());


        }
        return studentList;
    }

    public ArrayList<HashMap<String, String>> Getresultmidcancelhigh(String studnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        midhigh = new ArrayList<>();
        String query = "SELECT " + students_answer101 + "," + students_score101 + "," + students_answerID101 + " FROM " + tbl_students_answer101 + " WHERE " + students_examname101 + " ='" + studnum + "'";
        Cursor cursor = db.query(tbl_students_answer101, new String[]{students_answer101, students_score101, students_answerID101}, students_examname101 + "=?", new String[]{String.valueOf(studnum)}, null, null, students_answer101 + " ASC", null);
        //   "select * from "+CONTACTS_TABLE_NAME+" WHERE salary BETWEEN '10' AND '10000' ", null );


                    countcor = 0;
                    countincor = 0;


                    String queselect = "";
                    int e;
                    for (e = 0; e < answers.size(); e++) {

                        queselect = "SELECT " + students_answer101 + " FROM " + tbl_students_answer101 + " WHERE " + students_answerID101 + " = '" + answers.get(e) + "'";

                        SQLiteDatabase dbselect = this.getReadableDatabase();
                        Cursor res = dbselect.rawQuery(queselect, null);
                        System.out.println("query:: " + queselect);
                        if (res.moveToFirst()) {
                            userIDmid = (res.getString(0));
                        }
                        StringTokenizer tokens = new StringTokenizer(userIDmid, "[]");

                        for (int i = 0; i < 1; i++) {
                            halftok = tokens.nextToken();
                        }
                        String a = halftok;
                        System.out.println("bbbb " + a);
                        StringTokenizer tokens1 = new StringTokenizer(a, ", ");
                        int numberofmid = 1;
                        int totalcount = Integer.parseInt(test.listview_total_count);

                        for (int i = 0; i < totalcount; i++) {
                            midone = tokens1.nextToken();
                            if (i == test.countmid) {
                                mifinal = midone;
                                if (mifinal.equals("1")) {
                                    countcor++;
                                } else if (mifinal.equals("0")) {
                                    countincor++;
                                }
                            }
                        }

                            // sbforid.append(cursor.getString(cursor.getColumnIndex(students_number101))+ ",");

                }


        return midhigh;
    }

    public ArrayList<HashMap<String, String>> Getexamidformid(String studnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        ArrayList<HashMap<String, String>> studentList2 = new ArrayList<>();
        Cursor cursor = db.query(tbl_students_answer101, new String[]{students_answer101, students_score101, students_answerID101}, students_examname101 + "=?", new String[]{String.valueOf(studnum)}, null, null, students_answer101 + " DESC", null);
        Cursor cursor2 = db2.query(tbl_students_answer101, new String[]{students_answer101, students_score101, students_answerID101}, students_examname101 + "=?", new String[]{String.valueOf(studnum)}, null, null, students_answer101 + " ASC", null);
        answers = new ArrayList<>();
        counter_mid = 0;
        if (cursor.moveToFirst() && cursor2.moveToFirst()) {
            do {
                //  counter_low++;
                counter_mid++;

                String samplehigh = cursor2.getString(cursor.getColumnIndex(students_answerID101));
                String samplelow = cursor2.getString(cursor2.getColumnIndex(students_answerID101));
                int totalcount = Integer.parseInt(test.listview_total_count);
                int counterminus = 0;
                if (counter_mid <= test.result) {

                    sb_getlow.append(samplelow + ",");
                    System.out.println("sb_getlow " + sb_getlow);
                    sb_gethigh.append(samplehigh + ",");
                    System.out.println("sb_gethigh " + sb_gethigh);
                } else if (counter_mid < counter_item-(test.result-1)) {
                    System.out.println("counteritem " + counter_item);
                    System.out.println("HAPPY NEW YEAR");
                    sb_gethighformid.append(samplehigh + ",");
                    sb_getlowformid.append(samplelow + ",");
                    System.out.println("HANDA: " + sb_gethighformid);
                    System.out.println("HONDO: " + sb_getlowformid);
                    System.out.println("storeletsgo "+samplelow);

                    answers.add(Integer.valueOf(samplelow));
                }
                System.out.println("answers1011 "+answers);

            }
            while (cursor.moveToNext() && cursor2.moveToNext());
        }

        return studentList;
    }
    public ArrayList<HashMap<String, String>> GetScorForPerformance(String classname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT " + students_answer101 + "," + students_score101 + " FROM " + tbl_students_answer101 + " WHERE " + students_class101 + " ='" + classname + "'";
        Cursor cursor = db.query(tbl_students_answer101, new String[]{students_score101}, students_class101 + "=?", new String[]{String.valueOf(classname)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                student.put("students_score101", cursor.getString(cursor.getColumnIndex("students_score101")));
                String scoree =  cursor.getString(cursor.getColumnIndex("students_score101"));
                System.out.println("LETSGOO2SCORE" + scoree);
                studentList.add(student);
            }
            while (cursor.moveToNext());

        }
        return studentList;
        }
}


