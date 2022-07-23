package com.chekka.capstone;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
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


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ExportExcelResult extends AppCompatActivity {
    String str_resultid,str_resultname, str_resultscore;
    HSSFWorkbook hssfWorkbook;
    String Sthuhu = "";
    String sthuhu1= "";
    String sthuhu2= "";
    String sthuhu3= "";
    String sthuhu4= "";
    private EditText editTextExcel;
    SQLiteHelper mydb;
    public static ArrayList<HashMap<String, String>> examlist;
    public static ArrayList<HashMap<String, String>> studentinfolistfordisplay;
    public static ListAdapter adapter, examadapter, studentinfoadapter;
    public static ListView student_lv, lv, exam_lv, student_info_lv;
    public static RelativeLayout  layout_listview_examsheet;
    String listview_exam_Name;
    File filepath;
    SQLiteDatabase sqLiteDatabaseObj;
    ImageView finish_dastud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exportexcelresult);
        sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.tbl_students_answer101 + " (" + mydb.students_answerID101 + " INTEGER PRIMARY KEY, " + mydb.students_answer101 + " VARCHAR NOT NULL, " + mydb.students_score101 + " VARCHAR NOT NULL, " + mydb.students_average101 + " VARCHAR NOT NULL, " + mydb.students_examID101 + " VARCHAR NOT NULL, " + mydb.students_examname101 + " VARCHAR NOT NULL, " + mydb.students_itemcount101+ " VARCHAR NOT NULL, " + mydb.students_image101 + " BLOB NOT NULL, " + mydb.students_userID101 + " VARCHAR NOT NULL, " + mydb.students_name101 + " VARCHAR NOT NULL, " + mydb.students_number101 + " VARCHAR NOT NULL," + "FOREIGN KEY (students_number101) REFERENCES tbl_Students(studentsID))");
        layout_listview_examsheet = (RelativeLayout) findViewById(R.id.layout_listview_examsheet);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        mydb = new SQLiteHelper(this);
            exam_lv = (ListView) findViewById(R.id.listview_examsheet);
            examlist = mydb.GetExamName(Chekka.textView_id_LO.getText().toString());
            System.out.println("Examlist" + examlist);
            examadapter = new SimpleAdapter(ExportExcelResult.this, examlist, R.layout.list_item_for_examsheet, new String[]{"final_exam_name", "final_exam_set", "final_total_count"}, new int[]{R.id.item_examname_EN, R.id.item_examset_EN, R.id.itemcount});
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
                mydb.counter_excel=0;
                listview_exam_Name = examlist.get(position).get("final_exam_name");
                System.out.println("ndashdoi" + listview_exam_Name);
                try{
                    studentinfolistfordisplay = mydb.GetStudentExcel(listview_exam_Name);
                }catch (Exception e){
                    Toast.makeText(ExportExcelResult.this, "Can't download empty result", Toast.LENGTH_LONG).show();
                }

                System.out.println("studentinfolistfordisplay" + studentinfolistfordisplay);
                if (studentinfolistfordisplay.isEmpty()) {
                    Toast.makeText(ExportExcelResult.this, "Can't download empty result", Toast.LENGTH_LONG).show();
                } else {
                    hssfWorkbook = new HSSFWorkbook();
                    HSSFSheet hssfSheet = hssfWorkbook.createSheet("Custom Sheet");

                    String sbforid_str = "Student ID," + mydb.sbforid.toString();
                    StringTokenizer tokens = new StringTokenizer(sbforid_str, ",");

                    String sbforname_str = "Student Name," + mydb.sbforname.toString();
                    StringTokenizer tokensforname = new StringTokenizer(sbforname_str, ",");

                    String sbforscore_str = "Score," + mydb.sbforscore.toString();
                    StringTokenizer tokensforscore = new StringTokenizer(sbforscore_str, ",");

                    String sbfor_label = "Student ID,Student Name,Score";

                    StringTokenizer tokensforlabel = new StringTokenizer(sbfor_label, ",");

                    System.out.println("" + sbforid_str + "\n" + sbforname_str + "\n" + sbforscore_str);
                    hssfSheet.setColumnWidth(0, 5000);
                    hssfSheet.setColumnWidth(1, 5000);
                    hssfSheet.setColumnWidth(2, 5000);

// By applying style for cells we can see the total text in the cell for specified width

                    HSSFRow hssfRow1 = hssfSheet.createRow(0);
                    HSSFCell hssfCell1 = hssfRow1.createCell(0);
                    hssfCell1.setCellValue(listview_exam_Name);

                    for (int i = 0; i <= mydb.counter_excel; i++) {


                        HSSFRow hssfRow = hssfSheet.createRow(i + 1);

                        str_resultid = tokens.nextToken();


                        HSSFCell hssfCell = hssfRow.createCell(0);
                        hssfCell.setCellValue(str_resultid);

                        str_resultname = tokensforname.nextToken();


                        HSSFCell hssfCellname = hssfRow.createCell(1);
                        hssfCellname.setCellValue(str_resultname);

                        str_resultscore = tokensforscore.nextToken();

                        HSSFCell hssfCellscore = hssfRow.createCell(2);
                        hssfCellscore.setCellValue(str_resultscore);
                    }

                    CreateExcel();

                    mydb.sbforscore.delete(0, mydb.sbforscore.length());
                    mydb.sbforid.delete(0, mydb.sbforid.length());
                    mydb.sbforname.delete(0, mydb.sbforname.length());
                }
            }
        });

   }


    public void CreateExcel(){
        filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/Chekka-Result-ExcelFile/");
        dir.mkdir();
        File file = new File(dir,""+listview_exam_Name +  ".xls");

        try {
            if (file.exists()) {
                file.delete();
                file = new File(dir,""+listview_exam_Name +".xls");
            }

            FileOutputStream fileOutputStream= new FileOutputStream(file);
            hssfWorkbook.write(fileOutputStream);
            Toast.makeText(ExportExcelResult.this, "Downloading... "+file+"", Toast.LENGTH_LONG).show();
            if (fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        str_resultid = "";
        str_resultname = "";
        str_resultscore = "";
    }
}