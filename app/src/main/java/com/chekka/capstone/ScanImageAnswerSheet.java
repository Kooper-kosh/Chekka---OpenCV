package com.chekka.capstone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class ScanImageAnswerSheet extends AppCompatActivity implements View.OnClickListener {
    String examdb, examid, examnam, userid;
    String totalaverage;
    String totalscore;
    String haha = "hello";
    String iii;
    byte[] byteArray;
    private static final int REQUEST_CODE = 101;
    private ImageView scannedImageView; //dito ilalagay yung nascan
    private View cropAcceptBtn;
    private View cropRejectBtn;
    private FrameLayout cropLayout;
    private com.adityaarora.liveedgedetection.view.ScanSurfaceView mImageSurfaceView;
    private boolean isPermissionNotGranted;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;
    String SQLiteDataBaseQueryHolder;
    SQLiteHelper mydb;
    SQLiteDatabase sqLiteDatabaseObj;
    String hehe;
    int answerkeyid;
    Bitmap bMap;
    Bitmap bMap2;
    String huhu, huhucorrect, huhuincorrect;
    String sthuhu, sthuhu1, sthuhu2, sthuhu3, sthuhu4, sthuhufinal;
    int trypo, sttrypo, trypocorrect, trypoincorrect;
    int numofitemsminus;
    String answerid = "";
    String bago;
    String Holder_StudentNumber_ST, Holder_TemporaryStudentNumber_ST = "GO", Holder_studclass_AS, Holder_Temporarystudclass_AS = "";
    int[] answerkeyfromdatabase;
    public static List<Integer> answers, stanswers, answers_correct, answer_incorrect;
    public static String correct, incorrect;
    public static List<String> answerdatabase;
    Mat frame;
    public static String catches="";
    TextView txt_average_scanned, txt_score_scanned, txt_name_scanned;
    String studentsname;
    ImageView imageview;
    File pic;
    String studentname;
    byte[] imaa;
    Bitmap bmap2;
    String From_Email_S = "chekkathechecker@gmail.com", From_Password_S = "Chekka2021.", Email_Subject_S = "chekkacode", Email_Body_S = "";
    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //

        setContentView(R.layout.scan_activity_paper); //madidisplay sa layout ng activity_main
        ScannerAnswerSheet.multiadd = 0;
            iii = "";
        scannedImageView = findViewById(R.id.scanned_image); //ididisplay nascan na image
      // method para makapag scan
        cropAcceptBtn = findViewById(R.id.crop_accept_btn);
        answers = new ArrayList<>();
        answers_correct = new ArrayList<>();
        answer_incorrect = new ArrayList<>();
        stanswers = new ArrayList<>();
        cropRejectBtn = findViewById(R.id.crop_reject_btn);
        cropLayout = findViewById(R.id.crop_layout);
        mydb = new SQLiteHelper(this);
        txt_average_scanned = findViewById(R.id.txt_average_scanned);
        txt_score_scanned = findViewById(R.id.txt_score_scanned);
        txt_name_scanned = findViewById(R.id.txt_name_scanned);
        imageview = findViewById(R.id.imageview);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        startScan();

        cropAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Holder_TemporaryStudentNumber_ST.equals("Found")) {
                    builder.setTitle(""+studentsname+"'s score is already recorded.");
                    //SET THE MESSAGE
                    builder.setMessage("If you continue, "+studentsname+"'s  answer score will be updated.");

                    //WE DEFINE THE FUNCTION, WHEN THE USER CHOOSES "YES"
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //HERE GOES THE LOGIC  OF THE APPLICATION, IF THE USER SELECTS A POSITIVE CHOICE
                            //TO TEST IT, WE CAN SET THE VALUE OF THE TEXT VIEW
                            //LET'S SET THE TEXT TO YES
                            updateanswers();
                            finish();
                            Toast.makeText(ScanImageAnswerSheet.this, "Updated Answer Sheet Successfully", Toast.LENGTH_LONG).show();
                        }
                    });


                    //DEFINE THE FUNCTION, IF THE USERE CLICKS 'NO', OR SELECTS THE NEGATIVE CHOICE
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            finish();
                            //HERE YOU DEFINE THE LOGIC, IF THE USER SELECTS OR CLICKS THE 'NO' BUTTON
                            //USUALLY, NOTHING IS DEFINED HERE, BUT DEPENDS ON YOUR DECISION

                            //HERE, LET'S SET THE TEXT TO NO
                        }
                    });

                    //NOW, WE CREATE THE ALERT DIALG OBJECT
                    AlertDialog ad = builder.create();

                    //FINALLY, WE SHOW THE DIALOG
                    ad.show();
                    Chekka.exam_lv.setEnabled(true);
                    Chekka.layout_popforexam_PE.setVisibility(View.INVISIBLE);

                }else if(Holder_TemporaryStudentNumber_ST.equals("GO")){
                    insertanswers();
                    finish();
                    Toast.makeText(ScanImageAnswerSheet.this, "Scanned Answer Sheet Successfully", Toast.LENGTH_LONG).show();
                    Chekka.exam_lv.setEnabled(true);
                    Chekka.layout_popforexam_PE.setVisibility(View.INVISIBLE);

                }
            }
        });

        cropRejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mImageSurfaceView.setPreviewCallback();
                finish();
            }
        });


    }

    private void startScan() {
            examdb = String.valueOf(Chekka.itemscount);
            examid = String.valueOf(Chekka.examid);
            userid = String.valueOf(Chekka.userid);
            examnam = String.valueOf(Chekka.examnam);
            System.out.println("examid2" + examid);
            System.out.println("examdb" + examdb);
            try {
              frame = new Mat();
            }catch (Exception e){

            }
            int questionCount;
            //mat(data);
            //  Bitmap bMap = com.chekka.examchecker.util.ScanUtils.decodeBitmapFromFile(filePath, com.chekka.examchecker.constants.ScanConstants.IMAGE_NAME);

            //String name = "1,2,5,3";
            // String[] separated = name.split(",");


            String meh = mydb.getanswerkeyid(String.valueOf(Chekka.examid));
            System.out.println("meh" + meh);
            hehe = mydb.getIdFromAnswer(meh, String.valueOf(Chekka.examid));
            if(hehe.isEmpty()){
                finish();
                Toast.makeText(ScanImageAnswerSheet.this, "No record of answer key. Please, scan answer key first", Toast.LENGTH_LONG).show();
            }else{
            String[] separated = hehe.split(",");

            StringTokenizer tokens = new StringTokenizer(hehe, ", ");
            numofitemsminus = Chekka.itemscount - 1;
            for (int i = 0; i <= numofitemsminus; i++) {
                huhu = tokens.nextToken();
                System.out.println("kooper" + huhu);
                trypo = Integer.parseInt(huhu);
                answers.add(trypo);
            }
            System.out.println("answer102" + answers);
            correct = mydb.getcorrectscoreexam(String.valueOf(Chekka.examid));
            System.out.println("asdfgh" + correct);
            StringTokenizer token_correct = new StringTokenizer(correct, ", ");

            for (int i = 0; i <= Integer.valueOf(examdb) - 1; i++) {

                huhucorrect = token_correct.nextToken();
                System.out.println("kooperlove" + huhucorrect);
                trypocorrect = Integer.parseInt(huhucorrect);
                answers_correct.add(trypocorrect);
            }
            }
            System.out.println("correct_exam" + answers_correct);


            incorrect = mydb.getincorrectscoreexam(String.valueOf(Chekka.examid));
            StringTokenizer token_incorrect = new StringTokenizer(incorrect, ", ");
            for (int i = 0; i <= Integer.valueOf(examdb) - 1; i++) {

                huhuincorrect = token_incorrect.nextToken();
                System.out.println("kooperlove" + huhuincorrect);
                trypoincorrect = Integer.parseInt(huhuincorrect);
                answer_incorrect.add(trypoincorrect);

            }
            System.out.println("correct_exam" + incorrect);
            //answerkeyfromdatabase[0] = "hello poo";
            System.out.println("ako" + answers);
        bMap = Chekka.bmap;
        Utils.bitmapToMat(bMap, frame);


            try {
                ScannerAnswerSheet scannerAnswerSheet = new ScannerAnswerSheet(frame, Chekka.itemscount);
                System.out.println("AAYYAAWW");
                scannerAnswerSheet.setLogging(true);
                scannerAnswerSheet.scan();
                Utils.matToBitmap(frame, bMap);
                scannedImageView.setImageBitmap(bMap);
                scannedImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } catch (Exception e) {
                e.printStackTrace();
            }

                if(catches == "Error") {

                }else {
                    final String ststore = ScannerAnswerSheet.studentnumberstore_str;

                    totalscore = String.valueOf(ScannerAnswerSheet.countscore);
                    totalaverage = String.valueOf(ScannerAnswerSheet.totalaverage);
                    try {
                    StringTokenizer tokens2 = new StringTokenizer(ststore, ", []");
                    numofitemsminus = Chekka.itemscount - 1;

                        for (int i = 0; i <= 3; i++) {
                            sthuhu = tokens2.nextToken();
                            System.out.println("imisskooper" + sthuhu);
                            if (i == 0) {
                                sthuhu1 = sthuhu;
                            } else if (i == 1) {
                                sthuhu2 = sthuhu;
                            } else if (i == 2) {
                                sthuhu3 = sthuhu;
                            } else if (i == 3) {
                                sthuhu4 = sthuhu; }

                        }
                        sthuhufinal = sthuhu1 + "" + sthuhu2 + "" + sthuhu3 + "" + sthuhu4 + "";
                        Holder_StudentNumber_ST = sthuhufinal;
                        CheckingStudentNumberAlreadyExistsOrNot_ST();
                        studentsname = mydb.getstudentnameforimage(sthuhufinal, String.valueOf(Chekka.userid));
                        System.out.println("studentsname: " + studentsname);

                        txt_name_scanned.setText(studentsname);
                        txt_average_scanned.setText(String.format("%.2f", ScannerAnswerSheet.totalaverage));
                        txt_score_scanned.setText(String.valueOf(ScannerAnswerSheet.multiadd));
                        studentname = mydb.getstudentname(sthuhufinal, Chekka.textView_id_LO.getText().toString());


                    }catch (Exception e){
                 //       Toast.makeText(ScanImageAnswerSheet.this, "There's something wrong on your paper. Can't detect Student number", Toast.LENGTH_LONG).show();


                            finish();
                        Toast.makeText(ScanImageAnswerSheet.this, "There's something wrong on your paper. Can't detect student number", Toast.LENGTH_LONG).show();

                    }

                }

    }
        @Override
    public void onClick(View view) {
        System.out.println("Hello");
        cropAcceptBtn.setVisibility(View.INVISIBLE);
    }


