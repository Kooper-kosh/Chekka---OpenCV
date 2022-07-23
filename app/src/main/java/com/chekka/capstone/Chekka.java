package com.chekka.capstone;

import android.app.AlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import android.content.Context;
import android.content.DialogInterface;
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
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.xmlbeans.impl.xb.xsdschema.IncludeDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.chekka.capstone.Util.sout;
import static org.opencv.imgproc.Imgproc.Canny;
import static org.opencv.imgproc.Imgproc.blur;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.drawContours;
import static org.opencv.imgproc.Imgproc.findContours;
import static org.opencv.imgproc.Imgproc.getStructuringElement;

import com.sun.mail.imap.protocol.Item;

public class Chekka extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String ud = "";
    public static BottomNavigationView bottom_home_dash;
    RelativeLayout popclassname;
    ImageView ekisclassname, saveclassname;
    EditText editclassname;
    private RelativeLayout show;
    int pageHeight = 1095;
    int pagewidth = 843;
    Bitmap image;
    public static Bitmap bmap;
    private static final int PICK_IMAGE = 100;
    public static Uri imageUri;
    String identifier;
    // creating a bitmap variablefinvi
    // for storing our images
    Bitmap bmp, scaledbmp;
    Cursor cursor;
    String Holder_TemporaryAnswer_S = "Not_Found";
    TextView button_examname_PE;
    public static List<String> labels2, labelsstudent;
    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;

    TextView button_ok_CL, button_cancel_CL, button_ok_ST, button_cancel_ST;

    ImageView profile_to_logout_CH, header_faq_CH, header_back_CH, button_cadd_CIR,
            button_addstudent_CO, button_addclass_CO, button_addexamname_CO,
            header_backfordash_CH, button_closeclass_PP, button_savedone, sheetforall, mc1255, tf1255,
            button_closeexam_PE;

    TextView txtview_chekka_ch, txtview_changing_ch, button_viewclass_PP, button_editclass_PP,
            button_ok_EC, button_cancel_EC, button_delete_EC,
            numberofsets_EN, txtstudent_class_ST, txt_welcome, button_deleteexam_PE, textView_gmail_LO;
    RelativeLayout button_scananswerkey_PE, button_scananswersheet_PE;
    RelativeLayout paper;
    public static String Holder_Email_LO;
    public static TextView textView_id_LO;
    EditText EditText_classname_CL,
            EditText_addstudent_ST, EditText_addstudentnumber_ST, EditText_editclassname_EC;

    String Holder_classname_CL, SQLiteDataBaseQueryHolder,
            Holder_ClassName_CL, Holder_TemporaryClassName_CL = "Not_Found", Holder_ClassUserID_CL,
            Holder_StudentName_ST, Holder_TemporaryStudentName_ST = "Not_Found", studentsclass_str;
    Integer xxx;
    ImageView button_Viewimage,imageviewtesting;

    public static RelativeLayout layout_addclass_CL, layout_addstudent_ST, layout_listview_student_ST, layout_listview_class_CL, layout_dashboard_DA,
            button_classes_DA, layout_popforclass_PP, layout_editclass_EC, layout_listview_examsheet, layout_listview_student_info, layout_for_sheet,
            layout_proceed2, layout_for_addclass_white, layout_popforexam_PE;

    ArrayAdapter<CharSequence> adapter_nq1, adapter_qt1;
    FrameLayout layout_addoptions_CO, layout_meow_navigation_cf, layout_noaction_cf, layout_fordash_CH, layout_forsettings_CH, layout_forhomeanddash_CH;
    public static int itemscount, examid, userid, examsection, scoreitems, itempercount;
    public static String examcategory, firstnumberpersection, examclass;
    public static String examnam;
    String examsetholder = "no set 2";
    SQLiteHelper mydb;
    Classes getclassname;
    SQLiteDatabase sqLiteDatabaseObj;
    public static ListView student_lv, lv, exam_lv, student_info_lv;
    public static ListAdapter student_adapter;
    String listview_class_str, listview_student_str;
    int listview_exam_str, listview_items_str, listview_userid_str, listview_section_str, listview_scoreitems_str, listview_itempercount_str;
    String listview_category_str;
    String listview_exam_Name;
    String listview_firstnumber_str, listview_exam_Class_str;
    public static ArrayList<HashMap<String, String>> studentList;
    public static ArrayList<HashMap<String, String>> userList;
    ArrayList<HashMap<String, String>> examlist2;
    public static ArrayList<HashMap<String, String>> examlist;
    public static ArrayList<HashMap<String, String>> studentinfolist;
    public static ListAdapter adapter, examadapter, studentinfoadapter;
    final ArrayList<Classes> classes_cl = new ArrayList<Classes>();
    boolean Holder_Empty_L, Holder_Empty_CL;
    Bitmap bitmap;
    File filepath;
    File file;
    RelativeLayout exportexcel, student_edit, viewall,itemanalysisbutton;
    RelativeLayout chekka_header_id;
    TextView classdone;
    ImageView ButtonCExit_CIR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //
        setContentView(R.layout.chekka); //madidisplay sa layout ng activity_main

        chekka_header_id = findViewById(R.id.chekka_header_id);
       saveclassname = findViewById(R.id.saveclassmname);
        ekisclassname = findViewById(R.id.ekisclassname);
       editclassname = findViewById(R.id.editclassname);
       classdone = findViewById(R.id.txt_class_done);
        final RelativeLayout popclassname = findViewById(R.id.popclassname);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this,  R.style.AlertDialogStyle);
        final EditText input = new EditText(this);

        bottom_home_dash = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        button_examname_PE = findViewById(R.id.button_examname_PE);
        //...other code
        textView_gmail_LO = (TextView) findViewById(R.id.TextViewTGMail_LO); //
        textView_id_LO = (TextView) findViewById(R.id.TextViewTID_LO); //
        textView_gmail_LO = (TextView) findViewById(R.id.TextViewTGMail_LO); //
        mydb = new SQLiteHelper(this);


        // FOOTER

        // TEXT VIEW FOR USERNAME AND GMAIL AND PASS DATA
        textView_id_LO = (TextView) findViewById(R.id.TextViewTID_LO);
        Intent intent = getIntent();
        Holder_Email_LO = intent.getStringExtra(MainActivity.User_Email_L);

        int hehe = mydb.getIdFromClassName(Holder_Email_LO);
        textView_id_LO.setText("" + hehe);

        // EDIT TEXT
        button_Viewimage = (ImageView) findViewById(R.id.button_Viewimage) ;
        button_Viewimage.setVisibility(View.INVISIBLE);
        imageviewtesting = (ImageView) findViewById(R.id.imageviewtesting) ;
        EditText_classname_CL = (EditText) findViewById(R.id.EditTextEnterClassName_CL);
        EditText_addstudent_ST = (EditText) findViewById(R.id.EditTextAddStudent_ST);
        EditText_addstudentnumber_ST = (EditText) findViewById(R.id.EditTextAddStudentNumber_ST);
        EditText_editclassname_EC = (EditText) findViewById(R.id.EditTextEnterEditClassName_EC);
        // TEXT VIEW TO BUTTON
        button_cadd_CIR = (ImageView) findViewById(R.id.ButtonCAdd_CIR);
        ButtonCExit_CIR = (ImageView) findViewById(R.id.ButtonCExit_CIR);
        button_cancel_CL = (TextView) findViewById(R.id.ButtonCLCancel_CL);
        button_ok_CL = (TextView) findViewById(R.id.ButtonCLOkay_CL);
        button_ok_ST = (TextView) findViewById(R.id.ButtonOK_ST);
        button_cancel_ST = (TextView) findViewById(R.id.ButtonCancel_ST);
        button_ok_EC = (TextView) findViewById(R.id.ButtonEditOkay_EC);
        button_cancel_EC = (TextView) findViewById(R.id.ButtonEditCancel_EC);
        button_viewclass_PP = (TextView) findViewById(R.id.buttxt_viewclassname_PP);
        button_editclass_PP = (TextView) findViewById(R.id.buttxt_editclassname_PP);
        button_closeclass_PP = (ImageView) findViewById(R.id.button_closepopforclass_PP);
        button_delete_EC = (TextView) findViewById(R.id.buttxt_deleteclassname_PP);

        button_scananswersheet_PE = (RelativeLayout) findViewById(R.id.button_scan_answersheet_PE);
        button_scananswerkey_PE = (RelativeLayout) findViewById(R.id.button_scan_answerkey_PE);
        button_closeexam_PE = (ImageView) findViewById(R.id.button_close_exam_PE);
        button_deleteexam_PE = (TextView) findViewById(R.id.button_delete_exam_PE);


        // IMAGE VIEW TO BUTTON
        profile_to_logout_CH = (ImageView) findViewById(R.id.profiletologout);
        header_faq_CH = (ImageView) findViewById(R.id.header_faq_CH);
        header_back_CH = (ImageView) findViewById(R.id.header_back_CH);
        header_backfordash_CH = (ImageView) findViewById(R.id.header_backfordash_CH);
        button_addclass_CO = (ImageView) findViewById(R.id.ButtonCOAddClass_CO);
        button_addstudent_CO = (ImageView) findViewById(R.id.ButtonCOAddStudent_CO);
        button_addexamname_CO = (ImageView) findViewById(R.id.ButtonCOAddExamSheet_CO);

        // TEXTVIEW TO CHANGE

        txtview_chekka_ch = (TextView) findViewById(R.id.TextViewCHEKKA_CH);
        txtview_changing_ch = (TextView) findViewById(R.id.TextViewChanging_CH);
        txtstudent_class_ST = (TextView) findViewById(R.id.item_student_class_ST);
    txt_welcome = (TextView) findViewById(R.id.txt_welcome);

        // LAYOUT TO BUTTON
        button_classes_DA = (RelativeLayout) findViewById(R.id.ButtonCLASSES_DA);

        // LAYOUT
        layout_addoptions_CO = (FrameLayout) findViewById(R.id.layout_addoptions_CO);
        layout_addclass_CL = (RelativeLayout) findViewById(R.id.layout_addclass_CL);
        layout_addstudent_ST = (RelativeLayout) findViewById(R.id.layout_addstudent_ST);
        layout_listview_student_ST = (RelativeLayout) findViewById(R.id.layout_listview_student);
        layout_listview_class_CL = (RelativeLayout) findViewById(R.id.layout_listview_class);
        layout_dashboard_DA = (RelativeLayout) findViewById(R.id.layout_dashboard_DA);
        layout_forhomeanddash_CH = (FrameLayout) findViewById(R.id.forhomeanddash_CH);
        layout_fordash_CH = (FrameLayout) findViewById(R.id.fordash_CH);
        layout_forsettings_CH = (FrameLayout) findViewById(R.id.forsettings_CH);
        layout_noaction_cf = (FrameLayout) findViewById(R.id.noaction_CF);
        layout_popforclass_PP = (RelativeLayout) findViewById(R.id.popforclass_PP);
        layout_popforexam_PE = (RelativeLayout) findViewById(R.id.popforexam);
        layout_editclass_EC = (RelativeLayout) findViewById(R.id.layout_editlass_EC);
        layout_listview_examsheet = (RelativeLayout) findViewById(R.id.layout_listview_examsheet);
        layout_listview_student_info = (RelativeLayout) findViewById(R.id.layout_listview_student_info);
        layout_proceed2 = (RelativeLayout) findViewById(R.id.layout_proceedset2);
        layout_for_addclass_white = (RelativeLayout) findViewById(R.id.layout_for_addclass_white);
        // SPINNER

        // LIST VIEW METHODS


        userList = mydb.GetClassName_with_no_of_students(textView_id_LO.getText().toString());
        System.out.println("userlist"+userList);
        lv = (ListView) findViewById(R.id.listview_classname);
        adapter = new SimpleAdapter(Chekka.this, userList, R.layout.list_item, new String[]{"classname_with_no_of_students", "classstudents_with_no_of_students"}, new int[]{R.id.item_class_name_CL, R.id.item_noofstudents_CL});
        lv.setAdapter(adapter);

        student_lv = (ListView) findViewById(R.id.listview_studentname);


        exam_lv = (ListView) findViewById(R.id.listview_examsheet);


        student_info_lv = (ListView) findViewById(R.id.listview_student_info);
        sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.tbl_answerkey + " (" + mydb.answerkeyID + " INTEGER PRIMARY KEY, " + mydb.answerkey + " VARCHAR NOT NULL, " + mydb.examID + " VARCHAR NOT NULL, " + "FOREIGN KEY (examID) REFERENCES second_tbl_exam(second_exam_ID))");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.tbl_students_answer2 + " (" + mydb.students_answerID2 + " INTEGER PRIMARY KEY, " + mydb.students_answer2 + " VARCHAR NOT NULL, " + mydb.students_score2 + " VARCHAR NOT NULL, " + mydb.students_average2 + " VARCHAR NOT NULL, " + mydb.students_examID2 + " VARCHAR NOT NULL, " + mydb.students_number2 + " VARCHAR NOT NULL," + "FOREIGN KEY (students_number2) REFERENCES tbl_Students(studentsID))");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.tbl_answerkey + " (" + mydb.answerkeyID + " INTEGER PRIMARY KEY, " + mydb.answerkey + " VARCHAR NOT NULL, " + mydb.examID + " VARCHAR NOT NULL, " + "FOREIGN KEY (examID) REFERENCES second_tbl_exam(second_exam_ID))");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.tbl_students_answer101 + " (" + mydb.students_answerID101 + " INTEGER PRIMARY KEY, " + mydb.students_answer101 + " VARCHAR NOT NULL, " + mydb.students_score101 + " VARCHAR NOT NULL, " + mydb.students_average101 + " VARCHAR NOT NULL, " + mydb.students_examID101 + " VARCHAR NOT NULL, " + mydb.students_itemcount101 + " VARCHAR NOT NULL, " + mydb.students_examname101+ " VARCHAR NOT NULL, " + mydb.students_userID101 + " VARCHAR NOT NULL, " + mydb.students_name101 + " VARCHAR NOT NULL, " + mydb.students_image101 + " BLOB NOT NULL, " + mydb.students_class101 + " VARCHAR NOT NULL, "+ mydb.students_number101 + " VARCHAR NOT NULL," + "FOREIGN KEY (students_number101) REFERENCES tbl_Students(studentsID))");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.tbl_students + " (" + mydb.studentsID + " VARCHAR PRIMARY KEY, " + mydb.studentsname + " VARCHAR NOT NULL, " + mydb.studentsemail + " VARCHAR NOT NULL, " + mydb.studentsclass + " VARCHAR NOT NULL, " + mydb.studentsuserid + " INTEGER NOT NULL, " + "FOREIGN KEY(studentsuserid) REFERENCES tbl_users(UserID))");
     sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.final_tbl_exam + " (" + mydb.final_exam_ID + " INTEGER PRIMARY KEY, " + mydb.final_exam_name + " VARCHAR NOT NULL, " + mydb.final_exam_class + " VARCHAR NOT NULL, " + mydb.final_exam_set + " VARCHAR NOT NULL, " + mydb.final_exam_section + " VARCHAR NOT NULL, " + mydb.final_total_count + " VARCHAR NOT NULL, " + mydb.final_item_per_count + " VARCHAR NOT NULL, " + mydb.final_category + " VARCHAR NOT NULL, " + mydb.final_user_ID + " VARCHAR NOT NULL, " + mydb.final_correct + " VARCHAR NOT NULL, " + mydb.final_incorrect + " VARCHAR NOT NULL, " + mydb.final_scoreitems + " VARCHAR NOT NULL," + mydb.final_firstnumberpersection + " VARCHAR NOT NULL," + mydb.final_date + " VARCHAR NOT NULL)");
        //exam_lv.setVisibility(View.VISIBLE);
        try{
            examlist = mydb.GetExamName(Chekka.textView_id_LO.getText().toString());
            System.out.println("Examlist" + examlist);
            examadapter = new SimpleAdapter(Chekka.this, examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount});
            exam_lv.setAdapter(examadapter);
            exam_lv.setVisibility(View.VISIBLE);
        }catch (Exception e){

        }
        try {
            userList = mydb.GetClassName_with_no_of_students(textView_id_LO.getText().toString());
            System.out.println("userlisthahaha "+userList);
            txt_welcome.setVisibility(View.INVISIBLE);

            studentList = mydb.GetStudent(Chekka.textView_id_LO.getText().toString());
            if(studentList.isEmpty()) {
                txt_welcome.setVisibility(View.VISIBLE);
            }else if(studentList.isEmpty() && examlist.isEmpty()){
                txt_welcome.setVisibility(View.VISIBLE);
            }else if(examlist.isEmpty()){
                classdone.setVisibility(View.VISIBLE);
            }else{
                classdone.setVisibility(View.INVISIBLE);
                txt_welcome.setVisibility(View.INVISIBLE);
            }
            //  txt_welcome.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            // txt_welcome.setVisibility(View.VISIBLE);
        }
     //   System.out.println("HEHEHEHHE "+mydb.GetScorForPerformance("BSIT 4-2"));
        button_examname_PE.setText("");
