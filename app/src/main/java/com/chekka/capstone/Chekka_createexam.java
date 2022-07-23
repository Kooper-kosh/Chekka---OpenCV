package com.chekka.capstone;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
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
import android.support.v4.app.NotificationCompatExtras;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class Chekka_createexam extends AppCompatActivity {
    SQLiteHelper mydb;
    TextView txtview_examsets_EN, txtview_examsections_EN, buttonproceed_P2, text_details;
    EditText EditText_examname_EN;
    TextView txt_swipe;
    Integer xxx;
    Button buttonminusofsets_EN, buttonplusofsets_EN, buttonminusofsections_EN, buttonplusofsections_EN;
    RelativeLayout layout_forsection1_EX, layout_forsection2_EX, layout_forsection3_EX, layout_forsection4_EX,
            layout_forsection5_EX, layout_forsection6_EX, layout_forsection7_EX, layout_forsection8_EX, layout_forsection9_EX,
            layout_forsection10_EX, layout_createexam_AC, layout_for_sheet;
    ScrollView scrollview_for_exam;
    private BottomNavigationView bottom_next_prev, bottom_saveimage, bottom_savesections, bottom_block;
    Spinner addstudent_spinner;
    ImageView sheetfor1;
    String Holder_TemporaryExamName = "Not_Found", examsetholder, Holdername_ExamName;
    OutputStream outputStream;
    int counter = 1;
    int countersections = 1;
    int examset2_i = 1;
    int itemscounter;
    SQLiteDatabase sqLiteDatabaseObj;
    Cursor cursor;
    Spinner addexam_spinner;
    ArrayAdapter<String> dataAdapter2;
    RadioButton sec1, sec2, sec3, sec4, sec5, sec6;
    String selectedSuperStar;
    int g_exam_set_index, g_exam_section_index;
    String mcsec1, mcsec2, mcsec3, mcsec4, misec1, misec2, misec3, misec4;
    Button submit;
    int TOTAL_PAGES = 0;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    LinearLayout circles;
    Button btnSkip;
    Button btnDone;
    ImageButton btnNext;
    boolean isOpaque = true;
    RelativeLayout layout_welcomescreen_test, button_layout;
    ImageView button_customize;
    String str = "";
    String details_text = "";
    View fragmentCView;
    RadioGroup g_exam_section, g_exam_set;
    ScrollView scrollview_for_examsheet;
    Bitmap bmp, scaledbmp;
    String code_tag;
    int pageHeight = 1095;
    int pagewidth = 843;
    RelativeLayout customize_btn,popexamname;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String dbex_examname, dbex_classname, dbex_set, dbex_totalcount, dbex_section,
            dbex_itempercount, dbex_category, dbex_userid;
    String totalcount, itempercount;
    TextView itemscount_sec1, itemscount_sec2, itemscount_sec3, itemscount_sec4, examname_sec;
    int     marksforcorrect_sec1, marksforincorrect_sec1,
            marksforcorrect_sec2, marksforincorrect_sec2,
            marksforcorrect_sec3, marksforincorrect_sec3,
            marksforcorrect_sec4, marksforincorrect_sec4;
    Spinner mc_spinner_for_section1_EX, mc_spinner_for_section2_EX, mc_spinner_for_section3_EX, mc_spinner_for_section4_EX;
    Spinner mi_spinner_for_section1_EX, mi_spinner_for_section2_EX, mi_spinner_for_section3_EX, mi_spinner_for_section4_EX;
    List<Integer> dbex_correct = new ArrayList<>();
    List<Integer> dbex_incorrect = new ArrayList<>();
    List<Integer> dbex_firstnumberpersection = new ArrayList<>();
    ImageView ekisname, saveexamname;
    EditText editexamname;
    String SQLiteDataBaseQueryHolder;
    String inc_store, c_store,inc_fn_store;
    int allscore = 0;
    int allscore_final = 0;
    ImageView nextbutton;
    String input_Str;
    TextView examnamesection, examsheet_section;
    RelativeLayout sheet_layout,popforsaveimage, savepdf,saveimage;
    ImageView savebutton;

    String Holder_TemporaryClassName_CL = "Not Found";
    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //
        mydb = new SQLiteHelper(this);
        setContentView(R.layout.chekka_createexam);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this,  R.style.AlertDialogStyle);
        final EditText input = new EditText(this);
        txt_swipe = findViewById(R.id.txt_swipe);
        savepdf = findViewById(R.id.button_scan_as_pdf);
        saveimage = findViewById(R.id.button_scan_as_image);
        savebutton = findViewById(R.id.savebutton);
        popforsaveimage = findViewById(R.id.popforsaveimage);
        sheet_layout = findViewById(R.id.sheet_layout);
        nextbutton = findViewById(R.id.nextbutton);
        popexamname = findViewById(R.id.popexamname);
        ekisname = findViewById(R.id.ekisexamname);
        saveexamname = findViewById(R.id.saveexamname);
        editexamname = findViewById(R.id.editexamname);
        examnamesection = findViewById(R.id.examnamesection);
        examsheet_section = findViewById(R.id.examsheet_section);
        customize_btn = findViewById(R.id.customize_btn);
        scrollview_for_examsheet = (ScrollView) findViewById(R.id.scrollview_for_examsheet);
        addexam_spinner = (Spinner) findViewById(R.id.spinnerexam);
        bottom_savesections = (BottomNavigationView) findViewById(R.id.bottom_savesections);
        bottom_block = (BottomNavigationView) findViewById(R.id.bottom_navigationblock);
        bottom_next_prev = (BottomNavigationView) findViewById(R.id.bottom_navigationnextprev);
        bottom_saveimage = (BottomNavigationView) findViewById(R.id.bottom_saveimage);
        buttonproceed_P2 = (TextView) findViewById(R.id.button_proceedset2_P2);
        layout_createexam_AC = (RelativeLayout) findViewById(R.id.layout_createexam_AC);
        layout_forsection1_EX = (RelativeLayout) findViewById(R.id.layout_for_section1_EX);
        layout_forsection2_EX = (RelativeLayout) findViewById(R.id.layout_for_section2_EX);
        layout_forsection3_EX = (RelativeLayout) findViewById(R.id.layout_for_section3_EX);
        layout_forsection4_EX = (RelativeLayout) findViewById(R.id.layout_for_section4_EX);

        text_details = (TextView) findViewById(R.id.text_details);
        sheetfor1 = (ImageView) findViewById(R.id.sheetfor1);
        try {
            xxx = Integer.parseInt(txtview_examsections_EN.getText().toString());
        } catch (Exception e) {

        }
        dataAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner_addexam_list, Chekka.labels2);
        dataAdapter2.setDropDownViewResource(R.layout.spinner_addexam_list);
        addexam_spinner.setAdapter(dataAdapter2);
        sec1 = (RadioButton) findViewById(R.id.sec1);
        sec2 = (RadioButton) findViewById(R.id.sec2);
        sec3 = (RadioButton) findViewById(R.id.sec3);
        sec4 = (RadioButton) findViewById(R.id.sec4);
        layout_welcomescreen_test = (RelativeLayout) findViewById(R.id.layout_welcomescreen_test);
        button_layout = (RelativeLayout) findViewById(R.id.button_layout);
        bottom_next_prev = (BottomNavigationView) findViewById(R.id.bottom_navigationnextprev);
        bottom_savesections = (BottomNavigationView) findViewById(R.id.bottom_savesections);
        bottom_saveimage = (BottomNavigationView) findViewById(R.id.bottom_saveimage);
        //Setting up the toolbar
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        g_exam_section = (RadioGroup) findViewById(R.id.rg_exam);
        g_exam_set = (RadioGroup) findViewById(R.id.rg_examset);
        bottom_next_prev.setVisibility(View.VISIBLE);

        itemscount_sec1 = (TextView) findViewById(R.id.itemscount_sec1);
        itemscount_sec2 = (TextView) findViewById(R.id.itemscount_sec2);
        itemscount_sec3 = (TextView) findViewById(R.id.itemscount_sec3);
        itemscount_sec4 = (TextView) findViewById(R.id.itemscount_sec4);

        mc_spinner_for_section1_EX = (Spinner) findViewById(R.id.mc_spinner_for_section1_EX);
        mc_spinner_for_section2_EX = (Spinner) findViewById(R.id.mc_spinner_for_section2_EX);
        mc_spinner_for_section3_EX = (Spinner) findViewById(R.id.mc_spinner_for_section3_EX);
        mc_spinner_for_section4_EX = (Spinner) findViewById(R.id.mc_spinner_for_section4_EX);

        mi_spinner_for_section1_EX = (Spinner) findViewById(R.id.mi_spinner_for_section1_EX);
        mi_spinner_for_section2_EX = (Spinner) findViewById(R.id.mi_spinner_for_section2_EX);
        mi_spinner_for_section3_EX = (Spinner) findViewById(R.id.mi_spinner_for_section3_EX);
        mi_spinner_for_section4_EX = (Spinner) findViewById(R.id.mi_spinner_for_section4_EX);

        examname_sec = (TextView) findViewById(R.id.examname_sec);

        saveimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docorrectcount();
                doincorrectcount();
                forprintoutput();
                makeimage();
                checkPermission();
                requestPermission();
                sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                String date = sdf.format(new Date());
                System.out.println("WhatDate "+date);
                sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.final_tbl_exam + " (" + mydb.final_exam_ID + " INTEGER PRIMARY KEY, " + mydb.final_exam_name  + " VARCHAR NOT NULL, " + mydb.final_exam_class + " VARCHAR NOT NULL, " + mydb.final_exam_set + " VARCHAR NOT NULL, " + mydb.final_exam_section + " VARCHAR NOT NULL, " + mydb.final_total_count + " VARCHAR NOT NULL, " + mydb.final_item_per_count + " VARCHAR NOT NULL, " + mydb.final_category + " VARCHAR NOT NULL, " + mydb.final_user_ID + " VARCHAR NOT NULL, " + mydb.final_correct + " VARCHAR NOT NULL," + mydb.final_incorrect + " VARCHAR NOT NULL," + mydb.final_scoreitems + " VARCHAR NOT NULL," + mydb.final_firstnumberpersection + " VARCHAR NOT NULL," + mydb.final_date + " VARCHAR NOT NULL)");
                SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.final_tbl_exam+"(final_exam_name,final_exam_class,final_exam_set,final_exam_section,final_total_count,final_item_per_count,final_category,final_user_ID, final_correct, final_incorrect, final_scoreitems, final_firstnumberpersection, final_date) VALUES('"+editexamname.getText().toString()+"', '"+dbex_classname+"', '"+dbex_set+"', '"+dbex_section+"', '"+dbex_totalcount+"', '"+dbex_itempercount+"', '"+dbex_category+"', '"+dbex_userid+"', '"+c_store+"', '"+inc_store+"', '"+allscore+"','"+inc_fn_store+"','"+date+"')";
                sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
                Chekka.examlist = mydb.GetExamName(Chekka.textView_id_LO.getText().toString());
                System.out.println("Examlist" + Chekka.examlist);
                Chekka.examadapter = new SimpleAdapter(Chekka_createexam.this, Chekka.examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count","final_date"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount, R.id.item_date_EN});
                Chekka.exam_lv.setAdapter(Chekka.examadapter);
                Chekka.exam_lv.setEnabled(true);
                finish();
            }
        });
        savepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    generatePDF();
                }
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("HELLO");
                popforsaveimage.setVisibility(View.VISIBLE);
            }
        });
        customize_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popexamname.setVisibility(View.VISIBLE);
            }
        });

        ekisname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popexamname.setVisibility(View.INVISIBLE);
            }
        });
        saveexamname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editexamname.getText().toString().isEmpty()) {

                    SQLiteDataBaseBuild();
                    SQLiteTableBuild_CL();
                    CheckingClassNameAlreadyExistsOrNot_CL();
                } else {
                    Toast.makeText(Chekka_createexam.this, "Please input exam name.", Toast.LENGTH_SHORT).show();

                }
            }
        });
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next_section();
                displaysheet();
                nextbutton.setVisibility(View.INVISIBLE);
                examnamesection.setText("");
                sheet_layout.setVisibility(View.VISIBLE);
                examsheet_section.setText(""+editexamname.getText().toString());

            }
        });

        bottom_saveimage.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        docorrectcount();
                        doincorrectcount();
                        //Checking if the item is in checked state or not, if not take the required action
                        //EarlyCheckingExamNameAlreadyExistsOrNot_EX();
                        switch (item.getItemId()) {
                            case R.id.saveimage:
                                forprintoutput();
                                makeimage();
                                checkPermission();
                                requestPermission();
                                sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);
                                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                                String date = sdf.format(new Date());
                                System.out.println("WhatDate "+date);
                                sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.final_tbl_exam + " (" + mydb.final_exam_ID + " INTEGER PRIMARY KEY, " + mydb.final_exam_name  + " VARCHAR NOT NULL, " + mydb.final_exam_class + " VARCHAR NOT NULL, " + mydb.final_exam_set + " VARCHAR NOT NULL, " + mydb.final_exam_section + " VARCHAR NOT NULL, " + mydb.final_total_count + " VARCHAR NOT NULL, " + mydb.final_item_per_count + " VARCHAR NOT NULL, " + mydb.final_category + " VARCHAR NOT NULL, " + mydb.final_user_ID + " VARCHAR NOT NULL, " + mydb.final_correct + " VARCHAR NOT NULL," + mydb.final_incorrect + " VARCHAR NOT NULL," + mydb.final_scoreitems + " VARCHAR NOT NULL," + mydb.final_firstnumberpersection + " VARCHAR NOT NULL," + mydb.final_date + " VARCHAR NOT NULL)");
                                SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.final_tbl_exam+"(final_exam_name,final_exam_class,final_exam_set,final_exam_section,final_total_count,final_item_per_count,final_category,final_user_ID, final_correct, final_incorrect, final_scoreitems, final_firstnumberpersection, final_date) VALUES('"+input_Str+"', '"+dbex_classname+"', '"+dbex_set+"', '"+dbex_section+"', '"+dbex_totalcount+"', '"+dbex_itempercount+"', '"+dbex_category+"', '"+dbex_userid+"', '"+c_store+"', '"+inc_store+"', '"+allscore+"','"+inc_fn_store+"','"+date+"')";
                                sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
                                Chekka.examlist = mydb.GetExamName(Chekka.textView_id_LO.getText().toString());
                                System.out.println("Examlist" + Chekka.examlist);
                                Chekka.examadapter = new SimpleAdapter(Chekka_createexam.this, Chekka.examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count","final_date"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount, R.id.item_date_EN});
                                Chekka.exam_lv.setAdapter(Chekka.examadapter);
                                Chekka.exam_lv.setEnabled(true);
                                finish();
                                break;
                            case R.id.savepdf:
                                // your code
                                //show.setVisibility(View.VISIBLE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    generatePDF();
                                }

                                break;
                        }

                        return true;

                    }

                });
        bottom_savesections.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        //Checking if the item is in checked state or not, if not take the required action
                        //EarlyCheckingExamNameAlreadyExistsOrNot_EX();

                        switch (item.getItemId()) {
                            case R.id.next:
                                next_section();
                                displaysheet();
                                //exam_lv.setVisibility(View.INVISIBLE);


                                break;
                            case R.id.previous:
                               previous_section();
                                break;
                        }
                        // It will help to replace the
                        // one fragment to other.
                        return true;

                    }
                });
        bottom_next_prev.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        //Checking if the item is in checked state or not, if not take the required action
                        //EarlyCheckingExamNameAlreadyExistsOrNot_EX();

                        switch (item.getItemId()) {
                            case R.id.customize:
                                try {
                                    builder.setTitle("Enter Exam Name");
                                    input.setTextColor(Color.WHITE);
                                    builder.setView(input);
                                    //WE DEFINE THE FUNCTION, WHEN THE USER CHOOSES "YES"
                                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (input.getText().toString().isEmpty()) {
                                                Toast.makeText(Chekka_createexam.this, "Empty", Toast.LENGTH_LONG).show();

                                            } else {
                                                input_Str = input.getText().toString();
                                                customizevisibility();
                                                displaysection();
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

                                        }
                                    });

                                    //NOW, WE CREATE THE ALERT DIALG OBJECT
                                    AlertDialog ad = builder.create();

                                    //FINALLY, WE SHOW THE DIALOG
                                    ad.show();
                                }catch (Exception e){

                                }
                                break;
                            case R.id.discard_exam:
                                finish();
                                break;
                        }
                        // It will help to replace the
                        // one fragment to other.
                        return true;

                    }
                });

        g_exam_section.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                View radioButton = g_exam_section.findViewById(checkedId);
                g_exam_section_index = g_exam_section.indexOfChild(radioButton);

                button_layout.setVisibility(View.VISIBLE);
                System.out.println("checkedid" + checkedId);
                // Check which radio button was clicked
                switch (g_exam_section_index) {

                    case 0:
                        System.out.println("helloeveryone");
                        str = "1";
                        TOTAL_PAGES = 0;
                        TOTAL_PAGES = 2;
                        sheetslide();
                        buildCircles();
                        break;
                    case 1:

                        TOTAL_PAGES = 0;
                        TOTAL_PAGES = 2;
                        str = "2";
                        sheetslide();
                        buildCircles();
                        break;
                    case 2:

                        str = "3";
                        TOTAL_PAGES = 0;
                        TOTAL_PAGES = 2;
                        sheetslide();
                        buildCircles();

                        break;
                    case 3:
                        str = "4";
                        TOTAL_PAGES = 0;
                        TOTAL_PAGES = 2;
                        sheetslide();
                        buildCircles();
                        break;

                }
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });
        g_exam_set.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                View radioButton = g_exam_set.findViewById(checkedId);
                g_exam_set_index = g_exam_set.indexOfChild(radioButton);
                button_layout.setVisibility(View.VISIBLE);

                switch (g_exam_set_index) {
                    case 0:
                        System.out.println("g_exam_set " + g_exam_set_index);

                        break;
                    case 1:
                        System.out.println("g_examset " + g_exam_set_index);
                        break;
                }
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pager != null) {
            pager.clearOnPageChangeListeners();
        }
    }

    private void buildCircles() {
        circles = LinearLayout.class.cast(findViewById(R.id.circles));

        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);
        circles.removeAllViews();
        for (int i = 0; i < TOTAL_PAGES; i++) {
            circles.setVisibility(View.VISIBLE);
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.drawable.ic_circle);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            circles.addView(circle);
            System.out.println("CIRCLES:" + circles);
        }

        System.out.println("TOTAL:" + TOTAL_PAGES);
        setIndicator(0);

    }

    private void setIndicator(int index) {
        if (index < TOTAL_PAGES) {
            for (int i = 0; i < TOTAL_PAGES; i++) {
                ImageView circle = (ImageView) circles.getChildAt(i);
                if (i == index) {
                    circle.setColorFilter(getResources().getColor(R.color.darkblue));
                } else {
                    circle.setColorFilter(getResources().getColor(R.color.lightblue));
                }
            }
        }

    }

    private void endTutorial() {
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }


    private class ScreenSlideAdapter extends FragmentStatePagerAdapter {

        public ScreenSlideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SectionFragment sectionFragment = null;
            System.out.println("POSITIONALERT" + position);
            switch (position) {

                case 0:
                    sectionFragment = SectionFragment.newInstance(R.layout.zsheet_mt_10items_sect1);

                    break;
                case 1:
                    sectionFragment = SectionFragment.newInstance(R.layout.zsheet_mt_15items_sect1);

                    break;

            }

            return sectionFragment;
        }

        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }
    }

    private class ScreenSlideAdapterSection2 extends FragmentStatePagerAdapter {

        public ScreenSlideAdapterSection2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SectionFragment sectionFragment = null;
            switch (position) {
                case 0:
                    sectionFragment = sectionFragment.newInstance(R.layout.zsheet_mt_20items_sect2);

                    break;
                case 1:
                    sectionFragment = sectionFragment.newInstance(R.layout.zsheet_mt_30items_sect2);

                    break;
            }

            return sectionFragment;
        }

        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }
    }

    private class ScreenSlideAdapterSection3 extends FragmentStatePagerAdapter {

        public ScreenSlideAdapterSection3(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SectionFragment sectionFragment = null;
            switch (position) {
                case 0:
                    sectionFragment = sectionFragment.newInstance(R.layout.zsheet_mt_30items_sect3);

                    break;
                case 1:
                    sectionFragment = sectionFragment.newInstance(R.layout.zsheet_mt_45items_sect3);

                    break;

            }

            return sectionFragment;
        }

        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }
    }

    private class ScreenSlideAdapterSection4 extends FragmentStatePagerAdapter {

        public ScreenSlideAdapterSection4(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SectionFragment sectionFragment = null;
            switch (position) {
                case 0:
                    sectionFragment = sectionFragment.newInstance(R.layout.zsheet_mt_40items_sect4);
                    break;
                case 1:
                    sectionFragment = sectionFragment.newInstance(R.layout.zsheet_mt_60items_sect4);
                    break;

            }

            return sectionFragment;
        }

        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sheetslide() {
        pager = (ViewPager) findViewById(R.id.pager);
        if (str.equals("1")) {
            pagerAdapter = new Chekka_createexam.ScreenSlideAdapter(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);

        } else if (str.equals("2")) {
            pagerAdapter = new Chekka_createexam.ScreenSlideAdapterSection2(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);

        } else if (str.equals("3")) {
            pagerAdapter = new Chekka_createexam.ScreenSlideAdapterSection3(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);

        } else if (str.equals("4")) {
            pagerAdapter = new Chekka_createexam.ScreenSlideAdapterSection4(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);

        }

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == TOTAL_PAGES - 2 && positionOffset > 0) {
                    if (isOpaque) {
                        pager.setBackgroundColor(getResources().getColor(R.color.darkblue));

                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
                        pager.setBackgroundColor(getResources().getColor(R.color.lightblue));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                details_text = "";
                System.out.println("postiion" + position);
                int pos = g_exam_section.indexOfChild(findViewById(g_exam_section.getCheckedRadioButtonId()));
                switch (pos) {
                    case (0):

                        if (position == 0) {
                            details_text = "10 items multiple choice";
                            code_tag = "10MTSECT1";
                            totalcount = "10";
                            itempercount = "10";
                        } else if (position == 1) {
                            details_text = "15 items multiple choice";
                            code_tag = "15MTSECT1";
                            totalcount = "15";
                            itempercount = "15";
                        }
                        break;
                    case (1):

                        if (position == 0) {
                            details_text = "20 items multiple choice";
                            code_tag = "20MTSECT2";
                            totalcount = "20";
                            itempercount = "10";
                        } else if (position == 1) {
                            details_text = "30 items multiple choice";
                            code_tag = "30MTSECT2";
                            totalcount = "30";
                            itempercount = "15";
                        }
                        break;
                    case (2):

                        if (position == 0) {
                            details_text = "30 items multiple choice";
                            code_tag = "30MTSECT3";
                            totalcount = "30";
                            itempercount = "10";
                        } else if (position == 1) {
                            details_text = "45 items multiple choice";
                            code_tag = "45MTSECT3";
                            totalcount = "45";
                            itempercount = "15";
                        }
                        break;
                    case (3):
                        if (position == 0) {
                            details_text = "40 items multiple choice";
                            code_tag = "40MTSECT4";
                            totalcount = "40";
                            itempercount = "10";
                        } else if (position == 1) {
                            details_text = "60 items multiple choice";
                            code_tag = "60MTSECT4";
                            totalcount = "60";
                            itempercount = "15";
                        }
                        break;
                }
                text_details.setText("Details: " + details_text);
                txt_swipe.setVisibility(View.VISIBLE);
                customize_btn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // do this in a runnable to make sure the viewPager's views are already instantiated before triggering the onPageSelected call
        pager.post(new Runnable() {
            @Override
            public void run() {
                int pos = g_exam_section.indexOfChild(findViewById(g_exam_section.getCheckedRadioButtonId()));
                switch (pos) {
                    case (0):
                        details_text = "10 items multiple choice";
                        code_tag = "10MTSECT1";
                        itempercount = "10";
                        totalcount = "10";
                        break;
                    case (1):
                        details_text = "20 items multiple choice";
                        code_tag = "20MTSECT2";
                        itempercount = "10";
                        totalcount = "20";
                        break;
                    case (2):
                        details_text = "30 items multiple choice";
                        code_tag = "30MTSECT3";
                        itempercount = "10";
                        totalcount = "30";
                        break;
                    case (3):
                        details_text = "40 items multiple choice";
                        code_tag = "40MTSECT4";
                        itempercount = "10";
                        totalcount = "40";
                        break;
                }

                text_details.setText("Details: " + details_text);
                txt_swipe.setVisibility(View.VISIBLE);
                customize_btn.setVisibility(View.VISIBLE);
            }

        });

    }

    public void displaysheet() {

        if (code_tag.equals("10MTSECT1")) {
            sheetfor1.setImageResource(R.drawable.mt_10items_sect1);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mt_10items_sect1);
            scaledbmp = Bitmap.createScaledBitmap(bmp, 786, 962, false);
        } else if (code_tag.equals("15MTSECT1")) {
            sheetfor1.setImageResource(R.drawable.mt_15items_sect1);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mt_15items_sect1);
            scaledbmp = Bitmap.createScaledBitmap(bmp, 786, 962, false);
        } else if (code_tag.equals("20MTSECT2")) {
            sheetfor1.setImageResource(R.drawable.mt_20items_sect2);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mt_20items_sect2);
            scaledbmp = Bitmap.createScaledBitmap(bmp, 786, 962, false);
        } else if (code_tag.equals("30MTSECT2")) {
            sheetfor1.setImageResource(R.drawable.mt_30items_sect2);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mt_30items_sect2);
            scaledbmp = Bitmap.createScaledBitmap(bmp, 786, 962, false);
        } else if (code_tag.equals("30MTSECT3")) {
            sheetfor1.setImageResource(R.drawable.kk);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.kk);
            scaledbmp = Bitmap.createScaledBitmap(bmp, 786, 962, false);
        } else if (code_tag.equals("45MTSECT3")) {
            sheetfor1.setImageResource(R.drawable.mt_45items_sect3);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mt_45items_sect3);
            scaledbmp = Bitmap.createScaledBitmap(bmp, 786, 962, false);
        } else if (code_tag.equals("40MTSECT4")) {
            sheetfor1.setImageResource(R.drawable.mt_40items_sect4);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mt_40items_sect4);
            scaledbmp = Bitmap.createScaledBitmap(bmp, 786, 962, false);
        } else if (code_tag.equals("60MTSECT4")) {
            sheetfor1.setImageResource(R.drawable.mt60);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mt60);
            scaledbmp = Bitmap.createScaledBitmap(bmp, 786, 962, false);
        }
        checkPermission();
        requestPermission();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void generatePDF() {

        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint title = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();

        canvas.drawBitmap(scaledbmp, 20, 20, paint);


        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        title.setTextSize(15);

        pdfDocument.finishPage(myPage);

        // below line is used to set the name of
        // our PDF file and its path.
        String ex = input_Str;
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/Chekka/");
        dir.mkdir();
        File file = new File(dir, ex + " - " + System.currentTimeMillis() + ".pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(Chekka_createexam.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {

            e.printStackTrace();
        }

        pdfDocument.close();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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

    private void displaysection() {
        if (g_exam_section_index == 0) {
            layout_forsection1_EX.setVisibility(View.VISIBLE);
            layout_forsection2_EX.setVisibility(View.INVISIBLE);
            layout_forsection3_EX.setVisibility(View.INVISIBLE);
            layout_forsection4_EX.setVisibility(View.INVISIBLE);
            if (code_tag.equals("10MTSECT1")) {
                itemscount_sec1.setText("1-10");
                marksforcorrect_sec1 = 10;
                marksforincorrect_sec1 = 10;
            } else if (code_tag.equals("15MTSECT1")) {
                itemscount_sec1.setText("1-15");
                marksforcorrect_sec1 = 15;
                marksforincorrect_sec1 = 15;
            }
        } else if (g_exam_section_index == 1) {
            layout_forsection1_EX.setVisibility(View.VISIBLE);
            layout_forsection2_EX.setVisibility(View.VISIBLE);
            layout_forsection3_EX.setVisibility(View.INVISIBLE);
            layout_forsection4_EX.setVisibility(View.INVISIBLE);
            if (code_tag.equals("20MTSECT2")) {
                itemscount_sec1.setText("1-10");
                itemscount_sec2.setText("11-20");
                marksforcorrect_sec1 = 10;
                marksforincorrect_sec1 = 10;
                marksforcorrect_sec2 = 10;
                marksforincorrect_sec2 = 10;
            } else if (code_tag.equals("30MTSECT2")) {
                itemscount_sec1.setText("1-15");
                itemscount_sec2.setText("16-30");
                marksforcorrect_sec1 = 15;
                marksforincorrect_sec1 = 15;
                marksforcorrect_sec2 = 15;
                marksforincorrect_sec2 = 15;
            }
        } else if (g_exam_section_index == 2) {
            layout_forsection1_EX.setVisibility(View.VISIBLE);
            layout_forsection2_EX.setVisibility(View.VISIBLE);
            layout_forsection3_EX.setVisibility(View.VISIBLE);
            layout_forsection4_EX.setVisibility(View.INVISIBLE);
            if (code_tag.equals("30MTSECT3")) {
                itemscount_sec1.setText("1-10");
                itemscount_sec2.setText("11-20");
                itemscount_sec3.setText("21-30");
                marksforcorrect_sec1 = 10;
                marksforincorrect_sec1 = 10;
                marksforcorrect_sec2 = 10;
                marksforincorrect_sec2 = 10;
                marksforcorrect_sec3 = 10;
                marksforincorrect_sec3 = 10;
            } else if (code_tag.equals("45MTSECT3")) {
                itemscount_sec1.setText("1-15");
                itemscount_sec2.setText("16-30");
                itemscount_sec3.setText("31-45");
                marksforcorrect_sec1 = 15;
                marksforincorrect_sec1 = 15;
                marksforcorrect_sec2 = 15;
                marksforincorrect_sec2 = 15;
                marksforcorrect_sec3 = 15;
                marksforincorrect_sec3 = 15;
            }
        } else if (g_exam_section_index == 3) {
            layout_forsection1_EX.setVisibility(View.VISIBLE);
            layout_forsection2_EX.setVisibility(View.VISIBLE);
            layout_forsection3_EX.setVisibility(View.VISIBLE);
            layout_forsection4_EX.setVisibility(View.VISIBLE);
            if (code_tag.equals("40MTSECT4")) {
                itemscount_sec1.setText("1-10");
                itemscount_sec2.setText("11-20");
                itemscount_sec3.setText("21-30");
                itemscount_sec4.setText("31-40");
                marksforcorrect_sec1 = 10;
                marksforincorrect_sec1 = 10;
                marksforcorrect_sec2 = 10;
                marksforincorrect_sec2 = 10;
                marksforcorrect_sec3 = 10;
                marksforincorrect_sec3 = 10;
                marksforcorrect_sec4 = 10;
                marksforincorrect_sec4 = 10;
            } else if (code_tag.equals("60MTSECT4")) {
                itemscount_sec1.setText("1-15");
                itemscount_sec2.setText("16-30");
                itemscount_sec3.setText("31-45");
                itemscount_sec4.setText("46-60");
                marksforcorrect_sec1 = 15;
                marksforincorrect_sec1 = 15;
                marksforcorrect_sec2 = 15;
                marksforincorrect_sec2 = 15;
                marksforcorrect_sec3 = 15;
                marksforincorrect_sec3 = 15;
                marksforcorrect_sec4 = 15;
                marksforincorrect_sec4 = 15;
            }
        }

    }
    private void makeimage(){
        BitmapDrawable drawable = (BitmapDrawable) sheetfor1.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/Chekka-Exam/");
        dir.mkdir();
        File file = new File(dir,""+editexamname.getText().toString() + "-"+dbex_classname + ".jpg");
        try {
            outputStream = new FileOutputStream(file);
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Toast.makeText(getApplicationContext(), "" + file, Toast.LENGTH_SHORT).show();
        buttonproceed_P2.setEnabled(true);
        System.out.println("Not image saved");
        System.out.println("set 2");

        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private  void forprintoutput(){
        dbex_examname =  input_Str;
        dbex_classname = addexam_spinner.getSelectedItem().toString();
        dbex_set = String.valueOf(g_exam_set_index + 1);
        dbex_section = String.valueOf(g_exam_section_index + 1);
        dbex_totalcount = totalcount;
        dbex_itempercount = itempercount;
        dbex_category = code_tag;
        dbex_userid = Chekka.textView_id_LO.getText().toString();

        System.out.println("=== ?");
        System.out.println("exam name: " + dbex_examname);
        System.out.println("class name: " + dbex_classname);
        System.out.println("set: " + dbex_set);
        System.out.println("section: " + dbex_section);
        System.out.println("total count: " + dbex_totalcount);
        System.out.println("item per count: " + dbex_itempercount);
        System.out.println("category: " + dbex_category);
        System.out.println("userid: " + dbex_userid);
    }
    private  void customizevisibility(){

        bottom_next_prev.setVisibility(View.INVISIBLE);
        scrollview_for_examsheet.setVisibility(View.VISIBLE);
        layout_createexam_AC.setVisibility(View.INVISIBLE);
        layout_welcomescreen_test.setVisibility(View.INVISIBLE);
        bottom_savesections.setVisibility(View.VISIBLE);
    }
    private void customizevisibility_discard(){
        bottom_next_prev.setVisibility(View.INVISIBLE);
        layout_createexam_AC.setVisibility(View.INVISIBLE);
    }
    private void next_section(){
        scrollview_for_examsheet.setVisibility(View.INVISIBLE);
        bottom_next_prev.setVisibility(View.INVISIBLE);
        bottom_savesections.setVisibility(View.INVISIBLE);
        scrollview_for_examsheet.setVisibility(View.INVISIBLE);
        layout_createexam_AC.setVisibility(View.INVISIBLE);
        layout_welcomescreen_test.setVisibility(View.INVISIBLE);
        bottom_saveimage.setVisibility(View.VISIBLE);
        sheetfor1.setVisibility(View.VISIBLE);
    }
    private void previous_section(){
        bottom_next_prev.setVisibility(View.INVISIBLE);
        layout_createexam_AC.setVisibility(View.INVISIBLE);
    }
    private void docorrectcount() {
        mcsec1 = mc_spinner_for_section1_EX.getSelectedItem().toString();
        int mccount1 = 0;
        int mcscore1 = 0;
        for (int i = 1; i <= marksforcorrect_sec1; i++) {
            dbex_correct.add(Integer.valueOf(mc_spinner_for_section1_EX.getSelectedItem().toString()));
            System.out.println("dbex_correct" + dbex_correct);
            mccount1++;
        }
        if (mcsec1.equals("")) {

        } else {
            mcscore1 = Integer.parseInt(mcsec1) * mccount1;

        }
        mcsec2 = mc_spinner_for_section2_EX.getSelectedItem().toString();
        int mscount2 = 0;
        int mcscore2 = 0;

        for (int i = 1; i <= marksforcorrect_sec2; i++) {
            dbex_correct.add(Integer.valueOf(mc_spinner_for_section2_EX.getSelectedItem().toString()));
            System.out.println("dbex_correct2" + dbex_correct);
            mscount2++;
        }
        if (mcsec2.equals("")) {

        } else {
            mcscore2 = Integer.parseInt(mcsec2) * mscount2;

        }


       mcsec3 = mc_spinner_for_section3_EX.getSelectedItem().toString();
        int mscount3 = 0;
        int mcscore3 = 0;
        for (int i = 1; i <= marksforcorrect_sec3; i++) {
            dbex_correct.add(Integer.valueOf(mc_spinner_for_section3_EX.getSelectedItem().toString()));
            System.out.println("dbex_correct2" + dbex_correct);
            mscount3++;
        }

        if (mcsec3.equals("")) {

        } else {
            mcscore3 = Integer.parseInt(mcsec3) * mscount3;

        }
      mcsec4 = mc_spinner_for_section4_EX.getSelectedItem().toString();
        int mscount4 = 0;
        int mcscore4 = 0;

        for (int i = 1; i <= marksforcorrect_sec4; i++) {
            dbex_correct.add(Integer.valueOf(mc_spinner_for_section4_EX.getSelectedItem().toString()));
            System.out.println("dbex_correct2" + dbex_correct);
            mscount4++;
        }
        if (mcsec4.equals("")) {

        } else {
            mcscore4 = Integer.parseInt(mcsec4) * mscount4;


        }
        allscore = mcscore1 + mcscore2 + mcscore3 + mcscore4;


        System.out.println("TOTAL SCORE ALL: "+allscore);
        String c= dbex_correct.toString();
        StringTokenizer tokens = new StringTokenizer(c, "[]");
        for (int i = 0; i <= 0; i++) {
            c_store = tokens.nextToken();

        }
        System.out.println("kooper11-" + c_store);
      }
    private void doincorrectcount(){

        for(int i = 1; i <= marksforincorrect_sec1; i++){
                dbex_incorrect.add(Integer.valueOf(mi_spinner_for_section1_EX.getSelectedItem().toString()));
                System.out.println("correct"+dbex_incorrect);
            }

        for (int i = 1; i <= marksforincorrect_sec2; i++){
            dbex_incorrect.add(Integer.valueOf(mi_spinner_for_section2_EX.getSelectedItem().toString()));
            System.out.println("correct2"+dbex_incorrect);
        }
        for (int i = 1; i <= marksforincorrect_sec3; i++){
            dbex_incorrect.add(Integer.valueOf(mi_spinner_for_section3_EX.getSelectedItem().toString()));
            System.out.println("correct2"+dbex_incorrect);
        }
        for (int i = 1; i <= marksforincorrect_sec4; i++){
            dbex_incorrect.add(Integer.valueOf(mi_spinner_for_section4_EX.getSelectedItem().toString()));
            System.out.println("correct2"+dbex_incorrect );
        }

        String inc= dbex_incorrect.toString();
        StringTokenizer tokens = new StringTokenizer(inc, "[]");
        for (int i = 0; i <= 0; i++) {
            inc_store = tokens.nextToken();

        }
        misec1 =  mi_spinner_for_section1_EX.getSelectedItem().toString();
        misec2 =  mi_spinner_for_section2_EX.getSelectedItem().toString();
        misec3 =  mi_spinner_for_section3_EX.getSelectedItem().toString();
        misec4 =  mi_spinner_for_section4_EX.getSelectedItem().toString();
        System.out.println("kooper00-" + c_store);
        if (misec1.equals("")) {

        } else {
            dbex_firstnumberpersection.add(Integer.valueOf(misec1));

        }
        if (misec2.equals("")) {

        } else {
            dbex_firstnumberpersection.add(Integer.valueOf(misec2));

        }
        if (misec3.equals("")) {

        } else {
            dbex_firstnumberpersection.add(Integer.valueOf(misec3));

        }
        if (misec4.equals("")) {

        } else {
            dbex_firstnumberpersection.add(Integer.valueOf(misec4));

        }
        String inc_fn= dbex_firstnumberpersection.toString();
        StringTokenizer inc_fn_tokens = new StringTokenizer(inc_fn, "[]");
        for (int i = 0; i <= 0; i++) {
            inc_fn_store = inc_fn_tokens.nextToken();

        }
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


        // Opening SQLite database write permission.
        //   sqLiteDatabaseObj = mydb.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(mydb.final_tbl_exam, null, " " + mydb.final_exam_name + "=?", new String[]{editexamname.getText().toString()}, " " + mydb.final_user_ID + "= " + Chekka.textView_id_LO.getText().toString(), null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.

                System.out.println();
                Holder_TemporaryClassName_CL = "Email Found";
                System.out.println("Email Found 101" + mydb.final_exam_name);

                // Closing cursor.
                cursor.close();
            }

        }
        System.out.println("Email Found 102" + mydb.final_exam_name);
        CheckFinalResultClassName_CL();
        // Calling method to check final result and insert data into SQLite database.


    }
    public void CheckFinalResultClassName_CL() {

        // Checking whether email is already exists or not.
        if (Holder_TemporaryClassName_CL.equalsIgnoreCase("Email Found")) {
            // If email is exists then toast msg will display.
            editexamname.setText("");

            Toast.makeText(Chekka_createexam.this, "Exam Name Already Exists. Check it out!", Toast.LENGTH_LONG).show();

        } else {
            InsertClassNameIntoSQLiteDatabase_CL();
        }
        Holder_TemporaryClassName_CL = "Not_Found";
    }
    public void InsertClassNameIntoSQLiteDatabase_CL() {

        // If editText is not empty then this block will executed.
        nextbutton.setVisibility(View.VISIBLE);
        System.out.println("HEEEEY "+editexamname.getText().toString());
        examnamesection.setText(""+editexamname.getText().toString());
        examnamesection.setVisibility(View.VISIBLE);
        customizevisibility();
        displaysection();
        popexamname.setVisibility(View.INVISIBLE);

        // This block will execute if any of the registration EditText is empty.

    }

}


