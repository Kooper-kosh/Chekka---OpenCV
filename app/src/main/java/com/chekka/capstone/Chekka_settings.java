package com.chekka.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Chekka_settings extends AppCompatActivity  {

    //FOR TEXTVIEW
    TextView button_logout_LO, button_sendfeedback_LO, button_settings_LO, textView_username_LO, textView_gmail_LO,
            button_confirmpassword_SET;

    //FOR IMAGE VIEW
    ImageView BackButton;

    //FOR EDIT TEXT
    EditText EditText_NewPassword_SET, EditText_ConPassword_SET;

    // FOR LAYOUT
    FrameLayout layout_twopassword_SET;
    RelativeLayout layout_logout_LO;

    //FOR DATABASE
    SQLiteHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //
        mydb = new SQLiteHelper(this);
        setContentView(R.layout.chekka_settings);

        // IMAGEVIEW
        BackButton = (ImageView) findViewById(R.id.BackLogout_LO);
        // TEXT VIEW
        textView_username_LO = (TextView) findViewById(R.id.TextViewTUserName_LO);
        textView_gmail_LO = (TextView) findViewById(R.id.TextViewTGMail_LO);
        button_logout_LO = (TextView) findViewById(R.id.ButtonTLogOut_LO);
        button_sendfeedback_LO = (TextView) findViewById(R.id.ButtonTSendFeedBack_LO);
        button_settings_LO = (TextView) findViewById(R.id.ButtonTSettings_LO);
        button_confirmpassword_SET = (TextView) findViewById(R.id.ButtonMConfirmPassword_SET);

        // EDITTEXT
        EditText_NewPassword_SET = (EditText) findViewById(R.id.EditTextNewPassword_SET);
        EditText_ConPassword_SET = (EditText) findViewById(R.id.EditTextConPassword_SET);

        // LAYOUTS
        layout_twopassword_SET = (FrameLayout) findViewById(R.id.layout_twopassword_SET);
        layout_logout_LO = (RelativeLayout) findViewById(R.id.layout_logout_LO);
        // METHOD
        textView_gmail_LO.setText(Chekka.Holder_Email_LO);

        // ACTION CLICK
        button_logout_LO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chekka_settings.this, MainActivity.class);
                startActivity(intent);

            }
        });
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        button_settings_LO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_twopassword_SET.setVisibility(View.VISIBLE);
                layout_logout_LO.setVisibility(View.INVISIBLE);
                System.out.println("binhi");
            }
        });
        button_confirmpassword_SET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditText_ConPassword_SET.getText().toString().equals(EditText_NewPassword_SET.getText().toString())) {
                    System.out.println(mydb.update(EditText_NewPassword_SET.getText().toString(), Chekka.Holder_Email_LO));

                    Toast.makeText(Chekka_settings.this, "Success", Toast.LENGTH_LONG).show();

                    EditText_NewPassword_SET.setText("");
                    EditText_ConPassword_SET.setText("");

                    layout_twopassword_SET.setVisibility(View.INVISIBLE);
                    layout_logout_LO.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(Chekka_settings.this, "Not match", Toast.LENGTH_LONG).show();
                    EditText_NewPassword_SET.setText("");
                    EditText_ConPassword_SET.setText("");
                    layout_twopassword_SET.setVisibility(View.VISIBLE);
                    layout_logout_LO.setVisibility(View.INVISIBLE);
                }

            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });
    }
}