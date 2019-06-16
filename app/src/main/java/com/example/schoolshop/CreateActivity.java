package com.example.schoolshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class CreateActivity extends AppCompatActivity {

    private ImageView image1;
    private ImageView image2;
    private Button submit;
    private EditText item;
    private EditText price;
    private EditText description;
    private String get_item;
    private String get_price;
    private String get_description;


    public String postUrl= "http://merry.ee.ncku.edu.tw:10000/createStuff/";
    public String postBody="{\"name\": \"500G SSD\", \"owner\": \"1\", \"description\": \"9成新，用過一個月，附發票\", \"img_url\": {\"1\": \"https://i.imgur.com/3JIbb5O.jpg\", \"2\": \"https://i.imgur.com/OfGYgqk.png\"}, \"price\": \"1000\"}";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        //初始化
        image1 = (ImageView) findViewById(R.id.photo1_imageView);
        image2 = (ImageView) findViewById(R.id.photo2_imageView);
        submit = (Button) findViewById(R.id.submit_button);

        item = (EditText) findViewById(R.id.item_editText);
        price = (EditText) findViewById(R.id.price_editText);
        description = (EditText) findViewById(R.id.description_editText);




        //擷取照片按鈕監聽器
        image1.setOnClickListener(new Button.OnClickListener() {
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
        image2.setOnClickListener(new Button.OnClickListener() {
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



        /*//擷取照片按鈕監聽器
        image1.setOnClickListener(new Button.OnClickListener() {
            String url1 = "i.imgur.com/3JIbb5O.jpg";


            @Override
            public void onClick(View v) {
                //Drawable img = LoadImageFromWebOperations(url1);
                //image1.setImageDrawable(img);
                URL url = null;
                try {
                    url = new URL(url1);
                }
                catch (MalformedURLException e) {}
                catch (IOException e) {}
                try {
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    image1.setImageBitmap(bmp);
                }
                catch (MalformedURLException e) {}
                catch (IOException e) {}

            }
        });
        image2.setOnClickListener(new Button.OnClickListener() {

            String url2 = "https://i.imgur.com/OfGYgqk.png";
            @Override
            public void onClick(View v) {
                //LoadImageFromWebOperations(url2);
                //Bitmap bmp = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
                //image2.setImageDrawable(LoadImageFromWebOperations(url2));
                image2.setImageDrawable(LoadImageFromWebOperations(url2));

            }


        });*/

        submit.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                get_item = item.getText().toString();
                get_price = price.getText().toString();
                get_description = description.getText().toString();

                postBody = "{\"name\": \"" + get_item + "\", \"owner\": \"1\", \"description\": \"" + get_description + "\", \"img_url\": {\"1\": \"https://i.imgur.com/3JIbb5O.jpg\", \"2\": \"https://i.imgur.com/OfGYgqk.png\"}, \"price\": \"" + get_price + "\"}";

                try {
                    postRequest(postUrl, postBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(CreateActivity.this, DetailActivity.class);
                intent.putExtra("ITEM", postBody);
                startActivity(intent);
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
                Log.d("here", response.body().string());
            }
        });
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;

        } catch (Exception e) {
            return null;
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.e("456", "eeeeeeeeeeeee");
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            //Log.e("123", "fffffffffffffffff");

            Uri uri = data.getData();
            try {
                if (requestCode == 1) {
                    Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    image1.setImageBitmap(bitmap1);
                }
                else if (requestCode == 2) {
                    Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    image2.setImageBitmap(bitmap2);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

