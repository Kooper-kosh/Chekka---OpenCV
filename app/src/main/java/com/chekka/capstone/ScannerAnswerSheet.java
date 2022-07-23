package com.chekka.capstone;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static com.chekka.capstone.Util.*;
import static com.chekka.capstone.Util.sortLeft2Right;
import static com.chekka.capstone.Util.sortTopLeft2BottomRight;
import static com.chekka.capstone.Util.sout;
import static com.chekka.capstone.Util.write2File;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
import static org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.Canny;
import static org.opencv.imgproc.Imgproc.MORPH_DILATE;
import static org.opencv.imgproc.Imgproc.RETR_EXTERNAL;
import static org.opencv.imgproc.Imgproc.RETR_TREE;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.adaptiveThreshold;
import static org.opencv.imgproc.Imgproc.approxPolyDP;
import static org.opencv.imgproc.Imgproc.arcLength;
import static org.opencv.imgproc.Imgproc.blur;
import static org.opencv.imgproc.Imgproc.boundingRect;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.drawContours;
import static org.opencv.imgproc.Imgproc.findContours;
import static org.opencv.imgproc.Imgproc.getStructuringElement;
import static org.opencv.imgproc.Imgproc.threshold;

public class ScannerAnswerSheet {
    String fn, fn1, fn2, fn3, fn4, sthuhufinal;
    public static float totalaverage;
    private final Mat source;
    private final double[] ratio = new double[]{ 25, 25};
    private final int itemscount;
    int numofcolumns;
    int studentnumbercount = 5;
    int totalscoresections;
    List<MatOfPoint> rows2;
    int[] selection2;
    private final String[] options = new String[]{"0","1", "2", "3", "4","5","6","7","8","9","10"};
    private final String[] choices = new String[]{"A", "B", "C", "D"};
    //private final int[] answerkey = new int[]{Integer.parseInt(null),0,1,1,3,Integer.parseInt(null),2,2,3,1,Integer.parseInt(null),1};
    // private final int[] answerkey = new int[]{};
    List<MatOfPoint> rows;
    private List<Integer> storeanswer;
    public static List<Integer> answerfordb;
    private List<Integer> marksforcorrect;
    private List<Integer> marksforcorrectcatach;
    int section1counter;
    int opo;
    List<Integer> youranswer = new ArrayList<>();
    //List<Integer> answerkey = new ArrayList<>();
    int[] selection;
    int countitems;
    int arrayanswer[];
    public static List <Integer> store;
    public static List <Integer> studentnumberstore;
    public static String studentnumberstore_str;
    int a= 0;
    int b= 0;
    private Rect roi;
    private Mat dilated, gray, thresh, blur, canny, adaptiveThresh, hierarchy;
    private List<MatOfPoint> contours, bubbles, bubsforcircles;
    public static List<Integer> answers;
    private List<Integer> studentnumber;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    public static int countscore;
    int optionslength;
    int countscoreforcatch;
    Cursor cursor;
    private boolean logging = false;
    String status= "";
    String hehe;
    int counteritem;
    List<Integer> odds, evens, zero, new_incorrect;
    Integer array[], arrayscoreitems;
    public static int multi, multiadd;

