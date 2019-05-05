package com.codesroots.osamaomar.Grz.presentationn.screens.feature.chating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.codesroots.osamaomar.Grz.R;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.chating.adapters.ChatAllListAdapter;

public class ChatingActivity extends AppCompatActivity {


    RecyclerView contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chating);
        contacts = findViewById(R.id.contacts);
        contacts.setAdapter(new ChatAllListAdapter(this));


    }
}
