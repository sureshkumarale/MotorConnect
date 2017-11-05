package com.sureshale.motorconnect;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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

        viewPager = (ViewPager) findViewById(R.id.documents_view);
        adapter = new CustomSwipeAdapter(this,regNumber,documentTypePosition);
        if(adapter.getCount()==0){
            Toast.makeText(this, "No images found", Toast.LENGTH_SHORT).show();
        }
        else {
            viewPager.setAdapter(adapter);
        }
    }
}
