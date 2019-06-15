package com.example.schoolshop;

import android.os.Bundle;
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
    public String postBody=
            "{\"src\": \"1\"," +
            "\"dst\": \"2\"," +
            "\"content\": \"你好\"}";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private EditText editText;
    private MessageAdapter messageAdapter;
    private ListView messagesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        editText = (EditText) findViewById(R.id.editText);
        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        try {
            getRequest(getUrl);
            //postRequest(postUrl,postBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void chatOnClick(View view) {
        EditText message = (EditText)findViewById(R.id.editText);

        String msg = message.getText().toString();

        String postBody =
                "{\"src\": \"1\"," +
                        "\"dst\": \"2\"," +
                        "\"content\": \"" + msg + "\"}";

        try {
            postRequest(postUrl, postBody);
            getRequest(getUrl);
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
                //Log.d("TAG", response.body().string());
                String json = response.body().string();
                Log.e("TAG", json);
                try {
                    JSONObject obj = new JSONObject(json);
                    JSONArray arr = obj.getJSONArray("allMsgs");


                    for (int i=0; i<arr.length(); i++) {
                        JSONObject all = arr.getJSONObject(i);
                        final Message message = new Message(all.getString("content"), all.getString("src").equals("1"));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messageAdapter.add(message);
                                messagesView.setSelection(messagesView.getCount()-1);
                            }
                        });
                        /*
                        if (all.getString("src").equals("1")) {
                            Log.e("content", all.getString("content"));

                        } else {
                            Log.d("content", all.getString("content"));
                        }*/
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

}