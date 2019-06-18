package com.example.schoolshop;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.ByteArrayOutputStream;
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
    private TextView id;
    private EditText item;
    private EditText price;
    private EditText description;
    private String get_userid;
    private String get_item;
    private String get_price;
    private String get_description;
    private String userID;
    private final int REQUEST_PICK_IMAGE = 1;


    public String postUrl = "http://merry.ee.ncku.edu.tw:10000/createStuff/";
    public String postBody = "{\"name\": \"500G SSD\", \"owner\": \"1\", \"description\": \"9成新，用過一個月，附發票\", \"img_url\": {\"1\": \"https://i.imgur.com/3JIbb5O.jpg\", \"2\": \"https://i.imgur.com/OfGYgqk.png\"}, \"price\": \"1000\"}";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Intent intent = this.getIntent();
        userID = intent.getStringExtra("UserID");

        image1 = (ImageView) findViewById(R.id.photo1_imageView);
        image2 = (ImageView) findViewById(R.id.photo2_imageView);
        submit = (Button) findViewById(R.id.submit_button);
        id = (TextView) findViewById(R.id.id_tv);
        item = (EditText) findViewById(R.id.item_editText);
        price = (EditText) findViewById(R.id.price_editText);
        description = (EditText) findViewById(R.id.description_editText);

        id.setText(userID);


       /* //擷取照片按鈕監聽器
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
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
            }


        });*/

        Picasso.get().load("https://i.imgur.com/3JIbb5O.jpg").into(image1);
        Picasso.get().load("https://i.imgur.com/OfGYgqk.png").into(image2);


        submit.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                get_userid = id.getText().toString();
                get_item = item.getText().toString();
                get_price = price.getText().toString();
                get_description = description.getText().toString().replace("\n", "\\n");

                postBody = "{\"name\": \"" + get_item + "\", \"owner\": \"" + get_userid + "\", \"description\": \"" + get_description + "\", \"img_url\": {\"1\": \"https://i.imgur.com/3JIbb5O.jpg\", \"2\": \"https://i.imgur.com/OfGYgqk.png\"}, \"price\": \"" + get_price + "\"}";

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


    void postRequest(String postUrl, String postBody) throws IOException {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(postBody, JSON);
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("here", e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("here", response.body().string());
            }
        });
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_PICK_IMAGE:
                getSelectImage(data); //處理選取圖片的程式 寫在後面
                break;
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {


            Uri uri = data.getData();
            try {
                if (requestCode == 1) {
                    Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    image1.setImageBitmap(bitmap1);
                } else if (requestCode == 2) {
                    Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    image2.setImageBitmap(bitmap2);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*private void getSelectImage(Intent data) {
        //從 onActivityResult 傳入的data中，取得圖檔路徑
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        if (cursor == null) {
            return;
        }
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imagePath = cursor.getString(columnIndex);
        cursor.close();
        //Log.d("editor","image:"+imagePath);

        //使用圖檔路徑產生調整過大小的Bitmap
        Bitmap bitmap = getResizedBitmap(imagePath); //程式寫在後面

        //將 Bitmap 轉為 base64 字串
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapData = bos.toByteArray();
        String imageBase64 = Base64.encodeToString(bitmapData, Base64.DEFAULT);
        //Log.d("editor",imageBase64);

        //將圖檔上傳至 Imgur，將取得的圖檔網址插入文字輸入框
//      imgurUpload(imageBase64); //程式寫在後面
    }*/

    /*private Bitmap getResizedBitmap(String imagePath) {
        final int MAX_WIDTH = 1024; // 新圖的寬要小於等於這個值

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //只讀取寬度和高度
        BitmapFactory.decodeFile(imagePath, options);
        int width = options.outWidth, height = options.outHeight;

        // 求出要縮小的 scale 值，必需是2的次方，ex: 1,2,4,8,16...
        int scale = 1;
        while (width > MAX_WIDTH * 2) {
            width /= 2;
            height /= 2;
            scale *= 2;
        }

        // 使用 scale 值產生縮小的圖檔
        BitmapFactory.Options scaledOptions = new BitmapFactory.Options();
        scaledOptions.inSampleSize = scale;
        Bitmap scaledBitmap = BitmapFactory.decodeFile(imagePath, scaledOptions);

        float resize = 1; //縮小值 resize 可為任意小數
        if (width > MAX_WIDTH) {
            resize = ((float) MAX_WIDTH) / width;
        }

        Matrix matrix = new Matrix(); // 產生縮圖需要的參數 matrix
        matrix.postScale(resize, resize); // 設定寬與高的縮放比例

        // 產生縮小後的圖
        return Bitmap.createBitmap(scaledBitmap, 0, 0, width, height, matrix, true);
    }*/

    /*private void imgurUpload(final String image) { //插入圖片
        String urlString = "https://imgur-apiv3.p.mashape.com/3/image/";
        String mashapeKey = "3e2d3a6799msh2933e1ccd123cb5p18a23djsn03d073e8c889"; //設定自己的 Mashape Key
        String clientId = "1035827e94db77b"; //設定自己的 Clinet ID
        String titleString = ""; //設定圖片的標題


        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Mashape-Key", mashapeKey);
        client.addHeader("Authorization", "Client-ID " + clientId);
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        RequestParams params = new RequestParams();
        params.put("title", titleString);
        params.put("image", image);
        client.post(urlString, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (!response.optBoolean("success") || !response.has("data")) {
                    Log.d("editor", "response: " + response.toString());
                    return;
                }
                JSONObject data = response.optJSONObject("data");
                //Log.d("editor","link: "+data.optString("link"));
                String link = data.optString("link", "");
                int width = data.optInt("width", 0);
                int height = data.optInt("height", 0);
                String bbcode = "[img=" + width + "x" + height + "]" + link + "[/img]";
                //將文字插入輸入框的程式 寫在後面
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject error) {

                Log.d("editor", "error: " + error.toString());
                /*if (error.has("data")) {
                    JSONObject data = error.optJSONObject("data");
                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setTitle("Error: " + statusCode + " " + e.getMessage())
                            .setMessage(data.optString("error", ""))
                            .setPositiveButton("確定", null)
                            .create();
                    dialog.show();
                }
            }
        });

    }*/
}