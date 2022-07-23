package com.chekka.capstone;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.chekka.capstone.Util.sout;


public class Chekka_dashboard_student extends AppCompatActivity {
    SQLiteHelper mydb;
    ImageView header_backfordash_CH;
    String listview_student_str,listview_studentname_str,listview_studentemail_str;
    RelativeLayout layout_popforclass_PP;
    FrameLayout header_student_dash_CH;
    TextView button_viewclass_PP, button_editclass_PP;
    ImageView button_closeclass_PP;
    public static String listview_str;
    public static ArrayList<HashMap<String, String>> studentList;
    TextView TextViewChanging_CH, cn_stud, nu_stud;
    public static ArrayList<HashMap<String, String>> studentinfolist;
    ImageView finish_dastud, back_choose;
    TextView numberstud;
    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //
        setContentView(R.layout.chekka_dashboard_student);
        mydb = new SQLiteHelper(this);
        header_student_dash_CH = (FrameLayout) findViewById(R.id.header_student_dash_CH);
        button_viewclass_PP = (TextView) findViewById(R.id.buttxt_viewclassname_PP);
        button_editclass_PP = (TextView) findViewById(R.id.buttxt_editclassname_PP);
        button_closeclass_PP = (ImageView) findViewById(R.id.button_closepopforclass_PP);
        header_backfordash_CH = (ImageView) findViewById(R.id.header_backfordash_CH);
        header_student_dash_CH = (FrameLayout) findViewById(R.id.header_student_dash_CH);
        layout_popforclass_PP = (RelativeLayout) findViewById(R.id.popforclass_PP);
        TextViewChanging_CH = (TextView) findViewById(R.id.TextViewChanging_CH);

        cn_stud = (TextView) findViewById(R.id.cn_stud);
        cn_stud.setText(""+listview_str);
        numberstud = findViewById(R.id.numberstud);
        numberstud.setText(""+SQLiteHelper.counter_stud);
        finish_dastud = (ImageView) findViewById(R.id.finish_dastud);
        finish_dastud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

                try {
                        TextViewChanging_CH.setText("Students of "+listview_str);
                        header_student_dash_CH.setVisibility(View.VISIBLE);
                   // System.out.println("HEHEHEHHE "+mydb.GetScorForPerformance(listview_str));
                        Chekka.studentList = mydb.GetStudentName(Chekka.textView_id_LO.getText().toString(), listview_str);
                        Chekka.student_lv = (ListView) findViewById(R.id.listview_studentname);
                        Chekka.student_adapter = new SimpleAdapter(Chekka_dashboard_student.this, Chekka.studentList, R.layout.list_item_for_student, new String[]{"studentsname", "studentsID", "studentsclass"}, new int[]{R.id.item_student_name_ST, R.id.item_student_number_ST, R.id.item_student_class_ST});
                        Chekka.student_lv.setAdapter(Chekka.student_adapter);
                        Chekka.student_lv.setVisibility(View.VISIBLE);
                        header_student_dash_CH.setVisibility(View.VISIBLE);
                        System.out.println("studentlist sa view class" + Chekka.studentList);


                } catch (Exception e) {
                    Toast.makeText(Chekka_dashboard_student.this, "Empty Score", Toast.LENGTH_LONG).show();
                    sout("error 3:" + e);
                    sout("error 4:" + e.toString());

                }
        Chekka.student_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                //By using data id
                //layout_popforclass_PP.setVisibility(View.VISIBLE);
                // listview_class_str = userList.get(position).get("classname_with_no_of_students");

                // listview_student_str = studentList.get(position).get("studentsclass");
                studentList = mydb.GetStudentName(Chekka.textView_id_LO.getText().toString(), listview_str);
                listview_student_str = Chekka.studentList.get(position).get("studentsID");
                listview_studentname_str = Chekka.studentList.get(position).get("studentsname");
                listview_studentemail_str = Chekka.studentList.get(position).get("studentsemail");
                  Chekka_dashboard_student_info.listview_student_str= listview_student_str;
                  Chekka_dashboard_student_info.listview_studentname_str = listview_studentname_str;
                  Chekka_dashboard_student_info.listview_studentemail_str = listview_studentemail_str;
                System.out.println("studentlist" + listview_studentemail_str);
                // System.out.println("listview_class_str"+listview_class_str);

                studentinfolist = mydb.GetStudentInfo(listview_student_str);
                System.out.println("studentlistchecking"+studentinfolist);
                try {
                    String b = "yey";
                    studentinfolist = mydb.GetStudentInfo(listview_student_str);
                } catch (Exception e) {
                    Toast.makeText(Chekka_dashboard_student.this, "Empty Score", Toast.LENGTH_LONG).show();
                    sout("error 1:" + e);
                }
                try {
                    if (studentinfolist.isEmpty()) {
                        Toast.makeText(Chekka_dashboard_student.this, "Empty Score", Toast.LENGTH_LONG).show();
                        sout("error 2:");
                    } else {
                       // Chekka_dashboard_student.listview_str = listview_class_str;
                        Intent intent = new Intent(Chekka_dashboard_student.this, Chekka_dashboard_student_info.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(Chekka_dashboard_student.this, "Empty Score", Toast.LENGTH_LONG).show();
                    sout("error 3:" + e);
                    sout("error 4:" + e.toString());
                }

            }

        });
    }

}


