package com.example.schoolshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerUsers;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerUsers = findViewById(R.id.spinner_users);
        final String[] userIDs = {"1", "2"};
        ArrayAdapter<CharSequence> userList = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.users,
                android.R.layout.simple_spinner_dropdown_item);
        spinnerUsers.setAdapter(userList);
        spinnerUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userID = userIDs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                userID = "1";
            }
        });
    }

    public void detailOnClick(View v) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }

    public void loginOnClick(View v) {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putExtra("UserID", userID);
        startActivity(intent);
    }
}
