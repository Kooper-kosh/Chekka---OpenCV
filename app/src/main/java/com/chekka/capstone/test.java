package com.chekka.capstone;

import android.Manifest;
import android.app.admin.SystemUpdateInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
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

import org.apache.harmony.misc.SystemUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHexColor;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import static org.apache.poi.ss.usermodel.HorizontalAlignment.*;

public class test extends AppCompatActivity {
    String str_resultupper,str_resultmid, str_resultlower;
    int resultmps = 0;
    int str_total_frequency;
    int str_resultfrequency;
    float testdifficulty, discrimination;
    String difficultylevel, discriminationlevel, remarks, correctreponse;
    int str_resultpercentage;
    HSSFWorkbook hssfWorkbook;
    StringBuilder sbformidcor = new StringBuilder();
    StringBuilder sbformidincor = new StringBuilder();
    StringBuilder sbforlowcor = new StringBuilder();
    StringBuilder sbforlowincor = new StringBuilder();
    StringBuilder sbforhighcor = new StringBuilder();
    StringBuilder sbforhighincor = new StringBuilder();
    int forcounter;

    int numberofitems;
    public static int countmid = 0, countlow = 0, counthigh = 0;
    String Sthuhu = "";
    String sthuhu1= "";
    String sthuhu2= "";
    String sthuhu3= "";
    String sthuhu4= "";
    private EditText editTextExcel;
    SQLiteHelper mydb;
    public static ArrayList<HashMap<String, String>> examlist, resultlist;
    public static ArrayList<HashMap<String, String>> studentinfolistfordisplay;
    public static ListAdapter adapter, examadapter, studentinfoadapter;
    public static ListView student_lv, lv, exam_lv, student_info_lv;
    public static RelativeLayout  layout_listview_examsheet;
    public static String listview_exam_Name,listview_total_count;
    int numberofmerge = 0;
    File filepath;
    SQLiteDatabase sqLiteDatabaseObj;
    ImageView finish_dastud;
    public static int result = 0;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.tbl_students_answer101 + " (" + mydb.students_answerID101 + " INTEGER PRIMARY KEY, " + mydb.students_answer101 + " VARCHAR NOT NULL, " + mydb.students_score101 + " VARCHAR NOT NULL, " + mydb.students_average101 + " VARCHAR NOT NULL, " + mydb.students_examID101 + " VARCHAR NOT NULL, " + mydb.students_examname101 + " VARCHAR NOT NULL, " + mydb.students_itemcount101+ " VARCHAR NOT NULL, " + mydb.students_image101 + " BLOB NOT NULL, " + mydb.students_userID101 + " VARCHAR NOT NULL, " + mydb.students_name101 + " VARCHAR NOT NULL, " + mydb.students_number101 + " VARCHAR NOT NULL," + "FOREIGN KEY (students_number101) REFERENCES tbl_Students(studentsID))");
        layout_listview_examsheet = (RelativeLayout) findViewById(R.id.layout_listview_examsheet);
         ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        mydb = new SQLiteHelper(this);
        exam_lv = (ListView) findViewById(R.id.listview_examsheet);
       examlist = mydb.GetExamName(Chekka.textView_id_LO.getText().toString());
      System.out.println("Examlist" + examlist);
       examadapter = new SimpleAdapter(test.this, examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count","final_date"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount,R.id.item_date_EN});
      exam_lv.setAdapter(examadapter);
       exam_lv.setVisibility(View.VISIBLE);
      layout_listview_examsheet.setVisibility(View.VISIBLE);
        finish_dastud = findViewById(R.id.finish_dastud);

        finish_dastud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
      exam_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
             listview_exam_Name = examlist.get(position).get("final_exam_name");
             System.out.println("listviewexamname " + listview_exam_Name);
             listview_total_count = examlist.get(position).get("final_total_count");
             System.out.println("listview_total_count " + listview_total_count);

             str_total_frequency = 0;
             mydb.counter_item = 0;

             mydb.counter_low = 0;
             mydb.counter_high = 0;
             resultlist = mydb.GetStudentResultItem(listview_exam_Name);
             if (mydb.counter_item >= 3) {
                 System.out.println("counteritem: " + mydb.counter_item);
                 float roundOff = ((float) mydb.counter_item * (float) 27) / 100;
                 System.out.println("rff " + String.format("%.2f", roundOff));
                 int exact = (int) roundOff;
                 String high5 = exact + "." + 50;
                 double high5db = Double.parseDouble(high5);

                 String low49 = exact + "." + 49;
                 double low49db = Double.parseDouble(low49);


                 if (roundOff >= high5db) {
                     result = exact + 1;
                 } else if (roundOff <= low49db) {
                     result = exact;
                 }

                 System.out.println("resultdb " + result);
                 //  roundTwoDecimals(a);
                 System.out.println("round of: " + high5db);
                 System.out.println("round of: " + roundOff);


                 int countforcorrect = 0;
                 int countforincorrect = 0;


                 mydb.Getresultlowcounter(listview_exam_Name);
                 mydb.Getresulthighcounter(listview_exam_Name);
                 mydb.Getexamidformid(listview_exam_Name);
                 int totalcount = Integer.parseInt(listview_total_count);
                 for (counthigh = 0; counthigh < totalcount; counthigh++) {
                     mydb.Getresulthigh(listview_exam_Name);
                     sbforhighcor.append(mydb.highcountcor + ",");
                     sbforhighincor.append(mydb.highcountincor + ",");

                     mydb.counter_high_sb = 0;
                 }

                 mydb.highcountcor = 0;
                 mydb.highcountincor = 0;
                 for (countlow = 0; countlow < totalcount; countlow++) {
                     mydb.Getresultlow(listview_exam_Name);
                     sbforlowcor.append(mydb.lowcountcor + ",");
                     sbforlowincor.append(mydb.lowcountincor + ",");

                     mydb.counter_low_sb = 0;
                 }
                 mydb.lowcountcor = 0;
                 mydb.lowcountincor = 0;
                 for (countmid = 0; countmid < totalcount; countmid++) {

                     mydb.Getresultmidcancelhigh(listview_exam_Name);

                     sbformidcor.append(mydb.countcor + ",");
                     sbformidincor.append(mydb.countincor + ",");

                 }

                 mydb.countcor = 0;
                 mydb.countincor = 0;

                 System.out.println("sbformidcor =           " + sbformidcor);
                 System.out.println("sbformidincor =         " + sbformidincor);
                 System.out.println("sbforhighcor =          " + sbforhighcor);
                 System.out.println("sbforhighincor =        " + sbforhighincor);
                 System.out.println("sbforlowcor =           " + sbforlowcor);
                 System.out.println("sbforlowincor =         " + sbforlowincor);

                 System.out.println("result + high " + mydb.sbforhigh);
                 System.out.println("result + low " + mydb.sbforlow);


                 hssfWorkbook = new HSSFWorkbook();
                 HSSFSheet hssfSheet = hssfWorkbook.createSheet("Custom Sheet");
                 HSSFCellStyle fortwerk = hssfWorkbook.createCellStyle();
                 fortwerk.setAlignment(CellStyle.ALIGN_CENTER);
                 Font fontfortwerk = hssfWorkbook.createFont();
                 HSSFPalette palette = hssfWorkbook.getCustomPalette();

                 //HSSFColor pink = palette.findSimilarColor(255, 192, 203);

                 HSSFCellStyle hssfSheet2 = hssfWorkbook.createCellStyle();
                 hssfSheet2.setAlignment(CellStyle.ALIGN_CENTER);
                 hssfSheet2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 hssfSheet2.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 hssfSheet2.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 hssfSheet2.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                 HSSFCellStyle hssfSheet4 = hssfWorkbook.createCellStyle();
                 hssfSheet2.setAlignment(CellStyle.ALIGN_LEFT);

                 HSSFCellStyle hssfSheet3 = hssfWorkbook.createCellStyle();
                 hssfSheet3.setAlignment(CellStyle.ALIGN_CENTER);

                 HSSFCellStyle itemanalysistitle = hssfWorkbook.createCellStyle();
                 Font font = hssfWorkbook.createFont();
              //   font.setColor(HSSFColor.DARK_BLUE.index);
                 font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                 itemanalysistitle.setFont(font);
                 itemanalysistitle.setAlignment(CellStyle.ALIGN_CENTER);


                 HSSFCellStyle quartertype = hssfWorkbook.createCellStyle();
                 Font quartertypefont = hssfWorkbook.createFont();
                 quartertypefont.setColor(HSSFColor.DARK_RED.index);
                 quartertypefont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                 quartertype.setFont(quartertypefont);
                 quartertype.setAlignment(CellStyle.ALIGN_CENTER);


                 HSSFCellStyle backgroundcolorleft = hssfWorkbook.createCellStyle();
                 backgroundcolorleft.setAlignment(CellStyle.ALIGN_LEFT);
                 Font fontleft = hssfWorkbook.createFont();
                 fontleft.setColor(HSSFColor.BLACK.index);
                 fontleft.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                 backgroundcolorleft.setFont(fontleft);
                 backgroundcolorleft.setWrapText(true);
                 backgroundcolorleft.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundcolorleft.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundcolorleft.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundcolorleft.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);


                 HSSFCellStyle last = hssfWorkbook.createCellStyle();
                 last.setAlignment(CellStyle.ALIGN_CENTER);
                 Font fontlast = hssfWorkbook.createFont();
                 fontlast.setColor(HSSFColor.BLACK.index);
                 fontlast.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                 last.setFont(fontlast);
                 last.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 last.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 last.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 last.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);


                 HSSFCellStyle backgroundcolorcenter = hssfWorkbook.createCellStyle();
              //   backgroundcolorcenter.setFillForegroundColor(pink.getIndex());
                 Font fontcenter = hssfWorkbook.createFont();
                 fontcenter.setColor(HSSFColor.RED.index);
                 fontcenter.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                 backgroundcolorcenter.setFont(fontcenter);
             //    backgroundcolorcenter.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                 backgroundcolorcenter.setAlignment(CellStyle.ALIGN_CENTER);
                 backgroundcolorcenter.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundcolorcenter.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundcolorcenter.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundcolorcenter.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);


                 HSSFCellStyle backgroundveinte = hssfWorkbook.createCellStyle();
                // backgroundveinte.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
                 Font fontveinte = hssfWorkbook.createFont();
               //  fontveinte.setColor(HSSFColor.BLACK.index);
                 fontveinte.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                 backgroundveinte.setFont(fontveinte);
                // backgroundveinte.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                 backgroundveinte.setAlignment(CellStyle.ALIGN_LEFT);
                 backgroundveinte.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundveinte.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundveinte.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundveinte.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                 HSSFCellStyle examineesdata = hssfWorkbook.createCellStyle();
              //   examineesdata.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
                 Font fontexamineesdata = hssfWorkbook.createFont();
               //  fontexamineesdata.setColor(HSSFColor.RED.index);
                 fontexamineesdata.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                 examineesdata.setFont(fontexamineesdata);
               //  examineesdata.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                 examineesdata.setAlignment(CellStyle.ALIGN_CENTER);
                 examineesdata.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 examineesdata.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 examineesdata.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 examineesdata.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                 HSSFCellStyle sevendata = hssfWorkbook.createCellStyle();
                 Font fontsevendata = hssfWorkbook.createFont();
                // fontexamineesdata.setColor(HSSFColor.DARK_BLUE.index);
                 fontexamineesdata.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                 sevendata.setFont(fontsevendata);
                 sevendata.setAlignment(CellStyle.ALIGN_CENTER);
                 sevendata.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 sevendata.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 sevendata.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 sevendata.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                 HSSFCellStyle itemdata = hssfWorkbook.createCellStyle();
              //   itemdata.setFillForegroundColor(pink.getIndex());
                 itemdata.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                 Font fontitemdata = hssfWorkbook.createFont();
             //    fontitemdata.setColor(HSSFColor.DARK_BLUE.index);
                 fontitemdata.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                 itemdata.setFont(fontitemdata);
                 itemdata.setAlignment(CellStyle.ALIGN_CENTER);
                 itemdata.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 itemdata.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 itemdata.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 itemdata.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);


                 HSSFCellStyle backgroundcolorleftcenter = hssfWorkbook.createCellStyle();
                 backgroundcolorleftcenter.setAlignment(CellStyle.ALIGN_CENTER);
                 Font fontleftcenter = hssfWorkbook.createFont();
                // fontleftcenter.setColor(HSSFColor.BLACK.index);
                 fontleftcenter.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                 backgroundcolorleftcenter.setFont(fontleftcenter);
                 backgroundcolorleftcenter.setBorderBottom(HSSFCellStyle.BORDER_THICK);
                 backgroundcolorleftcenter.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundcolorleftcenter.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundcolorleftcenter.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                 HSSFCellStyle backgroundLOWTHIN = hssfWorkbook.createCellStyle();
                 backgroundLOWTHIN.setAlignment(CellStyle.ALIGN_CENTER);
                 Font fontbackgroundLOWTHIN = hssfWorkbook.createFont();
               //  fontbackgroundLOWTHIN.setColor(HSSFColor.BLACK.index);
                 fontbackgroundLOWTHIN.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
                 backgroundLOWTHIN.setFont(fontbackgroundLOWTHIN);
                 backgroundLOWTHIN.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundLOWTHIN.setBorderTop(HSSFCellStyle.BORDER_THICK);
                 backgroundLOWTHIN.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 backgroundLOWTHIN.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                 HSSFCellStyle difficlevel = hssfWorkbook.createCellStyle();
                 difficlevel.setAlignment(CellStyle.ALIGN_CENTER);
                 Font fontdifficlevel = hssfWorkbook.createFont();
               //  fontdifficlevel.setColor(HSSFColor.BLUE.index);
                 difficlevel.setFont(fontdifficlevel);
                 difficlevel.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 difficlevel.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 difficlevel.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 difficlevel.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);


                 HSSFCellStyle forbot = hssfWorkbook.createCellStyle();
                 forbot.setAlignment(CellStyle.ALIGN_CENTER);
                 Font fontforbot = hssfWorkbook.createFont();
               //  fontforbot.setColor(HSSFColor.BLACK.index);
                 forbot.setFont(fontforbot);
                 forbot.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 forbot.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 forbot.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 forbot.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                 HSSFCellStyle forgreen = hssfWorkbook.createCellStyle();
                 forgreen.setAlignment(CellStyle.ALIGN_CENTER);
                 Font fontforgreen = hssfWorkbook.createFont();
              //   fontforgreen.setColor(HSSFColor.GREEN.index);
                 forgreen.setFont(fontforgreen);
                 forgreen.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 forgreen.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 forgreen.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 forgreen.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                 HSSFColor gray = palette.findSimilarColor(211, 211, 211);
                 HSSFCellStyle forupper = hssfWorkbook.createCellStyle();
                 forupper.setAlignment(CellStyle.ALIGN_CENTER);
                 Font fontforupper = hssfWorkbook.createFont();
               //  fontforbot.setColor(HSSFColor.BLACK.index);
                 forupper.setFont(fontforupper);
                 forupper.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 forupper.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 forupper.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 forupper.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
                 forupper.setFillForegroundColor(gray.getIndex());
               //  forupper.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


                 HSSFCellStyle formid = hssfWorkbook.createCellStyle();
                 formid.setAlignment(CellStyle.ALIGN_CENTER);
                 Font fontformid = hssfWorkbook.createFont();
               //  fontformid.setColor(HSSFColor.BLACK.index);
                 formid.setFont(fontformid);
                 formid.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 formid.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 formid.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 formid.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
                 formid.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
              //   formid.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                 HSSFColor fenk = palette.findSimilarColor(253, 200, 218);
                 HSSFCellStyle forlow = hssfWorkbook.createCellStyle();
                 forlow.setAlignment(CellStyle.ALIGN_CENTER);
                 Font fontforlow = hssfWorkbook.createFont();
               //  fontforbot.setColor(HSSFColor.BLACK.index);
                 forlow.setFont(fontforlow);
                 forlow.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 forlow.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 forlow.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 forlow.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
                 forlow.setFillForegroundColor(fenk.getIndex());
           //      forlow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                 HSSFCellStyle forred = hssfWorkbook.createCellStyle();
                 forred.setAlignment(CellStyle.ALIGN_CENTER);
                 Font fontforred = hssfWorkbook.createFont();
               //  fontforred.setColor(HSSFColor.RED.index);
                 forred.setFont(fontforred);
                 forred.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                 forred.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                 forred.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                 forred.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                 String sbforid_str = sbforhighincor.toString();
                 StringTokenizer tokens = new StringTokenizer(sbforid_str, ",");

                 String sbforname_str = sbformidincor.toString();
                 StringTokenizer tokensforname = new StringTokenizer(sbforname_str, ",");

                 String sbforscore_str = sbforlowincor.toString();
                 StringTokenizer tokensforscore = new StringTokenizer(sbforscore_str, ",");

                 // String sbfor_label = "Student ID,Student Name,Score";

                 // StringTokenizer tokensforlabel = new StringTokenizer(sbfor_label, ",");

                 System.out.println("" + sbforid_str + "\n" + sbforname_str + "\n" + sbforscore_str);

                 //  hssfSheet.setColumnWidth(0, 3000);
                 // hssfSheet.setColumnWidth(1, 3000);
                 for (int i = 0; i < 12; i++) {
                     if (i < 11) {
                         hssfSheet.setColumnWidth(i, 5000);
                     } else if (i == 11) {
                         hssfSheet.setColumnWidth(i, 10000);
                     }
                 }


                 numberofmerge = 13;
                 for (int i = 0; i < numberofmerge; i++) {
                     switch (i) {
                         case 0:
                         case 1:
                         case 2:
                         case 3:
                         case 4:
                         case 5:
                         case 6:
                         case 7:
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 0, 12));
                             break;
                         case 8:
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 1, 5));
                             break;
                         case 9:
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 0, 2));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 3, 5));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 8, 9));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 10, 11));
                             break;
                         case 10:
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 0, 1));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 2, 2));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 3, 4));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 5, 5));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 7, 8));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 9, 9));
                             break;
                         case 11:
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 1, 3));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 4, 5));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 6, 7));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 8, 9));
                             break;
                         case 12:
                             hssfSheet.addMergedRegion(new CellRangeAddress(i - 1, i, 0, 0));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i - 1, i, 10, 10));
                             hssfSheet.addMergedRegion(new CellRangeAddress(i - 1, i, 11, 11));

                             break;

                     }
                     //  hssfSheet.addMergedRegion(new CellRangeAddress(i,i,0,12));
                 }


                 //  HSSFRow hssfRowRepublic = hssfSheet.createRow(0);
                 for (int i = 0; i < numberofmerge; i++) {
                     HSSFRow hssfRowRepublic = hssfSheet.createRow(i);
                     HSSFCell hssfCellDepartment = hssfRowRepublic.createCell(0);
                     switch (i) {
                         case 0:

                             hssfCellDepartment.setCellValue("Republic of the Philippines");
                             break;
                         case 1:
                             hssfCellDepartment.setCellValue("Department of Education");
                             break;
                         case 2:
                             hssfCellDepartment.setCellValue("National Capital Region");
                             break;
                         case 3:
                             hssfCellDepartment.setCellValue("DIVISIONS OF CITY SCHOOLS - VALENZUELA");
                             break;
                         case 4:
                             hssfCellDepartment.setCellValue("Pio Valenzuela St., Marulas, Vaelnzuela");
                             break;
                         case 5:
                             hssfCellDepartment.setCellValue("");
                             break;
                         case 6:
                             HSSFCell hssfCellblue = hssfRowRepublic.createCell(0);
                             hssfCellblue.setCellValue("Item Analysis for " + listview_exam_Name);
                             hssfCellblue.setCellStyle(itemanalysistitle);
                             break;
                         case 7:
                             HSSFCell hssfCellred = hssfRowRepublic.createCell(0);
                             hssfCellred.setCellValue("<edit space for quarter title and SY xxxx - xxxx>");
                             hssfCellred.setCellStyle(quartertype);
                             break;
                         case 8:
                             HSSFCell hssfCellNew5 = hssfRowRepublic.createCell(0);
                             hssfCellNew5.setCellValue("School: ");
                             hssfCellNew5.setCellStyle(backgroundcolorleft);

                             HSSFCell hssfCellNew2 = hssfRowRepublic.createCell(1);
                             hssfCellNew2.setCellValue("Veinte Reales National High School");
                             hssfCellNew2.setCellStyle(backgroundveinte);

                             HSSFCell hssfCellNewtwo = hssfRowRepublic.createCell(2);
                             hssfCellNewtwo.setCellStyle(backgroundveinte);
                             HSSFCell hssfCellNewthree = hssfRowRepublic.createCell(3);
                             hssfCellNewthree.setCellStyle(backgroundveinte);
                             HSSFCell hssfCellNewfour = hssfRowRepublic.createCell(4);
                             hssfCellNewfour.setCellStyle(backgroundveinte);
                             HSSFCell hssfCellNewfive = hssfRowRepublic.createCell(5);
                             hssfCellNewfive.setCellStyle(backgroundveinte);

                             break;
                         case 9:
                             HSSFCell hssfCellNew11 = hssfRowRepublic.createCell(0);
                             hssfCellNew11.setCellValue("Grade Level: ");
                             hssfCellNew11.setCellStyle(backgroundcolorleft);

                             HSSFCell hssfCellNew13 = hssfRowRepublic.createCell(3);
                             hssfCellNew13.setCellValue("<edit space for grade level>");
                             hssfCellNew13.setCellStyle(backgroundcolorcenter);

                             HSSFCell hssfCellNew = hssfRowRepublic.createCell(8);
                             hssfCellNew.setCellValue("Subject Teacher:");
                             hssfCellNew.setCellStyle(backgroundcolorleft);

                             HSSFCell hssfCellNew10 = hssfRowRepublic.createCell(10);
                             hssfCellNew10.setCellValue("<edit space for your name>");
                             hssfCellNew10.setCellStyle(backgroundcolorcenter);

                             HSSFCell hssfCellNew1one = hssfRowRepublic.createCell(1);
                             hssfCellNew1one.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfCellNew1two = hssfRowRepublic.createCell(2);
                             hssfCellNew1two.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfCellNew1four = hssfRowRepublic.createCell(4);
                             hssfCellNew1four.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfCellNew1five = hssfRowRepublic.createCell(5);
                             hssfCellNew1five.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfCellNew1six = hssfRowRepublic.createCell(6);
                             hssfCellNew1six.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfCellNew1seven = hssfRowRepublic.createCell(7);
                             hssfCellNew1seven.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfCellNew1nine = hssfRowRepublic.createCell(9);
                             hssfCellNew1nine.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfCellNew1eleven = hssfRowRepublic.createCell(11);
                             hssfCellNew1eleven.setCellStyle(backgroundcolorleft);

                             break;

                         case 10:
                             HSSFCell hssfnumberofexaminees = hssfRowRepublic.createCell(0);
                             hssfnumberofexaminees.setCellValue("Number of Examinees");
                             hssfnumberofexaminees.setCellStyle(backgroundcolorleft);

                             HSSFCell hssfnumberofexaminiesDATA = hssfRowRepublic.createCell(2);
                             hssfnumberofexaminiesDATA.setCellValue("" + mydb.counter_item);
                             hssfnumberofexaminiesDATA.setCellStyle(examineesdata);

                             HSSFCell hssf27examinees = hssfRowRepublic.createCell(3);
                             hssf27examinees.setCellValue("27 % of the Examinees");
                             hssf27examinees.setCellStyle(backgroundcolorleft);

                             HSSFCell hssf27examineesDATA = hssfRowRepublic.createCell(5);
                             hssf27examineesDATA.setCellValue("" + result);
                             hssf27examineesDATA.setCellStyle(sevendata);

                             HSSFCell numberofitems = hssfRowRepublic.createCell(7);
                             numberofitems.setCellValue("Number of Items");
                             numberofitems.setCellStyle(backgroundcolorleft);

                             HSSFCell numberofitemsDATA = hssfRowRepublic.createCell(9);
                             numberofitemsDATA.setCellValue("" + listview_total_count);
                             numberofitemsDATA.setCellStyle(itemdata);

                             HSSFCell hssfnumberofexamineesone = hssfRowRepublic.createCell(1);
                             hssfnumberofexamineesone.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfnumberofexamineestwo = hssfRowRepublic.createCell(4);
                             hssfnumberofexamineestwo.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfnumberofexamineesthree = hssfRowRepublic.createCell(6);
                             hssfnumberofexamineesthree.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfnumberofexamineesfour = hssfRowRepublic.createCell(8);
                             hssfnumberofexamineesfour.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfnumberofexamineesfive = hssfRowRepublic.createCell(10);
                             hssfnumberofexamineesfive.setCellStyle(backgroundcolorleft);
                             HSSFCell hssfnumberofexamineessix = hssfRowRepublic.createCell(11);
                             hssfnumberofexamineessix.setCellStyle(backgroundcolorleft);
                             break;

                         case 11:
                             HSSFCell frequency = hssfRowRepublic.createCell(1);
                             frequency.setCellValue("Frequency of Error");
                             frequency.setCellStyle(forbot);

                             HSSFCell correct = hssfRowRepublic.createCell(4);
                             correct.setCellValue("Correct Response");
                             correct.setCellStyle(forbot);

                             HSSFCell diff = hssfRowRepublic.createCell(6);
                             diff.setCellValue("Difficulty");
                             diff.setCellStyle(forbot);

                             HSSFCell discri = hssfRowRepublic.createCell(8);
                             discri.setCellValue("Discrimination");
                             discri.setCellStyle(backgroundcolorleftcenter);
                             HSSFCell itemnumber = hssfRowRepublic.createCell(0);

                             itemnumber.setCellValue("Item Number");
                             itemnumber.setCellStyle(last);

                             HSSFCell remarks = hssfRowRepublic.createCell(10);
                             remarks.setCellValue("Remarks");
                             remarks.setCellStyle(last);

                             HSSFCell mastery = hssfRowRepublic.createCell(11);
                             mastery.setCellValue("Mastery Level Description");
                             mastery.setCellStyle(last);


                             break;
                         case 12:


                             HSSFCell upper = hssfRowRepublic.createCell(1);
                             upper.setCellValue("Upper 27%");
                             upper.setCellStyle(backgroundcolorleftcenter);

                             HSSFCell middle = hssfRowRepublic.createCell(2);
                             middle.setCellValue("Middle 46%");
                             middle.setCellStyle(backgroundcolorleftcenter);

                             HSSFCell lower = hssfRowRepublic.createCell(3);
                             lower.setCellValue("Lower 27%");
                             lower.setCellStyle(backgroundcolorleftcenter);

                             HSSFCell frequencycor = hssfRowRepublic.createCell(4);
                             frequencycor.setCellValue("Frequency");
                             frequencycor.setCellStyle(backgroundcolorleftcenter);

                             HSSFCell percentagecor = hssfRowRepublic.createCell(5);
                             percentagecor.setCellValue("Percentage");
                             percentagecor.setCellStyle(backgroundcolorleftcenter);

                             HSSFCell indexdiff = hssfRowRepublic.createCell(6);
                             indexdiff.setCellValue("Index");
                             indexdiff.setCellStyle(backgroundcolorleftcenter);

                             HSSFCell leveldiff = hssfRowRepublic.createCell(7);
                             leveldiff.setCellValue("Level");
                             leveldiff.setCellStyle(backgroundcolorleftcenter);

                             HSSFCell indexdisc = hssfRowRepublic.createCell(8);
                             indexdisc.setCellValue("Index");
                             indexdisc.setCellStyle(backgroundcolorleftcenter);

                             HSSFCell leveldisc = hssfRowRepublic.createCell(9);
                             leveldisc.setCellValue("Power");
                             leveldisc.setCellStyle(backgroundcolorleftcenter);

                             HSSFCell upperzero = hssfRowRepublic.createCell(0);
                             upperzero.setCellStyle(backgroundcolorleftcenter);
                             HSSFCell upperten = hssfRowRepublic.createCell(10);
                             upperten.setCellStyle(backgroundcolorleftcenter);
                             HSSFCell uppereleven = hssfRowRepublic.createCell(11);
                             uppereleven.setCellStyle(backgroundcolorleftcenter);
                             break;

                     }

                     hssfCellDepartment.setCellStyle(hssfSheet3);
                 }
                 for (forcounter = numberofmerge; forcounter < totalcount + numberofmerge; forcounter++) {


                     HSSFRow hssfRow = hssfSheet.createRow(forcounter);
                     str_resultupper = tokens.nextToken();


                     HSSFCell hssfnumbers = hssfRow.createCell(0);
                     hssfnumbers.setCellValue(forcounter + 1 - numberofmerge);
                     hssfnumbers.setCellStyle(forbot);

                     HSSFCell hssfCell = hssfRow.createCell(1);
                     hssfCell.setCellValue(str_resultupper);
                     hssfCell.setCellStyle(forupper);

                     str_resultmid = tokensforname.nextToken();

                     HSSFCell hssfCellname = hssfRow.createCell(2);
                     hssfCellname.setCellValue(str_resultmid);
                     hssfCellname.setCellStyle(formid);

                     str_resultlower = tokensforscore.nextToken();

                     HSSFCell hssfCellscore = hssfRow.createCell(3);
                     hssfCellscore.setCellValue(str_resultlower);
                     hssfCellscore.setCellStyle(forlow);

                     str_resultfrequency = mydb.counter_item - (Integer.parseInt(str_resultupper) + Integer.parseInt(str_resultmid) + Integer.parseInt(str_resultlower));

                     str_total_frequency = str_resultfrequency + str_total_frequency;

                     //  System.out.println("str_resultpercentage "+str_resultpercentage);
                     HSSFCell hssfCellfrequency = hssfRow.createCell(4);
                     hssfCellfrequency.setCellValue(str_resultfrequency);
                     hssfCellfrequency.setCellStyle(forbot);
                     //  System.out.println("str_freq "+str_resultfrequency);
                     float testsaverage = ((float) str_resultfrequency / (float) Integer.parseInt(listview_total_count)) * 100;

                     // str_resultfrequency = Integer.parseInt(String.format("%.2f",testsaverage));

                     HSSFCell hssfCellpercentage = hssfRow.createCell(5);
                     hssfCellpercentage.setCellValue(String.format("%.2f", testsaverage) + " %");
                     hssfCellpercentage.setCellStyle(forbot);


                     int resultlower = Integer.parseInt(str_resultlower);
                     int resultupper = Integer.parseInt(str_resultupper);
                     testdifficulty = ((float) resultlower + (float) resultupper) / (result + result);

                     System.out.println("indexdifficulty " + String.format("%.2f", testdifficulty));

                     HSSFCell hssfCelldifficulty = hssfRow.createCell(6);
                     hssfCelldifficulty.setCellValue(String.format("%.2f", testdifficulty));
                     hssfCelldifficulty.setCellStyle(forbot);

                     checkdifficultylevel(testdifficulty);

                     HSSFCell hssfCelldifficultylevel = hssfRow.createCell(7);

                     int correctperupper = result - resultupper;
                     int correctperlower = result - resultlower;

                     System.out.println("correctperupper : " + correctperupper);
                     System.out.println("correctperlower : " + correctperlower);

                     hssfCelldifficultylevel.setCellValue(difficultylevel);
                     hssfCelldifficultylevel.setCellStyle(difficlevel);

                     System.out.println("level: " + difficultylevel);


                     discrimination = ((float) correctperupper - (float) correctperlower) / result;

                     HSSFCell hssfCelldiscrimination = hssfRow.createCell(8);
                     hssfCelldiscrimination.setCellValue(String.format("%.2f", discrimination));
                     hssfCelldiscrimination.setCellStyle(forbot);

                     checkdiscriminationlevel(discrimination);
                     HSSFCell hssfCelldiscriminationlevel = hssfRow.createCell(9);
                     hssfCelldiscriminationlevel.setCellValue(discriminationlevel);
                     hssfCelldiscriminationlevel.setCellStyle(difficlevel);

                     checkremarks(discriminationlevel);
                     HSSFCell hssfCellremarks = hssfRow.createCell(10);
                     hssfCellremarks.setCellValue(remarks);
                     hssfCellremarks.setCellStyle(forbot);

                     checkcorrectresponse(testsaverage);
                     HSSFCell hssfCellcorrectresponse = hssfRow.createCell(11);
                     hssfCellcorrectresponse.setCellValue(correctreponse);
                     hssfCellcorrectresponse.setCellStyle(forbot);

                 }


                 float frecount = ((float) str_total_frequency / (float) mydb.counter_item);
                 float listcount = Float.parseFloat(listview_total_count);
                 float MPS = ((float) frecount / (float) listcount);

                 for (int i = forcounter; i < forcounter + 14; i++) {

                     HSSFRow hssfRowRepublic = hssfSheet.createRow(i);
                     if (i == forcounter) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 0, 3));
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 5, 7));

                         HSSFCell mean = hssfRowRepublic.createCell(0);
                         mean.setCellValue("Mean = ");
                         mean.setCellStyle(backgroundcolorleftcenter);

                         HSSFCell meanDATA = hssfRowRepublic.createCell(4);
                         meanDATA.setCellValue("" + String.format("%.2f", frecount));
                         meanDATA.setCellStyle(forred);


                         HSSFCell mps = hssfRowRepublic.createCell(5);
                         mps.setCellValue("Mean Percentage Score (MPS) = ");
                         mps.setCellStyle(backgroundcolorleftcenter);


                         HSSFCell mpsDATA = hssfRowRepublic.createCell(8);
                         mpsDATA.setCellValue("" + String.format("%.2f", MPS));
                         mpsDATA.setCellStyle(forred);

                         HSSFCell meanone = hssfRowRepublic.createCell(1);
                         meanone.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meantwo = hssfRowRepublic.createCell(2);
                         meantwo.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meanthree = hssfRowRepublic.createCell(3);
                         meanthree.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meanfour = hssfRowRepublic.createCell(6);
                         meanfour.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meanfive = hssfRowRepublic.createCell(7);
                         meanfive.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meanseven = hssfRowRepublic.createCell(10);
                         meanseven.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meaneight = hssfRowRepublic.createCell(11);
                         meaneight.setCellStyle(backgroundcolorleftcenter);

                     } else if (i == forcounter + 1) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 5, 7));
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 8, 10));
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i + 1, 0, 1));
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i + 1, 2, 4));

                         HSSFCell diffbot = hssfRowRepublic.createCell(5);
                         diffbot.setCellValue("Difficulty");
                         diffbot.setCellStyle(backgroundLOWTHIN);

                         HSSFCell discbot = hssfRowRepublic.createCell(8);
                         discbot.setCellValue("Discrimination");
                         discbot.setCellStyle(backgroundLOWTHIN);

                         HSSFCell disccorres = hssfRowRepublic.createCell(0);
                         disccorres.setCellValue("Percentage of Correct Response");
                         disccorres.setCellStyle(backgroundcolorleftcenter);

                         HSSFCell mld = hssfRowRepublic.createCell(2);
                         mld.setCellValue("Mastery Level Description (MLD)");
                         mld.setCellStyle(backgroundLOWTHIN);


                         HSSFCell meanone = hssfRowRepublic.createCell(1);
                         meanone.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meantwo = hssfRowRepublic.createCell(3);
                         meantwo.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meanthree = hssfRowRepublic.createCell(4);
                         meanthree.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meanfour = hssfRowRepublic.createCell(6);
                         meanfour.setCellStyle(backgroundLOWTHIN);
                         HSSFCell meanfive = hssfRowRepublic.createCell(7);
                         meanfive.setCellStyle(backgroundLOWTHIN);
                         HSSFCell meansix = hssfRowRepublic.createCell(9);
                         meansix.setCellStyle(backgroundLOWTHIN);
                         HSSFCell meanseven = hssfRowRepublic.createCell(10);
                         meanseven.setCellStyle(backgroundLOWTHIN);
                         HSSFCell meaneight = hssfRowRepublic.createCell(11);
                         meaneight.setCellStyle(backgroundLOWTHIN);


                     } else if (i == forcounter + 2) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 5, 6));
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 8, 9));

                         HSSFCell index = hssfRowRepublic.createCell(5);
                         index.setCellValue("Index");
                         index.setCellStyle(backgroundcolorleftcenter);

                         HSSFCell level = hssfRowRepublic.createCell(7);
                         level.setCellValue("Level");
                         level.setCellStyle(backgroundcolorleftcenter);

                         HSSFCell index1 = hssfRowRepublic.createCell(8);
                         index1.setCellValue("Index");
                         index1.setCellStyle(backgroundcolorleftcenter);

                         HSSFCell power = hssfRowRepublic.createCell(10);
                         power.setCellValue("Power");
                         power.setCellStyle(backgroundcolorleftcenter);

                         HSSFCell meanone = hssfRowRepublic.createCell(0);
                         meanone.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meantwo = hssfRowRepublic.createCell(1);
                         meantwo.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meanthree = hssfRowRepublic.createCell(2);
                         meanthree.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meanfour = hssfRowRepublic.createCell(3);
                         meanfour.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meanfive = hssfRowRepublic.createCell(4);
                         meanfive.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meansix = hssfRowRepublic.createCell(6);
                         meansix.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meanseven = hssfRowRepublic.createCell(9);
                         meanseven.setCellStyle(backgroundcolorleftcenter);
                         HSSFCell meaneight = hssfRowRepublic.createCell(11);
                         meaneight.setCellStyle(backgroundcolorleftcenter);

                     } else if (i == forcounter + 3) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 2, 4));


                         HSSFCell zero = hssfRowRepublic.createCell(0);
                         zero.setCellValue("0.00%");
                         zero.setCellStyle(forgreen);

                         HSSFCell one = hssfRowRepublic.createCell(1);
                         one.setCellValue("4.00%");
                         one.setCellStyle(forgreen);

                         HSSFCell two = hssfRowRepublic.createCell(2);
                         two.setCellValue("Absolutely No Mastery");
                         two.setCellStyle(forbot);

                         HSSFCell five = hssfRowRepublic.createCell(5);
                         five.setCellValue("0.00");
                         five.setCellStyle(forgreen);

                         HSSFCell six = hssfRowRepublic.createCell(6);
                         six.setCellValue("0.10");
                         six.setCellStyle(forgreen);


                         HSSFCell seven = hssfRowRepublic.createCell(7);
                         seven.setCellValue("Too Easy");
                         seven.setCellStyle(forbot);


                         HSSFCell eight = hssfRowRepublic.createCell(8);
                         eight.setCellValue("-1.00");
                         eight.setCellStyle(forgreen);

                         HSSFCell nine = hssfRowRepublic.createCell(9);
                         nine.setCellValue("0.10");
                         nine.setCellStyle(forgreen);

                         HSSFCell ten = hssfRowRepublic.createCell(10);
                         ten.setCellValue("Poor");
                         ten.setCellStyle(forbot);

                         HSSFCell meaneight = hssfRowRepublic.createCell(3);
                         meaneight.setCellStyle(forbot);
                         HSSFCell meaneleven = hssfRowRepublic.createCell(11);
                         meaneleven.setCellStyle(forbot);


                     } else if (i == forcounter + 4) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 2, 4));


                         HSSFCell zero = hssfRowRepublic.createCell(0);
                         zero.setCellValue("5.00%");
                         zero.setCellStyle(forgreen);

                         HSSFCell one = hssfRowRepublic.createCell(1);
                         one.setCellValue("14.00%");
                         one.setCellStyle(forgreen);

                         HSSFCell two = hssfRowRepublic.createCell(2);
                         two.setCellValue("Very Low Mastery");
                         two.setCellStyle(forbot);

                         HSSFCell five = hssfRowRepublic.createCell(5);
                         five.setCellValue("0.11");
                         five.setCellStyle(forgreen);

                         HSSFCell six = hssfRowRepublic.createCell(6);
                         six.setCellValue("0.25");
                         six.setCellStyle(forgreen);


                         HSSFCell seven = hssfRowRepublic.createCell(7);
                         seven.setCellValue("Easy");
                         seven.setCellStyle(forbot);


                         HSSFCell eight = hssfRowRepublic.createCell(8);
                         eight.setCellValue("0.11");
                         eight.setCellStyle(forgreen);

                         HSSFCell nine = hssfRowRepublic.createCell(9);
                         nine.setCellValue("0.20");
                         nine.setCellStyle(forgreen);

                         HSSFCell ten = hssfRowRepublic.createCell(10);
                         ten.setCellValue("Marginal");
                         ten.setCellStyle(forbot);
                         HSSFCell meaneight = hssfRowRepublic.createCell(3);
                         meaneight.setCellStyle(forbot);
                         HSSFCell meanefour = hssfRowRepublic.createCell(4);
                         meanefour.setCellStyle(forbot);
                         HSSFCell meaneleven = hssfRowRepublic.createCell(11);
                         meaneleven.setCellStyle(forbot);
                     } else if (i == forcounter + 5) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 2, 4));


                         HSSFCell zero = hssfRowRepublic.createCell(0);
                         zero.setCellValue("15.00%");
                         zero.setCellStyle(forgreen);

                         HSSFCell one = hssfRowRepublic.createCell(1);
                         one.setCellValue("34.00%");
                         one.setCellStyle(forgreen);

                         HSSFCell two = hssfRowRepublic.createCell(2);
                         two.setCellValue("Low Mastery");
                         two.setCellStyle(forbot);

                         HSSFCell five = hssfRowRepublic.createCell(5);
                         five.setCellValue("0.26");
                         five.setCellStyle(forgreen);

                         HSSFCell six = hssfRowRepublic.createCell(6);
                         six.setCellValue("0.75");
                         six.setCellStyle(forgreen);


                         HSSFCell seven = hssfRowRepublic.createCell(7);
                         seven.setCellValue("Average");
                         seven.setCellStyle(forbot);


                         HSSFCell eight = hssfRowRepublic.createCell(8);
                         eight.setCellValue("0.21");
                         eight.setCellStyle(forgreen);

                         HSSFCell nine = hssfRowRepublic.createCell(9);
                         nine.setCellValue("0.30");
                         nine.setCellStyle(forgreen);

                         HSSFCell ten = hssfRowRepublic.createCell(10);
                         ten.setCellValue("Reasonably");
                         ten.setCellStyle(forbot);
                         HSSFCell meaneight = hssfRowRepublic.createCell(3);
                         meaneight.setCellStyle(forbot);
                         HSSFCell meanefour = hssfRowRepublic.createCell(4);
                         meanefour.setCellStyle(forbot);
                         HSSFCell meaneleven = hssfRowRepublic.createCell(11);
                         meaneleven.setCellStyle(forbot);
                     } else if (i == forcounter + 6) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 2, 4));


                         HSSFCell zero = hssfRowRepublic.createCell(0);
                         zero.setCellValue("35.00%");
                         zero.setCellStyle(forgreen);

                         HSSFCell one = hssfRowRepublic.createCell(1);
                         one.setCellValue("64.00%");
                         one.setCellStyle(forgreen);

                         HSSFCell two = hssfRowRepublic.createCell(2);
                         two.setCellValue("Average Mastery");
                         two.setCellStyle(forbot);

                         HSSFCell five = hssfRowRepublic.createCell(5);
                         five.setCellValue("0.76");
                         five.setCellStyle(forgreen);

                         HSSFCell six = hssfRowRepublic.createCell(6);
                         six.setCellValue("0.90");
                         six.setCellStyle(forgreen);


                         HSSFCell seven = hssfRowRepublic.createCell(7);
                         seven.setCellValue("Difficult");
                         seven.setCellStyle(forbot);


                         HSSFCell eight = hssfRowRepublic.createCell(8);
                         eight.setCellValue("0.31");
                         eight.setCellStyle(forgreen);

                         HSSFCell nine = hssfRowRepublic.createCell(9);
                         nine.setCellValue("0.40");
                         nine.setCellStyle(forgreen);

                         HSSFCell ten = hssfRowRepublic.createCell(10);
                         ten.setCellValue("Good");
                         ten.setCellStyle(forbot);
                         HSSFCell meaneight = hssfRowRepublic.createCell(3);
                         meaneight.setCellStyle(forbot);
                         HSSFCell meanefour = hssfRowRepublic.createCell(4);
                         meanefour.setCellStyle(forbot);
                         HSSFCell meaneleven = hssfRowRepublic.createCell(11);
                         meaneleven.setCellStyle(forbot);
                     } else if (i == forcounter + 7) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 2, 4));


                         HSSFCell zero = hssfRowRepublic.createCell(0);
                         zero.setCellValue("66.00%");
                         zero.setCellStyle(forgreen);

                         HSSFCell one = hssfRowRepublic.createCell(1);
                         one.setCellValue("85.00%");
                         one.setCellStyle(forgreen);

                         HSSFCell two = hssfRowRepublic.createCell(2);
                         two.setCellValue("Moving Towards Mastery");
                         two.setCellStyle(forbot);

                         HSSFCell five = hssfRowRepublic.createCell(5);
                         five.setCellValue("0.91");
                         five.setCellStyle(forgreen);

                         HSSFCell six = hssfRowRepublic.createCell(6);
                         six.setCellValue("1.00");
                         six.setCellStyle(forgreen);


                         HSSFCell seven = hssfRowRepublic.createCell(7);
                         seven.setCellValue("Too Difficult");
                         seven.setCellStyle(forbot);


                         HSSFCell eight = hssfRowRepublic.createCell(8);
                         eight.setCellValue("0.41");
                         eight.setCellStyle(forgreen);

                         HSSFCell nine = hssfRowRepublic.createCell(9);
                         nine.setCellValue("1.00");
                         nine.setCellStyle(forgreen);

                         HSSFCell ten = hssfRowRepublic.createCell(10);
                         ten.setCellValue("Very Good");
                         ten.setCellStyle(forbot);

                         HSSFCell meaneight = hssfRowRepublic.createCell(3);
                         meaneight.setCellStyle(forbot);
                         HSSFCell meanefour = hssfRowRepublic.createCell(4);
                         meanefour.setCellStyle(forbot);
                         HSSFCell meaneleven = hssfRowRepublic.createCell(11);
                         meaneleven.setCellStyle(forbot);
                     } else if (i == forcounter + 8) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 2, 4));


                         HSSFCell zero = hssfRowRepublic.createCell(0);
                         zero.setCellValue("86.00%");
                         zero.setCellStyle(forgreen);

                         HSSFCell one = hssfRowRepublic.createCell(1);
                         one.setCellValue("95.00%");
                         one.setCellStyle(forgreen);

                         HSSFCell two = hssfRowRepublic.createCell(2);
                         two.setCellValue("Closely Approximating Mastery");
                         two.setCellStyle(forbot);

                         for (int r = 3; r < 12; r++) {
                             HSSFCell meaneleven = hssfRowRepublic.createCell(r);
                             meaneleven.setCellStyle(forbot);
                         }

                         HSSFCell meaneight = hssfRowRepublic.createCell(3);
                         meaneight.setCellStyle(forbot);
                         HSSFCell meaneleven = hssfRowRepublic.createCell(11);
                         meaneleven.setCellStyle(forbot);
                     } else if (i == forcounter + 9) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 2, 4));


                         HSSFCell zero = hssfRowRepublic.createCell(0);
                         zero.setCellValue("96.00%");
                         zero.setCellStyle(forgreen);

                         HSSFCell one = hssfRowRepublic.createCell(1);
                         one.setCellValue("100.00%");
                         one.setCellStyle(forgreen);

                         HSSFCell two = hssfRowRepublic.createCell(2);
                         two.setCellValue("Mastered");
                         two.setCellStyle(forbot);

                         for (int r = 3; r < 12; r++) {
                             HSSFCell meaneleven = hssfRowRepublic.createCell(r);
                             meaneleven.setCellStyle(forbot);
                         }
                     } else if (i == forcounter + 10) {

                         HSSFCell zero = hssfRowRepublic.createCell(0);
                         zero.setCellValue("Prepared by: ");
                         zero.setCellStyle(hssfSheet4);

                         HSSFCell one = hssfRowRepublic.createCell(4);
                         one.setCellValue("Checked by: ");
                         one.setCellStyle(hssfSheet4);

                         HSSFCell two = hssfRowRepublic.createCell(8);
                         two.setCellValue("Noted by: ");
                         two.setCellStyle(hssfSheet4);

                     } else if (i == forcounter + 11) {


                     } else if (i == forcounter + 12) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 0, 2));
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 4, 6));
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 8, 10));

                         HSSFCell zero = hssfRowRepublic.createCell(0);
                         zero.setCellValue("<edit space for subject teacher's name>");
                         zero.setCellStyle(hssfSheet3);

                         HSSFCell one = hssfRowRepublic.createCell(4);
                         one.setCellValue("<edit space for Coordinator's name>");
                         one.setCellStyle(hssfSheet3);

                         HSSFCell eight = hssfRowRepublic.createCell(8);
                         eight.setCellValue("IRENE L. PANTORILLO");
                         eight.setCellStyle(hssfSheet3);

                     } else if (i == forcounter + 13) {
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 0, 2));
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 4, 6));
                         hssfSheet.addMergedRegion(new CellRangeAddress(i, i, 8, 10));

                         HSSFCell zero = hssfRowRepublic.createCell(0);
                         zero.setCellValue("Subject Teacher");
                         zero.setCellStyle(hssfSheet3);

                         HSSFCell one = hssfRowRepublic.createCell(4);
                         one.setCellValue("Department Head/Coordinator");
                         one.setCellStyle(hssfSheet3);

                         HSSFCell eight = hssfRowRepublic.createCell(8);
                         eight.setCellValue("School Head");
                         eight.setCellStyle(hssfSheet3);

                     }
                 }
                 CreateExcel();
                 numberofmerge = 0;

             }else {

                 Toast.makeText(test.this, "Have atleast 4 records." + "", Toast.LENGTH_LONG).show();
         }
          }
      });



    }
    public void checkdifficultylevel(float testdiff){
        if(testdiff>=0.00 && testdiff<=0.10){
            difficultylevel = "Too Easy";
        }else if(testdiff>=0.11 && testdiff<=0.25){
            difficultylevel = "Easy";
        }else if(testdiff>=0.26 && testdiff<=0.75){
            difficultylevel = "Average";
        }else if(testdiff>=0.76 && testdiff<=0.90){
            difficultylevel = "Difficult";
        }else if(testdiff>=0.91 && testdiff<=1.00){
            difficultylevel = "Too Difficult";
        }else{
            difficultylevel = "Too Easy";
        }


    }
    public void checkdiscriminationlevel(float testdiff){
        if(testdiff>=-1.00 && testdiff<=0.10){
            discriminationlevel = "Poor";
        }else if(testdiff>=0.11 && testdiff<=0.20){
            discriminationlevel = "Marginal";
        }else if(testdiff>=0.21 && testdiff<=0.30){
            discriminationlevel = "Reasonably";
        }else if(testdiff>=0.31 && testdiff<=0.40){
            discriminationlevel = "Good";
        }else if(testdiff>=0.41 && testdiff<=1.00){
            discriminationlevel = "Very Good";
        }else{

        }


    }
    public void checkremarks(String power){

        if(power.equals("Marginal")){
            remarks = "Revise";

        }else if(power.equals("Poor")){
            remarks = "Reject";

        }else if(power.equals("Very Good") || power.equals("Reasonably")
        || power.equals("Good")){
            remarks = "Retain";

        }



    }

    public void checkcorrectresponse(float percentage){
        if(percentage>=0.00 && percentage <= 4.00){
            correctreponse = "Absolutely No Mastery";
        }else if(percentage>=5.00 && percentage <= 14.00){
            correctreponse = "Very Low Mastery";
        }else if(percentage>=15.00 && percentage <= 34.00){
            correctreponse = "Low Mastery";
        }else if(percentage>=35.00 && percentage <= 65.00){
            correctreponse = "Average Mastery";
        }else if(percentage>=66.00 && percentage <= 85.00){
            correctreponse = "Moving Towards Mastery";
        }else if(percentage>=86.00 && percentage <= 95.00){
            correctreponse = "Closely Approximating Mastery";
        }else if(percentage>=96.00 && percentage <= 100.00){
            correctreponse = "Mastered";
        }


    }
    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
    public void CreateExcel(){
        try {
            filepath = Environment.getExternalStorageDirectory();
            File dir = new File(filepath.getAbsolutePath() + "/Chekka-Item-Analysis-ExcelFile/");
            dir.mkdir();
            File file = new File(dir, "" + listview_exam_Name + "_item_analysis.xls");

            try {
                if (file.exists()) {
                    file.delete();
                    file = new File(dir, "" + listview_exam_Name + "_item_analysis.xls");
                }

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                hssfWorkbook.write(fileOutputStream);
                Toast.makeText(test.this, "Downloading... " + file + "", Toast.LENGTH_LONG).show();
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            str_resultupper = "";
            str_resultmid = "";
            str_resultlower = "";
        } catch (Exception e){
            Toast.makeText(test.this, "Try again" + "", Toast.LENGTH_LONG).show();
        }
    }
}

