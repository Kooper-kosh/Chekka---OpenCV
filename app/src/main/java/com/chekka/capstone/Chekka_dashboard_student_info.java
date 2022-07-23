package com.chekka.capstone;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import static com.chekka.capstone.Util.sout;


public class Chekka_dashboard_student_info extends AppCompatActivity {
    SQLiteHelper mydb;
    public static ListAdapter adapter, examadapter, studentinfoadapter;
    public static ListView student_lv, lv, exam_lv, student_info_lv;
    public static ArrayList<HashMap<String, String>> studentinfolistfordisplay;
    public static String listview_student_str,listview_studentname_str,listview_studentemail_str;
    TextView sna, snu, em;
    ImageView imageviewtesting;
    ImageView finish_dastud;
    String studentnumberforbitmap, studentnameforbitmap,studentaverageforbitmap,studentscoreforbitmap;
    File filepath, file;
    RelativeLayout layout_imagetesting;
    RelativeLayout savepic, gmailpic, cross;
    Bitmap bitmap;
    String what = "";
    String email;
    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //
        setContentView(R.layout.chekka_dashboard_student_info);
        mydb = new SQLiteHelper(this);
        sna = findViewById(R.id.sna_info);
        snu = findViewById(R.id.snu_info);
        em = findViewById(R.id.em_info);

        savepic = findViewById(R.id.savepic);
        gmailpic = findViewById(R.id.gmailpic);
        cross = findViewById(R.id.cross);
        student_info_lv = (ListView) findViewById(R.id.listview_student_info);
        studentinfolistfordisplay = mydb.GetStudentInfo(listview_student_str);

        sna.setText(""+listview_studentname_str);
        snu.setText(""+listview_student_str);
        em.setText(""+listview_studentemail_str);
        imageviewtesting = findViewById(R.id.imageviewtesting);
        layout_imagetesting = findViewById(R.id.layout_imagetesting);
        studentinfoadapter = new SimpleAdapter(Chekka_dashboard_student_info.this, Chekka_dashboard_student.studentinfolist, R.layout.list_item_for_student_info, new String[]{"students_examname101", "students_itemcount101", "students_score101", "students_average101","students_image101"}, new int[]{R.id.examname_IF, R.id.itemname_IF, R.id.scorename_IF, R.id.itemaverage_IF, R.id.examname_icon_IF}); student_info_lv.setAdapter(studentinfoadapter);

        student_info_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                 studentnumberforbitmap = studentinfolistfordisplay.get(position).get("students_number101");
                 studentnameforbitmap = studentinfolistfordisplay.get(position).get("students_examname101");
                studentaverageforbitmap = studentinfolistfordisplay.get(position).get("students_average101");
                studentscoreforbitmap = studentinfolistfordisplay.get(position).get("students_score101");
                email = mydb.getemailtblusers(studentnumberforbitmap,Chekka.textView_id_LO.getText().toString());
                System.out.println("studentnumberforbitmap"+email);
              bytetheimage();
            }
        });
        finish_dastud = (ImageView) findViewById(R.id.finish_dastud);
        finish_dastud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        savepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = "savepic";
                 savebitmap(bitmap);
               layout_imagetesting.setVisibility(View.INVISIBLE);

            }
        });
        gmailpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                what = "gmailpic";
                savebitmap(bitmap);
                gmail_answersheet();
                layout_imagetesting.setVisibility(View.INVISIBLE);

            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_imagetesting.setVisibility(View.INVISIBLE);

            }
        });
    }
    public void bytetheimage(){
        byte[] data = mydb.getBitmapbyStudentnumber(studentnumberforbitmap, studentnameforbitmap);
       System.out.println("whatdata"+data);
         if(data!= null){
            bitmap = UtilsDb.getImage(data);
        System.out.println("bitmapstudentchekka"+bitmap);
        layout_imagetesting.setVisibility(View.VISIBLE);
        imageviewtesting.setImageBitmap(bitmap);
     //   gmail_answersheet();
         }else{

         }
    }
    public void gmail_answersheet(){
        //  File  mFile = savebitmap(bitmap);
        // Uri u = null;
        // u = Uri.fromFile(mFile);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT,""+studentnameforbitmap+" result");
        i.putExtra(Intent.EXTRA_TEXT, "Score: "+studentscoreforbitmap+" \n Average: "+studentaverageforbitmap+" \n Scanned answer sheet located to "+file);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.setType("image/*");
        //i.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(file)));
        startActivity(Intent.createChooser(i,"Sending..."));
    }
    private File savebitmap(Bitmap bmp) {
        filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/Chekka/");
        dir.mkdir();
        file = new File(dir, studentnameforbitmap+"-"+studentnumberforbitmap + ".png");

        OutputStream outStream = null;

        if (file.exists()) {
            file.delete();
            file = new File(filepath, studentnameforbitmap+"-"+studentnumberforbitmap  + ".png");

        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            if(what.equals("savepic")) {
                Toast.makeText(getApplicationContext(), "Downloading... "+file, Toast.LENGTH_SHORT).show();
            }else if(what.equals("gmailpic")){
                Toast.makeText(getApplicationContext(), "Choose Gmail.", Toast.LENGTH_SHORT).show();
            }
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

}


