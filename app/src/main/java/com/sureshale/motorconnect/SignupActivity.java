package com.sureshale.motorconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

/**
 * Created by sureshale on 21-09-2017.
 */

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private Vibrator vibrator;
    Animation animShake;
    TextInputLayout layoutName, layoutEmail, layoutPhone, layoutPassword, layoutConfirmPassword;
    EditText inputName, inputEmail, inputPhone, inputPassword, inputConfirmPassword;
    Button registerBtn;
    DatabaseHelper databaseHelper;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        layoutName = (TextInputLayout)findViewById(R.id.signup_input_layout_name);
        layoutEmail = (TextInputLayout)findViewById(R.id.signup_input_layout_email);
        layoutPhone = (TextInputLayout)findViewById(R.id.signup_input_layout_phnumber);
        layoutPassword = (TextInputLayout)findViewById(R.id.signup_input_layout_password);
        layoutConfirmPassword = (TextInputLayout)findViewById(R.id.signup_input_layout_confirmPassword);

        inputName = (EditText)findViewById(R.id.signup_input_name);
        inputEmail = (EditText)findViewById(R.id.signup_input_email);
        inputPhone = (EditText)findViewById(R.id.signup_input_phnumber);
        inputPassword = (EditText)findViewById(R.id.signup_input_password);
        inputConfirmPassword = (EditText)findViewById(R.id.signup_input_confirmPassword);

        toolbar = (Toolbar) findViewById(R.id.generic_appbar);
        registerBtn = (Button)findViewById(R.id.register_button);

        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        databaseHelper = new DatabaseHelper(this);

        addNewUserData();
    }

    public void addNewUserData(){

            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateForm()==true) {
                        boolean isInserted = databaseHelper.insert_newUser_data(inputName.getText().toString().trim(),
                                inputEmail.getText().toString().trim(),
                                inputPhone.getText().toString().trim(),
                                inputPassword.getText().toString().trim());
                        if (isInserted = true) {
                            Toast.makeText(SignupActivity.this, "User Details added Successfully", Toast.LENGTH_SHORT).show();
                            inputName.setText("");
                            inputEmail.setText("");
                            inputPhone.setText("");
                            inputPassword.setText("");

                            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(SignupActivity.this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    }
                }
//                    else
//                        Toast.makeText(SignupActivity.this, "Please Check Entered Data", Toast.LENGTH_SHORT).show();
            });

    }


//
//    Form Validation

    private boolean validateForm(){

        if(!checkName()){
            inputName.setAnimation(animShake);
            inputName.startAnimation(animShake);
            vibrator.vibrate(120);
            return false;
        }
        if(!checkEmail()){
            inputEmail.setAnimation(animShake);
            inputEmail.startAnimation(animShake);
            vibrator.vibrate(120);
            return false;
        }
        if(!checkPhone()){
            inputPhone.setAnimation(animShake);
            inputPhone.startAnimation(animShake);
            vibrator.vibrate(120);
            return false;
        }
        if(!checkPassword()){
            inputPassword.setAnimation(animShake);
            inputPassword.startAnimation(animShake);
            vibrator.vibrate(120);
            return false;
        }
        if(!checkConfirmPassword()){
            inputConfirmPassword.setAnimation(animShake);
            inputConfirmPassword.startAnimation(animShake);
            vibrator.vibrate(120);
            return false;
        }
        if(!inputConfirmPassword.getText().toString().contentEquals(inputPassword.getText().toString())){
            inputName.setAnimation(animShake);
            inputName.startAnimation(animShake);
            vibrator.vibrate(120);
            return false;
        }
        layoutName.setErrorEnabled(false);
        layoutEmail.setErrorEnabled(false);
        layoutPhone.setErrorEnabled(false);
        layoutPassword.setErrorEnabled(false);
        layoutConfirmPassword.setErrorEnabled(false);

        Toast.makeText(this, "Registered Successfully !!", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean checkName(){
        if (inputName.getText().toString().trim().isEmpty()){
            layoutName.setErrorEnabled(true);
            layoutName.setError("Please Enter a Name");
            inputName.setError("Valid input required !");
            return false;
        }
        layoutName.setErrorEnabled(false);
        return true;
    }

    private boolean checkEmail(){
        String email = inputEmail.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Please Enter Email");
            inputEmail.setError("Valid input required !");
            return false;
        }
        layoutEmail.setErrorEnabled(false);
        return true;
    }

    private boolean checkPhone(){
        if (inputPhone.getText().toString().trim().isEmpty()){
            layoutPhone.setErrorEnabled(true);
            layoutPhone.setError("Please Enter Phone Number");
            inputPhone.setError("Valid input required !");
            return false;
        }
        layoutPhone.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword(){
        if (inputPassword.getText().toString().trim().isEmpty()){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Please Enter Password");
            inputPassword.setError("Valid input required !");
            return false;
        }
        layoutPassword.setErrorEnabled(false);
        return true;
    }

    private boolean checkConfirmPassword(){
        if (inputConfirmPassword.getText().toString().trim().isEmpty() ||
                !inputConfirmPassword.getText().toString().contentEquals(inputPassword.getText().toString())){
            layoutName.setErrorEnabled(true);
            layoutName.setError("Please Enter Confirm Password");
            inputName.setError("Enter same as password");
            return false;
        }
        layoutConfirmPassword.setErrorEnabled(false);
        return true;
    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
