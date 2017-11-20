package com.sureshale.motorconnect;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.sureshale.motorconnect.R;

/**
 * Created by sureshale on 02-11-2017.
 */

public class DocumentsViewPagerActivity extends AppCompatActivity {

    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_view_pager);


        int documentTypePosition = getIntent().getIntExtra("documentType",-1);
        String regNumber = getIntent().getStringExtra("regNumber");
        String intentString = getIntent().getStringExtra("yearOfManu");

        viewPager = (ViewPager) findViewById(R.id.documents_view);
        adapter = new CustomSwipeAdapter(this,regNumber,documentTypePosition,intentString);
        if(adapter.getCount()==0){
            Snackbar snackbar = Snackbar.make(viewPager,"No images found !",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {
            viewPager.setAdapter(adapter);
        }
    }



}
