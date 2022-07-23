package com.chekka.capstone;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {

    RelativeLayout layout_header, layout_signin_signup_forgotpassword;

    FrameLayout header_mainbackground,
            ssf_signin, ssf_signup, ssf_confirmation,
            ssf_twopassword, ssf_sendemail, ssf_inputemail;
    int output, output2;
    String rec, rec2;
    TextView button_signin_L, button_signupsignin_L, button_forgotpassword_L,
            button_loginrecoverconfirmation_C, button_sendrecoverconfirmation_C,
            button_sendrecoveremail_E, button_loginrecoveremail_E,
            button_signup_S, button_signuplogin_S,
            button_sendemailregister_SE, button_loginsendemailregister_SE,
            button_confirmationpassword_T, button_loginconfirmation_T;

    EditText EdiText_Email_L, EditText_Password_L,
            EditText_Email_S, EditText_Password_S, EditText_Username_S, EditText_EmailCode_SE,
            EditText_InputEmail_E, EditText_NewPassword_T,  EditText_ConPassword_T;

    String Holder_Email_L, Holder_Password_L, Holder_TemporaryPassword_L = "NOT_FOUND",
            Pattern_Email_S = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", SQLiteDataBaseQueryHolder,
            From_Email_S = "chekkathechecker@gmail.com", From_Password_S = "iocd nbfs bhpx scts",
            Email_Subject_S = "chekkacode", Email_Body_S = "",
            Holder_Email_S, Holder_Password_S, Holder_Username_S, Holder_TemporaryEmail_S = "Not_Found",
            emailholder;
    TextView editerror;
    Properties emailProperties;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    FrameLayout splashscreen;
    boolean Holder_Empty_L,Holder_Empty_S ;
    public static final String User_Email_L = "";
    public static final String User_UserName_L = "";
    TextView emailtxt;
    String EmailCode_SE;
    SQLiteHelper mydb;
    TextView edittexthome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailtxt = ((TextView) findViewById(R.id.EditTextEmailCode_SE));
        splashscreen = findViewById(R.id.splashscreen);
        edittexthome = findViewById(R.id.edittexthome);
        mydb = new SQLiteHelper(this);
        String tempMin = "1000";
        String tempMax = "9999";

        String tempMin2 = "1000";
        String tempMax2 = "9999";
        Random r = new Random();

        int min = Integer.parseInt(tempMin);
        int max = Integer.parseInt(tempMax);
        if (max > min) {
            output = r.nextInt((max - min) + 1) + min;
            rec = String.valueOf(output);
            rec2 = String.valueOf(output);
            System.out.println("codepassword"+rec);

        }


        int min2 = Integer.parseInt(tempMin2);
        int max2 = Integer.parseInt(tempMax2);
        if (max2 > min2) {
            output2 = r.nextInt((max2 - min2) + 1) + min2;
            rec2 = String.valueOf(output2);
            System.out.println("codepassword"+rec2);

        }

        layout_header = (RelativeLayout) findViewById(R.id.includelayout_header);
        layout_signin_signup_forgotpassword = (RelativeLayout) findViewById(R.id.includelayout_signin_signup_forgotpassword);

        header_mainbackground = (FrameLayout) findViewById(R.id.header_mainbackground);
        ssf_signin = (FrameLayout) findViewById(R.id.layout_signin);
        ssf_confirmation = (FrameLayout) findViewById(R.id.layout_confirmation);
        ssf_inputemail = (FrameLayout) findViewById(R.id.layout_inputemailforrecover);
        ssf_signup = (FrameLayout) findViewById(R.id.layout_signup);
        ssf_sendemail = (FrameLayout) findViewById(R.id.layout_sendemail);
        ssf_twopassword = (FrameLayout) findViewById(R.id.layout_twopassword);

        button_signin_L = (TextView) findViewById(R.id.ButtonMSignin_L);
        button_signupsignin_L = (TextView) findViewById(R.id.ButtonMSignupSignin_L);
        button_forgotpassword_L = (TextView) findViewById(R.id.ButtonMForgotPasswordSignin_L);
        button_loginrecoverconfirmation_C = (TextView) findViewById(R.id.ButtonMLoginRecoverConfirmation_C);
        button_sendrecoverconfirmation_C = (TextView) findViewById(R.id.ButtonMSendRecoverConfirmation_C);
//        button_loginrecoveremail_E = (TextView) findViewById(R.id.ButtonMLoginRecoverEmail_E);
        button_sendrecoveremail_E = (TextView) findViewById(R.id.ButtonMSendRecoverEmail_E);
        button_signup_S = (TextView) findViewById(R.id.ButtonMSignup_S);
        button_signuplogin_S = (TextView) findViewById(R.id.ButtonMSignupLogin_S);
        button_sendemailregister_SE = (TextView) findViewById(R.id.ButtonMSendEmailRegister_SE);
        button_loginsendemailregister_SE = (TextView) findViewById(R.id.ButtonMLoginSendEmailRegister_SE);
        button_confirmationpassword_T = (TextView) findViewById(R.id.ButtonMConfirmPassword_T);
        button_loginconfirmation_T = (TextView) findViewById(R.id.ButtonMLoginConfirmationPassword_T);

        EdiText_Email_L = (EditText) findViewById(R.id.EditTextEmail_L);
        EditText_Password_L = (EditText) findViewById(R.id.EditTextPassword_L);
        EditText_Email_S = (EditText) findViewById(R.id.EditTextEmail_S);
        EditText_Password_S = (EditText) findViewById(R.id.EditTextPassword_S);
        EditText_Username_S = (EditText) findViewById(R.id.EditTextUserName_S);
        EditText_InputEmail_E = (EditText) findViewById(R.id.EditTextInputEmail_E);
        EditText_NewPassword_T = (EditText) findViewById(R.id.EditTextNewPassword_T);
        EditText_ConPassword_T = (EditText) findViewById(R.id.EditTextConPassword_T);



        splashscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_header.setVisibility(View.VISIBLE);
                layout_signin_signup_forgotpassword.setVisibility(View.VISIBLE);
                splashscreen.setVisibility(View.INVISIBLE);
            }
        });

        button_signin_L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittexthome.setText("Log in to");
                if(EdiText_Email_L.getText().toString().isEmpty()|| EditText_Password_L.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Empty username or password", Toast.LENGTH_LONG).show();

                }else {
                    CheckEditTextStatus_L();
                    LoginFunction_L();
                    SQLiteTableBuild_CL();
                    //SQLiteTableBuild_ST();
                    SQLiteTableBuildnew_ST();
                }
            }
        });
        button_forgotpassword_L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittexthome.setText("Forgot Password");
                ssf_signin.setVisibility(View.INVISIBLE);
                ssf_confirmation.setVisibility(View.INVISIBLE);
                ssf_signup.setVisibility(View.INVISIBLE);
                ssf_sendemail.setVisibility(View.INVISIBLE);
                ssf_twopassword.setVisibility(View.INVISIBLE);
                ssf_inputemail.setVisibility(View.VISIBLE);
                button_loginconfirmation_T.setVisibility(View.VISIBLE);
                EditText_Password_S.setText("");
                EditText_Email_S.setText("");
                EditText_Username_S.setText("");
                EditText_Password_L.setText("");
                EdiText_Email_L.setText("");
            }
        });
        button_signupsignin_L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittexthome.setText("Register to");
                ssf_signin.setVisibility(View.INVISIBLE);
                ssf_confirmation.setVisibility(View.INVISIBLE);
                ssf_signup.setVisibility(View.VISIBLE);
                ssf_sendemail.setVisibility(View.INVISIBLE);
                ssf_twopassword.setVisibility(View.INVISIBLE);
                ssf_inputemail.setVisibility(View.INVISIBLE);
                button_signuplogin_S.setVisibility(View.VISIBLE);

                EditText_Password_S.setText("");
                EditText_Email_S.setText("");
                EditText_Username_S.setText("");
            }

        });
        button_loginrecoverconfirmation_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittexthome.setText("Email Verification");
                ssf_signin.setVisibility(View.VISIBLE);
                ssf_confirmation.setVisibility(View.INVISIBLE);
                ssf_signup.setVisibility(View.INVISIBLE);
                ssf_sendemail.setVisibility(View.INVISIBLE);
                ssf_twopassword.setVisibility(View.INVISIBLE);
                ssf_inputemail.setVisibility(View.INVISIBLE);
            }
        });
        button_sendrecoverconfirmation_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EmailCode = ((TextView) findViewById(R.id.EditTextConfirmationRecover_C))
                        .getText().toString();

                TextView recovertxt = ((TextView) findViewById(R.id.EditTextConfirmationRecover_C));
                if (rec2.equals(EmailCode)) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();

                    recovertxt.setText("");

                    ssf_signin.setVisibility(View.INVISIBLE);
                    ssf_confirmation.setVisibility(View.INVISIBLE);
                    ssf_signup.setVisibility(View.INVISIBLE);
                    ssf_sendemail.setVisibility(View.INVISIBLE);
                    ssf_twopassword.setVisibility(View.VISIBLE);
                    ssf_inputemail.setVisibility(View.INVISIBLE);

                } else {
                    Toast.makeText(MainActivity.this, "Enter Correct Code", Toast.LENGTH_LONG).show();
                    recovertxt.setText("");
                    EditText_Password_L.setText("");
                    EdiText_Email_L.setText("");
                }

            }

        });
        button_sendrecoveremail_E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittexthome.setText("Email Verification");
                if (EditText_InputEmail_E.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "enter email address", Toast.LENGTH_SHORT).show();
                    EditText_InputEmail_E.setText("");
                } else {
                    if (EditText_InputEmail_E.getText().toString().trim().matches(Pattern_Email_S)) {
                        Thread thread = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try  {
                                    String toEmails = ((TextView) findViewById(R.id.EditTextInputEmail_E))
                                            .getText().toString();
                                    String fromEmail = From_Email_S;
                                    String fromPassword = From_Password_S;
                                    emailProperties = new Properties();
                                    // emailProperties = System.getProperties();
                                    final String emailPort = "587";// gmail's smtp port
                                    final String smtpAuth = "true";
                                    final String starttls = "true";
                                    final String emailHost = "smtp.gmail.com";

                                    emailProperties.put("mail.smtp.port", emailPort);
                                    emailProperties.put("mail.smtp.auth", smtpAuth);
                                    emailProperties.put("mail.smtp.starttls.enable", starttls);
                                    emailProperties.put("mail.smtp.host", emailHost);
                                    Session session = Session.getInstance(emailProperties,   new javax.mail.Authenticator() {
                                        String fromEmail = From_Email_S;
                                        String fromPassword = From_Password_S;
                                        @Override
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(fromEmail,fromPassword);
                                        }
                                    });

                                    MimeMessage emailmessage = new MimeMessage(session);
                                    emailmessage.setFrom(new InternetAddress(fromEmail));
                                    emailmessage.setRecipients(Message.RecipientType.TO,
                                            InternetAddress.parse(toEmails));
                                    emailmessage.setSubject("Chekkacode");
                                    emailmessage.setContent( "Hello! To recover your account. The code is "+rec2+". Thank you!", "text/html");
                                    Transport.send(emailmessage);

                                    //Your code goes here
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        thread.start();

                        ssf_signin.setVisibility(View.INVISIBLE);
                        ssf_confirmation.setVisibility(View.VISIBLE);
                        ssf_signup.setVisibility(View.INVISIBLE);
                        ssf_sendemail.setVisibility(View.INVISIBLE);
                        ssf_twopassword.setVisibility(View.INVISIBLE);
                        ssf_inputemail.setVisibility(View.INVISIBLE);

                    }
                }
            }
        });
        //   button_loginrecoveremail_E.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //   public void onClick(View view) {
        //       ssf_signin.setVisibility(View.VISIBLE);
        //      ssf_confirmation.setVisibility(View.INVISIBLE);
        //     ssf_signup.setVisibility(View.INVISIBLE);
        //      ssf_sendemail.setVisibility(View.INVISIBLE);
        //     ssf_twopassword.setVisibility(View.INVISIBLE);
        //     ssf_inputemail.setVisibility(View.INVISIBLE);

        //    EditText_Password_S.setText("");
        //   EditText_Email_S.setText("");
        //    EditText_Username_S.setText("");
        //   }
        //  });
        button_signuplogin_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittexthome.setText("Log in to");
                ssf_signin.setVisibility(View.VISIBLE);
                ssf_confirmation.setVisibility(View.INVISIBLE);
                ssf_signup.setVisibility(View.INVISIBLE);
                ssf_sendemail.setVisibility(View.INVISIBLE);
                ssf_twopassword.setVisibility(View.INVISIBLE);
                ssf_inputemail.setVisibility(View.INVISIBLE);
                button_signuplogin_S.setVisibility(View.INVISIBLE);
                EditText_Password_L.setText("");
                EdiText_Email_L.setText("");

            }
        });
        button_signup_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittexthome.setText("Email Verification");
                EditText_Password_L.setText("");
                EdiText_Email_L.setText("");
                if (EditText_Email_S.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "enter email address", Toast.LENGTH_SHORT).show();
                    EditText_Email_S.setText("");
                } else {
                    try {
                        if (EditText_Email_S.getText().toString().trim().matches(Pattern_Email_S)) {
                            Log.i("SendMailActivity", "Send Button Clicked.");
                            Thread thread = new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    try  {
                                        String fromEmail = From_Email_S;
                                        String fromPassword = From_Password_S;
                                        emailProperties = new Properties();
                                        // emailProperties = System.getProperties();
                                        final String emailPort = "587";// gmail's smtp port
                                        final String smtpAuth = "true";
                                        final String starttls = "true";
                                        final String emailHost = "smtp.gmail.com";

                                        emailProperties.put("mail.smtp.port", emailPort);
                                        emailProperties.put("mail.smtp.auth", smtpAuth);
                                        emailProperties.put("mail.smtp.starttls.enable", starttls);
                                        emailProperties.put("mail.smtp.host", emailHost);
                                        Session session = Session.getInstance(emailProperties,   new javax.mail.Authenticator() {
                                            String fromEmail = From_Email_S;
                                            String fromPassword = From_Password_S;
                                            @Override
                                            protected PasswordAuthentication getPasswordAuthentication() {
                                                return new PasswordAuthentication(fromEmail,fromPassword);
                                            }
                                        });

                                        MimeMessage emailmessage = new MimeMessage(session);
                                        String toEmails = ((TextView) findViewById(R.id.EditTextEmail_S))
                                                .getText().toString();
                                        emailmessage.setFrom(new InternetAddress(fromEmail));
                                        emailmessage.setRecipients(Message.RecipientType.TO,
                                                InternetAddress.parse(toEmails));
                                        emailmessage.setSubject("Chekkacode");
                                        emailmessage.setContent("Hello! To complete your registration. Here's your code "+rec+". Thank you!", "text/html");
                                        Transport.send(emailmessage);

                                        //Your code goes here
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            thread.start();
                            ssf_signin.setVisibility(View.INVISIBLE);
                            ssf_confirmation.setVisibility(View.INVISIBLE);
                            ssf_signup.setVisibility(View.INVISIBLE);
                            ssf_sendemail.setVisibility(View.VISIBLE);
                            ssf_twopassword.setVisibility(View.INVISIBLE);
                            ssf_inputemail.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                            EditText_Email_S.setText("");
                        }
                        // Creating SQLite database if dose n't exists

                    } catch (Exception e) {
                        editerror.setText(""+e);
                    }
                }
            }
        });
        button_sendemailregister_SE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("obsess with your body");
                EmailCode_SE = emailtxt.getText().toString();
                System.out.println("rec"+rec);
                System.out.println("email"+EmailCode_SE);

                if (rec.equals(EmailCode_SE)) {
                    System.out.println("entercode"+EmailCode_SE);
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();

                    // Creating SQLite database if dose n't exists
                    SQLiteDataBaseBuild_S();

                    // Creating SQLite table if dose n't exists.
                    SQLiteTableBuild_S();

                    // Checking EditText is empty or Not.
                    CheckEditTextStatus_S();

                    // Method to check Email is already exists or not.
                    CheckingEmailAlreadyExistsOrNot_S();

                    // Empty EditText After done inserting process.
                    EmptyEditTextAfterDataInsert_S();

                    ssf_signin.setVisibility(View.VISIBLE);
                    ssf_confirmation.setVisibility(View.INVISIBLE);
                    ssf_signup.setVisibility(View.INVISIBLE);
                    ssf_sendemail.setVisibility(View.INVISIBLE);
                    ssf_twopassword.setVisibility(View.INVISIBLE);
                    ssf_inputemail.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(MainActivity.this, "Enter Correct Code", Toast.LENGTH_LONG).show();
                    // emailtxt.setText("");
                }
            }
        });
        button_loginsendemailregister_SE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssf_signin.setVisibility(View.VISIBLE);
                ssf_confirmation.setVisibility(View.INVISIBLE);
                ssf_signup.setVisibility(View.INVISIBLE);
                ssf_sendemail.setVisibility(View.INVISIBLE);
                ssf_twopassword.setVisibility(View.INVISIBLE);
                ssf_inputemail.setVisibility(View.INVISIBLE);
                EditText_Password_L.setText("");
                EdiText_Email_L.setText("");
            }
        });
        button_confirmationpassword_T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String NewPassword = ((TextView) findViewById(R.id.EditTextNewPassword_T))
                        .getText().toString();
                String ConPassword = ((TextView) findViewById(R.id.EditTextConPassword_T))
                        .getText().toString();
                String Email = ((TextView) findViewById(R.id.EditTextInputEmail_E))
                        .getText().toString();
                System.out.println("pass - "+NewPassword);
                System.out.println("pass con - "+ConPassword);
                System.out.println("pass email - "+Email);
                try {
                    if (NewPassword.equals(ConPassword)) {

                        sqLiteDatabaseObj = openOrCreateDatabase(mydb.DATABASE_NAME, Context.MODE_PRIVATE, null);

                        mydb.update(NewPassword, Email);

                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                        EditText_InputEmail_E.setText("");
                        EditText_NewPassword_T.setText("");
                        EditText_ConPassword_T.setText("");

                        ssf_signin.setVisibility(View.VISIBLE);
                        ssf_confirmation.setVisibility(View.INVISIBLE);
                        ssf_signup.setVisibility(View.INVISIBLE);
                        ssf_sendemail.setVisibility(View.INVISIBLE);
                        ssf_twopassword.setVisibility(View.INVISIBLE);
                        ssf_inputemail.setVisibility(View.INVISIBLE);

                    } else {
                        Toast.makeText(MainActivity.this, "Not Match", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Try again + "+e, Toast.LENGTH_LONG).show();
                }
            }
        });
        button_loginconfirmation_T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittexthome.setText("Log In");
                ssf_signin.setVisibility(View.VISIBLE);
                ssf_confirmation.setVisibility(View.INVISIBLE);
                ssf_signup.setVisibility(View.INVISIBLE);
                ssf_sendemail.setVisibility(View.INVISIBLE);
                ssf_twopassword.setVisibility(View.INVISIBLE);
                ssf_inputemail.setVisibility(View.INVISIBLE);
                button_loginconfirmation_T.setVisibility(View.INVISIBLE);
            }
        });
    }


    public void LoginFunction_L(){
        System.out.println("holderempty"+Holder_Email_L);
        if(Holder_Empty_L==true) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
//            mydb.select(Email.getText().toString());
            // Adding search email query to cursor.

            Holder_TemporaryPassword_L = mydb.getpassword(Holder_Email_L);
            System.out.println("holdtemp2 "+Holder_TemporaryPassword_L);
            // Closing cursor.

            // Calling method to check final result ..
            CheckFinalResult_L();
            EdiText_Email_L.setText("");
            EditText_Password_L.setText("");

        }
        else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(MainActivity.this,"Please Enter UserName or Password.",Toast.LENGTH_LONG).show();

        }

    }


    // Checking EditText is empty or not.
    public void CheckEditTextStatus_L(){

        // Getting value from All EditText and storing into String Variables.  EditText_Password_L.setText("");
        //                EdiText_Email_L.setText("");

        Holder_Email_L = EdiText_Email_L.getText().toString();
        Holder_Password_L =  EditText_Password_L.getText().toString();
        //theoaesso@gmail.com
        //katkat11.
        // Checking EditText is empty or no using TextUtils.
        if( TextUtils.isEmpty(Holder_Email_L) || TextUtils.isEmpty(Holder_Password_L)){

            Holder_Empty_L = false ;

        }
        else {

            Holder_Empty_L = true ;
        }
    }

    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult_L(){
        System.out.println("holdtmp"+Holder_TemporaryPassword_L);
        System.out.println("holdpass"+Holder_Password_L);
        try {
            if (Holder_TemporaryPassword_L.equalsIgnoreCase(Holder_Password_L)) {

                Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();

                // Going to Dashboard activity after login success message.
                Intent intent = new Intent(MainActivity.this, Chekka.class);

                // Sending Email to Dashboard Activity using intent.
                intent.putExtra(User_Email_L, Holder_Email_L);
                startActivity(intent);

            } else {

                Toast.makeText(MainActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                EditText_Password_L.setText("");
                EdiText_Email_L.setText("");
            }
            Holder_TemporaryPassword_L = "NOT_FOUND";
        }catch (Exception e){
            Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_LONG).show();
            System.out.println("errortalaga"+e);
        }
    }
    public void SQLiteDataBaseBuild_S(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // SQLite table build method.
    public void SQLiteTableBuild_S() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.tbl_users + "(" + SQLiteHelper.userID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.username + " VARCHAR, " + SQLiteHelper.email + " VARCHAR, " + SQLiteHelper.password + " VARCHAR);");

    }






    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase_S(){

        // If editText is not empty then this block will executed.
        if(Holder_Empty_S == true)
        {


            // SQLite query to insert data into table.

            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.tbl_users+"(username,email,password) VALUES('"+EditText_Username_S.getText().toString()+"', '"+EditText_Email_S.getText().toString()+"', '"+EditText_Password_S.getText().toString()+"')";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            System.out.println("editmail "+EditText_Email_S.getText().toString());
            try {
                int hehe = sqLiteHelper.getIdFromClassName(EditText_Email_S.getText().toString());
                String a = "1";
                System.out.println("IDID" + hehe);
                sqLiteHelper.insert_class_details("Everyone", Integer.toString(hehe), a);
            }catch (Exception e){

            }

            // Executing query.

            // Closing SQLite database object.


            // Printing toast message after done inserting.
            Toast.makeText(MainActivity.this,"User Registered Successfully", Toast.LENGTH_LONG).show();
            EditText_Password_S.setText("");
            EditText_Email_S.setText("");
            EditText_Username_S.setText("");

        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(MainActivity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert_S(){

        EditText_Username_S.getText().clear();

        EditText_Email_S.getText().clear();

        EditText_Password_S.getText().clear();

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus_S(){

        // Getting value from All EditText and storing into String Variables.
        Holder_Username_S = EditText_Username_S.getText().toString() ;
        Holder_Email_S = EditText_Email_S.getText().toString();
        Holder_Password_S = EditText_Password_S.getText().toString();

        if(TextUtils.isEmpty(Holder_Username_S) || TextUtils.isEmpty(Holder_Email_S) || TextUtils.isEmpty(Holder_Password_S)){

            Holder_Empty_S = false ;

        }
        else {

            Holder_Empty_S = true ;
        }
    }

    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot_S(){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
//      sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.tbl_users, null, " " + SQLiteHelper.email + "=?", new String[]{Holder_Email_S}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                Holder_TemporaryEmail_S = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult_S();

    }


    // Checking result
    public void CheckFinalResult_S(){

        // Checking whether email is already exists or not.
        if(Holder_TemporaryEmail_S.equalsIgnoreCase("Email Found"))
        {

            // If email is exists then toast msg will display.
            Toast.makeText(MainActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase_S();

        }

        Holder_TemporaryEmail_S = "Not_Found" ;

    }
    public void SQLiteTableBuild_CL() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+mydb.tbl_class+" ("+mydb.classID+" INTEGER PRIMARY KEY, "+mydb.classname+" VARCHAR NOT NULL,"+mydb.UserID+" INTEGER NOT NULL," +
        //"FOREIGN KEY(UserID) REFERENCES tbl_users(userID))");\
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+sqLiteHelper.tbl_class_with_no_of_students+" ("+sqLiteHelper.classID_with_no_of_students+" INTEGER PRIMARY KEY, "+sqLiteHelper.classname_with_no_of_students+" VARCHAR NOT NULL, "+sqLiteHelper.classstudents_with_no_of_students+" VARCHAR NOT NULL, "+sqLiteHelper.UserID_with_no_of_students+" INTEGER NOT NULL, "+ "FOREIGN KEY(UserID_with_no_of_students) REFERENCES tbl_users(UserID))");
        // String CREATE_TABLE_CLASS_WITH_NO_OF_STUDENTS="CREATE TABLE IF NOT EXISTS "+mydb.tbl_class_with_no_of_students+" ("+classID_with_no_of_students+" INTEGER PRIMARY KEY, "+classname_with_no_of_students+", VARCHAR NOT NULL, "+classstudents_with_no_of_students+", INTEGER PRIMARY KEY, "+UserID_with_no_of_students+", INTEGER NOT NULL, "+ "FOREIGN KEY(UserID) REFERENCES tbl_users(userID))";

    }
    //  public void SQLiteTableBuild_ST() {

    //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+mydb.tbl_class+" ("+mydb.classID+" INTEGER PRIMARY KEY, "+mydb.classname+" VARCHAR NOT NULL,"+mydb.UserID+" INTEGER NOT NULL," +
    //"FOREIGN KEY(UserID) REFERENCES tbl_users(userID))");
    // sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+sqLiteHelper.tbl_students+" ("+sqLiteHelper.studentsID+" INTEGER PRIMARY KEY, "+sqLiteHelper.studentsname+" VARCHAR NOT NULL, "+sqLiteHelper.studentsclass+" VARCHAR NOT NULL, "+sqLiteHelper.studentsuserid+" INTEGER NOT NULL, "+ "FOREIGN KEY(studentsuserid) REFERENCES tbl_users(UserID))");
    // String CREATE_TABLE_CLASS_WITH_NO_OF_STUDENTS="CREATE TABLE IF NOT EXISTS "+mydb.tbl_class_with_no_of_students+" ("+classID_with_no_of_students+" INTEGER PRIMARY KEY, "+classname_with_no_of_students+", VARCHAR NOT NULL, "+classstudents_with_no_of_students+", INTEGER PRIMARY KEY, "+UserID_with_no_of_students+", INTEGER NOT NULL, "+ "FOREIGN KEY(UserID) REFERENCES tbl_users(userID))";
    // }
    public void SQLiteTableBuildnew_ST() {

        //sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+mydb.tbl_class+" ("+mydb.classID+" INTEGER PRIMARY KEY, "+mydb.classname+" VARCHAR NOT NULL,"+mydb.UserID+" INTEGER NOT NULL," +
        //"FOREIGN KEY(UserID) REFERENCES tbl_users(userID))");
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+sqLiteHelper.new_tbl_students+" ("+sqLiteHelper.new_studentsID+" INTEGER PRIMARY KEY NOT NULL, "+sqLiteHelper.new_studentsname+" VARCHAR NOT NULL, "+sqLiteHelper.new_studentsclass+" VARCHAR NOT NULL,  "+sqLiteHelper.new_studentsnumber+" INTEGER NOT NULL,"+sqLiteHelper.new_studentsuserid+" INTEGER NOT NULL, "+ "FOREIGN KEY(new_studentsuserid) REFERENCES tbl_users(UserID))");
        // String CREATE_TABLE_CLASS_WITH_NO_OF_STUDENTS="CREATE TABLE IF NOT EXISTS "+mydb.tbl_class_with_no_of_students+" ("+classID_with_no_of_students+" INTEGER PRIMARY KEY, "+classname_with_no_of_students+", VARCHAR NOT NULL, "+classstudents_with_no_of_students+", INTEGER PRIMARY KEY, "+UserID_with_no_of_students+", INTEGER NOT NULL, "+ "FOREIGN KEY(UserID) REFERENCES tbl_users(userID))";
    }

}