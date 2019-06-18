package com.example.schoolshop;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity {

    public String postUrl= "http://merry.ee.ncku.edu.tw:10000/getStuffs/";
    public String postBody="{\"owner\": \"1\"}";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private TextView userIDTextView;
    private StuffAdapter stuffAdapter;
    private ListView stuffListView;
    private int count = 0;

    private String userID;

    private String responseString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        stuffAdapter = new StuffAdapter(this);
        stuffListView = findViewById(R.id.stuff_list);
        stuffListView.setAdapter(stuffAdapter);

        Intent intent = this.getIntent();
        userID = intent.getStringExtra("UserID");
        String userIDstr = "User ID: " + userID;

        userIDTextView = findViewById(R.id.textview_userid);
        userIDTextView.setText(userIDstr);

        postBody="{\"owner\": \"" + userID + "\"}";
        try {
            postRequest(postUrl, postBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        stuffListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                detailOnClick(view);
//            }
//        });
    }

    void postRequest(String postUrl,String postBody) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(postBody, JSON);

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                try {
                    JSONObject obj = new JSONObject(json);
                    JSONArray arr = obj.getJSONArray("allStuffs");

                    for (int i=count; i<arr.length(); i++) {
                        JSONObject all = arr.getJSONObject(i);
                        final Stuff stuff = new Stuff(
                                all.getString("id"),
                                all.getString("name"),
                                all.getString("owner"),
                                all.getString("description"),
                                all.getString("img_url"),
                                all.getString("price"),
                                all.getString("status"));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stuffAdapter.add(stuff);
                                stuffListView.setSelection(stuffListView.getCount()-1);
                            }
                        });
                    }
                    count = arr.length();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void createOnClick(View v) {
        Intent intent = new Intent(ListActivity.this, CreateActivity.class);
        intent.putExtra("UserID", userID);
        startActivity(intent);
    }

    public void chatOnClick(View v) {
        Intent intent = new Intent(ListActivity.this, ChatActivity.class);
        intent.putExtra("UserID", userID);
        startActivity(intent);
    }

    public void detailOnClick(View v) {
        Intent intent = new Intent(ListActivity.this, DetailActivity.class);
        intent.putExtra("UserID", userID);
        startActivity(intent);
    }

}
