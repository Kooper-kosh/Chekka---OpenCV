package com.chekka.capstone;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.chekka.capstone.Util.sout;


public class Chekka_dashboard_classes extends AppCompatActivity {
    SQLiteHelper mydb;
    ImageView header_backfordash_CH;
    public static String listview_class_str;
    RelativeLayout layout_popforclass_PP;
    TextView button_viewclass_PP, button_editclass_PP;
    ImageView button_closeclass_PP;
    TextView nu_stud;
    ImageView finish_dastud;
    ArrayList<HashMap<String, String>> studentList;
    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //
        setContentView(R.layout.chekka_dashboard_classes);
        mydb = new SQLiteHelper(this);
        button_viewclass_PP = (TextView) findViewById(R.id.buttxt_viewclassname_PP);
        button_editclass_PP = (TextView) findViewById(R.id.buttxt_editclassname_PP);
        button_closeclass_PP = (ImageView) findViewById(R.id.button_closepopforclass_PP);
        header_backfordash_CH = (ImageView) findViewById(R.id.header_backfordash_CH);
        Chekka.userList = mydb.GetClassName_with_no_of_students(Chekka.textView_id_LO.getText().toString());
        Chekka.lv = (ListView) findViewById(R.id.listview_classname);
        Chekka.adapter = new SimpleAdapter(Chekka_dashboard_classes.this, Chekka.userList, R.layout.list_item, new String[]{"classname_with_no_of_students", "classstudents_with_no_of_students"}, new int[]{R.id.item_class_name_CL, R.id.item_noofstudents_CL});
        layout_popforclass_PP = (RelativeLayout) findViewById(R.id.popforclass_PP);
        Chekka.lv.setAdapter(Chekka.adapter);
        finish_dastud = (ImageView) findViewById(R.id.finish_dastud);
        nu_stud = (TextView) findViewById(R.id.nu_stud);
        nu_stud.setText(""+SQLiteHelper.counter_class);
       SQLiteHelper.counter_stud=0;
        System.out.println("cs__"+SQLiteHelper.counter_stud);
        Chekka.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                //By using data id
                //layout_popforclass_PP.setVisibility(View.VISIBLE);
                try {
                    listview_class_str = Chekka.userList.get(position).get("classname_with_no_of_students");

                    System.out.println("listview_class" + listview_class_str);
                    try {
                        String b = "yey";

                        Chekka.studentList = mydb.GetStudentName(Chekka.textView_id_LO.getText().toString(), listview_class_str);

                    } catch (Exception e) {
                        Toast.makeText(Chekka_dashboard_classes.this, "Empty Students", Toast.LENGTH_LONG).show();
                        sout("error 1:" + e);
                    }
                    try {
                        if (Chekka.studentList.isEmpty()) {
                            Toast.makeText(Chekka_dashboard_classes.this, "Empty Students", Toast.LENGTH_LONG).show();
                            sout("error 2:");
                        } else {
                            Chekka_dashboard_student.listview_str = listview_class_str;
                            Intent intent = new Intent(Chekka_dashboard_classes.this, Chekka_dashboard_student.class);
                            startActivity(intent);

                        }
                    } catch (Exception e) {
                        Toast.makeText(Chekka_dashboard_classes.this, "Empty Students", Toast.LENGTH_LONG).show();
                        sout("error 3:" + e);
                        sout("error 4:" + e.toString());

                    }
                    if (listview_class_str.equals(listview_class_str)) {
                       // layout_popforclass_PP.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {

                }
            }

        });
        finish_dastud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button_viewclass_PP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_popforclass_PP.setVisibility(View.INVISIBLE);
                try {
                    String b = "yey";
                    Chekka.studentList = mydb.GetStudentName(Chekka.textView_id_LO.getText().toString(), listview_class_str);
                } catch (Exception e) {
                    Toast.makeText(Chekka_dashboard_classes.this, "Empty Students", Toast.LENGTH_LONG).show();
                    sout("error 1:" + e);
                }
                try {
                    if (Chekka.studentList.isEmpty()) {
                        Toast.makeText(Chekka_dashboard_classes.this, "Empty Students", Toast.LENGTH_LONG).show();
                        sout("error 2:");
                    } else {
                        Chekka_dashboard_student.listview_str = listview_class_str;
                        Intent intent = new Intent(Chekka_dashboard_classes.this, Chekka_dashboard_student.class);
                        startActivity(intent);

                    }
                } catch (Exception e) {
                    Toast.makeText(Chekka_dashboard_classes.this, "Empty Students", Toast.LENGTH_LONG).show();
                    sout("error 3:" + e);
                    sout("error 4:" + e.toString());

                }
            }

        });

    }

}


