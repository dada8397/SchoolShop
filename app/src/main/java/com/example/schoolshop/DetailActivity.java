package com.example.schoolshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {

    public String postUrl = null;
    public String postBody = null;
    public String UserID = null;
    public String Item = null;

    //Json
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public JSONObject J;
    //public Object jsonOb;

    //View
    private Button buyer;
    private Button sell;
    private Button reject;
    private TextView status;
    private TextView nameText;
    private TextView priceText;
    private TextView descriptionText;
    private ImageView image1;
    private ImageView image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Intent intent = this.getIntent();
        //UserID = intent.getStringExtra("UserID");
        //Item = intent.getStringExtra("item");
        UserID = "1";
        Item = "{\"id\": 2, \"name\": \"aaa\",\"owner\": \"2\",\"description\": \"bbb\",\"img_url\": {\"1\":\"http://i.imgur.com/A1WNjc2.jpg\",\"2\":\"http://i.imgur.com/9kXHdt0.jpg\"},\"price\": \"100\",\"status\": \"selling\",\"created_at\": \"1560442508\",\"updated_at\": \"1560442508\"}";
        try {
            J = new JSONObject(Item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Object itemOwner = "";
        try {
            itemOwner = J.get("owner");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postUrl = "http://merry.ee.ncku.edu.tw:10000/getStuffs/";
        postBody ="{\"owner\": \"1\"}";

        try {
            postRequest(postUrl, postBody);
            changeSite(UserID, itemOwner.toString());
            changeText();
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
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("TAG", Objects.requireNonNull(response.body()).string());
                Log.d("UserID", UserID);
            }
        });
    }

    void changeSite(String UserID, String owner) {
        sell = findViewById(R.id.sell_button);
        reject = findViewById(R.id.reject_button);
        status = findViewById(R.id.status_textview);
        buyer = findViewById(R.id.buyer_button);
        if (UserID.equals(owner)) {
            Object itemStatus = null;
            try {
                itemStatus = J.get("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            assert itemStatus != null;
            if (itemStatus.equals("selling")) {
                sell.setVisibility(View.INVISIBLE);
                reject.setVisibility(View.INVISIBLE);
                status.setVisibility(View.VISIBLE);
                status.setText(R.string.selling);
                buyer.setVisibility(View.INVISIBLE);
            }
            else if (itemStatus.equals("buying")) {
                sell.setVisibility(View.VISIBLE);
                reject.setVisibility(View.VISIBLE);
                status.setVisibility(View.INVISIBLE);
                buyer.setVisibility(View.INVISIBLE);
            }
            else if (itemStatus.equals("soldout")) {
                sell.setVisibility(View.INVISIBLE);
                reject.setVisibility(View.INVISIBLE);
                status.setVisibility(View.VISIBLE);
                buyer.setVisibility(View.INVISIBLE);
                status.setText(R.string.sold_out);
            }
        }
        else {
            sell.setVisibility(View.INVISIBLE);
            reject.setVisibility(View.INVISIBLE);
            status.setVisibility(View.INVISIBLE);
            buyer.setVisibility(View.VISIBLE);
        }
    }

    public void Buyer(View view) {
        Object itemID = null;
        try {
            itemID = J.get("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*postUrl = "http://merry.ee.ncku.edu.tw:10000/setStuffBuying/";
        postBody = "{\"id\":" + itemID +"}";
        try {
            postRequest(postUrl, postBody);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Intent intent = new Intent(DetailActivity.this, ChatActivity.class);
        intent.putExtra("item", Item);
        intent.putExtra("buyer", UserID);
        startActivity(intent);
    }

    public void soldOut(View view) {
        Object itemID = null;
        try {
            itemID = J.get("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postUrl = "http://merry.ee.ncku.edu.tw:10000/setStuffSoldout/";
        postBody = "{\"id\":" + itemID +"}";
        try {
            postRequest(postUrl, postBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Reject(View view) {
        Object itemID = null;
        try {
            itemID = J.get("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postUrl = "http://merry.ee.ncku.edu.tw:10000/setStuffReject/";
        postBody = "{\"id\":" + itemID +"}";
        try {
            postRequest(postUrl, postBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void changeText() {
        Object itemName = "";
        Object itemPrice = "";
        Object itemDescription = "";
        JSONObject itemPhoto;
        Object itemPhoto1 = "";
        Object itemPhoto2 = "";
        try {
            itemName = J.get("name");
            itemPrice = J.get("price");
            itemDescription = J.get("description");
            itemPhoto = J.getJSONObject("img_url");
            Log.d("Tag", itemPhoto.toString());
            itemPhoto1 = itemPhoto.get("1");
            itemPhoto2 = itemPhoto.get("2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        nameText = findViewById(R.id.item_editText);
        priceText = findViewById(R.id.price_editText);
        descriptionText = findViewById(R.id.description_editText);
        image1 = findViewById(R.id.photo1_imageView);
        image2 = findViewById(R.id.photo2_imageView);

        nameText.setText(itemName.toString());
        priceText.setText(itemPrice.toString());
        descriptionText.setText(itemDescription.toString());

        Picasso.get().load(itemPhoto1.toString()).into(image1);
        Picasso.get().load(itemPhoto2.toString()).into(image2);
    }

}
