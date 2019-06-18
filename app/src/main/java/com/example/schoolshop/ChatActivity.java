package com.example.schoolshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {

    public String postUrl= "http://merry.ee.ncku.edu.tw:10000/sendMsg/";
    public String getUrl= "http://merry.ee.ncku.edu.tw:10000/getMsgs/";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private EditText editText;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    private String userID;
    private String stuffID;
    private final Handler handler = new Handler();
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = this.getIntent();
        userID = intent.getStringExtra("UserID");
        //stuffID = intent.getStringExtra("StuffID");
        stuffID = "1";

        editText = (EditText) findViewById(R.id.editText);
        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        try {
            getRequest(getUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.removeCallbacks(runnable);
        handler.post(runnable);

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try{
                getRequest(getUrl);
            } catch (Exception e){
            }
            handler.postDelayed(this, 1000);
        }
    };

    public void chatOnClick(View view) {
        EditText message = (EditText)findViewById(R.id.editText);

        String msg = message.getText().toString();
        String src;
        String dst;
        if (userID.equals("1")) {
            src = "1";
            dst = "2";
        } else {
            src = "2";
            dst = "1";
        }


        String postBody =
                "{\"src\": \"" + src + "\"," +
                        "\"dst\": \"" + dst + "\"," +
                        "\"stuff_id\": \"" + stuffID + "\"," +
                        "\"content\": \"" + msg + "\"}" ;

        try {
            postRequest(postUrl, postBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                //Log.d("post",response.body().string());
            }
        });
    }

    void getRequest(String getUrl) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(getUrl)
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
                    JSONArray arr = obj.getJSONArray("allMsgs");

                    for (int i=count; i<arr.length(); i++) {
                        JSONObject all = arr.getJSONObject(i);
                        final Message message = new Message(all.getString("content"), all.getString("src").equals(userID));
                        final String id = all.getString("stuff_id");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (id.equals(stuffID)){
                                    messageAdapter.add(message);
                                    messagesView.setSelection(messagesView.getCount()-1);
                                }
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

}