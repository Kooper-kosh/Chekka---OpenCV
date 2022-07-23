package com.chekka.capstone;


import android.app.AlertDialog;
import android.content.DialogInterface;
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


public class EditExam extends AppCompatActivity {
    EditText studname_edit, studnumber_edit, studemail_edit, studclass_edit;
    RelativeLayout update_stud, delete_stud;
    ArrayList<HashMap<String, String>> examlist;
    SQLiteHelper mydb;
     ListView exam_lv;
    ListAdapter examadapter;
    RelativeLayout layout_imagetesting;
    RelativeLayout cross;
    ImageView finish_dastud;
    String listview_student_str,listview_studentname_str,listview_studentemail_str,listview_studentclass_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editexam);
        mydb = new SQLiteHelper(this);
        cross = findViewById(R.id.cross);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        layout_imagetesting = findViewById(R.id.layout_imagetesting);
        studname_edit = findViewById(R.id.studentname_edittext);
        studnumber_edit = findViewById(R.id.studentnumber_edittext);
        studemail_edit = findViewById(R.id.studentemail_edittext);
        studclass_edit = findViewById(R.id.studentclass_edittext);
        finish_dastud = findViewById(R.id.finish_dastud);
        update_stud = findViewById(R.id.update_stud);
        delete_stud = findViewById(R.id.delete_stud);


        studnumber_edit.setEnabled(false);


        update_stud.setEnabled(false);
        delete_stud.setEnabled(false);

        exam_lv = (ListView) findViewById(R.id.listview_examsheet);

        examlist = mydb.GetExamName(Chekka.textView_id_LO.getText().toString());
        System.out.println("Examlist" + examlist);
        examadapter = new SimpleAdapter(EditExam.this, examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount});
        exam_lv.setAdapter(examadapter);
        exam_lv.setVisibility(View.VISIBLE);

        Chekka.examlist = mydb.GetExamName(Chekka.textView_id_LO.getText().toString());
        System.out.println("Examlist" + examlist);
        Chekka.examadapter = new SimpleAdapter(EditExam.this, examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount});
        Chekka.exam_lv.setAdapter(Chekka.examadapter);
        Chekka.exam_lv.setVisibility(View.VISIBLE);

        studnumber_edit.setText("");


        exam_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                layout_imagetesting.setVisibility(View.VISIBLE);

                studnumber_edit.setEnabled(true);


                update_stud.setEnabled(true);
                delete_stud.setEnabled(true);

                listview_student_str = examlist.get(position).get("final_exam_name");
                listview_studentname_str = examlist.get(position).get("final_exam_ID");

                System.out.println("id = "+listview_student_str);
                System.out.println("name = "+listview_studentname_str);
                System.out.println("number = "+listview_studentemail_str);
                System.out.println("class = "+listview_studentclass_str);


                studnumber_edit.setText(""+listview_student_str);

            }

        });
        update_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String examid = mydb.getexamname(studnumber_edit.getText().toString() );
                System.out.println("examidto"+examid);
                if(examid.isEmpty()){
                    try {
                        mydb.update_examanswers(studnumber_edit.getText().toString(),listview_student_str);
                        mydb.update_examedit(studnumber_edit.getText().toString(), listview_studentname_str);
                        layout_imagetesting.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {
                        System.out.println("problem: " + e);
                       // Toast.makeText(getApplicationContext(), "Exam name already registered", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Exam name already registered", Toast.LENGTH_SHORT).show();
                }
                examlist = mydb.GetExamName(Chekka.textView_id_LO.getText().toString());
                System.out.println("Examlist" + examlist);
                examadapter = new SimpleAdapter(EditExam.this, examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount});
                exam_lv.setAdapter(examadapter);
                exam_lv.setVisibility(View.VISIBLE);
                Chekka.examlist = mydb.GetExamName(Chekka.textView_id_LO.getText().toString());
                System.out.println("Examlist" + examlist);
                Chekka.examadapter = new SimpleAdapter(EditExam.this, examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount});
                Chekka.exam_lv.setAdapter(Chekka.examadapter);
                Chekka.exam_lv.setVisibility(View.VISIBLE);


            }
        });
        finish_dastud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
   finish();

            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_imagetesting.setVisibility(View.INVISIBLE);
                exam_lv.setEnabled(true);

                update_stud.setEnabled(true);
                delete_stud.setEnabled(true);
            }
        });
        delete_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Are you sure?");

                //SET THE MESSAGE
                builder.setMessage("If yes, students under this class name will be also deleted.");

                //WE DEFINE THE FUNCTION, WHEN THE USER CHOOSES "YES"
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        //HERE GOES THE LOGIC  OF THE APPLICATION, IF THE USER SELECTS A POSITIVE CHOICE
                        //TO TEST IT, WE CAN SET THE VALUE OF THE TEXT VIEW
                        //LET'S SET THE TEXT TO YES
                        System.out.println("YES");
                        mydb.deleteexam(listview_studentname_str);
                        mydb.deleteexamanswers(listview_student_str);
                        studnumber_edit.setText("");

                        layout_imagetesting.setVisibility(View.INVISIBLE);
                        exam_lv = (ListView) findViewById(R.id.listview_examsheet);

                        examlist = mydb.GetExamName(Chekka.textView_id_LO.getText().toString());
                        System.out.println("Examlist" + examlist);
                        examadapter = new SimpleAdapter(EditExam.this, examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount});
                        exam_lv.setAdapter(examadapter);
                        exam_lv.setVisibility(View.VISIBLE);
                        Chekka.examlist = mydb.GetExamName(Chekka.textView_id_LO.getText().toString());
                        System.out.println("Examlist" + examlist);
                        Chekka.examadapter = new SimpleAdapter(EditExam.this, examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount});
                        Chekka.exam_lv.setAdapter(Chekka.examadapter);
                        Chekka.exam_lv.setVisibility(View.VISIBLE);

                        studnumber_edit.setEnabled(false);


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
                        exam_lv.setEnabled(true);
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

    }

}

