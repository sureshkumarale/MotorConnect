package com.sureshale.motorconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

/**
 * Created by sureshale on 21-09-2017.
 */

public class LoginActivity extends AppCompatActivity {

    TextInputLayout layoutEmailPhone, layoutPassword;
    EditText inputEmailPhone, inputPassword;
    Button loginButton;
    TextView registerNewUser;
    Toolbar toolbar;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layoutEmailPhone = (TextInputLayout)findViewById(R.id.login_layout_email_number);
        layoutPassword = (TextInputLayout)findViewById(R.id.login_layout_password);

        inputEmailPhone = (EditText)findViewById(R.id.login_email_number);
        inputPassword = (EditText)findViewById(R.id.login_password);

        toolbar = (Toolbar) findViewById(R.id.generic_appbar);
        toolbar.setTitle("Login");
        registerNewUser = (TextView)findViewById(R.id.login_register_link);
        loginButton = (Button)findViewById(R.id.login_button);
        databaseHelper = new DatabaseHelper(this);



        registerNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        login();
    }

    private void login(){

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = inputEmailPhone.getText().toString().trim();
                    System.out.println("Login Text::::"+str);
                    Cursor result = databaseHelper.userValidation(str);
                    if (result.getCount()==0)
                        Toast.makeText(LoginActivity.this, "Invalid User !!", Toast.LENGTH_SHORT).show();
                    else {
                        while (result.moveToNext()){
                            if ((str.contentEquals(result.getString(1)) ||
                                    str.contentEquals(result.getString(2))) &&
                                            inputPassword.getText().toString().contentEquals(result.getString(3))) {
                                System.out.println("Column 1 :::"+result.getString(1));
                                System.out.println("Column 2 :::"+result.getString(2));
                                System.out.println("Column 3 :::"+result.getString(3));
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            });

    }
}
