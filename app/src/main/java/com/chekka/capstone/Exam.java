package com.chekka.capstone;


public class Exam {
    private String e_examname; //BSIT 3-1
    private int e_examname_icon; // CLASS NAME IMAGE
    private String e_examset;
    private String e_examitemcount;
    private String  e_examdate;

    /*
     * Create a new dessert object.
     *
     * @param vName is the name of the dessert
     * @param vNumber is the corresponding number of desserts
     * @param image is drawable reference ID that corresponds to the dessert
     * */
    public Exam(String e_exam_name, int  e_exam_name_icon, String e_exam_set, String e_item_count, String e_date_e)

    {
        e_examname = e_exam_name;
        e_examname_icon =  e_exam_name_icon;
        e_examset =  e_exam_set;
        e_examitemcount =  e_item_count;
        e_examdate = e_date_e;
    }



    public String getE_examname() {
        return e_examname;
    }
    public int getE_examname_icon() {
        return e_examname_icon;
    }
    public String getE_examset() {
        return e_examset;
    }
    public String getE_examitemcount() {
        return e_examitemcount;
    }
    public String getE_examdate() {
        return e_examdate;
    }



}