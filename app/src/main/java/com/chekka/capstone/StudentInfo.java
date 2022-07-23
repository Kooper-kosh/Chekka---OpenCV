package com.chekka.capstone;


public class StudentInfo {
    private String s_examname; //BSIT 3-1
    private int s_examname_icon; // CLASS NAME IMAGE
    private String s_examscore_name;
    private String s_examscore;
    private String s_itemscore_s;
    private String s_averagecore_s;
    /*
     * Create a new dessert object.
     *
     * @param vName is the name of the dessert
     * @param vNumber is the corresponding number of desserts
     * @param image is drawable reference ID that corresponds to the dessert
     * */
    public StudentInfo(String s_examname_s, int s_examname_icon_S, String s_examscore_name_s, String s_examscore_s,  String s_itemscore, String s_average)

    {
        s_examname = s_examname_s;
        s_examname_icon = s_examname_icon_S;
        s_examscore_name = s_examscore_name_s;
        s_examscore = s_examscore_s;
        s_itemscore_s = s_itemscore;
        s_itemscore_s = s_average;
    }



    public String getS_examname() {

        return s_examname;
    }
    public int getS_examname_icon() {
        return s_examname_icon;
    }

    public String getS_examscore_name() {

        return s_examscore_name;
    }
    public String getS_examscore(){

        return s_examscore;
    }

    public String getS_itemscore(){

        return s_itemscore_s;
    }

    public String getS_averagecore(){

        return s_averagecore_s;
    }
}