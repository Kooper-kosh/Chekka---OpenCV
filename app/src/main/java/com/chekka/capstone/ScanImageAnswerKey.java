package com.chekka.capstone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.List;
import java.util.StringTokenizer;

public class ScanImageAnswerKey extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 101;
    private ImageView scannedImageView; //dito ilalagay yung nascan
    private View cropAcceptBtn;
    private View cropRejectBtn;
    private FrameLayout cropLayout;
    private com.adityaarora.liveedgedetection.view.ScanSurfaceView mImageSurfaceView;
    private boolean isPermissionNotGranted;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;
    int itemscount, examid;
    int counter = 0;
    String checker="";
    Bitmap bMap;
    SQLiteHelper mydb;
    SQLiteDatabase sqLiteDatabaseObj;
    TextView txt;
    List<Integer> storeanswerkey;
    int st;
    String huhu;
    String pain;
    String SQLiteDataBaseQueryHolder;
    Cursor cursor;
    String Holder_TemporaryAnswer_S = "Not_Found";
    String Holder_examid_AK, Holder_Temporaryexamid_AK="";
    String checkblank="";
    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //
        setContentView(R.layout.scan_activity_answerkey); //madidisplay sa layout ng activity_main
        scannedImageView = findViewById(R.id.scanned_image); //ididisplay nascan na image

        startScan(); // method para makapag scan
        mydb = new SQLiteHelper(this);

        cropAcceptBtn = findViewById(R.id.crop_accept_btn);
        cropRejectBtn = findViewById(R.id.crop_reject_btn);
        cropLayout = findViewById(R.id.crop_layout);
        checkblank = "";
        counter = 0;
        cropAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mImageSurfaceView.setPreviewCallback();
            if(Chekka.ud.equals("yes ud")) {
                if(counter <= 3){
                    updateanswerk();
                    finish();
                    Toast.makeText(ScanImageAnswerKey.this, "Updated Answer Key Successfully", Toast.LENGTH_LONG).show();
                    Chekka.exam_lv.setEnabled(true);
                    Chekka.layout_popforexam_PE.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(ScanImageAnswerKey.this, "There's blank on your paper", Toast.LENGTH_LONG).show();
                    finish();
                }
            }else if(Chekka.ud.equals("no ud")){
                if(counter <= 3){
                    insertanswerk();
                    finish();
                    Toast.makeText(ScanImageAnswerKey.this, "Scanned Answer Key Successfully", Toast.LENGTH_LONG).show();
                    Chekka.exam_lv.setEnabled(true);
                    Chekka.layout_popforexam_PE.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(ScanImageAnswerKey.this, "There's blank on your paper", Toast.LENGTH_LONG).show();
                    finish();
                }
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
        try {
            itemscount = Chekka.itemscount;
            examid = Chekka.examid;

            System.out.println("examdb2" + itemscount);
            Mat frame = new Mat();
            int questionCount;
            //mat(data);
            //  Bitmap bMap = com.chekka.examchecker.util.ScanUtils.decodeBitmapFromFile(filePath, com.chekka.examchecker.constants.ScanConstants.IMAGE_NAME);

            bMap = Chekka.bmap;
            Utils.bitmapToMat(bMap, frame);
            ScannerAnswerKey scannerAnswerKey = new ScannerAnswerKey(frame, itemscount);
            scannerAnswerKey.setLogging(true);
            try {
                scannerAnswerKey.scan();
                Utils.matToBitmap(frame, bMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            scannedImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            scannedImageView.setImageBitmap(bMap);

            System.out.println("want to sleep" + itemscount);
            final List<Integer> storeanswerkey = com.chekka.capstone.ScannerAnswerKey.store;
            System.out.println("st " + storeanswerkey);
            String ak = storeanswerkey.toString();
            StringTokenizer tokens = new StringTokenizer(ak, "[]");
            for (int i = 0; i <= 0; i++) {
                huhu = tokens.nextToken();
                System.out.println("kooper" + huhu);
                if(huhu.equals("4")){
                    checkblank = "BAWAL";
                    counter++;
                }
            }
        }catch (Exception e){
                    finish();
                Toast.makeText(getApplicationContext(), "Your paper might cause the problem. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View view) {
        System.out.println("Hello");
        cropAcceptBtn.setVisibility(View.INVISIBLE);
    }



    public void insertanswerk() {
        sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);
        SQLiteDataBaseQueryHolder = "INSERT INTO " + mydb.tbl_answerkey + "(answerkey,examID) VALUES('" + huhu + "','" + examid + "')";
        sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
    }
    public void updateanswerk() {

        mydb.update_answerkey(huhu, String.valueOf(examid));
         }
}
