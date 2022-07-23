package com.chekka.capstone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.chekka.capstone.Util.sout;

public class Chekka_dashboard extends AppCompatActivity {
    private BottomNavigationView bottomasback;
    RelativeLayout exportexcel, student_edit, viewall,itemanalysisbutton,button_classes_DA, paper;
    ImageView settings_dastud, finishdastud;
    ArrayList<HashMap<String, String>> userList;
    SQLiteHelper mydb;
    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //
        setContentView(R.layout.chekka_dashboard);
        mydb = new SQLiteHelper(this);
        settings_dastud = findViewById(R.id.settings_dastud);
        finishdastud = findViewById(R.id.finish_dastud);
        exportexcel =(RelativeLayout) findViewById(R.id.exportexcel);
        paper =(RelativeLayout) findViewById(R.id.paper);
        student_edit =(RelativeLayout) findViewById(R.id.student_edit);
        viewall =(RelativeLayout) findViewById(R.id.viewall);
        button_classes_DA = (RelativeLayout) findViewById(R.id.ButtonCLASSES_DA);
        itemanalysisbutton = (RelativeLayout) findViewById(R.id.itemanalysisbutton);

        settings_dastud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chekka_dashboard.this, Chekka_settings.class);
                startActivity(intent);
            }
        });
        finishdastud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chekka.bottom_home_dash.setSelectedItemId(R.id.home);
                finish();
            }
        });
        itemanalysisbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chekka_dashboard.this, test.class);
                startActivity(intent);
            }
        });
        button_classes_DA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chekka_dashboard.this, EditClass.class);
                startActivity(intent);
            }
        });
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chekka_dashboard.this, EditExam.class);
                startActivity(intent);
            }
        });
        student_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chekka_dashboard.this, EditStudent.class);
                startActivity(intent);
            }
        });
        exportexcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chekka_dashboard.this, ExportExcelResult.class);
                startActivity(intent);
            }
        });
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userList = mydb.GetClassName_with_no_of_students(Chekka.textView_id_LO.getText().toString());
                if(userList.isEmpty()){
                    sout("true ba");
                    Toast.makeText(Chekka_dashboard.this, "Result folder is empty.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Chekka_dashboard.this, Chekka_dashboard_classes.class);
                    startActivity(intent);
                    SQLiteHelper.counter_class=0;
                }
            }
        });
    }


}