// Create an ArrayAdapter using the string array and a default spinner layout


        // student_info_lv.setVisibility(View.INVISIBLE);
        // studentinfolist = mydb.GetStudentInfo(textView_id_LO.getText().toString(), student_lv.getSelectedItem().toString());
        //  System.out.println("studentinfo"+studentinfolist);
        //   studentinfoadapter = new SimpleAdapter(Chekka.this, studentinfolist, R.layout.list_item_for_student_info,new String[]{"exam_Name"}, new int[]{R.id.examname_IF});
        //   student_info_lv.setAdapter(studentinfoadapter);
        //studentList = mydb.GetStudentName(textView_id_LO.getText().toString(),  listview_class_str);


        student_info_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                //By using data id
                //layout_popforclass_PP.setVisibility(View.VISIBLE);
                // studentinfolist = mydb.GetStudentInfo(textView_id_LO.getText().toString(), listview_class_str);

                //lv = (ListView) findVfviewById(R.id.listview_classname);
                // adapter = new SimpleAdapter(Chekka.this, userList, R.layout.list_item,new String[]{"classname_with_no_of_students","classstudents_with_no_of_students"}, new int[]{R.id.item_class_name_CL,  R.id.item_noofstudents_CL});
                // lv.setAdapter(adapter);
                // student_info_lv.setVisibility(View.VISIBLE);

            }

        });

        exam_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                //By using data id
                //layout_popforclass_PP.setVisibility(View.VISIBLE);
                // studentinfolist = mydb.GetStudentInfo(textView_id_LO.getText().toString(), listview_class_str);

                //lv = (ListView) findViewById(R.id.listview_classname);
                // adapter = new SimpleAdapter(Chekka.this, userList, R.layout.list_item,new String[]{"classname_with_no_of_students","classstudents_with_no_of_students"}, new int[]{R.id.item_class_name_CL,  R.id.item_noofstudents_CL});
                // lv.setAdapter(adapter);
                // student_info_lv.setVisibility(View.VISIBLE);
                studentList = mydb.GetStudent(textView_id_LO.getText().toString());

                if(studentList.isEmpty()) {
                    System.out.println("No Student List");
                    Toast.makeText(getApplicationContext(), "You have to add students", Toast.LENGTH_SHORT).show();
                }else{
                    layout_popforexam_PE.setVisibility(View.VISIBLE);
                    listview_exam_str = Integer.parseInt(examlist.get(position).get("final_exam_ID"));
                    listview_items_str = Integer.parseInt(examlist.get(position).get("final_total_count"));
                    listview_category_str = examlist.get(position).get("final_category");
                    listview_exam_Name = examlist.get(position).get("final_exam_name");
                    listview_userid_str = Integer.parseInt(examlist.get(position).get("final_user_ID"));
                    listview_section_str = Integer.parseInt(examlist.get(position).get("final_exam_section"));
                    listview_scoreitems_str = Integer.parseInt(examlist.get(position).get("final_scoreitems"));
                    listview_firstnumber_str = examlist.get(position).get("final_firstnumberpersection");
                    listview_itempercount_str = Integer.parseInt(examlist.get(position).get("final_item_per_count"));
                    listview_exam_Class_str = examlist.get(position).get("final_exam_class");
                    System.out.println("listexams" + listview_exam_str);
                    examid = listview_exam_str;
                    itemscount = listview_items_str;
                    examnam = listview_exam_Name;
                    userid = listview_userid_str;
                    examsection = listview_section_str;
                    examcategory = listview_category_str;
                    scoreitems = listview_scoreitems_str;
                    firstnumberpersection = listview_firstnumber_str;
                    itempercount = listview_itempercount_str;
                    examclass = listview_exam_Class_str;
                    System.out.println("examcategory" + examcategory);
                    System.out.println("examdb" + itemscount);
                    System.out.println("examid" + examclass);
                    button_examname_PE.setText("" + listview_exam_Name);
                    exam_lv.setEnabled(false);
                }
            }

        });

        button_Viewimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                     //   byte[] data = mydb.getBitmapbyStudentnumber("6543");
                       // System.out.println("whatdata"+data);
                      //  if(data!= null){
                     //       bitmap = UtilsDb.getImage(data);
                         //   System.out.println("bitmapstudentchekka"+bitmap);
                         //   imageviewtesting.setImageBitmap(bitmap);
                         //   gmail_answersheet();
                      //  }else{

                      //  }
                    }
        });


        button_scananswerkey_PE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckingAnswerkey_AK();
            }
        });
        button_scananswersheet_PE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
                identifier = "scanpaper";

            }
        });
        button_deleteexam_PE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_popforexam_PE.setVisibility(View.INVISIBLE);
                exam_lv.setEnabled(true);

            }
        });
        button_closeexam_PE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_examname_PE.setText("");
                exam_lv.setEnabled(true);
                layout_popforexam_PE.setVisibility(View.INVISIBLE);

            }
        });
        bottom_home_dash.setVisibility(View.VISIBLE);
        bottom_home_dash.setSelectedItemId(R.id.home);
        bottom_home_dash.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        //Checking if the item is in checked state or not, if not take the required action
                        if (item.isChecked()) {
                            return true;
                        } else {
                            switch (item.getItemId()) {

                                case R.id.home:
                                    // your code
                                    //show.setVisibility(View.VISIBLE);
                                    button_cadd_CIR.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                                    layout_forhomeanddash_CH.setVisibility(View.VISIBLE);
                                    exam_lv.setVisibility(View.VISIBLE);
                                    layout_listview_examsheet.setVisibility(View.VISIBLE);
                                    txtview_chekka_ch.setVisibility(View.VISIBLE);



                                    return true;

                                default:
                                    Toast.makeText(getApplicationContext(), "Dashboard", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Chekka.this, Chekka_dashboard.class);
                                    startActivity(intent);
                                    return true;
                            }
                        }
                    }
                }
        );




        button_addexamname_CO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_welcome.getVisibility() == View.VISIBLE){
                    Toast.makeText(Chekka.this, "Inactive... Add students to activate it.", Toast.LENGTH_LONG).show();
                }else {
                    button_addexamname_CO.setEnabled(true);
                    layout_addoptions_CO.setVisibility(View.INVISIBLE);
                    loadSpinnerDataForExam();
                    Intent intent = new Intent(Chekka.this, Chekka_createexam.class);
                    startActivity(intent);
                    classdone.setVisibility(View.INVISIBLE);
                    ButtonCExit_CIR.setVisibility(View.INVISIBLE);
                    txt_welcome.setVisibility(View.INVISIBLE);
                    button_cadd_CIR.setVisibility(View.VISIBLE);
                    exam_lv.setEnabled(true);
                }
            }
        });


        button_ok_EC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mydb.update_classname(EditText_editclassname_EC.getText().toString(), listview_class_str);
                userList = mydb.GetClassName_with_no_of_students(textView_id_LO.getText().toString());
                lv = (ListView) findViewById(R.id.listview_classname);
                adapter = new SimpleAdapter(Chekka.this, userList, R.layout.list_item, new String[]{"classname_with_no_of_students", "classstudents_with_no_of_students"}, new int[]{R.id.item_class_name_CL, R.id.item_noofstudents_CL});
                lv.setAdapter(adapter);
                lv.setVisibility(View.VISIBLE);
                layout_editclass_EC.setVisibility(View.INVISIBLE);
                bottom_home_dash.setVisibility(View.VISIBLE);

            }
        });
        button_cancel_EC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        button_cadd_CIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classdone.setVisibility(View.INVISIBLE);
                txt_welcome.setVisibility(View.INVISIBLE);

                     ButtonCExit_CIR.setVisibility(View.VISIBLE);
                    layout_addoptions_CO.setVisibility(View.VISIBLE);
                    exam_lv.setEnabled(false);


            }
        });
        ButtonCExit_CIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classdone.setVisibility(View.INVISIBLE);
                txt_welcome.setVisibility(View.INVISIBLE);

                    layout_addoptions_CO.setVisibility(View.INVISIBLE);
                    ButtonCExit_CIR.setVisibility(View.INVISIBLE);
                    button_cadd_CIR.setVisibility(View.VISIBLE);
                    exam_lv.setEnabled(true);

            }
        });

        button_addclass_CO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Chekka.this, Chekka_addclass.class);
               // startActivity(intent);
                popclassname.setVisibility(View.VISIBLE);
                layout_addoptions_CO.setVisibility(View.INVISIBLE);
                txt_welcome.setVisibility(View.INVISIBLE);
                classdone.setVisibility(View.INVISIBLE);
                txt_welcome.setVisibility(View.INVISIBLE);

                layout_addoptions_CO.setVisibility(View.INVISIBLE);
                ButtonCExit_CIR.setVisibility(View.INVISIBLE);
                button_cadd_CIR.setVisibility(View.VISIBLE);
                exam_lv.setEnabled(true);

            }
        });
        ekisclassname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = new Intent(Chekka.this, Chekka_addclass.class);
                // startActivity(intent);
                popclassname.setVisibility(View.INVISIBLE);
                layout_addoptions_CO.setVisibility(View.INVISIBLE);
                ButtonCExit_CIR.setVisibility(View.INVISIBLE);
                exam_lv.setEnabled(true);

            }
        });
        saveclassname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Holder_ClassName_CL = editclassname.getText().toString();
                if(Holder_ClassName_CL.isEmpty()){
                    Toast.makeText(Chekka.this, "Please input class name", Toast.LENGTH_LONG).show();
                    }else {
                    popclassname.setVisibility(View.INVISIBLE);
                    layout_addoptions_CO.setVisibility(View.INVISIBLE);
                    exam_lv.setEnabled(true);
                    SQLiteDataBaseBuild();
                    SQLiteTableBuild_CL();
                    CheckingClassNameAlreadyExistsOrNot_CL();
                    if(examlist.isEmpty()){
                        classdone.setVisibility(View.VISIBLE);
                    }
                    ButtonCExit_CIR.setVisibility(View.INVISIBLE);
                }
            }

        });
        button_addstudent_CO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    layout_addoptions_CO.setVisibility(View.INVISIBLE);
                    ButtonCExit_CIR.setVisibility(View.INVISIBLE);
                    exam_lv.setVisibility(View.VISIBLE);
                    loadSpinnerData();

                    Intent intent = new Intent(Chekka.this, Chekka_addstudent.class);
                    startActivity(intent);
            }
        });


        profile_to_logout_CH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    public void InsertClassNameIntoSQLiteDatabase_CL() {

        // If editText is not empty then this block will executed.


        // SQLite query to insert data into table.

        String a = "0";
        mydb.insert_class_details(Holder_ClassName_CL, textView_id_LO.getText().toString(), a);
        editclassname.setText("");
        // Printing toast message after done inserting.
        Toast.makeText(Chekka.this, "Added Class Successfully", Toast.LENGTH_LONG).show();

        Holder_ClassName_CL = "";

        // This block will execute if any of the registration EditText is empty.

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

    private void loadSpinnerData() {

        labelsstudent = mydb.getAllLClassNamesForSpinner(textView_id_LO.getText().toString());

    }

    private void loadSpinnerDataForExam() {

        labels2 = mydb.getAllLClassNamesForSpinnerExam(textView_id_LO.getText().toString());
        sout("dataadapter" + labels2);
    }

    public void CheckingAnswerkey_AK() {
        sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);
        // Opening SQLite database write permission.
        sqLiteDatabaseObj = mydb.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.tbl_answerkey, null, " " + SQLiteHelper.examID + "=?", new String[]{String.valueOf(examid)}, null, null, null);
        try {
            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    // If Email is already exists then Result variable value set as Email Found.
                    Holder_TemporaryAnswer_S = "Email Found";

                    // Closing cursor.
                    cursor.close();
                }
            }
        }catch (Exception e){

        }
        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult_AK();

    }

    public void CheckFinalResult_AK() {

        // Checking whether email is already exists or not.
        if (Holder_TemporaryAnswer_S.equalsIgnoreCase("Email Found")) {
            // If email is exists then toast msg will display.
            Toast.makeText(Chekka.this, "Answer Key Already Exists", Toast.LENGTH_LONG).show();
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Answer Key is already exist.");
            //SET THE MESSAGE
            builder.setMessage("If you continue, answer key will be updated.");

            //WE DEFINE THE FUNCTION, WHEN THE USER CHOOSES "YES"
            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    //HERE GOES THE LOGIC  OF THE APPLICATION, IF THE USER SELECTS A POSITIVE CHOICE
                    //TO TEST IT, WE CAN SET THE VALUE OF THE TEXT VIEW
                    //LET'S SET THE TEXT TO YES
                    System.out.println("YES");
                    System.out.println("Updated");
                    openGallery();
                    identifier = "answerkey";
                    ud = "yes ud";
                }
            });


            //DEFINE THE FUNCTION, IF THE USERE CLICKS 'NO', OR SELECTS THE NEGATIVE CHOICE
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    System.out.println("NO");
                    ud = "yes ud";
                    //HERE YOU DEFINE THE LOGIC, IF THE USER SELECTS OR CLICKS THE 'NO' BUTTON
                    //USUALLY, NOTHING IS DEFINED HERE, BUT DEPENDS ON YOUR DECISION

                    //HERE, LET'S SET THE TEXT TO NO
                }
            });

            //NOW, WE CREATE THE ALERT DIALG OBJECT
            AlertDialog ad = builder.create();

            //FINALLY, WE SHOW THE DIALOG
            ad.show();

        } else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            openGallery();
            identifier = "answerkey";
            ud = "no ud";
        }

        Holder_TemporaryAnswer_S = "Not_Found";

    }

    public void CheckingClassNameAlreadyExistsOrNot_CL() {

        Holder_ClassUserID_CL = textView_id_LO.getText().toString();
        // Opening SQLite database write permission.
        sqLiteDatabaseObj = mydb.getWritableDatabase();

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

            layout_addoptions_CO.setVisibility(View.INVISIBLE);




            Toast.makeText(Chekka.this, "Class Name Already Exists. Check it out!", Toast.LENGTH_LONG).show();

        } else {
            // If email already dose n't exists then user registration details will entered to SQLite database.
            if (mydb.UserID_with_no_of_students != Holder_ClassUserID_CL) {
                InsertClassNameIntoSQLiteDatabase_CL();
            }
        }
        Holder_TemporaryClassName_CL = "Not_Found";
    }


    // SQLite table build method.




    public void CheckFinalResultStudentName_ST() {

        // Checking whether email is already exists or not.
        if (Holder_TemporaryStudentName_ST.equalsIgnoreCase("Email Found")) {
            // If email is exists then toaslist_item.xml
            //list_item_for_student.xmlt msg will display.
            EditText_addstudent_ST.setText("");
            layout_addstudent_ST.setVisibility(View.VISIBLE);
            layout_listview_student_ST.setVisibility(View.INVISIBLE);
            layout_listview_class_CL.setVisibility(View.INVISIBLE);
            bottom_home_dash.setVisibility(View.INVISIBLE);
            header_back_CH.setVisibility(View.VISIBLE);
            button_cadd_CIR.setVisibility(View.INVISIBLE);
            Toast.makeText(Chekka.this, "Student Name Already Exists", Toast.LENGTH_LONG).show();
        } else {
            // If email already dose n't exists then user registration details will entered to SQLite database.


            layout_listview_class_CL.setVisibility(View.VISIBLE);

        }
        Holder_TemporaryStudentName_ST = "Not_Found";
    }





    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void generatePDF () {
        // creating an object variable
        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas
        // from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        canvas.drawBitmap(scaledbmp, 20, 20, paint);

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.setTextSize(15);

        // below line is sued for setting color
        // of our text inside our PDF file.


        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.


        // similarly we are creating another text and in this
        // we are aligning this text to center of our PDF file.


        // below line is used for setting
        // our text to center of PDF.


        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage);

        // below line is used to set the name of
        // our PDF file and its path.
        String ex = "binhi";
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/Chekka/");
        dir.mkdir();
        File file = new File(dir, ex+" - "+System.currentTimeMillis()+ ".pdf");

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(Chekka.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
    }

    private boolean checkPermission () {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission () {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {

                } else {

                    finish();
                }
            }
        }
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            String s = getRealPathFromURI(imageUri);
            System.out.println("eses"+s);

            try {
                bmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //   imageView.setImageBitmap(bmap);
            if(identifier.equals("scanpaper")) {
                Intent intent = new Intent(Chekka.this, ScanImageAnswerSheet.class);
                startActivity(intent);
                identifier = "";
            } else if(identifier.equals("answerkey")){
                Intent intent = new Intent(Chekka.this, ScanImageAnswerKey.class);
                startActivity(intent);
                identifier = "";
            }
        }
    }
    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public void examlistrefresh(){
        examlist = mydb.GetExamName(textView_id_LO.getText().toString());
        System.out.println("Examlist" + examlist);
        examadapter = new SimpleAdapter(Chekka.this, examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount});
        exam_lv.setAdapter(examadapter);
        exam_lv.setVisibility(View.VISIBLE);
        layout_listview_examsheet.setVisibility(View.VISIBLE);
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public void gmail_answersheet(){
      //  File  mFile = savebitmap(bitmap);
       // Uri u = null;
       // u = Uri.fromFile(mFile);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"theoaesso@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT,"On The Job");
        i.putExtra(Intent.EXTRA_TEXT, "Average: 0");
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.setType("image/*");
        //i.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(file)));
    startActivity(Intent.createChooser(i,"Share you on the jobing"));
    }
    private File savebitmap(Bitmap bmp) {
       filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/Chekka/");
        dir.mkdir();
        file = new File(dir, "Chekka3333" + ".png");

        OutputStream outStream = null;

        if (file.exists()) {
            file.delete();
            file = new File(filepath, 12345 + ".png");
        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

}




