package com.chekka.capstone;


public class Student {
    private String s_StudentName; //BSIT 3-1
    private String s_studentClass; //BSIT 3-1
    private int s_StudentIcon; // CLASS NAME IMAGE
    private int s_StudentNumberIcon; // NUMBER OF STUDENTS IMAGE
    private int s_StudentNumber;  //NUMBER OF STUDENTS
    private int  s_StudentEditIcon; // EDIT IMAGE
    /*
     * Create a new dessert object.
     *
     * @param vName is the name of the dessert
     * @param vNumber is the corresponding number of desserts
     * @param image is drawable reference ID that corresponds to the dessert
     * */
    public Student(String s_student_name, String s_studentclass, int s_studentname_icon, int s_studentnumber_icon, int s_studentnumber, int s_studentedit_icon)

    {
        s_StudentName = s_student_name;
        s_StudentIcon= s_studentname_icon;
        s_StudentNumberIcon = s_studentnumber_icon;
        s_StudentNumber = s_studentnumber;
        s_StudentEditIcon = s_studentedit_icon;
        s_studentClass = s_studentclass;
    }


    public String getS_StudentName() {
        return s_StudentName;
    }
    public int getS_StudentIcon() {
        return s_StudentIcon;
    }
    public int getS_StudentNumberIcon() { return s_StudentNumberIcon; }
    public int getS_StudentNumber() {
        return s_StudentNumber;
    }
    public int getS_StudentEditIcon() { return s_StudentEditIcon; }
    public String getS_StudentClass() {return s_studentClass; }
}