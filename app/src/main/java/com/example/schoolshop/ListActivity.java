package com.example.schoolshop;

import android.content.Context;
import android.content.Intent;
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

import java.io.IOException;

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
    private ListView listView;

    private String commodities[] = {
            "CONTACTS多功能真皮鑰匙包",
            "BAGMIO duet 牛皮鑰匙零錢包",
            "TOMICA 多美小汽車NO.039 奧迪R8"
    };

    private String descriptions[] = {
            "圓形滑動古銅色六匙掛勾, 義大利頭層油蠟皮&Nappa軟皮, 精緻手工在乎每個細節",
            "頭層牛皮材質 黑藍雙色二重奏, 雙卡設計 零錢包加厚側寬易於拿取, 附原廠包裝盒",
            "創立於1970年的TOMICA多美小汽車, 依據真車按60-70%比例縮小製成, 共120款車種等你來收集"
    };

    private int images[] = {
            R.drawable.commodity_01,
            R.drawable.commodity_02,
            R.drawable.commodity_03,
    };

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = this.getIntent();
        userID = intent.getStringExtra("UserID");
        String userIDstr = "User ID: " + userID;

        userIDTextView = findViewById(R.id.textview_userid);
        userIDTextView.setText(userIDstr);

        listView = findViewById(R.id.listView);

        try {
            postRequest(postUrl,postBody);
            Log.d("TAG", "post successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

        MyAdapter adapter = new MyAdapter(this, commodities, descriptions, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                detailOnClick(view);
            }
        });
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
                Log.d("TAG",response.body().string());
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String comTitle[];
        String comDescription[];
        int comImage[];

        MyAdapter (Context c, String title[], String description[], int image[]) {
            super(c, R.layout.item, R.id.textView1, title);
            this.context = c;
            this.comTitle = title;
            this.comDescription = description;
            this.comImage = image;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item = layoutInflater.inflate(R.layout.item, parent, false);
            ImageView images = item.findViewById(R.id.image);
            TextView title = item.findViewById(R.id.textView1);
            TextView description = item.findViewById(R.id.textView2);

            images.setImageResource(comImage[position]);
            title.setText(comTitle[position]);
            description.setText(comDescription[position]);

            return item;
        }
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
