package com.chekka.capstone;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class EditStudent extends AppCompatActivity {
    EditText studname_edit, studnumber_edit, studemail_edit, studclass_edit;
    RelativeLayout update_stud, delete_stud;
    ArrayList<HashMap<String, String>> studentList;
    SQLiteHelper mydb;
     ListView student_lv;
    ListAdapter student_adapter;
    String listview_student_str,listview_studentname_str,listview_studentemail_str,listview_studentclass_str;
    RelativeLayout layout_imagetesting,cross;
    ImageView finish_dastud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editstudent);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        student_lv = (ListView) findViewById(R.id.listview_studentname);
        mydb = new SQLiteHelper(this);
        finish_dastud = findViewById(R.id.finish_dastud);
        layout_imagetesting = findViewById(R.id.layout_imagetesting);
        cross = findViewById(R.id.cross);
        studname_edit = findViewById(R.id.studentname_edittext);
        studnumber_edit = findViewById(R.id.studentnumber_edittext);
        studemail_edit = findViewById(R.id.studentemail_edittext);
        studclass_edit = findViewById(R.id.studentclass_edittext);

        update_stud = findViewById(R.id.update_stud);
        delete_stud = findViewById(R.id.delete_stud);

        studname_edit.setEnabled(false);
        studnumber_edit.setEnabled(false);
        studemail_edit.setEnabled(false);
        studclass_edit.setEnabled(false);

        update_stud.setEnabled(false);
        delete_stud.setEnabled(false);

        student_lv = (ListView) findViewById(R.id.listview_studentname);

        studentList = mydb.GetStudent(Chekka.textView_id_LO.getText().toString());
        student_lv = (ListView) findViewById(R.id.listview_studentname);
        student_adapter = new SimpleAdapter(EditStudent.this, studentList, R.layout.list_item_for_student, new String[]{"studentsname", "studentsID", "studentsclass"}, new int[]{R.id.item_student_name_ST, R.id.item_student_number_ST, R.id.item_student_class_ST});
        student_lv.setAdapter(student_adapter);
        student_lv.setVisibility(View.VISIBLE);

        studname_edit.setText("");
        studnumber_edit.setText("");
        studclass_edit.setText("");
        studemail_edit.setText("");

        student_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                layout_imagetesting.setVisibility(View.VISIBLE);
                student_lv.setEnabled(false);
                studname_edit.setEnabled(true);
                studnumber_edit.setEnabled(true);
                studemail_edit.setEnabled(true);
                studclass_edit.setEnabled(true);

                update_stud.setEnabled(true);
                delete_stud.setEnabled(true);

                listview_student_str = studentList.get(position).get("studentsID");
                listview_studentname_str = studentList.get(position).get("studentsname");
                listview_studentemail_str = studentList.get(position).get("studentsemail");
                listview_studentclass_str = studentList.get(position).get("studentsclass");

                System.out.println("id = " + listview_student_str);
                System.out.println("name = " + listview_studentname_str);
                System.out.println("number = " + listview_studentemail_str);
                System.out.println("class = " + listview_studentclass_str);

                studname_edit.setText("" + listview_studentname_str);
                studnumber_edit.setText("" + listview_student_str);
                studclass_edit.setText("" + listview_studentclass_str);
                studemail_edit.setText("" + listview_studentemail_str);
            }

        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_imagetesting.setVisibility(View.INVISIBLE);
                student_lv.setEnabled(true);
                studname_edit.setEnabled(false);
                studnumber_edit.setEnabled(false);
                studemail_edit.setEnabled(false);
                studclass_edit.setEnabled(false);

                update_stud.setEnabled(true);
                delete_stud.setEnabled(true);
            }
        });
        update_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydb.update_studentanswerkey(studnumber_edit.getText().toString(),listview_student_str);
                System.out.println("id2 = " + studnumber_edit.getText().toString());
                try {

                    mydb.update_studentedit(studnumber_edit.getText().toString(), studname_edit.getText().toString(), studemail_edit.getText().toString(), studclass_edit.getText().toString(), Chekka.textView_id_LO.getText().toString(), listview_student_str);
                } catch (Exception e) {
                    System.out.println("problem: " + e);
                    Toast.makeText(getApplicationContext(), "Student number already registered", Toast.LENGTH_SHORT).show();
                }
                studentList = mydb.GetStudent(Chekka.textView_id_LO.getText().toString());
                student_lv = (ListView) findViewById(R.id.listview_studentname);
                student_adapter = new SimpleAdapter(EditStudent.this, studentList, R.layout.list_item_for_student, new String[]{"studentsname", "studentsID", "studentsclass"}, new int[]{R.id.item_student_name_ST, R.id.item_student_number_ST, R.id.item_student_class_ST});
                student_lv.setAdapter(student_adapter);
                student_lv.setVisibility(View.VISIBLE);
                studname_edit.setText("");
                studnumber_edit.setText("");
                studclass_edit.setText("");
                studemail_edit.setText("");
                layout_imagetesting.setVisibility(View.INVISIBLE);
                student_lv.setEnabled(true);
                studname_edit.setEnabled(false);
                studnumber_edit.setEnabled(false);
                studemail_edit.setEnabled(false);
                studclass_edit.setEnabled(false);

                update_stud.setEnabled(true);
                delete_stud.setEnabled(true);
            }
        });
        finish_dastud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });
        delete_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Are you sure?");

                //SET THE MESSAGE
                builder.setMessage("If yes, student information will be also deleted.");

                //WE DEFINE THE FUNCTION, WHEN THE USER CHOOSES "YES"
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        //HERE GOES THE LOGIC  OF THE APPLICATION, IF THE USER SELECTS A POSITIVE CHOICE
                        //TO TEST IT, WE CAN SET THE VALUE OF THE TEXT VIEW
                        //LET'S SET THE TEXT TO YES
                        System.out.println("YES");
                        mydb.delete_students(listview_student_str);
                        studentList = mydb.GetStudent(Chekka.textView_id_LO.getText().toString());
                        student_lv = (ListView) findViewById(R.id.listview_studentname);
                        student_adapter = new SimpleAdapter(EditStudent.this, studentList, R.layout.list_item_for_student, new String[]{"studentsname", "studentsID", "studentsclass"}, new int[]{R.id.item_student_name_ST, R.id.item_student_number_ST, R.id.item_student_class_ST});
                        student_lv.setAdapter(student_adapter);
                        student_lv.setVisibility(View.VISIBLE);
                        studname_edit.setText("");
                        studnumber_edit.setText("");
                        studclass_edit.setText("");
                        studemail_edit.setText("");
                        layout_imagetesting.setVisibility(View.INVISIBLE);
                        student_lv.setEnabled(true);
                        studname_edit.setEnabled(false);
                        studnumber_edit.setEnabled(false);
                        studemail_edit.setEnabled(false);
                        studclass_edit.setEnabled(false);

                        update_stud.setEnabled(true);
                        delete_stud.setEnabled(true);
                    }
                });


                //DEFINE THE FUNCTION, IF THE USERE CLICKS 'NO', OR SELECTS THE NEGATIVE CHOICE
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        studname_edit.setText("");
                        studnumber_edit.setText("");
                        studclass_edit.setText("");
                        studemail_edit.setText("");
                        layout_imagetesting.setVisibility(View.INVISIBLE);
                        student_lv.setEnabled(true);
                        studname_edit.setEnabled(false);
                        studnumber_edit.setEnabled(false);
                        studemail_edit.setEnabled(false);
                        studclass_edit.setEnabled(false);

                        update_stud.setEnabled(true);
                        delete_stud.setEnabled(true);

                        System.out.println("NO");
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
        //SET THE TITLE OF THE DIALOG
    }
}

