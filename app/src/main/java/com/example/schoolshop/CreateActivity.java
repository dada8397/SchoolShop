package com.example.schoolshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateActivity extends AppCompatActivity {

    //private LinearLayout LL;
    private ImageView imageView1;
    private ImageView imageView2;
    private OkHttpClient httpClient;
    //private OkHttpClient httpClient;
    //private Button submit;
    //private int REQUEST_CODE = 1;
    //public static final int GET_FROM_GALLERY = 3;

    public String postUrl= "http://merry.ee.ncku.edu.tw:10000/createStuff/";
    public String postBody="{\"name\": \"500G SSD\", \"owner\": \"1\", \"description\": \"9成新，用過一個月，附發票\", \"img_url\": {\"1\": \"https://i.imgur.com/3JIbb5O.jpg\", \"2\": \"https://i.imgur.com/OfGYgqk.png\", \"price\": \"1000\"}";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        try {
            postRequest(postUrl,postBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //初始化
        //LL =(LinearLayout)this.findViewById(R.id.LL);
        imageView1 = (ImageView) findViewById(R.id.photo1_imageView);
        imageView2 = (ImageView) findViewById(R.id.photo2_imageView);
        //submit = (Button) findViewById(R.id.submit_button);




        //擷取照片按鈕監聽器
        imageView1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //讀取圖片
                Intent intent = new Intent();
                //開啟Pictures畫面Type設定為image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT這個Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //取得照片後返回此畫面

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        imageView2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //讀取圖片
                Intent intent = new Intent();
                //開啟Pictures畫面Type設定為image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT這個Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //取得照片後返回此畫面
                //onActivityResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("456", "eeeeeeeeeeeee");
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.e("123", "fffffffffffffffff");

            Uri uri = data.getData();
            try {
                if (requestCode == 1) {
                    Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView1.setImageBitmap(bitmap1);
                }
                else if (requestCode == 2) {
                    Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView2.setImageBitmap(bitmap2);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
