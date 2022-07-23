package com.chekka.capstone;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.chekka.capstone.Util.sout;


public class Chekka_addclass extends AppCompatActivity {
    private BottomNavigationView bottomasback;
    TextView TextViewChanging_Student, button_ok_CL;
    TextView EditText_classname_CL;
    SQLiteHelper mydb;
    SQLiteDatabase sqLiteDatabaseObj;
    String Holder_ClassName_CL, Holder_ClassUserID_CL,Holder_TemporaryClassName_CL = "Not_Found";
    Cursor cursor;
    ImageView header_backfordash_CH;
    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        setContentView(R.layout.chekka_addclass);


        bottomasback = (BottomNavigationView) findViewById(R.id.bottom_as_back);
        bottomasback.setVisibility(View.VISIBLE);
        TextViewChanging_Student = (TextView) findViewById(R.id.TextViewChanging_Student);
        //bottomasback.setVisibility(View.VISIBLE);
       // TextViewChanging_Student.setText("Add Class");
        button_ok_CL = (TextView) findViewById(R.id.ButtonCLOkay_CL);
        EditText_classname_CL= (TextView) findViewById(R.id.EditTextEnterClassName_CL);
        mydb = new SQLiteHelper(this);
        button_ok_CL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CHINGCHONG"+EditText_classname_CL.getText().toString());
                builder.setTitle("Add Class");

                builder.setView(input);
                //WE DEFINE THE FUNCTION, WHEN THE USER CHOOSES "YES"
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(input.getText().toString().isEmpty()){
                            Toast.makeText(Chekka_addclass.this, "Empty", Toast.LENGTH_LONG).show();
                        } else {
                            Holder_ClassName_CL = input.getText().toString();
                            SQLiteDataBaseBuild();
                            SQLiteTableBuild_CL();
                            CheckingClassNameAlreadyExistsOrNot_CL();
                        }
                        //HERE GOES THE LOGIC  OF THE APPLICATION, IF THE USER SELECTS A POSITIVE CHOICE
                        //TO TEST IT, WE CAN SET THE VALUE OF THE TEXT VIEW
                        //LET'S SET THE TEXT TO YES


                    }
                });


                //DEFINE THE FUNCTION, IF THE USERE CLICKS 'NO', OR SELECTS THE NEGATIVE CHOICE
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        //HERE YOU DEFINE THE LOGIC, IF THE USER SELECTS OR CLICKS THE 'NO' BUTTON
                        //USUALLY, NOTHING IS DEFINED HERE, BUT DEPENDS ON YOUR DECISION

                        //HERE, LET'S SET THE TEXT TO NO
                    }
                });

                //NOW, WE CREATE THE ALERT DIALG OBJECT
                AlertDialog ad = builder.create();

                //FINALLY, WE SHOW THE DIALOG
                ad.show();

            }
        });

    }

    public void SQLiteDataBaseBuild() {

        sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // SQLite table build method.
    public void SQLiteTableBuild_CL() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+mydb.tbl_class+" ("+mydb.classID+" INTEGER PRIMARY KEY, "+mydb.classname+" VARCHAR NOT NULL,"+mydb.UserID+" INTEGER NOT NULL," +
        //"FOREIGN KEY(UserID) REFERENCES tbl_users(userID))");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.tbl_class_with_no_of_students + " (" + mydb.classID_with_no_of_students + " INTEGER PRIMARY KEY, " + mydb.classname_with_no_of_students + " VARCHAR NOT NULL, " + mydb.classstudents_with_no_of_students + " VARCHAR NOT NULL, " + mydb.UserID_with_no_of_students + " INTEGER NOT NULL, " + "FOREIGN KEY(UserID_with_no_of_students) REFERENCES tbl_users(UserID))");
        // String CREATE_TABLE_CLASS_WITH_NO_OF_STUDENTS="CREATE TABLE IF NOT EXISTS "+mydb.tbl_class_with_no_of_students+" ("+classID_with_no_of_students+" INTEGER PRIMARY KEY, "+classname_with_no_of_students+", VARCHAR NOT NULL, "+classstudents_with_no_of_students+", INTEGER PRIMARY KEY, "+UserID_with_no_of_students+", INTEGER NOT NULL, "+ "FOREIGN KEY(UserID) REFERENCES tbl_users(userID))";

    }
    public void CheckingClassNameAlreadyExistsOrNot_CL() {

        Holder_ClassUserID_CL = Chekka.textView_id_LO.getText().toString();
        // Opening SQLite database write permission.
     //   sqLiteDatabaseObj = mydb.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(mydb.tbl_class_with_no_of_students, null, " " + mydb.classname_with_no_of_students + "=?", new String[]{Holder_ClassName_CL}, " " + mydb.UserID_with_no_of_students + "= " + Holder_ClassUserID_CL, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.

                System.out.println();
                Holder_TemporaryClassName_CL = "Email Found";
                System.out.println("Email Found 101" + mydb.classname_with_no_of_students);

                // Closing cursor.
                cursor.close();
            }

        }
        System.out.println("Email Found 102" + mydb.classname_with_no_of_students);
        CheckFinalResultClassName_CL();
        // Calling method to check final result and insert data into SQLite database.


    }
    public void CheckFinalResultClassName_CL() {

        // Checking whether email is already exists or not.
        if (Holder_TemporaryClassName_CL.equalsIgnoreCase("Email Found")) {
            // If email is exists then toast msg will display.
            EditText_classname_CL.setText("");

            Toast.makeText(Chekka_addclass.this, "Class Name Already Exists. Check it out!", Toast.LENGTH_LONG).show();

        } else {
            // If email already dose n't exists then user registration details will entered to SQLite database.
            if (mydb.UserID_with_no_of_students != Holder_ClassUserID_CL) {
                InsertClassNameIntoSQLiteDatabase_CL();
            }
        }
        Holder_TemporaryClassName_CL = "Not_Found";
    }
    public void InsertClassNameIntoSQLiteDatabase_CL() {

        // If editText is not empty then this block will executed.

        // SQLite query to insert data into table.
        System.out.println("CLASSNAME" + Chekka.textView_id_LO.getText().toString());
        String a = "0";
        mydb.insert_class_details(EditText_classname_CL.getText().toString(), Chekka.textView_id_LO.getText().toString(), a);

        // Printing toast message after done inserting.
        Toast.makeText(Chekka_addclass.this, "Added Class Successfully", Toast.LENGTH_LONG).show();
        EditText_classname_CL.setText("");
        finish();
        // This block will execute if any of the registration EditText is empty.

    }


}