public void insertanswers() {

            String answers_str = ScannerAnswerSheet.answerfordb.toString();
            Bitmap bitmap = ((BitmapDrawable)scannedImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

        mydb.insertData(answers_str, txt_score_scanned.getText().toString(),
                txt_average_scanned.getText().toString(), String.valueOf(examid),examnam, String.valueOf(Chekka.scoreitems),byteArray
             , Chekka.textView_id_LO.getText().toString(),  studentname, sthuhufinal, Chekka.examclass);

            haha="";
            txt_score_scanned.setText("");
            txt_average_scanned.setText("");
            sthuhufinal="";
            sthuhu = "";
            studentname = "";

        finish();
}
    public void updateanswers() {
        String answers_str = ScannerAnswerSheet.answerfordb.toString();
        mydb.updateStudents(answers_str,txt_score_scanned.getText().toString(), txt_average_scanned.getText().toString(), String.valueOf(examid),
                String.valueOf(Chekka.scoreitems),examnam, userid,sthuhufinal,examnam, studentname, Chekka.examclass);

        haha="";
        txt_score_scanned.setText("");
        txt_average_scanned.setText("");
        sthuhufinal="";
        sthuhu = "";
        finish();
    }
    public void CheckingStudentNumberAlreadyExistsOrNot_ST() {

        String studentnumber = mydb.getstudidforpaper(Holder_StudentNumber_ST, examnam);
        String userid = mydb.getstuduserid(Holder_StudentNumber_ST);
        String studentidfromtblstudents = mydb.getstudclass(Holder_StudentNumber_ST, Chekka.textView_id_LO.getText().toString());
        String useridfromtblstudents = mydb.getuseridtblusers(Holder_StudentNumber_ST, Chekka.textView_id_LO.getText().toString());
        System.out.println("checktime"+userid);

        if(studentnumber.equals("") && studentidfromtblstudents.equals(Holder_StudentNumber_ST) && useridfromtblstudents.equals(Chekka.textView_id_LO.getText().toString())
            ){
            Holder_TemporaryStudentNumber_ST = "GO";
            System.out.println("test101");
        } else {

            if(userid.equals(Chekka.textView_id_LO.getText().toString()) && studentidfromtblstudents.equals(Holder_StudentNumber_ST)){
                Holder_TemporaryStudentNumber_ST = "Found";
                System.out.println("ff1"+Chekka.textView_id_LO.getText().toString());
            }else if(userid != Chekka.textView_id_LO.getText().toString()){
                System.out.println("ff2");
                finish();
                Toast.makeText(ScanImageAnswerSheet.this, "The student does not belong to this class.", Toast.LENGTH_LONG).show();
            }
        }

    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
