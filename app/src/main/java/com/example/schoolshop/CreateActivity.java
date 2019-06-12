package com.example.schoolshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CreateActivity extends AppCompatActivity {

    //private LinearLayout LL;
    private ImageView imageView1;
    private ImageView imageView2;
    //private Button button;
    private int REQUEST_CODE = 1;
    public static final int GET_FROM_GALLERY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //初始化
        //LL =(LinearLayout)this.findViewById(R.id.LL);
        imageView1 = (ImageView) findViewById(R.id.photo1_imageView);
        imageView2 = (ImageView) findViewById(R.id.photo2_imageView);
        //button = (Button) findViewById(R.id.choose_button);




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
