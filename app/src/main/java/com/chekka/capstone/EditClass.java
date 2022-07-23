package com.chekka.capstone;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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


public class EditClass extends AppCompatActivity {
    EditText studname_edit, studnumber_edit, studemail_edit, studclass_edit;
    RelativeLayout update_stud, delete_stud;
    ArrayList<HashMap<String, String>> userList;
    SQLiteHelper mydb;
     ListView lv;
    ListAdapter adapter;
    RelativeLayout layout_imagetesting,cross;
    String listview_student_str,listview_studentname_str,listview_studentemail_str,listview_studentclass_str,listview_classid_str;
    ImageView finish_dastud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editclass);
        finish_dastud = findViewById(R.id.finish_dastud);
        lv = (ListView) findViewById(R.id.listview_classname);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        mydb = new SQLiteHelper(this);
        layout_imagetesting = findViewById(R.id.layout_imagetesting);
        cross = findViewById(R.id.cross);
        studnumber_edit = findViewById(R.id.studentnumber_edittext);

        update_stud = findViewById(R.id.update_stud);
        delete_stud = findViewById(R.id.delete_stud);


        studnumber_edit.setEnabled(false);


        update_stud.setEnabled(false);
        delete_stud.setEnabled(false);

        lv = (ListView) findViewById(R.id.listview_classname);

        userList = mydb.GetClassName_with_no_of_students(Chekka.textView_id_LO.getText().toString());
        adapter = new SimpleAdapter(EditClass.this, userList, R.layout.list_item, new String[]{"classname_with_no_of_students", "classstudents_with_no_of_students"}, new int[]{R.id.item_class_name_CL, R.id.item_noofstudents_CL});
        lv.setAdapter(adapter);


        studnumber_edit.setText("");


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                layout_imagetesting.setVisibility(View.VISIBLE);
                studnumber_edit.setEnabled(true);


                update_stud.setEnabled(true);
                delete_stud.setEnabled(true);

                listview_classid_str = userList.get(position).get("classID_with_no_of_students");

                listview_student_str = userList.get(position).get("classname_with_no_of_students");
                listview_studentname_str = userList.get(position).get("classstudents_with_no_of_students");
                listview_studentemail_str = userList.get(position).get("UserID_with_no_of_students");


                System.out.println("id = "+listview_student_str);
                System.out.println("name = "+listview_studentname_str);
                System.out.println("number = "+listview_studentemail_str);
                System.out.println("class = "+listview_classid_str);

                studnumber_edit.setText(""+listview_student_str);

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
                lv.setEnabled(true);

                update_stud.setEnabled(true);
                delete_stud.setEnabled(true);
            }
        });
        update_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              try {
                  mydb.update_studit(studnumber_edit.getText().toString(),listview_student_str);
                  mydb.update_classedit(listview_classid_str,studnumber_edit.getText().toString(), listview_studentname_str, Chekka.textView_id_LO.getText().toString());
                  System.out.println("peace");

               }catch (Exception e){
                  Toast.makeText(getApplicationContext(), "Class Name already registered", Toast.LENGTH_SHORT).show();
              }
                lv = (ListView) findViewById(R.id.listview_classname);
                userList = mydb.GetClassName_with_no_of_students(Chekka.textView_id_LO.getText().toString());
                adapter = new SimpleAdapter(EditClass.this, userList, R.layout.list_item, new String[]{"classname_with_no_of_students", "classstudents_with_no_of_students"}, new int[]{R.id.item_class_name_CL, R.id.item_noofstudents_CL});
                lv.setAdapter(adapter);
                layout_imagetesting.setVisibility(View.INVISIBLE);


                lv.setEnabled(true);

                studnumber_edit.setEnabled(false);


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
                        mydb.delete_classname(listview_classid_str);
                        mydb.deleteclassunder(listview_student_str);

                        studnumber_edit.setText("");

                        layout_imagetesting.setVisibility(View.INVISIBLE);
                        lv.setEnabled(true);
                        lv = (ListView) findViewById(R.id.listview_classname);

                        userList = mydb.GetClassName_with_no_of_students(Chekka.textView_id_LO.getText().toString());
                        adapter = new SimpleAdapter(EditClass.this, userList, R.layout.list_item, new String[]{"classname_with_no_of_students", "classstudents_with_no_of_students"}, new int[]{R.id.item_class_name_CL, R.id.item_noofstudents_CL});
                        lv.setAdapter(adapter);

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
                        lv.setEnabled(true);
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