    int onecounter = 0;
    int twocounter = 0;
    int counterstud = 0;
    public ScannerAnswerSheet(Mat source, int questionCount) {
        this.source = source;
        this.itemscount = questionCount;
        hierarchy = new Mat();
        contours = new ArrayList<>();
        bubbles = new ArrayList<>();
        answers = new ArrayList<>();
        store = new ArrayList<>();
        storeanswer = new ArrayList<>();
        answerfordb = new ArrayList<>();
        studentnumber = new ArrayList<>();
        odds = new ArrayList<>();
        evens = new ArrayList<>();
        zero = new ArrayList<>();
        new_incorrect = new ArrayList<>();
        marksforcorrect = new ArrayList<>();
        marksforcorrectcatach = new ArrayList<>();
        studentnumberstore = new ArrayList<>();
        countscore = 0;
        counteritem = 0;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    public void scan() throws Exception {
            counterstud = 0;
        dilated = new Mat(source.size(), CV_8UC1);
        dilate(source, dilated, getStructuringElement(MORPH_DILATE, new Size(3, 3)));
        // if(logging) write2File(dilated, "step_1_dilated.png");

        gray = new Mat(dilated.size(), CV_8UC1);
        cvtColor(dilated, gray, COLOR_BGR2GRAY);
        // if(logging) write2File(gray, "step_2_gray.png");

        thresh = new Mat(gray.rows(), gray.cols(), gray.type());
        threshold(gray, thresh, 200, 255, THRESH_BINARY);
        // if(logging) write2File(thresh, "step_3_thresh.png");

        blur = new Mat(gray.size(), CV_8UC1);
        blur(gray, blur, new Size(5., 5.));
        //  if(logging) write2File(blur, "step_4_blur.png");

        canny = new Mat(blur.size(), CV_8UC1);
        Canny(blur, canny, 200, 20);
        //if(logging) write2File(canny, "step_5_canny.png");

        adaptiveThresh = new Mat(canny.rows(), gray.cols(), gray.type());
        adaptiveThreshold(canny, adaptiveThresh, 255, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY, 11, 2);
        // if(logging) write2File(adaptiveThresh, "step_6_adaptive_thresh.png");
        //array = new int[]{1, 2, 3, 2, 3, 1, 1, 2, 2, 0, 1, 1};
        final List<Integer> answersfromdatabase = ScanImageAnswerSheet.answers;
       array = new Integer[answersfromdatabase.size()];

       answersfromdatabase.toArray(array);
        String firstnumberpersection = String.valueOf(Chekka.firstnumberpersection);
        System.out.println("fnps"+firstnumberpersection);
        StringTokenizer tokens2 = new StringTokenizer(firstnumberpersection, ", ");

        for (int i = 1; i <= Chekka.examsection; i++) {
            fn = tokens2.nextToken();
            System.out.println("imisskooper" + fn);
            if (i==1) {
                fn1 = fn;
            } else if (i == 2) {
                fn2 = fn;
            } else if (i == 3) {
                fn3 = fn;
            } else if (i == 4) {
                fn4 = fn;
            }
        }
        if(Chekka.examsection == 1) {
            for (int i = 0; i < Chekka.itempercount; i++) {
                new_incorrect.add(Integer.valueOf(fn1));
            }
        }else if (Chekka.examsection == 2){
                for (int i = 0; i < Chekka.itempercount; i++) {
                new_incorrect.add(Integer.valueOf(fn1));
                new_incorrect.add(Integer.valueOf(fn2));
            }
        }else if (Chekka.examsection == 3){
            for (int i = 0; i < Chekka.itempercount; i++) {
                new_incorrect.add(Integer.valueOf(fn1));
                new_incorrect.add(Integer.valueOf(fn2));
                new_incorrect.add(Integer.valueOf(fn3));
            }

        }else if (Chekka.examsection == 4){
            for (int i = 0; i < Chekka.itempercount; i++) {
                new_incorrect.add(Integer.valueOf(fn1));
                new_incorrect.add(Integer.valueOf(fn2));
                new_incorrect.add(Integer.valueOf(fn3));
                new_incorrect.add(Integer.valueOf(fn4));
            }

        }
        System.out.println("new_incorrect"+new_incorrect);
        findParentRectangle();

        findBubbles();

        recognizeAnswers();
        recognizeAnswers2();
        sout("answer key by order   " + youranswer);
       // sout("answer not by order   "+storeanswer);
        sout("answer by order       "+answers);
        for (int index = 0; index < answers.size(); index++) {
            Integer optionIndex = answers.get(index);
            sout((index + 1) + ". " + (optionIndex == 4 ? "EMPTY" : choices[optionIndex]));
        }
        for (int index = 0; index < studentnumber.size(); index++) {
            Integer optionIndex = studentnumber.get(index);
            sout((index + 1) + ". " + (optionIndex == null ? "NONE" : options[optionIndex]));
            if(optionIndex != 10) {
                studentnumberstore.add(Integer.valueOf(options[optionIndex]));
            }

        }

        studentnumberstore_str = String.valueOf(studentnumberstore);
        System.out.println("studentnumberstore"+studentnumberstore_str);




        System.out.println("marks"+marksforcorrect);
        System.out.println("marks2"+marksforcorrectcatach);

        int nullcounter = 0;
        for(int i = 0; i < marksforcorrect.size(); i++){
            Integer marksforcorrect_index = marksforcorrect.get(i);
            if(marksforcorrect_index == 1){
               int answercorrect_index = ScanImageAnswerSheet.answers_correct.get(i);
               multi = marksforcorrect_index * answercorrect_index;
                System.out.println("multi"+multi);
                System.out.println("answercorrect"+answercorrect_index);
               onecounter++;
               int a = i +1;
                System.out.println(a+" checked");

            } else if(marksforcorrect_index == 0) {
                int answerincorrect_index = ScanImageAnswerSheet.answer_incorrect.get(i);
                if(answerincorrect_index == 0){

               }else if(answerincorrect_index == 1){
                    int a = i +1;
                    System.out.println(a+" checked plus");
                    multi = marksforcorrect_index + answerincorrect_index;
                } else if(answerincorrect_index > 1){
                    int a = i +1;
                    System.out.println(a+" checked times");
                    multi = marksforcorrect_index * answerincorrect_index;
                }
                twocounter++;

            }

            multiadd = multiadd + multi;
            multi = 0;

        }
        average();
           System.out.println("multiadd"+multiadd);

    }


    private void findParentRectangle() throws Exception {

        findContours(adaptiveThresh.clone(), contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);

        if(logging) sout("getParentRectangle > hiearchy data:\n" + hierarchy.dump());

        // find rectangles
        HashMap<Double, MatOfPoint> rectangles = new HashMap<>();
        for(int i = 0; i < contours.size(); i++){
            MatOfPoint2f approxCurve = new MatOfPoint2f( contours.get(i).toArray() );
            approxPolyDP(approxCurve, approxCurve, 0.02 * arcLength(approxCurve, true), true);

            if(approxCurve.toArray().length == 4){
                rectangles.put((double) i, contours.get(i));
            }
        }

        if(logging) sout("getParentRectangle > contours.size: " + contours.size());
        if(logging) sout("getParentRectangle > rectangles.size: " + rectangles.size());

        int parentIndex = -1;

        // choose hierarchical rectangle which is our main wrapper rect
        for (Map.Entry<Double, MatOfPoint> rectangle : rectangles.entrySet()) {
            double index = rectangle.getKey();

            double[] ids = hierarchy.get(0, (int) index);
            double nextId = ids[0];
            double previousId = ids[1];
//            double childId = ids[2];

            if(nextId != -1 && previousId != -1) continue;

            int k = (int) index;
            int c = 0;

            while(hierarchy.get(0, k)[2] != -1){
                k = (int) hierarchy.get(0, k)[2];
                c++;
            }

            if(hierarchy.get(0, k)[2] != -1) c++;

            System.out.println("csakalam"+c);
            if (c >= 3){
                parentIndex = (int) index;
                System.out.println("pasok sa banga"+parentIndex);
            }

            if(logging) sout("getParentRectangle > index: " + index + ", c: " + c);
        }

        if(logging) sout("getParentRectangle > parentIndex: " + parentIndex);

        if(parentIndex < 0){
            throw new Exception("Couldn't capture main wrapper");
        }

        roi = boundingRect(contours.get(parentIndex));

        if(logging) sout("getParentRectangle > original roi.x: " + roi.x + ", roi.y: " + roi.y);
        if(logging) sout("getParentRectangle > original roi.width: " + roi.width + ", roi.height: " + roi.height);

        int padding = 30;

        roi.x += padding;
        roi.y += padding;
        roi.width -= 2 * padding;
        roi.height -= 2 * padding;

        if(logging) sout("getParentRectangle > modified roi.x: " + roi.x + ", roi.y: " + roi.y);
        if(logging) sout("getParentRectangle > modified roi.width: " + roi.width + ", roi.height: " + roi.height);

        if(logging) write2File(source.submat(roi), "step_7_roi.png");
    }

    private void findBubbles() throws Exception {

        contours.clear();

        findContours(canny.submat(roi), contours, hierarchy, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);

        double threshold = 10;
        double _w = roi.width / this.ratio[0];
        double _h = roi.height / this.ratio[1];
        sout("ww"+_w);
        sout("hh"+_h);
        System.out.println("_w"+_w+" _h"+_h);
        double minThreshold = Math.floor(Math.min(_w, _h)-threshold);
        double maxThreshold = Math.ceil(Math.max(_w, _h)+threshold);

        if(logging) sout("findBubbles > ideal circle size > minThreshold: " + minThreshold + ", maxThreshold: " + maxThreshold);

        List<MatOfPoint> drafts = new ArrayList<>();
        List<MatOfPoint> draftsforcircles = new ArrayList<>();
        for(MatOfPoint contour : contours) {

            Rect _rect = boundingRect(contour);
            int w = _rect.width;
            int h = _rect.height;
            double ratio = Math.max(w, h) / Math.min(w, h);

            if (logging) sout("findBubbles > founded circle > w: " + w + ", h: " + h);

            sout("sobad" + b++);
            System.out.println("ratio" + ratio);
            if (ratio == 1.0)
                sout(" maxthres: " + maxThreshold + ", minthres: " + minThreshold);
            sout(" mathmax " + Math.max(w, h) + ", mathmin: " + Math.min(w, h));
            if (Math.min(w, h) >= 36 && Math.max(w, h) <= 51) {
                counterstud++;
                if(counterstud<=50) {
                    drafts.add(contour);
                    System.out.println("true");

                }
                sout("tochange" + a++);
            } else if (Math.min(w, h) >= 44 && Math.max(w, h) <= 78 ) {
                int itemcount = Chekka.itemscount * choices.length;
                counteritem++;
                if(counteritem <= itemcount) {
                    draftsforcircles.add(contour);
                    System.out.println("true");

                    sout("tochange" + a++);
                }

            }
        }

        if(logging) sout("findBubbles > bubbles.size: " + drafts.size());
        optionslength = options.length - 1;
        System.out.println("drafts size "+drafts.size()+"/n questioncount"+studentnumbercount+"/n optionslength"+ optionslength);
        System.out.println("drafts size "+draftsforcircles.size()+"/n questioncount"+Chekka.itemscount+"/n optionslength"+choices.length);
        try {
            if (drafts.size() != studentnumbercount * optionslength) {
                throw new Exception("Couldn't capture all bubbles.");
            }
        }catch (Exception e){

        }
        try {
            if (draftsforcircles.size() != Chekka.itemscount * choices.length) {
                throw new Exception("Couldn't capture all bubbles.");
            }
        }catch (Exception e){

        }

        // order bubbles on coordinate system

        sortTopLeft2BottomRight(drafts);
        sortTopLeft2BottomRight(draftsforcircles);
        bubbles = new ArrayList<>();
        bubsforcircles = new ArrayList<>();
        if(Chekka.examcategory.equals("10MTSECT1") || Chekka.examcategory.equals("15MTSECT1")) {
            numofcolumns = 1;

        } else if(Chekka.examcategory.equals("20MTSECT2") || Chekka.examcategory.equals("30MTSECT2")) {
            numofcolumns = 2;
        }else if(Chekka.examcategory.equals("30MTSECT3") || Chekka.examcategory.equals("45MTSECT3")) {
            numofcolumns = 3;
        }else if(Chekka.examcategory.equals("40MTSECT4") || Chekka.examcategory.equals("60MTSECT4")) {
            numofcolumns = 4;
            }

        for(int j = 0; j < drafts.size(); j+=optionslength){

            List<MatOfPoint> row = drafts.subList(j, j + optionslength);

            sortLeft2Right(row);

            bubbles.addAll(row);
            System.out.println("rowrow"+row);
            System.out.println("bubbles"+bubbles);
        }

        for(int j = 0; j < draftsforcircles.size(); j+=choices.length*numofcolumns){

            List<MatOfPoint> row2 = draftsforcircles.subList(j, j + choices.length*numofcolumns);

            sortLeft2Right(row2);

            bubsforcircles.addAll(row2);
            System.out.println("rowrow"+row2);
            System.out.println("bubbles"+bubsforcircles);
        }
    }

    private void recognizeAnswers(){


        for(int i = 0; i< bubbles.size(); i+=optionslength) {

            rows = bubbles.subList(i, i + optionslength);
            int[][] filled = new int[rows.size()][9];

            for (int j = 0; j < rows.size(); j++) {

                MatOfPoint col = rows.get(j);

                List<MatOfPoint> list = Arrays.asList(col);

                Mat mask = new Mat(thresh.size(), CvType.CV_8UC1);
                drawContours(mask.submat(roi), list, -1, new Scalar(255, 0, 0), -1);

                Mat conjuction = new Mat(thresh.size(), CvType.CV_8UC1);
                Core.bitwise_and(thresh, mask, conjuction);

//                if(logging) write2File(mask, "mask_" + i + "_" + j + ".png");
//                if(logging) write2File(conjuction, "conjuction_" + i + "_" + j + ".png");

                int countNonZero = Core.countNonZero(conjuction);

                if (logging)
                    sout("recognizeAnswers > " + i + ":" + j + " > countNonZero: " + countNonZero);

                filled[j] = new int[]{countNonZero, i, j};

            }

            selection = chooseFilledCircle(filled);


            //  answerkey.add(hehe);

            if (logging) sout("recognizeAnswers > selection is " + (selection == null ? "empty/invalid" : selection[2]));
            if(selection != null) {

                Imgproc.drawContours(source.submat(roi), Arrays.asList(rows.get(selection[2])), -1, new Scalar(0, 255, 0), 3);

            }
            countscoreforcatch = countscore;
//            System.out.println("array[] "+array[opo]);

            studentnumber.add(selection == null ? 10 : selection[2]);

        }


        ;

        //sout("odds"+answers.addAll(odds));
        // sout("evens"+answers.addAll(evens));
    }
    private void recognizeAnswers2(){


        for(int i = 0; i< bubsforcircles.size(); i+=choices.length) {

            rows2 = bubsforcircles.subList(i, i + choices.length);
            int[][] filled = new int[rows2.size()][4];

            for (int j = 0; j < rows2.size(); j++) {

                MatOfPoint col = rows2.get(j);

                List<MatOfPoint> list = Arrays.asList(col);

                Mat mask = new Mat(thresh.size(), CvType.CV_8UC1);
                drawContours(mask.submat(roi), list, -1, new Scalar(255, 0, 0), -1);

                Mat conjuction = new Mat(thresh.size(), CvType.CV_8UC1);
                Core.bitwise_and(thresh, mask, conjuction);

//                if(logging) write2File(mask, "mask_" + i + "_" + j + ".png");
//                if(logging) write2File(conjuction, "conjuction_" + i + "_" + j + ".png");

                int countNonZero = Core.countNonZero(conjuction);

                if (logging)
                    sout("recognizeAnswers > " + i + ":" + j + " > countNonZero: " + countNonZero);

                filled[j] = new int[]{countNonZero, i, j};

            }

            selection2 = chooseFilledCircle2(filled);


            //  answerkey.add(hehe);

            if (logging) sout("recognizeAnswers > selection is " + (selection2 == null ? "empty/invalid" : selection2[2]));
            // optionanswer = answerkey.get(2);
            //sout("sarap mo tignan: "+optionanswer);
            // int countscore = 0;
            if (selection2 == null) {
                marksforcorrect.add(0);
                storeanswer.add(4);
                youranswer.add(array[opo]);
                sout("opopo"+array[opo]);
                dothis();
                answerfordb.add(0);

            } else if (selection2 != null) {
                try {
                    storeanswer.add(selection2[2]);
                    youranswer.add(array[opo]);
                    sout("opopo" + array[opo]);

                    if (array[opo] == 4) {
                        answerfordb.add(0);
                      marksforcorrect.add(0);
                        countitems++;
                    } else {
                        if (selection2[2] == array[opo]) {
                            Imgproc.drawContours(source.submat(roi), Arrays.asList(rows2.get(selection2[2])), -1, new Scalar(0, 255, 0), 2);
                            marksforcorrect.add(1);
                            countscore++;
                            countitems++;
                            answerfordb.add(1);
                        } else {
                            answerfordb.add(0);
                            marksforcorrect.add(0);
                            countitems++;
                            int answerincorrect_index = new_incorrect.get(countitems - 1);
                            if (answerincorrect_index == 0) {
                                Imgproc.drawContours(source.submat(roi), Arrays.asList(rows2.get(selection2[2])), -1, new Scalar(255, 0, 0), 3);
                                Imgproc.drawContours(source.submat(roi), Arrays.asList(rows2.get(array[opo])), -1, new Scalar(0, 0, 255), 3);
                            } else if (answerincorrect_index >= 1) {
                                Imgproc.drawContours(source.submat(roi), Arrays.asList(rows2.get(selection2[2])), -1, new Scalar(255, 218, 41), 3);
                                Imgproc.drawContours(source.submat(roi), Arrays.asList(rows2.get(array[opo])), -1, new Scalar(0, 0, 255), 3);
                            }
                        }
                    }
                }catch (Exception e){

                }
                    dothis();

            }
            answers.add(selection2 == null ? 4 : selection2[2]);
            countscoreforcatch = countscore;
            sout("storeanswer"+storeanswer);
            sout("answerfordb"+answerfordb);
//            System.out.println("array[] "+array[opo]);
        }




        List<Integer> oddsfour = new ArrayList<>();
        List<Integer> evensfour = new ArrayList<>();
        List<Integer> twofour = new ArrayList<>();
        List<Integer> threefour = new ArrayList<>();




       if(Chekka.examcategory.equals("20MTSECT2") || Chekka.examcategory.equals("30MTSECT2")) {
            List<Integer> odds = new ArrayList<>();
            List<Integer> evens = new ArrayList<>();
           List<Integer> odds11 = new ArrayList<>();
           List<Integer> evens11 = new ArrayList<>();
           for (int i = 0; i < marksforcorrect.size(); i++) {
               if (i % 2 == 0) odds11.add(marksforcorrect.get(i));
               if (i % 2 == 1) evens11.add(marksforcorrect.get(i));
           }
           marksforcorrect.clear();
           marksforcorrect.addAll(odds11);
           marksforcorrect.addAll(evens11);


           for (int i = 0; i < answers.size(); i++) {
                if (i % 2 == 0) odds.add(answers.get(i));
                if (i % 2 == 1) evens.add(answers.get(i));

            }

            answers.clear();
            answers.addAll(odds);
            answers.addAll(evens);

            List<Integer> odds1 = new ArrayList<>();
            List<Integer> evens1 = new ArrayList<>();
            for (int i = 0; i < answerfordb.size(); i++) {
                if (i % 2 == 0) odds1.add(answerfordb.get(i));
                if (i % 2 == 1) evens1.add(answerfordb.get(i));

            }
            answerfordb.clear();
            answerfordb.addAll(odds1);
            answerfordb.addAll(evens1);


        }else if(Chekka.examcategory.equals("30MTSECT3") || Chekka.examcategory.equals("45MTSECT3")) {
            List<Integer> odds = new ArrayList<>();
            List<Integer> evens = new ArrayList<>();
            List<Integer> zero = new ArrayList<>();
           List<Integer> oddse = new ArrayList<>();
           List<Integer> evense = new ArrayList<>();
           List<Integer> zeroe = new ArrayList<>();
           for (int i = 0; i < marksforcorrect.size(); i++) {
               if (i % 3 == 0) oddse.add(marksforcorrect.get(i));
               if (i % 3 == 1) evense.add(marksforcorrect.get(i));
               if (i % 3 == 2) zeroe.add(marksforcorrect.get(i));
           }
           marksforcorrect.clear();
           marksforcorrect.addAll(oddse);
           marksforcorrect.addAll(evense);
           marksforcorrect.addAll(zeroe);
            for (int i = 0; i < answers.size(); i++) {
                if (i % 3 == 0) odds.add(answers.get(i));
                if (i % 3 == 1) evens.add(answers.get(i));
                if (i % 3 == 2) zero.add(answers.get(i));
            }

            answers.clear();
            answers.addAll(odds);
            answers.addAll(evens);
            answers.addAll(zero);

            List<Integer> odds1 = new ArrayList<>();
            List<Integer> evens1 = new ArrayList<>();
            List<Integer> zero1 = new ArrayList<>();

            for (int i = 0; i < answerfordb.size(); i++) {
                if (i % 3 == 0) odds1.add(answerfordb.get(i));
                if (i % 3 == 1) evens1.add(answerfordb.get(i));
                if (i % 3 == 2) zero1.add(answerfordb.get(i));
            }
            answerfordb.clear();
            answerfordb.addAll(odds1);
            answerfordb.addAll(evens1);
            answerfordb.addAll(zero1);
        }else if(Chekka.examcategory.equals("40MTSECT4") || Chekka.examcategory.equals("60MTSECT4")) {
            {
                List<Integer> zero = new ArrayList<>();
                List<Integer> one = new ArrayList<>();
                List<Integer> two = new ArrayList<>();
                List<Integer> three = new ArrayList<>();
                for (int i = 0; i < marksforcorrect.size(); i++) {
                    if (i % 4 == 0) oddsfour.add(marksforcorrect.get(i));
                    if (i % 4 == 1) evensfour.add(marksforcorrect.get(i));
                    if (i % 4 == 2) twofour.add(marksforcorrect.get(i));
                    if (i % 4 == 3) threefour.add(marksforcorrect.get(i));
                }
                marksforcorrect.clear();
                marksforcorrect.addAll(oddsfour);
                marksforcorrect.addAll(evensfour);
                marksforcorrect.addAll(twofour);
                marksforcorrect.addAll(threefour);

                for (int i = 0; i < answers.size(); i++) {
                    if (i % 4 == 0) zero.add(answers.get(i));
                    if (i % 4 == 1) one.add(answers.get(i));
                    if (i % 4 == 2) two.add(answers.get(i));
                    if (i % 4 == 3) three.add(answers.get(i));
                }
                answers.clear();
                answers.addAll(zero);
                answers.addAll(one);
                answers.addAll(two);
                answers.addAll(three);

                List<Integer> zero1 = new ArrayList<>();
                List<Integer> one1 = new ArrayList<>();
                List<Integer> two1 = new ArrayList<>();
                List<Integer> three1 = new ArrayList<>();
                for (int i = 0; i < answerfordb.size(); i++) {
                    if (i % 4 == 0) zero1.add(answerfordb.get(i));
                    if (i % 4 == 1) one1.add(answerfordb.get(i));
                    if (i % 4 == 2) two1.add(answerfordb.get(i));
                    if (i % 4 == 3) three1.add(answerfordb.get(i));
                }
                answerfordb.clear();
                answerfordb.addAll(zero1);
                answerfordb.addAll(one1);
                answerfordb.addAll(two1);
                answerfordb.addAll(three1);
            }
        }
    }

private void dothis(){
    opo++;
}

    private void average(){

        double num1= countscore;
        double num2= itemscount;

        totalaverage = ((float) multiadd /(float) Chekka.scoreitems)*100;
        System.out.println("total average: "+String.format("%.2f",totalaverage));
        System.out.println("total score: "+multiadd);


    }
    private int[] chooseFilledCircle(int[][] rows){

        double mean = 0;
        for(int i = 0; i < rows.length; i++){
            mean += rows[i][0];
        }
        mean = 1.0d * mean / optionslength;

        int anomalouses = 0;
        for(int i = 0; i < rows.length; i++){
            if(rows[i][0] > mean) anomalouses++;
        }

        if(anomalouses == optionslength - 1){

            int[] lower = null;
            for(int i = 0; i < rows.length; i++){
                if(lower == null || lower[0] > rows[i][0]){
                    lower = rows[i];

                }
            }

            return lower;

        } else {
            return null;
        }
    }
    private int[] chooseFilledCircle2(int[][] rows){

        double mean = 0;
        for(int i = 0; i < rows.length; i++){
            mean += rows[i][0];
        }
        mean = 1.0d * mean / choices.length;

        int anomalouses = 0;
        for(int i = 0; i < rows.length; i++){
            if(rows[i][0] > mean) anomalouses++;
        }

        if(anomalouses == choices.length - 1){

            int[] lower = null;
            for(int i = 0; i < rows.length; i++){
                if(lower == null || lower[0] > rows[i][0]){
                    lower = rows[i];

                }
            }

            return lower;

        } else {
            return null;
        }
    }
}
