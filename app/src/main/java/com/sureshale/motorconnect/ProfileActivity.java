package com.sureshale.motorconnect;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

public class ProfileActivity extends BaseActivity {

    EditText profile_userName;
    TextView profile_phNumber, profile_email;
    Button profile_updateBtn;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_userName = (EditText)findViewById(R.id.profile_userName);
        profile_phNumber = (TextView)findViewById(R.id.profile_phNumber);
        profile_email = (TextView)findViewById(R.id.profile_email);
        profile_updateBtn = (Button)findViewById(R.id.profile_updateButton);
        databaseHelper = new DatabaseHelper(this);

        useToolbar("Profile");

        final String userEmail = getIntent().getExtras().getString("userEmail");
        profile_email.setText(userEmail);

        Cursor result = databaseHelper.getUserData(userEmail);
        if (result.getCount()==0)
            Toast.makeText(ProfileActivity.this, "Invalid User !!", Toast.LENGTH_SHORT).show();
        else {
            while (result.moveToNext()){

                profile_phNumber.setText(result.getString(2));
                profile_userName.setText(result.getString(0));
            }
        }

        profile_updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUserName = profile_userName.getText().toString();
                databaseHelper.updateUserData(userEmail,newUserName);
                Toast.makeText(ProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed(){

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
