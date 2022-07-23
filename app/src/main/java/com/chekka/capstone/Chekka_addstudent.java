package com.chekka.capstone;



import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Chekka_addstudent extends AppCompatActivity {
    EditText EditText_addstudent_ST, EditText_addstudentnumber_ST,EditText_addstudentemail_ST ;
    TextView button_ok_ST, button_cancel_ST;
    RelativeLayout layout_chooseaddstudent, addstudentmanually, addstudentexcel,layout_addstudent_ST;
    RelativeLayout cross;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper mydb;
    String Holder_StudentName_ST, Holder_TemporaryStudentName_ST = "Not_Found";
    String Holder_StudentNumber_ST, Holder_TemporaryStudentNumber_ST = "";
    Spinner addstudent_spinner, excelstudent_spinner;
    String halftok, a,b;
    TextView TextViewChanging_Student;
    int c;
    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;
    Button btnUpDirectory,btnSDCard;
    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;
    ArrayList<XYValue> uploadData;
    ListView lvInternalStorage;
    private static final String TAG = "Chekka_addstudent";
    RelativeLayout relLayout;
    int checker = 0;
    int counterforaddedstudents = 0;
    private BottomNavigationView bottomasback;
    ImageView back_choose;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //
        setContentView(R.layout.chekka_addstudent);
        relLayout = (RelativeLayout) findViewById(R.id.relLayout);
        mydb = new SQLiteHelper(this);
        cross = findViewById(R.id.cross);
        layout_chooseaddstudent = (RelativeLayout) findViewById(R.id.layout_chooseaddstudent);
        layout_addstudent_ST = (RelativeLayout) findViewById(R.id.layout_addstudent_ST);
        addstudentmanually = (RelativeLayout) findViewById(R.id.addstudentmanually);
        addstudentexcel = (RelativeLayout) findViewById(R.id.addstudentexcel);
        addstudent_spinner = (Spinner) findViewById(R.id.spinneraddstudent);
        excelstudent_spinner = (Spinner) findViewById(R.id.spinner_excelstudent);
        EditText_addstudentnumber_ST = (EditText) findViewById(R.id.EditTextAddStudentNumber_ST);
        EditText_addstudent_ST = (EditText) findViewById(R.id.EditTextAddStudent_ST);
        EditText_addstudentemail_ST = (EditText) findViewById(R.id.EditTextAddStudentEmail_ST);
        button_ok_ST = (TextView) findViewById(R.id.ButtonOK_ST);
        button_cancel_ST = (TextView) findViewById(R.id.ButtonCancel_ST);
        bottomasback = (BottomNavigationView) findViewById(R.id.bottom_as_back);
        TextViewChanging_Student = (TextView) findViewById(R.id.TextViewChanging_Student);
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_addstudent_list, Chekka.labelsstudent);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner_addstudent_excel, Chekka.labelsstudent);
        dataAdapter.setDropDownViewResource(R.layout.spinner_addstudent_list);
        addstudent_spinner.setAdapter(dataAdapter);
        excelstudent_spinner.setAdapter(dataAdapter2);
        lvInternalStorage = (ListView) findViewById(R.id.lvInternalStorage);
        btnUpDirectory = (Button) findViewById(R.id.btnUpDirectory);
        uploadData = new ArrayList<>();
        mydb = new SQLiteHelper(this);
        TextViewChanging_Student.setText("Add Student");
        sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);
        sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.tbl_students + " (" + mydb.studentsID + " STRING PRIMARY KEY, " + mydb.studentsname + " VARCHAR NOT NULL, " + mydb.studentsemail + " VARCHAR NOT NULL, " + mydb.studentsclass + " VARCHAR NOT NULL, " + mydb.studentsuserid + " INTEGER NOT NULL, " + "FOREIGN KEY(studentsuserid) REFERENCES tbl_users(UserID))");
        back_choose = findViewById(R.id.back_choose);
        //  sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.tbl_students + " (" + mydb.studentsID + " INTEGER PRIMARY KEY, " + mydb.studentsname + " VARCHAR NOT NULL, " + mydb.studentsemail + " VARCHAR NOT NULL, " + mydb.studentsclass + " VARCHAR NOT NULL, " + mydb.studentsuserid + " INTEGER NOT NULL, " + "FOREIGN KEY(studentsuserid) REFERENCES tbl_users(UserID))");
        checkFilePermissions();
        Chekka.exam_lv.setEnabled(true);
        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(adapterView.getItemAtPosition(i))){
                    Log.d(TAG, "lvInternalStorage: Selected a file for upload: " + lastDirectory);
                    System.out.println("lstdirect1");
                    //Execute method for reading the excel data.
                    readExcelData(lastDirectory);
                }else
                {
                    count++;
                    pathHistory.add(count,(String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG, "lvInternalStorage: " + pathHistory.get(count));
                    System.out.println("lstdirect2");
                }
            }
        });
        back_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
                addstudentexcel.setEnabled(true);
            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_addstudent_ST.setVisibility(View.INVISIBLE);
                addstudentexcel.setEnabled(true);

            }
        });
        //Goes up one directory level
        btnUpDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0){
                    Log.d(TAG, "btnUpDirectory: You have reached the highest level directory.");
                }else{
                    pathHistory.remove(count);
                    count--;
                    checkInternalStorage();
                    Log.d(TAG, "btnUpDirectory: " + pathHistory.get(count));
                }
            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_addstudent_ST.setVisibility(View.INVISIBLE);

            }
        });

        //Opens the SDCard or phone memory

        button_ok_ST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addstudentexcel.setEnabled(true);
                if (TextUtils.isEmpty(EditText_addstudent_ST.getText().toString()) || TextUtils.isEmpty(EditText_addstudentnumber_ST.getText().toString()) || TextUtils.isEmpty(addstudent_spinner.getSelectedItem().toString())) {

                    Toast.makeText(Chekka_addstudent.this, "Empty", Toast.LENGTH_LONG).show();
                    EditText_addstudent_ST.setText("");
                    EditText_addstudentnumber_ST.setText("");
                    EditText_addstudentemail_ST.setText("");

                } else {
                    String text = addstudent_spinner.getSelectedItem().toString();
                    System.out.println("hehe" + text);

                    SQLiteDataBaseBuild();
                    SQLiteTableBuild_ST();

                    CheckingStudentNameAlreadyExistsOrNot_ST();


                }
            }
        });
        button_cancel_ST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addstudentexcel.setEnabled(true);
                    EditText_addstudent_ST.setText("");
                    EditText_addstudentnumber_ST.setText("");
                    EditText_addstudentemail_ST.setText("");
            }
        });
        addstudentmanually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    layout_addstudent_ST.setVisibility(View.VISIBLE);
                    addstudentexcel.setEnabled(false);
                    checker = 1;
                    TextViewChanging_Student.setText("Add Student Manually");
                    System.out.println("check"+checker);

                }

        });
        addstudentexcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ayoko");
                count = 0;
                checker = 2;
                pathHistory = new ArrayList<String>();
                pathHistory.add(count,System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "btnSDCard: " + pathHistory.get(count));
                checkInternalStorage();
                relLayout.setVisibility(View.VISIBLE);
                layout_chooseaddstudent.setVisibility(View.INVISIBLE);
                lvInternalStorage.setVisibility(View.VISIBLE);
                System.out.println("check"+checker);
                TextViewChanging_Student.setText("Import Excel File");
            }
        });
    }
    public void SQLiteDataBaseBuild() {

        sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }
    public void SQLiteTableBuild_ST() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+mydb.tbl_class+" ("+mydb.classID+" INTEGER PRIMARY KEY, "+mydb.classname+" VARCHAR NOT NULL,"+mydb.UserID+" INTEGER NOT NULL," +
        //"FOREIGN KEY(UserID) REFERENCES tbl_users(userID))");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + mydb.tbl_students + " (" + mydb.studentsID + " INTEGER PRIMARY KEY, " + mydb.studentsname + " VARCHAR NOT NULL, " + mydb.studentsclass + " VARCHAR NOT NULL, " + mydb.studentsuserid + " INTEGER NOT NULL, " + "FOREIGN KEY(studentsuserid) REFERENCES tbl_users(UserID))");

        // String CREATE_TABLE_CLASS_WITH_NO_OF_STUDENTS="CREATE TABLE IF NOT EXISTS "+mydb.tbl_class_with_no_of_students+" ("+classID_with_no_of_students+" INTEGER PRIMARY KEY, "+classname_with_no_of_students+", VARCHAR NOT NULL, "+classstudents_with_no_of_students+", INTEGER PRIMARY KEY, "+UserID_with_no_of_students+", INTEGER NOT NULL, "+ "FOREIGN KEY(UserID) REFERENCES tbl_users(userID))";
    }
    public void CheckingStudentNameAlreadyExistsOrNot_ST() {
        Holder_StudentName_ST = EditText_addstudentnumber_ST.getText().toString();

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = mydb.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(mydb.tbl_students, null, " " + mydb.studentsID + "=?", new String[]{Holder_StudentName_ST}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                Holder_TemporaryStudentName_ST = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResultStudentName_ST();

    }
    public void CheckFinalResultStudentName_ST() {

        // Checking whether email is already exists or not.
        if (Holder_TemporaryStudentName_ST.equalsIgnoreCase("Email Found")) {
            // If email is exists then toaslist_item.xml
            //list_item_for_student.xmlt msg will display.
            EditText_addstudentnumber_ST.setText("");

            Toast.makeText(Chekka_addstudent.this, "Student Number Already Exists", Toast.LENGTH_LONG).show();
        } else {
            // If email already dose n't exists then user registration details will entered to SQLite database.

            InsertStudentNameIntoSQLiteDatabase_ST();

        }
        Holder_TemporaryStudentName_ST = "Not_Found";
    }

    public void InsertStudentNameIntoSQLiteDatabase_ST() {


        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this,  R.style.AlertDialogStyle);
            String addstudentnumber =EditText_addstudentnumber_ST.getText().toString();
            String studentforclass = addstudent_spinner.getSelectedItem().toString();
            String studentemail = EditText_addstudentemail_ST.getText().toString();
            int length = EditText_addstudentnumber_ST.length();
            if (length == 4) {
                if (checker == 1) {
                    String convert = String.valueOf(length);
                    System.out.println("COUNT" + convert);
                   mydb.insert_student_details(EditText_addstudent_ST.getText().toString(), Chekka.textView_id_LO.getText().toString(), studentforclass, addstudentnumber, studentemail);
                    Toast.makeText(Chekka_addstudent.this, "Added Student Successfully", Toast.LENGTH_LONG).show();

                    EditText_addstudent_ST.setText("");
                    EditText_addstudentnumber_ST.setText("");
                    EditText_addstudentemail_ST.setText("");


                }
            } else{

                builder.setTitle("Student Number Limit");
                //SET THE MESSAGE
                builder.setMessage("Student number contains 4 digits only. Try again?");

                //WE DEFINE THE FUNCTION, WHEN THE USER CHOOSES "YES"
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText_addstudent_ST.setText("");
                        EditText_addstudentnumber_ST.setText("");
                        EditText_addstudentemail_ST.setText("");
                    }
                });


                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                AlertDialog ad = builder.create();

                ad.show();
                }
        } catch (Exception e) {
            EditText_addstudent_ST.setText("");
            EditText_addstudentnumber_ST.setText("");
            EditText_addstudentemail_ST.setText("");
            Toast.makeText(Chekka_addstudent.this, "Student Number is already exist", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Method for parsing imported data and storing in ArrayList<XYValue>
     */
    public void parseStringBuilder(StringBuilder mStringBuilder){
        Log.d(TAG, "parseStringBuilder: Started parsing.");

        // splits the sb into rows.
        String[] rows = mStringBuilder.toString().split(":");

        //Add to the ArrayList<XYValue> row by row
        for(int i=0; i<rows.length; i++) {
            //Split the columns of the rows
            String[] columns = rows[i].split(",");

            //use try catch to make sure there are no "" that try to parse into doubles.
            try{
                int sn = Integer.parseInt(columns[0]);
                int email = Integer.parseInt(columns[1]);
                int sid = Integer.parseInt(columns[2]);
                String sn_sr = String.valueOf(sn);
                String email_sr = String.valueOf(email);
                String sid_sr = String.valueOf(sid);
                String cellInfo = "(StudentID,StudentName,Email): (" + sid + "," + sn +  "," + email + ")";
                Log.d(TAG, "ParseStringBuilder: Data from row: " + cellInfo);

                //add the the uploadData ArrayList
                uploadData.add(new XYValue(sn_sr,email_sr,sid_sr));

            }catch (NumberFormatException e){

                Log.e(TAG, "parseStringBuilder: NumberFormatException: " + e.getMessage());

            }
        }

        printDataToLog();
    }

    private void printDataToLog() {
        Log.d(TAG, "printDataToLog: Printing data to log...");

        for(int i = 0; i< uploadData.size(); i++){
            String StudentName= uploadData.get(i).getSN();
            String Email = uploadData.get(i).getEmail();
            String StudentID = uploadData.get(i).getSid();


        }
    }

    /**
     * Returns the cell as a string from the excel file
     * @param row
     * @param c
     * @param formulaEvaluator
     * @return
     */
    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    int numericValue = (int) cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("MM/dd/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {

            Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage() );
        }
        return value;
    }

    private void checkInternalStorage() {
        Log.d(TAG, "checkInternalStorage: Started.");
        try{
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                toastMessage("No SD card found.");
            }
            else{
                // Locate the image folder in your SD Car;d
                file = new File(pathHistory.get(count));
                Log.d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();

            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];

            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            for (int i = 0; i < listFile.length; i++)
            {
                Log.d("Files", "FileName:" + listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilePathStrings);
            lvInternalStorage.setAdapter(adapter);

        }catch(NullPointerException e){
            Log.e(TAG, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage() );
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
    private void readExcelData(String filePath) {
        Log.d(TAG, "readExcelData: Reading Excel File.");

        //decarle input file
        File inputFile = new File(filePath);

        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();

            //outter loop, loops through rows
            for (int r = 1; r < rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //inner loop, loops through columns
                for (int c = 0; c < cellsCount; c++) {
                    //handles if there are to many columns on the excel sheet.
                    if(c>2){
                        Log.e(TAG, "readExcelData: ERROR. Excel File Format is incorrect! " );
                        toastMessage("ERROR: Excel File Format is incorrect!");
                        break;
                    }else{
                        String value = getCellAsString(row, c, formulaEvaluator);
                        String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                        Log.d(TAG, "readExcelData: Data from row: " + cellInfo);
                        sb.append(value + ",");

                    }
                }
                String ak = sb.toString();
                System.out.println("akak"+ak);
                StringTokenizer tokens = new StringTokenizer(ak, ",");
                for (int i = 0; i <= 2; i++) {
                    halftok = tokens.nextToken();
                    System.out.println("kooper" + halftok);
                    if(i == 0){
                        c = Integer.parseInt(halftok);
                        Holder_StudentNumber_ST = String.valueOf(c);
                    }
                    if(i == 1){
                       a = halftok;
                    }
                    if(i == 2){
                       b = halftok;
                    }
                }
                CheckingStudentNumberAlreadyExistsOrNot_ST();
                System.out.println("Email Found Ba? "+Holder_TemporaryStudentNumber_ST);
               if(Holder_TemporaryStudentNumber_ST.equals("Email Found")) {

               } else if(Holder_TemporaryStudentNumber_ST.equals("GO")) {
                   mydb.insert_student_details(a, Chekka.textView_id_LO.getText(). toString(), excelstudent_spinner.getSelectedItem().toString(), Holder_StudentNumber_ST, b);
                   System.out.println("ay ayaw");
                   counterforaddedstudents++;
               }
                sb.delete(0, sb.length());

            }
            Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());
            parseStringBuilder(sb);
            lvInternalStorage.setVisibility(View.INVISIBLE);
            layout_chooseaddstudent.setVisibility(View.VISIBLE);
            relLayout.setVisibility(View.INVISIBLE);
            TextViewChanging_Student.setText("Add Student");
            Intent intent = new Intent(Chekka_addstudent.this, Chekka.class);
            startActivity(intent);
            Toast.makeText(Chekka_addstudent.this, "Added "+counterforaddedstudents+" Students Successfully", Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e) {
            Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage() );
        }
    }
    public void CheckingStudentNumberAlreadyExistsOrNot_ST() {

        String a = mydb.getstudentsidforchecking(Holder_StudentNumber_ST);
        System.out.println("checktime"+a);
        if(a.equals("")){
            Holder_TemporaryStudentNumber_ST = "GO";
        } else {
            Holder_TemporaryStudentNumber_ST = "Email Found";
        }

    }
}


