package com.chekka.capstone;


public class Classes {
    private String c_ClassName; //BSIT 3-1
    private int c_Class_icon; // CLASS NAME IMAGE
    private int c_ClassStudent_icon; // NUMBER OF STUDENTS IMAGE
    private int c_ClassNoStudents;  //NUMBER OF STUDENTS
    private int  c_ClassEditListView_icon; // EDIT IMAGE
    /*
     * Create a new dessert object.
     *
     * @param vName is the name of the dessert
     * @param vNumber is the corresponding number of desserts
     * @param image is drawable reference ID that corresponds to the dessert
     * */
    public Classes(String c_class_name, int c_class_icon, int c_class_student_icon, int c_class_no_of_students, int c_class_edit_listView_icon)

    {
        c_ClassName = c_class_name;
        c_Class_icon = c_class_icon;
        c_ClassStudent_icon = c_class_student_icon;
        c_ClassNoStudents = c_class_no_of_students;
        c_ClassEditListView_icon = c_class_edit_listView_icon;
    }



    public String getClassName() {
        return c_ClassName;
    }
    public String getClassNameForStud(long id) {
        return c_ClassName;
    }
    public int getClassIcon() {
        return c_Class_icon;
    }
    public int getC_ClassNoStudents() {
        return c_ClassNoStudents;
    }
    public int getC_ClassStudent_icon() {
        return c_ClassStudent_icon;
    }
    public int getC_ClassEditListView_icon() {
        return c_ClassEditListView_icon;
    }



}